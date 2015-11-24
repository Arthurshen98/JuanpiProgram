package com.sf.main.juanpiprogram;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.RelativeLayout;

import com.sf.main.juanpiprogram.sf.activity.AboutJuanpiActivity;
import com.sf.main.juanpiprogram.sf.activity.CallCenterActivity;
import com.sf.main.juanpiprogram.sf.activity.FuzhuangActivity;
import com.sf.main.juanpiprogram.sf.activity.JiajuActivity;
import com.sf.main.juanpiprogram.sf.activity.MeishiActivity;
import com.sf.main.juanpiprogram.sf.activity.MeizhuangActivity;
import com.sf.main.juanpiprogram.sf.activity.MuyingActivity;
import com.sf.main.juanpiprogram.sf.activity.PersonCenterActivity;
import com.sf.main.juanpiprogram.sf.activity.SearchGoodsActivity;


public class MainActivity extends AppCompatActivity
        /*implements NavigationView.OnNavigationItemSelectedListener*/ {

    /**
     * 搜索
     */
    private RelativeLayout relativeSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //浮动按钮
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageResource(R.mipmap.sell_bhz);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "去购物车！", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        /**
         * 抽屉布局里面的
         */
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        /*NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        *//*navigationView.setNavigationItemSelectedListener(this);*/



        // 抽屉布局里面组件的初始化和监听this
        initLeftMenuDreawerLayout();

    }


    /**
     * 打开或关闭抽屉
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    *//**
     * 抽屉布局内部选项
     * @param item
     * @return
     *//*
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camara) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        }

        //关闭抽屉
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }*/

    /**
     * 抽屉布局里面组件的组件初始化+Fragment的事物
     */
    private void initLeftMenuDreawerLayout() {
        relativeSearch = (RelativeLayout) findViewById(R.id.relativeLayout_left_menu_search);
        relativeSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent searchIntent = new Intent(MainActivity.this, SearchGoodsActivity.class);
                startActivity(searchIntent);
                //关闭抽屉
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });




    }

    //首页
    public void toMain(View v) {
        Intent personIntent = new Intent(MainActivity.this, MainActivity.class);
        startActivity(personIntent);
        //关闭抽屉
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }
    //服装
    public void toFuzhuang(View view) {
        Intent fuzhuangIntent = new Intent(MainActivity.this, FuzhuangActivity.class);
        startActivity(fuzhuangIntent);
        //关闭抽屉
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }
    //家居
    public void toJaju(View view) {
        Intent jiajuIntent = new Intent(MainActivity.this, JiajuActivity.class);
        startActivity(jiajuIntent);
        //关闭抽屉
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }
    //母婴
    public void toMuyin(View view) {
        Intent muyingIntent = new Intent(MainActivity.this, MuyingActivity.class);
        startActivity(muyingIntent);
        //关闭抽屉
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }
    //美食
    public void toMeishi(View view) {

        Intent meishiIntent = new Intent(MainActivity.this, MeishiActivity.class);
        startActivity(meishiIntent);
        //关闭抽屉
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }
    //美妆
    public void toMeizhuang(View view) {

        Intent MeizhuangIntent = new Intent(MainActivity.this, MeizhuangActivity.class);
        startActivity(MeizhuangIntent);
        //关闭抽屉
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    //个人中心
    public void toPersonCenter(View view) {
        Intent personIntent = new Intent(MainActivity.this, PersonCenterActivity.class);
        startActivity(personIntent);
        //关闭抽屉
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    //关于卷皮
    public void toAboutJuanpi(View view) {
        Intent aboutIntent = new Intent(MainActivity.this, AboutJuanpiActivity.class);
        startActivity(aboutIntent);
        //关闭抽屉
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    //客服中心
    public void toCallCenter(View view) {
        Intent callIntent = new Intent(MainActivity.this, CallCenterActivity.class);
        startActivity(callIntent);
        //关闭抽屉
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }



}
