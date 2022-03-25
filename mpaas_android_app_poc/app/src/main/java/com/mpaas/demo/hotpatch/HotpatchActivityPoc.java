package com.mpaas.demo.hotpatch;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.mpaas.demo.R;
import com.mpaas.demo.base.BaseActivity;
import com.mpaas.hotpatch.adapter.api.MPHotpatch;

public class HotpatchActivityPoc extends BaseActivity {
    private Button mHotpatchNeedHotpatchBtn;
    private Button mHotpatchTriggerHotpatchBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotpatch_poc);
        findView();
        initView();
    }

    private void findView() {
        mHotpatchNeedHotpatchBtn = (Button) findViewById(R.id.hotpatch_need_hotpatch_btn);
        mHotpatchTriggerHotpatchBtn = (Button) findViewById(R.id.hotpatch_trigger_hotpatch_btn);
    }

    private void initView() {
        mHotpatchNeedHotpatchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hotpatch();
            }
        });
        mHotpatchTriggerHotpatchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initHotPatch();
            }
        });


    }

    private void hotpatch(){
        // 以下代码为需要被热修复的部分
        String content = getString(R.string.hot_fix_abnormal);
        //String content =  getString(R.string.hot_fix_abnormal_success);
        Toast.makeText(HotpatchActivityPoc.this, content, Toast.LENGTH_SHORT).show();
    }

    private void initHotPatch(){
        MPHotpatch.init();
    }
}
