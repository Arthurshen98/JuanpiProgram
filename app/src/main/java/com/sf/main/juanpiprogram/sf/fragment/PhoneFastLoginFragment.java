package com.sf.main.juanpiprogram.sf.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sf.main.juanpiprogram.R;
import com.sf.main.juanpiprogram.sf.activity.LoginActivity;
import com.sf.main.juanpiprogram.sf.activity.PersonCenterActivity;
import com.sf.main.juanpiprogram.sf.entities.PhoneFastLogin;
import com.sf.main.juanpiprogram.sf.utils.BaseApplication;
import com.sf.main.juanpiprogram.sf.utils.MyProgressBar;
import com.sf.main.juanpiprogram.sf.utils.UserManager;

import cn.bmob.v3.listener.SaveListener;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * A simple {@link Fragment} subclass.
 */
public class PhoneFastLoginFragment extends Fragment implements View.OnClickListener {

    private ImageView imageView_checkNum_clear,imageView_phone_password_clear;
    private EditText editText_input_phoneNum,editText_phone_checkNum;
    //登录+获取验证码框
    private RelativeLayout relative_phonefast_login_btn,relative_getCheckedNum;
    private TextView text_onclick_getcheckNum,text_onclick_reget,text_onclick_countdown;


    public PhoneFastLoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_phone_fast_login, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //布局组件初始化+set监听
        init(view);

        //文本改变监听
        edittextChangeLinstener();
        //验证码文本改变监听
        checkChangeLinstener();
    }


   //验证码文本改变监听
    private void checkChangeLinstener() {
        imageView_phone_password_clear.setVisibility(View.GONE);
        editText_phone_checkNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    imageView_phone_password_clear.setVisibility(View.GONE);
                } else {
                    imageView_phone_password_clear.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    /**
     * 文本改变监听
     */
    private void edittextChangeLinstener() {
        imageView_checkNum_clear.setVisibility(View.GONE);
        editText_input_phoneNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    imageView_checkNum_clear.setVisibility(View.GONE);
                } else {
                    imageView_checkNum_clear.setVisibility(View.VISIBLE);
                }
            }
        });


    }

    /**
     * 布局组件初始化
     */
    private void init(View view) {
        imageView_checkNum_clear = (ImageView) view.findViewById(R.id.imageView_checkNum_clear);
        imageView_phone_password_clear = (ImageView) view.findViewById(R.id.imageView_phone_password_clear);

        editText_input_phoneNum = (EditText) view.findViewById(R.id.editText_input_phoneNum);
        editText_phone_checkNum = (EditText) view.findViewById(R.id.editText_phone_checkNum);

        relative_phonefast_login_btn = (RelativeLayout) view.findViewById(R.id.relative_phonefast_login_btn);
        relative_getCheckedNum = (RelativeLayout) view.findViewById(R.id.relative_getCheckedNum);

        text_onclick_getcheckNum = (TextView) view.findViewById(R.id.text_onclick_getcheckNum);
        text_onclick_reget = (TextView) view.findViewById(R.id.text_onclick_reget);
        text_onclick_countdown = (TextView) view.findViewById(R.id.text_onclick_countdown);

        imageView_checkNum_clear.setOnClickListener(this);
        imageView_phone_password_clear.setOnClickListener(this);
        relative_phonefast_login_btn.setOnClickListener(this);
        relative_getCheckedNum.setOnClickListener(this);
        text_onclick_getcheckNum.setOnClickListener(this);
        text_onclick_reget.setOnClickListener(this);
        text_onclick_countdown.setOnClickListener(this);

        //隐藏两个控件
        text_onclick_countdown.setVisibility(View.GONE);
        text_onclick_reget.setVisibility(View.GONE);

        //启动SMS
        SMSSDK.initSDK(BaseApplication.getContext(), "c21ce1d85935", "4824fa95b122eba98158d4cfb02588ab");
        SMSSDK.registerEventHandler(eh);


    }

    //点击监听
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageView_checkNum_clear:
                //清除电话号码
                clearPhoneNum();
                break;
            case R.id.imageView_phone_password_clear:
               //清除验证码
                clearCheckNum();
                break;
            case R.id.relative_phonefast_login_btn:
                //登录的时候验证验证码
                isTureCheckNum();
                //还应该判断手机是否注册过，如果没有再将手机号码存储
                testIsUsed();
                //将手机号码存到云端
                forNetStorage();
                break;

            case R.id.relative_getCheckedNum:
                break;

            case R.id.text_onclick_getcheckNum:

                if (!editText_input_phoneNum.getText().toString().equals("")&& editText_input_phoneNum.getText().toString().length() == 11) {
                    text_onclick_getcheckNum.setVisibility(View.GONE);
                    text_onclick_countdown.setVisibility(View.VISIBLE);
                    //给用户发送短信
                    toSendSMS();
                    //执行倒计时
                    countdown();
                }else{
                    Toast.makeText(BaseApplication.getContext(), "手机号不能为空", Toast.LENGTH_SHORT).show();
                    editText_input_phoneNum.requestFocus();
                }


                break;
            case R.id.text_onclick_reget:
                if (!editText_input_phoneNum.getText().toString().equals("") && editText_input_phoneNum.getText().toString().length() == 11) {
                    //给用户发送短信
                    toSendSMS();
                    //重新发送
                    countdown();
                    text_onclick_reget.setVisibility(View.GONE);
                    text_onclick_countdown.setVisibility(View.VISIBLE);
                }else {
                    Toast.makeText(BaseApplication.getContext(), "手机号不能为空", Toast.LENGTH_SHORT).show();
                    editText_input_phoneNum.requestFocus();
                }


                break;
            case R.id.text_onclick_countdown:
                break;
        }
    }

    /**
     * 还应该判断手机是否注册过，如果没有再将手机号码存储
     */
    private void testIsUsed() {

    }

    /**
     * 将手机号码存到云端
     */
    private void forNetStorage() {
        //progressBar
        MyProgressBar.show(getActivity(), "正在登录……",true,null);

        final PhoneFastLogin phoneNum = new PhoneFastLogin();
        phoneNum.setPhoneNum(editText_input_phoneNum.getText().toString().trim());
        phoneNum.save(BaseApplication.getContext(), new SaveListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(BaseApplication.getContext(), "登录成功！", Toast.LENGTH_SHORT).show();

                //存入共享参数
                UserManager userManager = new UserManager(BaseApplication.getContext());
                userManager.saveSharePrefrence(null, editText_input_phoneNum.getText().toString().trim(), true);

                Intent fastIntent = new Intent(BaseApplication.getContext(), PersonCenterActivity.class);
                fastIntent.putExtra("userName", editText_input_phoneNum.getText().toString().trim());
                startActivity(fastIntent);
            }

            @Override
            public void onFailure(int i, String s) {
                MyProgressBar myProgressBar = null;
                myProgressBar.dismissDialog();
                Toast.makeText(BaseApplication.getContext(), "登录失败！", Toast.LENGTH_SHORT).show();

            }
        });
    }

    /**
     * 给用户发送短信
     */
    private String phoneNum = null;
    private void toSendSMS() {
        /*//打开注册页面
        RegisterPage registerPage = new RegisterPage();
        registerPage.setRegisterCallback(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
       // 解析注册结果
                if (result == SMSSDK.RESULT_COMPLETE) {
                    @SuppressWarnings("unchecked")
                    HashMap<String,Object> phoneMap = (HashMap<String, Object>) data;
                    String country = (String) phoneMap.get("country");
                    String phone = (String) phoneMap.get("phone");

            // 提交用户信息
                 //   registerUser(country, phone);
                }
            }
        });
        registerPage.show(BaseApplication.getContext());*/


        //获取到用户手机号码
        phoneNum = editText_input_phoneNum.getText().toString();
        if (!TextUtils.isEmpty(phoneNum)) {
                SMSSDK.getVerificationCode("+86", phoneNum);
                editText_phone_checkNum.requestFocus();
        }
        //((Button) view).setText("重新发送");
    }

    private  void isTureCheckNum(){
        //获取到用户手机号码
        phoneNum = editText_input_phoneNum.getText().toString();
        //验证的验证码
        String checkNum = editText_phone_checkNum.getText().toString();
        if (!TextUtils.isEmpty(phoneNum) && !TextUtils.isEmpty(checkNum)) {
            SMSSDK.submitVerificationCode("+86", phoneNum, checkNum);
            editText_phone_checkNum.requestFocus();
        } else {
            Toast.makeText(BaseApplication.getContext(), "验证码不能为空", Toast.LENGTH_SHORT).show();
            editText_phone_checkNum.requestFocus();
        }
    }

    /**
     * 判断验证码是否正确
     */
    private EventHandler eh=new EventHandler(){
        @Override
        public void afterEvent(final int event, final int result, final Object data) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if (result == SMSSDK.RESULT_COMPLETE) {
                        //回调完成
                        if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                            //提交验证码成功
                            Toast.makeText(BaseApplication.getContext(), "验证成功", Toast.LENGTH_SHORT).show();
                        } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                            //获取验证码成功
                            Toast.makeText(BaseApplication.getContext(), "验证码已发送", Toast.LENGTH_SHORT).show();
                        } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                            //返回支持发送验证码的国家列表
                        }
                    } else {
                        ((Throwable) data).printStackTrace();
                        //回调完成
                        if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                            //提交验证码成功
                            Toast.makeText(BaseApplication.getContext(), "验证失败", Toast.LENGTH_SHORT).show();
                        } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                            //获取验证码成功
                            Toast.makeText(getActivity(), "发送失败", Toast.LENGTH_SHORT).show();
                        } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                            //返回支持发送验证码的国家列表
                        }
                    }
                }
            });
        }
    };


    /**
     * 清除电话号码
     */
    private void clearPhoneNum() {
        //点击按钮删除文本
        editText_input_phoneNum.setText("");
        imageView_checkNum_clear.setVisibility(View.GONE);
    }
    /**
     * 清除验证码
     */
    private void clearCheckNum() {
//点击按钮删除文本
        editText_phone_checkNum.setText("");
        imageView_phone_password_clear.setVisibility(View.GONE);
    }

    /**
     * 实现倒计时
     */
    //验证码倒计时
    private Handler handler=new Handler(){
        //处理消息
        public void handleMessage(Message msg) {
           int count = msg.arg1;
            if (count > 1) {
                text_onclick_countdown.setText(count + "s");
            //    relative_getCheckedNum.setSelected(true);
            }else{
                //倒计时之后重新发送
                text_onclick_countdown.setVisibility(View.GONE);
                text_onclick_reget.setVisibility(View.VISIBLE);
             //   relative_getCheckedNum.setSelected(false);
                return;
            }
        };
    };
//倒计时
   private  int count = 1;
    public  void  countdown(){
        count = 61;
        new Thread(new Runnable() {

            @Override
            public void run() {

                while (true) {
                    if(count > 0){
                        count--;
                        Message message = Message.obtain();
                        message.arg1 = count;
                        handler.sendMessage(message);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();


    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterAllEventHandler();
    }
}
