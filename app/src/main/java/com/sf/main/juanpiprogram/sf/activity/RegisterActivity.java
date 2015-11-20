package com.sf.main.juanpiprogram.sf.activity;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sf.main.juanpiprogram.MainActivity;
import com.sf.main.juanpiprogram.R;
import com.sf.main.juanpiprogram.sf.entities.User;
import com.sf.main.juanpiprogram.sf.utils.BaseApplication;

import cn.bmob.v3.listener.SaveListener;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class RegisterActivity extends Activity implements View.OnClickListener {

    private ImageView imageView_checkNum_clear,imageView_phone_password_clear;
    private EditText editText_input_phoneNum,editText_phone_checkNum;
    //登录+获取验证码框
    private RelativeLayout relative_phonefast_login_btn,relative_getCheckedNum;
    private TextView text_onclick_getcheckNum,text_onclick_reget,text_onclick_countdown;
    private ImageView register_login_clear_account1,register_login_clear_account2;
    private EditText register_editText_username_login,register_editText_pass_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉actionBar
        setContentView(R.layout.activity_register);

        //布局组件初始化+set监听
        initcontent();

        //用于名文本改变监听
        textchangelistener1();
        //密码文本改变监听
        textchangelistener2();
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
     * 布局组件初始化+set监听
     */
    private void initcontent() {
        imageView_checkNum_clear = (ImageView) findViewById(R.id.register_imageView_checkNum_clear);
        imageView_phone_password_clear = (ImageView)findViewById(R.id.register_imageView_phone_password_clear);

        editText_input_phoneNum = (EditText) findViewById(R.id.register_editText_input_phoneNum);
        editText_phone_checkNum = (EditText) findViewById(R.id.register_editText_phone_checkNum);

        relative_phonefast_login_btn = (RelativeLayout) findViewById(R.id.register_relative_phonefast_login_btn);
        relative_getCheckedNum = (RelativeLayout) findViewById(R.id.register_relative_getCheckedNum);

        text_onclick_getcheckNum = (TextView) findViewById(R.id.register_text_onclick_getcheckNum);
        text_onclick_reget = (TextView) findViewById(R.id.register_text_onclick_reget);
        text_onclick_countdown = (TextView) findViewById(R.id.register_text_onclick_countdown);

        //清除账户信息
        register_login_clear_account1 = (ImageView) findViewById(R.id.register_login_clear_account1);
        register_login_clear_account1.setOnClickListener(this);
        register_login_clear_account2 = (ImageView) findViewById(R.id.register_login_clear_account2);
        register_login_clear_account2.setOnClickListener(this);

        //文本
        register_editText_username_login = (EditText) findViewById(R.id.register_editText_username_login);
        register_editText_username_login.setOnClickListener(this);
        register_editText_pass_login = (EditText) findViewById(R.id.register_editText_pass_login);
        register_editText_pass_login.setOnClickListener(this);

        imageView_checkNum_clear.setOnClickListener(this);
        imageView_phone_password_clear.setOnClickListener(this);
        relative_phonefast_login_btn.setOnClickListener(this);
        relative_getCheckedNum.setOnClickListener(this);
        text_onclick_getcheckNum.setOnClickListener(this);
        text_onclick_reget.setOnClickListener(this);
        text_onclick_countdown.setOnClickListener(this);
        editText_input_phoneNum.setOnClickListener(this);
        editText_phone_checkNum.setOnClickListener(this);

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
            //清除账户信息
            case R.id.register_login_clear_account1:
                clearaccountinfoone();
                break;
            case R.id.register_login_clear_account2:
                clearaccountinfotwo();
                break;
            //点击注册
            case R.id.register_relative_phonefast_login_btn:
                onClickedRegisterToBmob();
                break;
        }
    }
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
     * 清除账户信息
     */
    private void clearaccountinfoone() {

        //点击按钮删除文本
        register_editText_username_login.setText("");
        register_login_clear_account1.setVisibility(View.GONE);

    }
    private void clearaccountinfotwo() {
        //点击按钮删除文本
        register_editText_pass_login.setText("");
        register_login_clear_account2.setVisibility(View.GONE);
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
    private boolean checkSuccess = false;
    private EventHandler eh=new EventHandler(){
        @Override
        public void afterEvent(final int event, final int result, final Object data) {
            RegisterActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if (result == SMSSDK.RESULT_COMPLETE) {
                        //回调完成
                        if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                            //提交验证码成功
                            Toast.makeText(BaseApplication.getContext(), "验证成功", Toast.LENGTH_SHORT).show();

                            checkSuccess = true;
                            
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
                            Toast.makeText(RegisterActivity.this, "发送失败", Toast.LENGTH_SHORT).show();
                        } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                            //返回支持发送验证码的国家列表
                        }
                    }
                }
            });
        }
    };


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

    /**
     * 用户名密码的文字改变监听
     */
    /**
     * 文字改变监听user
     */
    private void textchangelistener1() {
        register_login_clear_account1.setVisibility(View.GONE);
        register_editText_username_login.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    register_login_clear_account1.setVisibility(View.GONE);
                } else {
                    register_login_clear_account1.setVisibility(View.VISIBLE);
                }
            }
        });

    }
    /**
     * 文字改变监听pass
     */
    private void textchangelistener2() {

        register_login_clear_account2.setVisibility(View.GONE);
        register_editText_pass_login.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    register_login_clear_account2.setVisibility(View.GONE);
                } else {
                    register_login_clear_account2.setVisibility(View.VISIBLE);
                }
            }
        });
    }


    /**
     * 点击注册推送数据
     */
    public void onClickedRegisterToBmob() {

        if(!register_editText_username_login.getText().toString().equals("")){
            if (register_editText_username_login.getText().toString().length()>=5) {
                if (!register_editText_pass_login.getText().toString().equals("")) {
                    if (register_editText_pass_login.getText().toString().length() >= 6) {
                        if (checkSuccess) {
                            User user = new User();
                            user.setUserName(register_editText_username_login.getText().toString());
                            user.setPassword(register_editText_pass_login.getText().toString());
                            user.setPhoneNum(editText_input_phoneNum.getText().toString());
                            user.save(RegisterActivity.this, new SaveListener() {
                                @Override
                                public void onSuccess() {
                                    Toast.makeText(RegisterActivity.this, "注册成功！", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onFailure(int i, String s) {
                                    Toast.makeText(RegisterActivity.this, "注册失败！", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            Toast.makeText(RegisterActivity.this, "请输入正确的验证码！", Toast.LENGTH_SHORT).show();

                        }

                    }else {
                        Toast.makeText(RegisterActivity.this, "密码不能少于6位以上的数字或字母！", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(RegisterActivity.this, "密码不能为空！", Toast.LENGTH_SHORT).show();
                }

            }else {
                Toast.makeText(RegisterActivity.this, "用户名不能少于5个数字或字母！", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(RegisterActivity.this, "用户名不能为空！", Toast.LENGTH_SHORT).show();

        }

    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterAllEventHandler();
    }

    //返回
    private void onFinish(View view) {
        finish();
    }
}
