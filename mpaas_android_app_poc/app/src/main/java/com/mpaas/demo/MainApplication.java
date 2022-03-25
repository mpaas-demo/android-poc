package com.mpaas.demo;

import android.content.Context;

import androidx.multidex.MultiDex;

import com.alipay.mobile.framework.quinoxless.QuinoxlessApplicationLike;
import com.alipay.mobile.nebula.provider.H5AppCenterPresetProvider;
import com.alipay.mobile.nebula.util.H5Utils;
import com.mpaas.mas.adapter.api.MPLogger;
import com.mpaas.mps.adapter.api.MPPush;
import com.mpaas.mss.adapter.api.MPSync;
import com.mpaas.nebula.adapter.api.MPNebula;
import com.mpaas.nebula.adapter.api.MPNebulaOfflineInfo;
import com.mpaas.tinyappcommonres.TinyAppCenterPresetProvider;

/**
 * Create by ciwei
 * On 2020/12/24
 */
public class MainApplication extends QuinoxlessApplicationLike {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //分包
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MPSync.setup(getApplication());

    }


    @Override
    public void onMPaaSFrameworkInitFinished() {
        // 可以在用户登录成功后设置 MPLogger.setUserId("MpaasPoc");

        //初始化小程序公共资源包
        H5Utils.setProvider(H5AppCenterPresetProvider.class.getName(), new TinyAppCenterPresetProvider());
        loadOfflineNebula();
        //如已在 Application 中调用 mPaaS 初始化方法，则在 QuinoxlessFramework.init() 方法之后调用
        MPPush.init(this);
        //自动化日志用于记录页面切换事件。您可以借此分析应用各功能或运营页面的 PV 和 UV 等数据。
        MPLogger.enableAutoLog();
        //配置验签
        // 设置小程序离线包验签公钥，如需关闭验签，可在 custom_config.json 中，将 h5_shouldverifyapp 的 value 设置成 NO
        MPNebula.enableAppVerification("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAzRc51CZkfOX8bodSs8fh" +
                        "h8rBsODWvEYrzCMEa61iAi6YXG5yO3sm1q7A/to9wX8EKfXqZiJaUmtWFtPfIVBA" +
                        "CQ2+hsqxbkCI/MtjfM/EnkmVCXo1bo/gh2Nloc5KY0prupGjymXxgUtjBDiAMK4/" +
                        "xtpTz14rxKAUJDBHokoFmSOSao5sfrOAaQJ7EBvvD4X8QnIkoUqSTLNjK2uuoTdh" +
                        "6KE6l1zsgfEIanlSOpWPZNCBh0DkDl//zV+2iHesXEZtisWxaVmcQtGSwWM0pjuT" +
                        "uGdIyUd1AIkXX1vv6WfyfIt1nhABg+BdnJWvDw1//XnlStId05nyMIeFICDIwMJH" +
                        "IQIDAQAB");

    }

    private void loadOfflineNebula() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 此方法为阻塞调用，请不要在主线程上调用内置离线包方法。如果内置多个amr包，要确保文件已存在，如不存在，会造成其他内置离线包失败。
                // 此方法仅能调用一次，多次调用仅第一次调用有效。
                MPNebula.loadOfflineNebula("h5_json.json", new MPNebulaOfflineInfo("70000000_0.0.0.2.amr", "70000000", "0.0.0.2"));

            }
        }).start();
    }
}
