package com.sf.main.juanpiprogram.sf.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;

import com.sf.main.juanpiprogram.R;
import com.sf.main.juanpiprogram.sf.fragment.PersonAfterLoginFragment;
import com.sf.main.juanpiprogram.sf.fragment.PersonBeforeLoginFragment;
import com.sf.main.juanpiprogram.sf.fragment.SearchHistoryFragment;
import com.sf.main.juanpiprogram.sf.utils.BaseApplication;
import com.sf.main.juanpiprogram.sf.utils.SwipeBackActivity;
import com.sf.main.juanpiprogram.sf.utils.UserManager;

import java.security.PrivateKey;

public class PersonCenterActivity extends SwipeBackActivity implements View.OnClickListener {

    private RelativeLayout relativeLayout1,relativeLayout2,relativeLayout3,relativeLayout4,relativeLayout5,relativeLayout6,relativeLayout7;
    /**
     *
     * fragment事物
     */
    private FragmentManager fmanager;
    private FragmentTransaction ftransaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_center);

        //个人中心背景
        personBg();
        //个人中心的菜单项
        personCenterMenu();


        Intent intent = getIntent();
        if (intent.getAction() == "toUserName") {
            String userName = intent.getStringExtra("userName");
            //登录成功后接收传过来的用户名
            successLoginGetUserName(userName,null);
        }


    }

    /**
     * 登录成功后接收传过来的用户名
     */

    private void successLoginGetUserName(String user_name,String phoneNum) {

        fmanager = getSupportFragmentManager();
        ftransaction = fmanager.beginTransaction();
        PersonAfterLoginFragment afterFragment = new PersonAfterLoginFragment();
        Bundle bundle = new Bundle();
        bundle.putString("userName",user_name);
        bundle.putString("phoneNum", phoneNum);

        afterFragment.setArguments(bundle);
        ftransaction.replace(R.id.linearLayout_person_center, afterFragment);
        ftransaction.commit();

    }
    /**
     * 个人中心背景
     */
    private SharedPreferences share;
    private void personBg() {

        share = BaseApplication.getContext().getSharedPreferences("config", Context.MODE_PRIVATE);
        //得到共享参数对象
        String user_name = share.getString("userName","");
        String phoneNum = share.getString("phoneNum", "");
        boolean state = share.getBoolean("state", false);
        UserManager userManager = new UserManager(PersonCenterActivity.this);
        //判断用户是否登录
        if (userManager.isLogin() || !phoneNum.equals("") ) {
            //用户登录后的背景
            successLoginGetUserName(user_name,phoneNum);
        } else {
            //在用户未登录状态的背景
            noLoginBg();
        }


    }

    //在用户未登录状态的背景
    private void noLoginBg() {
        fmanager = getSupportFragmentManager();
        ftransaction = fmanager.beginTransaction();
        PersonBeforeLoginFragment personbeforeFragment = new PersonBeforeLoginFragment();
        ftransaction.replace(R.id.linearLayout_person_center, personbeforeFragment);
        ftransaction.commit();
    }

    //用户登录后的背景
    private void alreadLoginBg() {
        fmanager = getSupportFragmentManager();
        ftransaction = fmanager.beginTransaction();
        PersonAfterLoginFragment personafterFragment = new PersonAfterLoginFragment();
        ftransaction.replace(R.id.linearLayout_person_center, personafterFragment);
        ftransaction.commit();
    }
    /**
     * 第二，第三个人中心的菜单项
     */
    private void personCenterMenu() {
        relativeLayout1 = (RelativeLayout) findViewById(R.id.relativeLayout_person_menu_item1);
        relativeLayout1.setOnClickListener(this);
        relativeLayout2 = (RelativeLayout) findViewById(R.id.relativeLayout_person_menu_item2);
        relativeLayout2.setOnClickListener(this);
        relativeLayout3 = (RelativeLayout) findViewById(R.id.relativeLayout_person_menu_item3);
        relativeLayout3.setOnClickListener(this);
        relativeLayout4 = (RelativeLayout) findViewById(R.id.relativeLayout_person_menu_item4);
        relativeLayout4.setOnClickListener(this);
        relativeLayout5 = (RelativeLayout) findViewById(R.id.relativeLayout_person_menu_item5);
        relativeLayout5.setOnClickListener(this);
        relativeLayout6 = (RelativeLayout) findViewById(R.id.relativeLayout_person_menu_item6);
        relativeLayout6.setOnClickListener(this);
        relativeLayout7 = (RelativeLayout) findViewById(R.id.relativeLayout_person_menu_item7);
        relativeLayout7.setOnClickListener(this);

    }
   //个人中心的菜单项监听
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.relativeLayout_person_menu_item1:
                //to我的收藏
                Intent mc = new Intent(PersonCenterActivity.this, MyCollectActivity.class);
                startActivity(mc);
                break;
            case R.id.relativeLayout_person_menu_item2:
                //to我的优惠券
                Intent mcp = new Intent(PersonCenterActivity.this, MyCouponActivity.class);
                startActivity(mcp);
                break;
            case R.id.relativeLayout_person_menu_item3:
                //to我的浏览记录
                Intent sca = new Intent(PersonCenterActivity.this, ScanRecordActivity.class);
                startActivity(sca);
                break;
            case R.id.relativeLayout_person_menu_item4:
                //to我的积分
                Intent mig = new Intent(PersonCenterActivity.this, MyIntegralActivity.class);
                startActivity(mig);
                break;
            case R.id.relativeLayout_person_menu_item5:
                //to我的售后
                Intent maf = new Intent(PersonCenterActivity.this, MyafterserviceActivity.class);
                startActivity(maf);
                break;
            case R.id.relativeLayout_person_menu_item6:
                //to客服中心
                Intent cac = new Intent(PersonCenterActivity.this, CallCenterActivity.class);
                startActivity(cac);
                break;
            case R.id.relativeLayout_person_menu_item7:
                //to我的收藏
                Intent abj = new Intent(PersonCenterActivity.this, AboutJuanpiActivity.class);
                startActivity(abj);
                break;

        }
    }

    /**
     *
     * 第一个菜单组
     */
    //代付款
    public void toNoPay(View view) {
        Intent np = new Intent(PersonCenterActivity.this, NoPayActivity.class);
        startActivity(np);
    }
    //代收货
    public void toLogistic(View view) {
        Intent lg = new Intent(PersonCenterActivity.this, LogisticActivity.class);
        startActivity(lg);
    }
    //全部订单
    public void toAllOrder(View view) {
        Intent ao = new Intent(PersonCenterActivity.this, AllOrderActivity.class);
        startActivity(ao);
    }

    //返回关闭界面
    public void toFinish(View v) {
        ActivityCompat.finishAfterTransition(this);
        overridePendingTransition(0, R.anim.base_slide_right_out);
    }


}
