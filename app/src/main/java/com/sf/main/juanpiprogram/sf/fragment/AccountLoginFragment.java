package com.sf.main.juanpiprogram.sf.fragment;


import android.drm.DrmStore;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sf.main.juanpiprogram.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountLoginFragment extends Fragment implements View.OnClickListener {


    private TextView textView_forgetps;
    private RelativeLayout relativeLayout_loginin_btn;
    private ImageView imageqq,imageweixin,imageweibo,login_clear_account1,login_clear_account2;
    private EditText editText_username_login,editText_pass_login;
    public AccountLoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account_login, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textView_forgetps = (TextView) view.findViewById(R.id.textView_forget_password);
        textView_forgetps.setOnClickListener(this);
        relativeLayout_loginin_btn = (RelativeLayout) view.findViewById(R.id.relativeLayout_loginin_btn);
        relativeLayout_loginin_btn.setOnClickListener(this);
        //清除账户信息
        login_clear_account1 = (ImageView) view.findViewById(R.id.login_clear_account1);
        login_clear_account1.setOnClickListener(this);
        login_clear_account2 = (ImageView) view.findViewById(R.id.login_clear_account2);
        login_clear_account2.setOnClickListener(this);

        //文本
        editText_username_login = (EditText) view.findViewById(R.id.editText_username_login);

        editText_pass_login = (EditText) view.findViewById(R.id.editText_pass_login);

        textchangelistener1();
        textchangelistener2();
        //第三方登录初始化
        thirdLogin(view);
    }

    /**
     * 文字改变监听user
     */
    private void textchangelistener1() {
        login_clear_account1.setVisibility(View.GONE);
        editText_username_login.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    login_clear_account1.setVisibility(View.GONE);
                } else {
                    login_clear_account1.setVisibility(View.VISIBLE);
                }
            }
        });

    }
    /**
     * 文字改变监听pass
     */
    private void textchangelistener2() {

        login_clear_account2.setVisibility(View.GONE);
        editText_pass_login.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    login_clear_account2.setVisibility(View.GONE);
                } else {
                    login_clear_account2.setVisibility(View.VISIBLE);
                }
            }
        });
    }


    /**
     * 第三方登录
     */
    private void thirdLogin(View view) {
        imageqq = (ImageView) view.findViewById(R.id.imageView_qq);
        imageweixin = (ImageView) view.findViewById(R.id.imageView_weixin);
        imageweibo = (ImageView) view.findViewById(R.id.imageView_weibo);
        imageqq.setOnClickListener(this);
        imageweibo.setOnClickListener(this);
        imageweixin.setOnClickListener(this);
    }

    //本页的监听
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //登录按钮
            case R.id.relativeLayout_loginin_btn:
                break;
            //忘记密码
            case R.id.textView_forget_password:
                break;
            //qq
            case R.id.imageView_qq:
                break;
            //weixin
            case R.id.imageView_weixin:
                break;
            //weibo
            case R.id.imageView_weibo:
                break;
            //清除账户信息
            case R.id.login_clear_account1:
                clearaccountinfoone();
                break;
            case R.id.login_clear_account2:
                clearaccountinfotwo();
                break;
        }
    }

    /**
     * 清除账户信息
     */
    private void clearaccountinfoone() {

        //点击按钮删除文本
        editText_username_login.setText("");
        login_clear_account1.setVisibility(View.GONE);

    }
    private void clearaccountinfotwo() {
        //点击按钮删除文本
        editText_pass_login.setText("");
        login_clear_account2.setVisibility(View.GONE);
    }
}
