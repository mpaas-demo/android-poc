package com.mpaas.upgrade;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.mobile.android.security.upgrade.download.normal.UpgradeForceExitCallback;
import com.alipay.mobile.android.security.upgrade.util.UpdateUtils;
import com.alipay.mobile.android.security.upgrade.util.UpgradeConstants;
import com.alipay.mobile.framework.MicroApplicationContext;
import com.alipay.mobile.framework.app.ui.BaseActivity;
import com.alipay.mobileappcommon.biz.rpc.client.upgrade.model.ClientUpgradeRes;
import com.mpaas.mpaasadapter.api.upgrade.MPUpgrade;

public class UpgradeActivity extends BaseActivity {

    private TextView mCurVersionTv;
    private Button mFastCheckBtn;
    private Button mFastCheckHasBtn;
    private Button mFastCheckGetBtn;
    private Button mDefaultIntervalUpgradeBtn;
    private Button mCustomIntervalUpgradeBtn;

    private ProgressDialog mCheckUpgradeProgressDialog;
    private Dialog mDownloadDialog;
    private ProgressDialog mDownloadProgressDialog;
    private Dialog mInstallDialog;

    private MPUpgrade mMPUpgrade = new MPUpgrade();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upgrade);
        setTitle(getString(R.string.upgrade_release));
        // 设置升级服务的回调函数
        mMPUpgrade.setUpgradeCallback(new UpgradeCallBack(this));
        findView();
        initView();
        fixHuawei10();

        // 内部测试使用，开发者无需关注
//        test();
    }

    private void findView() {
        mCurVersionTv = (TextView) findViewById(R.id.upgrade_cur_version_tv);
        mFastCheckBtn = (Button) findViewById(R.id.upgrade_fast_check_btn);
        mFastCheckHasBtn = (Button) findViewById(R.id.upgrade_fast_check_has_btn);
        mFastCheckGetBtn = (Button) findViewById(R.id.upgrade_fast_check_get_btn);
        mDefaultIntervalUpgradeBtn = (Button) findViewById(R.id.upgrade_default_interval_btn);
        mCustomIntervalUpgradeBtn = (Button) findViewById(R.id.upgrade_custom_interval_btn);
    }

    private void initView() {
        mCheckUpgradeProgressDialog = new ProgressDialog(this);
        mCheckUpgradeProgressDialog.setMessage(getString(R.string.checking_upgrade));
        mCheckUpgradeProgressDialog.setCanceledOnTouchOutside(false);
        mDownloadProgressDialog = new ProgressDialog(this);
        mDownloadProgressDialog.setMessage(getString(R.string.downloading));
        mDownloadProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mDownloadProgressDialog.setMax(100);
        mDownloadProgressDialog.setIndeterminate(false);
        mDownloadProgressDialog.setCancelable(false);

        try {
            mCurVersionTv.setText(getString(R.string.cur_ver_is) + getPackageManager().getPackageInfo(getPackageName(), 0).versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        mFastCheckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMPUpgrade.fastCheckNewVersion(UpgradeActivity.this, getResources().getDrawable(R.drawable.appicon));
            }
        });
        mFastCheckHasBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final int result = mMPUpgrade.fastCheckHasNewVersion();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (result == UpgradeConstants.HAS_NEW_VERSION) {
                                    Toast.makeText(UpgradeActivity.this, R.string.havenew_version, Toast.LENGTH_SHORT).show();
                                } else if (result == UpgradeConstants.HAS_NO_NEW_VERSION) {
                                    Toast.makeText(UpgradeActivity.this, R.string.has_no_new_version, Toast.LENGTH_SHORT).show();
                                } else if (result == UpgradeConstants.HAS_SOME_ERROR) {
                                    Toast.makeText(UpgradeActivity.this, R.string.detection_newversion_error, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }).start();
            }
        });
        mFastCheckGetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final ClientUpgradeRes res = mMPUpgrade.fastGetClientUpgradeRes();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (null == res) {
                                    Toast.makeText(UpgradeActivity.this, R.string.error_getupgradleresult_null, Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(UpgradeActivity.this,
                                            R.string.upgradle_information + res.guideMemo
                                                    + R.string.upgradle_model + res.resultStatus
                                                    + R.string.newversion + res.newestVersion
                                                    + R.string.download_address + res.downloadURL, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }).start();
            }
        });
        mDefaultIntervalUpgradeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 更新提示间隔时间设置成-1时，采用默认的3天作为更新提示间隔时间
                // 开发者可以再此处设置任意更新提示间隔时间，该时间只对单次升级模式生效
                mMPUpgrade.setIntervalTime(-1);
                mMPUpgrade.checkNewVersion(UpgradeActivity.this);
            }
        });
        mCustomIntervalUpgradeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 更新提示间隔时间设置成60*1000时（1分钟），表示两次升级弹框超过一分钟才会弹出
                // 开发者可以再此处设置任意更新提示间隔时间，该时间只对单次升级模式生效
                mMPUpgrade.setIntervalTime(60 * 1000);
                mMPUpgrade.checkNewVersion(UpgradeActivity.this);
            }
        });
    }

    private void fixHuawei10() {
        // 在部分 rom 上，强制更新安装时，会因为进程被杀掉导致包解析失败，加入下面的回调，第一个方法返回false，则不再进行杀进程。
        mMPUpgrade.setForceExitCallback(new UpgradeForceExitCallback() {
            @Override
            public boolean needForceExit(boolean b, MicroApplicationContext microApplicationContext) {
                // 返回false不强制退出，避免包解析失败问题。返回true的话，会走到第二个方法，开发者可以在下面方法里自行杀进程。
                return false;
            }

            @Override
            public void doForceExit(boolean b, MicroApplicationContext microApplicationContext) {

            }
        });
    }

    /**
     * 显示版本检测的ProgressDialog
     */
    public void showCheckUpgradeProgressDialog() {
        mCheckUpgradeProgressDialog.show();
    }

    /**
     * 取消版本检测的ProgressDialog
     */
    public void cancelCheckUpgradeProgressDialog() {
        mCheckUpgradeProgressDialog.cancel();
    }

    /**
     * 显示下载dialog
     *
     * @param clientUpgradeRes 更新结果
     */
    public void showDownloadDialog(final ClientUpgradeRes clientUpgradeRes) {
        String title = getString(R.string.upgrade);
        // 默认不是强制升级
        boolean isForce = false;
        // 根据不同的升级策略，显示不同的dialog标题。
        // 强制升级策略下无法取消dialog。
        switch (clientUpgradeRes.resultStatus) {
            // 状态值为202，单次提醒升级
            case 202:
                title = getString(R.string.single_upgrade);
                break;
            // 状态值为204，多次提醒升级
            case 204:
                title = getString(R.string.multi_upgrade);
                break;
            // 状态值为203或206，强制升级
            case 203:
            case 206:
                title = getString(R.string.force_upgrade);
                isForce = true;
                break;
            default:
                title = getString(R.string.unknown_upgrade_state) + clientUpgradeRes.resultStatus;
                break;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(clientUpgradeRes.guideMemo)
                .setCancelable(!isForce)
                .setPositiveButton(R.string.upgrade, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mMPUpgrade.update(clientUpgradeRes, new DownloadCallback(UpgradeActivity.this));
                    }
                });
        if (!isForce) {
            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
        }
        mDownloadDialog = builder.create();
        mDownloadDialog.show();
    }

    /**
     * 显示下载ProgressDialog
     */
    public void showDownloadProgressDialog() {
        mDownloadProgressDialog.show();
        mDownloadProgressDialog.setProgress(0);
    }

    /**
     * 更新下载ProgressDialog的百分比
     *
     * @param percent 百分比
     */
    public void updateDownloadProgressDialog(int percent) {
        mDownloadProgressDialog.setProgress(percent);
    }

    /**
     * 取消下载ProgressDialog
     */
    public void cancelDownloadProgressDialog() {
        mDownloadProgressDialog.setProgress(0);
        mDownloadProgressDialog.cancel();
    }

    /**
     * 显示安装dialog
     *
     * @param apkPath apk路径
     * @param isForce 是否强制安装
     */
    public void showInstallDialog(final String apkPath, boolean isForce) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(R.string.install_new)
                .setMessage(getString(R.string.apk_path) + apkPath)
                .setCancelable(!isForce)
                .setPositiveButton(R.string.install, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        UpdateUtils.installApk(apkPath);
                    }
                });
        if (!isForce) {
            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
        }
        mInstallDialog = builder.create();
        mInstallDialog.show();
    }

    // 内部测试使用，开发者无需关注
    private void test() {
        try {
            Class healthActivity = Class.forName("com.mpaas.diagnose.ui.HealthBizSelectActivity");
            Intent intent = new Intent(this, healthActivity);
            startActivity(intent);
        } catch (Exception e) {
        }
    }


}