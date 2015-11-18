package com.sf.main.juanpiprogram.sf.activity;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.sf.main.juanpiprogram.R;

public class LoginActivity extends AppCompatActivity {

    /**
     *
     * fragment事物
     */
    private FragmentManager fmanager;
    private FragmentTransaction ftransaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //账号登录
    }

    //注册
    private void toRegister(View view) {
        Intent toreg = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(toreg);
    }
}
