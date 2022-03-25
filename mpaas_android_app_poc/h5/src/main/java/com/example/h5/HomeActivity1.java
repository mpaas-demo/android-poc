package com.example.h5;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.Nullable;
import com.alipay.mobile.framework.BuildConfig;
import com.alipay.mobile.framework.app.ui.BaseActivity;
import com.alipay.mobile.h5container.api.H5Plugin;
import com.alipay.mobile.nebula.provider.H5PublicRsaProvider;
import com.alipay.mobile.nebula.util.H5Utils;

import com.mpaas.H5.R;
import com.mpaas.nebula.adapter.api.MPNebula;
import com.mpaas.nebula.adapter.api.MPNebulaOfflineInfo;


public class HomeActivity1 extends BaseActivity {

    public static HomeActivity1 content;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        content = this;
        findViewById(R.id.btn_online).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MPNebula.startUrl("https://tech.antfin.com");
            }
        });

        findViewById(R.id.btn_enable_verify).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 设置离线包验签公钥
                H5Utils.setProvider(H5PublicRsaProvider.class.getName(), new H5RsaProviderImpl());
                Toast.makeText(HomeActivity1.this, getString(R.string.setting_shezhilixianbaogongyaochenggong), Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.btn_preset_offline).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 预置离线包，包括普通离线包和公共资源包

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        MPNebula.loadOfflineNebula("h5_json.json", new MPNebulaOfflineInfo("70000000_0.0.0.2.amr", "70000000", "0.0.0.2"));
                        Toast.makeText(HomeActivity1.this, getString(R.string.preset_offline_package_success), Toast.LENGTH_SHORT).show();
                    }
                });
//                new Thread(new PresetAmrPipeline()).start();
//                // 公共资源包返回 appid
//                H5Utils.setProvider(H5AppCenterPresetProvider.class.getName(), new H5AppCenterPresetProviderImpl());
//
//                Toast.makeText(HomeActivity.this, "预置离线包成功", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.btn_offline).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity1.this, NebulaAppActivity.class));
            }
        });

        findViewById(R.id.btn_plugin_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerJSAPI();
                Toast.makeText(HomeActivity1.this, getString(R.string.custom_jsapi_setting_tips), Toast.LENGTH_SHORT).show();
            }
        });

        //H5打开本地html预置页面
        findViewById(R.id.btn_open_html).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MPNebula.startUrl("file:///android_asset/input_file_load.html?");
            }
        });
    }


    public void registerJSAPI() {
        MPNebula.registerH5Plugin(
                MyJSApiPlugin.class.getName(),
                BuildConfig.BUNDLE_NAME,
                "page",
                new String[]{"myapi1", "myapi2", "getAppVersion", H5Plugin.CommonEvents.H5_PAGE_PHYSICAL_BACK}
        );
    }


//     内部测试使用，开发者无需关注/
//    private void test(){
//        try {
//            Class healthActivity = Class.forName("com.mpaas.diagnose.ui.HealthBizSelectActivity");
//            Intent intent = new Intent(this, healthActivity);
//            startActivity(intent);
//        } catch (Exception e) {
//        }
//    }


}
