package com.sf.main.juanpiprogram.sf.fragment;


import android.os.Message;
import android.support.v4.app.Fragment;
import android.os.Bundle;
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

import android.os.Handler;

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
                break;

            case R.id.relative_getCheckedNum:
                break;

            case R.id.text_onclick_getcheckNum:
                text_onclick_getcheckNum.setVisibility(View.GONE);
                countdown();
                break;
            case R.id.text_onclick_reget:
                //重新发送
                countdown();
                break;
            case R.id.text_onclick_countdown:
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
     * 实现倒计时
     */
    //验证码倒计时
    private Handler handler=new Handler(){
        //处理消息
        public void handleMessage(Message msg) {
            int count = msg.arg1;
            text_onclick_countdown.setText(count+"");
        };
    };

    public  void  countdown(){
        new Thread(new Runnable() {

            @Override
            public void run() {
                int count = 0;
                while (true) {
                    if(count < 60){
                        count++;
                        Message message = Message.obtain();
                        message.arg1 = count;
                        handler.sendMessage(message);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }else{
                        //倒计时之后重新发送
                        text_onclick_countdown.setVisibility(View.GONE);
                        text_onclick_reget.setVisibility(View.VISIBLE);
                    }
                }
            }
        }).start();
    }
}
