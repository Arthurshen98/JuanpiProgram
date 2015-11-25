package com.sf.main.juanpiprogram.sf.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import com.sf.main.juanpiprogram.R;
import com.sf.main.juanpiprogram.sf.entities.Good;
import com.sf.main.juanpiprogram.sf.fragment.DetailFragment;
import com.sf.main.juanpiprogram.sf.urls.Url;
import com.sf.main.juanpiprogram.sf.utils.BaseApplication;
import com.sf.main.juanpiprogram.sf.utils.BitmapHelper;
import com.sf.main.juanpiprogram.sf.utils.DBHelper;
import com.sf.main.juanpiprogram.sf.utils.SwipeBackActivity;

import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.onekeyshare.OnekeyShare;

public class DetailActivity extends SwipeBackActivity implements View.OnClickListener {

    private ViewPager detail_viewpager;
    private List<ImageView> list;
    private MyAdapter myAdapter;
    //商品详情
    private Button detail_btn1;
    //咨询与售后
    private Button detail_btn2;
    private boolean isOnclick = true;
    private Button detail_btn_color1;
    private Button detail_btn_color2;
    private Button detail_btn_color3;
    private Button detail_share;
    private Button detail_back;
    private ImageView detail_lv_collect;
    private TextView detail_title;
    private TextView detail_cprice;
    private TextView detail_oprice;
    private TextView detail_integral;
    private Button detail_add_bag;
    private Good good;
    private Button detail_shop_bag;
    //存放ViewPager的网址
    private List<String> images;
    private boolean isCollectionClick = true;
    private RelativeLayout detail_rl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initView();
        initViewPagerData();

        initGoodData();

        detail_viewpager.setAdapter(myAdapter);

        detail_btn1.setOnClickListener(this);
        detail_btn2.setOnClickListener(this);
        detail_btn_color1.setOnClickListener(this);
        detail_btn_color2.setOnClickListener(this);
        detail_btn_color3.setOnClickListener(this);
        detail_share.setOnClickListener(this);
        detail_back.setOnClickListener(this);
        detail_add_bag.setOnClickListener(this);
        detail_shop_bag.setOnClickListener(this);
        detail_lv_collect.setOnClickListener(this);
        detail_rl.setOnClickListener(this);
        information();
    }

    private void initGoodData() {
        HttpUtils utils = new HttpUtils(10000);
        utils.send(HttpRequest.HttpMethod.GET, Url.url6, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                JSONObject jsonObject = JSON.parseObject(responseInfo.result);
                JSONObject object = JSON.parseObject(jsonObject.getString("data"));
                good = JSON.parseObject(object.getString("info"), Good.class);
                detail_title.setText(good.getTitle());
                detail_oprice.setText(good.getOprice());
                detail_oprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                detail_cprice.setText(good.getCprice());
                detail_integral.setText(good.getPoint() + "积分");

            }

            @Override
            public void onFailure(HttpException e, String s) {
                e.printStackTrace();
                Toast.makeText(DetailActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void information() {
        if (isOnclick) {
            DetailFragment detailFragment = new DetailFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("index", 0);
            detailFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.detail_fragment, detailFragment).commit();

        } else {
            DetailFragment detailFragment = new DetailFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("index", 1);
            detailFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.detail_fragment, detailFragment).commit();

        }
    }

    private void initView() {
        detail_viewpager = ((ViewPager) findViewById(R.id.detail_viewpager));
        list = new ArrayList<>();
        myAdapter = new MyAdapter();
        detail_btn1 = ((Button) findViewById(R.id.detail_btn1));
        detail_btn2 = ((Button) findViewById(R.id.detail_btn2));
        detail_btn_color1 = ((Button) findViewById(R.id.detail_btn_color1));
        detail_btn_color2 = ((Button) findViewById(R.id.detail_btn_color2));
        detail_btn_color3 = ((Button) findViewById(R.id.detail_btn_color3));
        detail_share = ((Button) findViewById(R.id.detail_share));
        detail_back = ((Button) findViewById(R.id.detail_back));
        detail_lv_collect = ((ImageView) findViewById(R.id.detail_lv_collect));
        detail_title = ((TextView) findViewById(R.id.detail_title));
        detail_cprice = ((TextView) findViewById(R.id.detail_cprice));
        detail_oprice = ((TextView) findViewById(R.id.detail_oprice));
        detail_integral = ((TextView) findViewById(R.id.detail_integral));
        detail_add_bag = ((Button) findViewById(R.id.detail_add_bag));
        detail_shop_bag = ((Button) findViewById(R.id.detail_shop_bag));
        detail_rl = ((RelativeLayout) findViewById(R.id.detail_rl));

    }

    private void initViewPagerData() {
        HttpUtils utils = new HttpUtils(10000);
        utils.send(HttpRequest.HttpMethod.GET, Url.url5, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                JSONObject object = JSON.parseObject(responseInfo.result);
                JSONObject jsonObject = JSON.parseObject(object.getString("data"));
                JSONObject object1 = JSON.parseObject(jsonObject.getString("info"));
                images = JSON.parseArray(object1.getString("images"), String.class);
                for (int i = 0; i < images.size(); i++) {
                    ImageView imageView = new ImageView(BaseApplication.getContext());
                    imageView.setImageResource(R.mipmap.ic_launcher);
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    BitmapHelper.getUtils().display(imageView, images.get(i));
                    list.add(imageView);
                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(HttpException e, String s) {
                e.printStackTrace();
                Toast.makeText(DetailActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.detail_btn1:
                isOnclick = true;
                detail_btn2.setSelected(false);
                detail_btn1.setSelected(false);
                detail_btn1.setTextColor(Color.WHITE);
                detail_btn2.setTextColor(Color.BLACK);
                information();
                break;
            case R.id.detail_btn2:
                isOnclick = false;
                detail_btn2.setSelected(true);
                detail_btn1.setSelected(true);
                detail_btn2.setTextColor(Color.WHITE);
                detail_btn1.setTextColor(Color.BLACK);
                information();
                break;
            case R.id.detail_btn_color1:
                detail_btn_color1.setSelected(true);
                detail_btn_color1.setTextColor(getResources().getColor(R.color.juanpi_main));
                detail_btn_color2.setTextColor(getResources().getColor(R.color.black));
                detail_btn_color3.setTextColor(getResources().getColor(R.color.black));
                detail_btn_color2.setSelected(false);
                detail_btn_color3.setSelected(false);
                break;
            case R.id.detail_btn_color2:
                detail_btn_color2.setSelected(true);
                detail_btn_color2.setTextColor(getResources().getColor(R.color.juanpi_main));
                detail_btn_color1.setTextColor(getResources().getColor(R.color.black));
                detail_btn_color3.setTextColor(getResources().getColor(R.color.black));
                detail_btn_color3.setSelected(false);
                detail_btn_color1.setSelected(false);
                break;
            case R.id.detail_btn_color3:
                detail_btn_color3.setSelected(true);
                detail_btn_color3.setTextColor(getResources().getColor(R.color.juanpi_main));
                detail_btn_color1.setTextColor(getResources().getColor(R.color.black));
                detail_btn_color2.setTextColor(getResources().getColor(R.color.black));
                detail_btn_color1.setSelected(false);
                detail_btn_color2.setSelected(false);
                break;
            case R.id.detail_share:
                showShare();
                break;
            case R.id.detail_back:
                ActivityCompat.finishAfterTransition(this);
                overridePendingTransition(0, R.anim.base_slide_right_out);
                break;
            case R.id.detail_lv_collect:
                if (isCollectionClick) {
                    detail_lv_collect.setSelected(true);
                    isCollectionClick = false;
                } else {
                    detail_lv_collect.setSelected(false);
                    isCollectionClick = true;
                }

                break;
            //添加购物袋
            case R.id.detail_add_bag:
                DbUtils utils = DBHelper.getUtils();
                try {
                    //保证相同good_id存入数据库显示一条，只改变数量
                    List<Good> goods_id = utils.findAll(Selector.from(Good.class).where("goods_id", "=", good.getGoods_id()));
                    if (goods_id != null) {
                        if (goods_id.size() != 0) {
                            int num = goods_id.get(0).getNum();
                            good.setNum(num + 1);
                            utils.delete(Good.class, WhereBuilder.b("goods_id", "=", good.getGoods_id()));

                        } else {
                            good.setNum(1);

                        }
                    } else {
                        good.setNum(1);
                    }

                    utils.saveOrUpdate(good);


                    Toast.makeText(DetailActivity.this, "添加购物车成功", Toast.LENGTH_SHORT).show();
                } catch (DbException e) {
                    e.printStackTrace();
                }
                break;
            //跳转到购物车页面
            case R.id.detail_shop_bag:
                Intent intent = new Intent(this, BagActivity.class);
                intent.putExtra("url", images.get(0));
                startActivity(intent);
                break;
            //跳转质量检测页面
            case R.id.detail_rl:
                Intent intent1=new Intent(this,QualityActivity.class);
                intent1.putExtra("url",good.getAbout_juanpi_url());
                startActivity(intent1);
                break;
            default:
                break;
        }

    }

    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("分享");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

        // 启动分享GUI
        oks.show(this);
    }

    class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(list.get(position));
            return list.get(position);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(list.get(position));
        }
    }
}
