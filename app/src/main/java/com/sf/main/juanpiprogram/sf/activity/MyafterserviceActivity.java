package com.sf.main.juanpiprogram.sf.activity;

import android.app.Activity;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.sf.main.juanpiprogram.R;
import com.sf.main.juanpiprogram.sf.utils.SwipeBackActivity;

public class MyafterserviceActivity extends SwipeBackActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myafterservice);
    }

    //返回关闭界面
    public void toFinish(View v) {
        ActivityCompat.finishAfterTransition(this);
        overridePendingTransition(0, R.anim.base_slide_right_out);
    }
}
