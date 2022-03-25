package com.mpaas.tinyapp;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.alipay.android.phone.scancode.export.ScanRequest;
import com.alipay.android.phone.scancode.export.adapter.MPScan;
import com.alipay.mobile.framework.app.ui.BaseActivity;
import com.mpaas.mas.adapter.api.MPLogger;
import com.mpaas.mas.adapter.api.MPTracker;
import com.mpaas.nebula.adapter.api.MPNebula;
import com.mpaas.nebula.adapter.api.MPTinyHelper;

public class TinyAppSettingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiny_app_setting);
        MPTracker.onActivityCreate(this);
        findViewById(R.id.tv_phone_white).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //tinyapp_debug_setting
                Toast.makeText(TinyAppSettingActivity.this ,TinyAppSettingActivity.this.getString(R.string.tinyapp_debug_setting),Toast.LENGTH_LONG).show();
                //没有配置虚列域名、白名单也会出现50002
                phoneWhite();
            }
        });

        findViewById(R.id.tv_scan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scan();
            }
        });

        findViewById(R.id.tv_preview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scan();
            }
        });
        findViewById(R.id.tv_startapp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MPNebula.startApp("2021071220210714");
            }
        });
    }

    //真机调试
    private void phoneWhite(){
        //设置虚拟域名
        MPTinyHelper tinyHelper = MPTinyHelper.getInstance();
        tinyHelper.setTinyAppVHost("testj.com");
        //设置白名单
        MPLogger.setUserId("MpaasPoc");
    }


    private void scan(){
        ScanRequest scanRequest = new ScanRequest();
        scanRequest.setNotSupportAlbum(false);//是否显示从相册中选择
        scanRequest.setTranslucentStatusBar(true);//状态栏半透明
        scanRequest.setScanType(ScanRequest.ScanType.QRCODE);
        scanRequest.setRecognizeType(
                ScanRequest.RecognizeType.BAR_CODE,
                ScanRequest.RecognizeType.QR_CODE,
                ScanRequest.RecognizeType.DM_CODE,
                ScanRequest.RecognizeType.PDF417_Code
        );

        MPScan.startMPaasScanActivity(TinyAppSettingActivity.this, scanRequest, new com.alipay.android.phone.scancode.export.ScanCallback() {
            @Override
            public void onScanResult(final boolean isProcessed, final Intent result) {
                if (!isProcessed) {
                    // 扫码界面点击物理返回键或左上角返回键
                    //MainActivity.this.finish();
                    return;
                }

                // 注意：本回调是在子线程中执行
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (result == null || result.getData() == null) {
                            return;
                        }

                        // 扫码成功
                        String url = result.getData().toString();
                        Uri myUri = Uri.parse(url);
                        MPTinyHelper.getInstance().launchIdeQRCode(myUri, new Bundle());

                    }
                });
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        MPTracker.onActivityWindowFocusChanged(this, hasFocus);
    }
    @Override
    protected void onResume() {
        super.onResume();
        MPTracker.onActivityResume(this);
    }
    @Override
    protected void onPause() {
        super.onPause();
        MPTracker.onActivityPause(this);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        MPTracker.onActivityDestroy(this);
    }
}