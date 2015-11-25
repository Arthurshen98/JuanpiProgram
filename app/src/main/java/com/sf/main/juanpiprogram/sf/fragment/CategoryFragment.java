package com.sf.main.juanpiprogram.sf.fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.sf.main.juanpiprogram.R;
import com.sf.main.juanpiprogram.sf.activity.DetailActivity;
import com.sf.main.juanpiprogram.sf.activity.ViewPagerActivity;
import com.sf.main.juanpiprogram.sf.adapter.GridAdapter;
import com.sf.main.juanpiprogram.sf.entities.InfoEntity;
import com.sf.main.juanpiprogram.sf.entities.ViewPagerImageEntity;
import com.sf.main.juanpiprogram.sf.urls.Url;
import com.sf.main.juanpiprogram.sf.utils.BaseApplication;
import com.sf.main.juanpiprogram.sf.utils.BitmapHelper;


import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment implements PullToRefreshBase.OnRefreshListener2, AdapterView.OnItemClickListener {


    private PullToRefreshScrollView pull_to_refresh_scrollView;
    private ViewPager frag1_pager;
    private GridView frag1_gridView;
    private List<ImageView> images;
    private List<ViewPagerImageEntity> slide;
    private List<InfoEntity> infoEntities;
    private Handler handler=new Handler(){
        public void handleMessage(Message msg) {
            int what=msg.what;
            switch (what) {
                case 0:
                    int totalcount = images.size();
                    int currentItem = frag1_pager.getCurrentItem();
                    int toItem = currentItem + 1 == totalcount ? 0 : currentItem + 1;
                    frag1_pager.setCurrentItem(toItem);
                    break;

                default:
                    break;
            }
        };
    };
    private ImageAdapter imageAdapter;
    private GridAdapter gridAdapter;
    private LinearLayout frag1_linearLayout;


    private Button mPreSelectedBt;
    public CategoryFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category_one, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pull_to_refresh_scrollView = ((PullToRefreshScrollView) view.findViewById(R.id.pull_to_refresh_scrollView));
        frag1_pager = ((ViewPager) view.findViewById(R.id.frag1_pager));
        frag1_gridView = ((GridView) view.findViewById(R.id.frag1_gridView));
        frag1_linearLayout = ((LinearLayout) view.findViewById(R.id.frag1_linearLayout));
        images=new ArrayList<>();
        slide=new ArrayList<>();
        imageAdapter = new ImageAdapter();
        initJson();
        frag1_pager.setAdapter(imageAdapter);
        new Timer().schedule(new TimerTask() {

            public void run() {
                //每隔2秒给handler发送消息
                handler.sendEmptyMessage(0);
            }
        }, 2000, 2000);
        infoEntities=new ArrayList<>();
        gridAdapter = new GridAdapter(infoEntities, BaseApplication.getContext());
        frag1_gridView.setAdapter(gridAdapter);
        downLoad();
        pull_to_refresh_scrollView.setOnRefreshListener(this);
        //既能上拉 也能下拉
        pull_to_refresh_scrollView.setMode(PullToRefreshBase.Mode.BOTH);
        frag1_gridView.setOnItemClickListener(this);


        //设置刷新文本
        ILoadingLayout startLabels = pull_to_refresh_scrollView.getLoadingLayoutProxy(true, false);
        startLabels.setPullLabel("下拉刷新...");
        startLabels.setRefreshingLabel("努力载入中...");
        startLabels.setReleaseLabel("释放刷新...");
        ILoadingLayout endLabel = pull_to_refresh_scrollView.getLoadingLayoutProxy(false, true);
        endLabel.setPullLabel("上拉加载...");
        endLabel.setRefreshingLabel("正在加载...");
        endLabel.setReleaseLabel("释放加载...");
    }


    /**
     * 获取Viewpager中的图片
     */
    private void initJson() {

        //json解析
        HttpUtils utils = new HttpUtils(10000);
        utils.send(HttpRequest.HttpMethod.GET, Url.url1, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                JSONObject object = JSON.parseObject(responseInfo.result);
                JSONObject jsonObject = JSON.parseObject(object.getString("data"));
                JSONObject object1 = JSON.parseObject(jsonObject.getString("slide_ads"));
                JSONObject object2 = JSON.parseObject(object1.getString("config"));
                slide = JSON.parseArray(object2.getString("slide"), ViewPagerImageEntity.class);
                initData();
            }

            @Override
            public void onFailure(HttpException e, String s) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "网络错误", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initData() {
        for (int i = 0; i < slide.size(); i++) {
            ImageView imageView = new ImageView(BaseApplication.getContext());
            imageView.setImageResource(R.mipmap.ic_launcher);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            BitmapHelper.getUtils().display(imageView, slide.get(i).getPic());
            images.add(imageView);
        }
        imageAdapter.notifyDataSetChanged();
    }




    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(BaseApplication.getContext(), DetailActivity.class);
        startActivity(intent);
    }



    class ImageAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View view = images.get(position);
            view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(BaseApplication.getContext(), ViewPagerActivity.class);
                    intent.putExtra("url",slide.get(position).getUrl());
                    startActivity(intent);
                }
            });
            container.addView(images.get(position));
            return images.get(position);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(images.get(position));
        }
    }
    /**
     * scrollView的下拉监听
     * @param refreshView
     */
    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        //initJson();
        downLoad();
    }

    private void downLoad() {
        HttpUtils utils = new HttpUtils(10000);
        //gridAdapter.clear();
        utils.send(HttpRequest.HttpMethod.GET, Url.url2, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                JSONObject object = JSON.parseObject(responseInfo.result);
                JSONObject jsonObject = JSON.parseObject(object.getString("data"));
                List<InfoEntity> entityList = JSON.parseArray(jsonObject.getString("goods"), InfoEntity.class);
                gridAdapter.addAll(entityList);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pull_to_refresh_scrollView.onRefreshComplete();
                    }
                });
            }

            @Override
            public void onFailure(HttpException e, String s) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "网络错误", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * scrollView的上拉监听
     * @param refreshView
     */
    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        downLoad();
    }
}
