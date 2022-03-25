package com.mpaas.demo.uicontrol;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.scan.scan.ScanActivity;
import com.example.share.share.ShareActivity1;
import com.mpaas.demo.R;
import com.mpaas.nebula.adapter.api.MPNebula;
import com.ut.device.UTDevice;

public class UIControlActivity extends AppCompatActivity {
    private Button btnH5;
    private Button btnGetid;
    private Button btnShare;
    private Button btnScan;
    private Button btnIDE;

    private boolean isEn = false;//是否是国际化的（英语）
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uicontrol);
        String type = getString(R.string.type);
        if(!TextUtils.isEmpty(type)){
            //0是中午
            if(TextUtils.equals("1",type)){
                isEn = true;
            }
        }
        initView();
    }

    private void initView() {
        btnH5 = (Button) findViewById(R.id.btn_H5);
        btnGetid = (Button) findViewById(R.id.btn_getid);
        btnShare = (Button) findViewById(R.id.btn_share);
        btnScan = (Button) findViewById(R.id.btn_scan);
        btnIDE = (Button) findViewById(R.id.btn_IDE);

        //H5
        btnH5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("name","我是name");
                if(isEn){
                    MPNebula.startApp("20220000",bundle);
                }else{
                    MPNebula.startApp("70000000",bundle);
                }

            }
        });


//获取唯一标识
        btnGetid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String utdid = UTDevice.getUtdid(UIControlActivity.this);
                Toast.makeText(UIControlActivity.this, utdid, Toast.LENGTH_SHORT).show();

            }
        });
        //分享跳转
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UIControlActivity.this, ShareActivity1.class));
            }
        });

        //扫码
        btnScan.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(UIControlActivity.this, ScanActivity.class));

            }
        });
    }
}