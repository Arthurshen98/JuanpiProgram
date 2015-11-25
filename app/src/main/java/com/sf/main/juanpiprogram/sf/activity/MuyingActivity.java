package com.sf.main.juanpiprogram.sf.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
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
import com.sf.main.juanpiprogram.sf.adapter.GridAdapter;
import com.sf.main.juanpiprogram.sf.entities.InfoEntity;
import com.sf.main.juanpiprogram.sf.utils.BaseApplication;
import com.sf.main.juanpiprogram.sf.utils.SwipeBackActivity;

import java.util.ArrayList;
import java.util.List;

public class MuyingActivity extends SwipeBackActivity implements PullToRefreshBase.OnRefreshListener2, AdapterView.OnItemClickListener {
    private List<InfoEntity> entities;
    private GridAdapter gridAdapter;
    private String path = "http://mapi.juanpi.com/goods/zhe/list?apisign=a5f63fcb84ed510353683001b30a25e5&app_name=zhe&catname=muying&fitperson=0&goods_utype=C2&location=&page=1&platform=Android&to_switch=0";
    private PullToRefreshScrollView muying_pull_to_refresh_scrollView;
    private GridView muying_gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muying);
        muying_pull_to_refresh_scrollView = ((PullToRefreshScrollView) findViewById(R.id.muying_pull_to_refresh_scrollView));
        muying_gridView = ((GridView) findViewById(R.id.muying_gridView));
        entities = new ArrayList<>();
        gridAdapter = new GridAdapter(entities, BaseApplication.getContext());
        muying_gridView.setAdapter(gridAdapter);

        downLoad();
        muying_gridView.setOnItemClickListener(this);
        muying_pull_to_refresh_scrollView.setOnRefreshListener(this);
        muying_pull_to_refresh_scrollView.setMode(PullToRefreshBase.Mode.BOTH);

        ILoadingLayout startLabels = muying_pull_to_refresh_scrollView.getLoadingLayoutProxy(true, false);
        startLabels.setPullLabel("下拉刷新...");
        startLabels.setRefreshingLabel("努力载入中...");
        startLabels.setReleaseLabel("释放刷新...");
        ILoadingLayout endLabel = muying_pull_to_refresh_scrollView.getLoadingLayoutProxy(false, true);
        endLabel.setPullLabel("上拉加载...");
        endLabel.setRefreshingLabel("正在加载...");
        endLabel.setReleaseLabel("释放加载...");
    }

    private void downLoad() {
        HttpUtils utils = new HttpUtils(10000);
        //gridAdapter.clear();
        utils.send(HttpRequest.HttpMethod.GET, path, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                JSONObject object = JSON.parseObject(responseInfo.result);
                JSONObject jsonObject = JSON.parseObject(object.getString("data"));
                List<InfoEntity> entityList = JSON.parseArray(jsonObject.getString("goods"), InfoEntity.class);
                gridAdapter.addAll(entityList);
                MuyingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        muying_pull_to_refresh_scrollView.onRefreshComplete();
                    }
                });
            }

            @Override
            public void onFailure(HttpException e, String s) {
                e.printStackTrace();
                Toast.makeText(BaseApplication.getContext(), "网络错误", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*private void setTranslucentStatus() {
        mTintManager = new SystemBarTintManager(this);
        mTintManager.setStatusBarTintEnabled(true);
        mTintManager.setNavigationBarTintEnabled(true);  mTintManager.setStatusBarTintResource(R.color.juanpi_main);
    }*/
    //返回关闭界面
    public void toFinish(View v) {
        ActivityCompat.finishAfterTransition(this);
        overridePendingTransition(0, R.anim.base_slide_right_out);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(BaseApplication.getContext(), DetailActivity.class);
        startActivity(intent);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        downLoad();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        downLoad();
    }
}
