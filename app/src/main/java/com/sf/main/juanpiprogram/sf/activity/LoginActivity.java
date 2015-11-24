package com.sf.main.juanpiprogram.sf.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sf.main.juanpiprogram.R;
import com.sf.main.juanpiprogram.sf.fragment.AccountLoginFragment;
import com.sf.main.juanpiprogram.sf.fragment.PersonAfterLoginFragment;
import com.sf.main.juanpiprogram.sf.fragment.PersonBeforeLoginFragment;
import com.sf.main.juanpiprogram.sf.fragment.PhoneFastLoginFragment;
import com.sf.main.juanpiprogram.sf.fragment.SearchDateFragment;
import com.sf.main.juanpiprogram.sf.utils.SwipeBackActivity;

import org.w3c.dom.Text;

public class LoginActivity extends SwipeBackActivity implements View.OnClickListener {

    /**
     * 选择登录方式
     */
    private RelativeLayout accountLogin,phoneFastLogin;
    private TextView textRegister;
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
        textRegister = (TextView) findViewById(R.id.textRegister);
        textRegister.setOnClickListener(this);
        //登录方式
        LoginMethod();

    }

    /**
     * 登录方式
     */
    private void LoginMethod() {
        accountLogin = (RelativeLayout) findViewById(R.id.relativeLayout_account_login);
        phoneFastLogin = (RelativeLayout) findViewById(R.id.relativeLayout_phone_login);
      accountLogin.setBackgroundColor(Color.argb(220,198,198,198));
        fmanager = getSupportFragmentManager();
        ftransaction = fmanager.beginTransaction();

        accountLogin.setOnClickListener(this);
        phoneFastLogin.setOnClickListener(this);
        AccountLoginFragment accountFragment = new AccountLoginFragment();
        ftransaction.replace(R.id.relativeLayout_login_content, accountFragment);
        ftransaction.commit();
    }
    @Override
    public void onClick(View v) {
        fmanager = getSupportFragmentManager();
        ftransaction = fmanager.beginTransaction();

        switch (v.getId()) {
            case R.id.relativeLayout_account_login:
               accountLogin.setBackgroundColor(Color.argb(220,198,198,198));
               phoneFastLogin.setBackgroundColor(Color.argb(255, 255, 255, 255));

                AccountLoginFragment accountFragment = new AccountLoginFragment();
                ftransaction.replace(R.id.relativeLayout_login_content, accountFragment);

                break;
            case R.id.relativeLayout_phone_login:
               accountLogin.setBackgroundColor(Color.argb(255,255,255,255));
               phoneFastLogin.setBackgroundColor(Color.argb(220, 198, 198, 198));

                PhoneFastLoginFragment phoneFragment = new PhoneFastLoginFragment();
                ftransaction.replace(R.id.relativeLayout_login_content, phoneFragment);
                break;
            case R.id.textRegister:
                Intent toreg = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(toreg);
                break;
        }
        ftransaction.commit();
    }

    public void toFinish(View view) {
        finish();
    }

}
