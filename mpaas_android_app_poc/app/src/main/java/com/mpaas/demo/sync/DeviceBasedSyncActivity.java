package com.mpaas.demo.sync;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.mpaas.demo.R;
import com.mpaas.mss.adapter.api.MPSync;
import com.ut.device.UTDevice;

/**
 * Created by larry.cl.
 */
public class DeviceBasedSyncActivity extends BaseSyncActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void fetchSyncData(final String data) {
        DeviceBasedSyncActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(DeviceBasedSyncActivity.this, DeviceBasedSyncActivity.this.getString(R.string.sync_deviceData) + data
                        , Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void bindViews() {
        super.bindViews();
        mTvDeviceId.setVisibility(View.VISIBLE);
        mDeviceIdTips.setVisibility(View.VISIBLE);
    }

    @Override
    public void init() {
        super.init();
        //注册接收业务数据的 callback。在获取到同步推送的数据后，回调 syncCallback。
        String utdid = UTDevice.getUtdid(this);
        mTvDeviceId.setText(getString(R.string.sync_deviceid) + utdid);
        LoggerFactory.getTraceLogger().debug(TAG, "设备id是 ： " + utdid);
        //默认基于设备id进行注册,biz是deviceSync
        MPSync.registerBiz("syncDevice", mSyncCallback);
        //建立与服务器的网络连接。
        MPSync.appToForeground();
    }

    @Override
    public void onClick(View v) {

    }
}
