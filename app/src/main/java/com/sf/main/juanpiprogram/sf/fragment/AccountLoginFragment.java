package com.sf.main.juanpiprogram.sf.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sf.main.juanpiprogram.MainActivity;
import com.sf.main.juanpiprogram.R;
import com.sf.main.juanpiprogram.sf.activity.LoginActivity;
import com.sf.main.juanpiprogram.sf.activity.PersonCenterActivity;
import com.sf.main.juanpiprogram.sf.entities.User;
import com.sf.main.juanpiprogram.sf.utils.BaseApplication;
import com.sf.main.juanpiprogram.sf.utils.MyProgressBar;
import com.sf.main.juanpiprogram.sf.utils.UserManager;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountLoginFragment extends Fragment implements View.OnClickListener {

    private MyProgressBar myProgressBar;
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

        //初始化bmob
        Bmob.initialize(BaseApplication.getContext(), "412b282b0b8896bf8791d8682843020d");

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
            //登录按钮,
            case R.id.relativeLayout_loginin_btn:
               //验证用户名和密码是否正确
                testUserAndPasswordIstrue();


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
     * 验证用户名和密码是否正确
     */
    private void testUserAndPasswordIstrue() {
        if (!editText_username_login.getText().toString().equals("") && !editText_pass_login.getText().toString().equals("")) {

            //loding
            myProgressBar.show(getActivity(), "正在登录……", true, null);

            final String userName = editText_username_login.getText().toString();
            String password = editText_pass_login.getText().toString();
            final BmobUser userLogin = new BmobUser();
            userLogin.setUsername(userName);
            userLogin.setPassword(password);
            userLogin.login(BaseApplication.getContext(), new SaveListener() {
                User user = null;

                @Override
                public void onSuccess() {
                    //缓存当前用户对象
                    user = BmobUser.getCurrentUser(BaseApplication.getContext(), User.class);
                    myProgressBar.dismissDialog();
                    Toast.makeText(BaseApplication.getContext(), "登录成功！", Toast.LENGTH_SHORT).show();

                    //存入共享参数
                    UserManager userManager = new UserManager(BaseApplication.getContext());
                    userManager.saveSharePrefrence(userName,null,true);

                    Intent intent = new Intent(BaseApplication.getContext(), PersonCenterActivity.class);
                    intent.putExtra("userName", userName);
                    intent.setAction("toUserName");
                    startActivity(intent);
                }

                @Override
                public void onFailure(int i, String s) {
                    myProgressBar.dismissDialog();
                    Toast.makeText(BaseApplication.getContext(), "登录失败！", Toast.LENGTH_SHORT).show();

                }
            });
        }else {
            Toast.makeText(BaseApplication.getContext(), "用户名或密码不能为空！", Toast.LENGTH_SHORT).show();

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
