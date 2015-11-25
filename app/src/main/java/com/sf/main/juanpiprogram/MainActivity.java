package com.sf.main.juanpiprogram;






import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.HorizontalScrollView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.sf.main.juanpiprogram.sf.activity.AboutJuanpiActivity;
import com.sf.main.juanpiprogram.sf.activity.BagActivity;
import com.sf.main.juanpiprogram.sf.activity.CallCenterActivity;
import com.sf.main.juanpiprogram.sf.activity.FuzhuangActivity;
import com.sf.main.juanpiprogram.sf.activity.JiajuActivity;
import com.sf.main.juanpiprogram.sf.activity.MeishiActivity;
import com.sf.main.juanpiprogram.sf.activity.MeizhuangActivity;
import com.sf.main.juanpiprogram.sf.activity.MuyingActivity;
import com.sf.main.juanpiprogram.sf.activity.PersonCenterActivity;
import com.sf.main.juanpiprogram.sf.activity.SearchGoodsActivity;
import com.sf.main.juanpiprogram.sf.fragment.CategoryFragment;
import com.sf.main.juanpiprogram.sf.fragment.OtherFragment;
import com.sf.main.juanpiprogram.sf.fragment.TomorrowFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener
        /*implements NavigationView.OnNavigationItemSelectedListener*/ {

    /**
     * 搜索
     */
    private RelativeLayout relativeSearch;

    private HorizontalScrollView main_scroll;
    private RadioGroup main_group;
    private View main_bar;
    private ViewPager main_pager;
    private List<String> categorys;
    private RadioButton button;
    private List<Fragment> list;
    private MyPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        setCategory();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        //浮动按钮
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageResource(R.mipmap.sell_bhz);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "去购物车！", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                Intent intent = new Intent(MainActivity.this, BagActivity.class);
                startActivity(intent);
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

    private void initView() {
        main_scroll = ((HorizontalScrollView) findViewById(R.id.main_scroll));
        main_group = ((RadioGroup) findViewById(R.id.main_group));
        main_bar = ((View) findViewById(R.id.main_bar));
        main_pager = ((ViewPager) findViewById(R.id.main_pager));
    }

    private void initData() {
        categorys=new ArrayList<String>();

        categorys.add("最近折扣");
        categorys.add("昨日上新");
        categorys.add("最后疯抢");
        categorys.add("9.9包邮");
        categorys.add("明日预告");
        list = new ArrayList<Fragment>();
        // 添加Fragment对象
        CategoryFragment fragment = new CategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putString("index", "0");
        fragment.setArguments(bundle);
        list.add(fragment);

        OtherFragment fragment1 = new OtherFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putString("index", "1");
        fragment1.setArguments(bundle1);
        list.add(fragment1);

        OtherFragment fragment2 = new OtherFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putString("index", "2");
        fragment2.setArguments(bundle2);
        list.add(fragment2);

        OtherFragment fragment3 = new OtherFragment();
        Bundle bundle3 = new Bundle();
        bundle3.putString("index", "3");
        fragment3.setArguments(bundle3);
        list.add(fragment3);

        TomorrowFragment fragment4 = new TomorrowFragment();
        Bundle bundle4 = new Bundle();
        bundle4.putString("index", "4");
        fragment4.setArguments(bundle4);
        list.add(fragment4);
    }

    private void setCategory() {
        int i=0;
        for(String cate :categorys){
            button= ((RadioButton) LayoutInflater.from(this).inflate(R.layout.category_item, main_group, false));
            button.setId(i++);
            button.setText(cate);
            button.setTextSize(14);
            main_group.addView(button);
        }
        main_group.post(new Runnable() {
            @Override
            public void run() {
                main_bar.setMinimumWidth(main_group.getChildAt(0).getWidth());
            }
        });
        main_group.check(0);
        adapter = new MyPagerAdapter(getSupportFragmentManager());
        // 给ViewPager设置适配器
        main_pager.setAdapter(adapter);
        main_pager.addOnPageChangeListener(this);
        main_group.setOnCheckedChangeListener(this);
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
        finish();
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

    class MyPagerAdapter extends FragmentPagerAdapter{

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * 返回下标为position的页面需要显示的Fragment对象
         */
        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        /**
         * 返回显示的页面的总数
         */
        @Override
        public int getCount() {
            return list.size();
        }
    }

    /**
     * viewpager的监听事件用于处理分类title滑动
     *
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //绿条跟随滑动
        ViewCompat.setX(main_bar, main_group.getChildAt(position).getLeft() + main_bar.getWidth() * positionOffset);
        /**
         * bar滚动出屏幕处理
         */
        if(ViewCompat.getX(main_bar)+main_bar.getWidth()>main_scroll.getScrollX()+main_scroll.getWidth()){
            int i = (int)((ViewCompat.getX(main_bar) +main_bar.getWidth())- (main_scroll.getScrollX() + main_scroll.getWidth()));
            main_scroll.scrollBy(i,0);
        }
        if(ViewCompat.getX(main_bar)<main_scroll.getScrollX()){
            main_scroll.scrollTo((int)ViewCompat.getX(main_bar),0);
        }
    }

    @Override
    public void onPageSelected(int position) {
        main_group.check(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * group的监听
     * @param group
     * @param checkedId
     */
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        main_pager.setCurrentItem(checkedId);
    }

    /**
     * 按两次退出
     */
    private long mExitTime;
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Object mHelperUtils;
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();

            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
