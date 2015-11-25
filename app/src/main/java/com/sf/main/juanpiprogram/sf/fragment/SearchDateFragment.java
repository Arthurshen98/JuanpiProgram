package com.sf.main.juanpiprogram.sf.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ScrollView;
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
import com.sf.main.juanpiprogram.sf.activity.MuyingActivity;
import com.sf.main.juanpiprogram.sf.adapter.GridAdapter;
import com.sf.main.juanpiprogram.sf.entities.InfoEntity;
import com.sf.main.juanpiprogram.sf.utils.BaseApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchDateFragment extends Fragment implements AdapterView.OnItemClickListener {

    private List<InfoEntity> entities;
    private GridAdapter gridAdapter;
    private String path = "http://mapi.juanpi.com/goods/search?apisign=6decb7fcf1d4426b2e720524e8d73f38&app_name=zhe&keyword=%E9%9E%8B&page=1&platform=Android&utm=101212";
   // private PullToRefreshScrollView search_pull_to_refresh_scrollView;
    private GridView search_gridView;
    public SearchDateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_date, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

      //  search_pull_to_refresh_scrollView = ((PullToRefreshScrollView) view.findViewById(R.id.muying_pull_to_refresh_scrollView));
        search_gridView = ((GridView) view.findViewById(R.id.search_gridView));
        entities = new ArrayList<>();
        gridAdapter = new GridAdapter(entities, BaseApplication.getContext());
        search_gridView.setAdapter(gridAdapter);


        downLoad();
        /*search_gridView.setOnItemClickListener(this);
        search_pull_to_refresh_scrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
                downLoad();
            }
        });*/
       // search_pull_to_refresh_scrollView.setMode(PullToRefreshBase.Mode.BOTH);

       // ILoadingLayout startLabels = search_pull_to_refresh_scrollView.getLoadingLayoutProxy(true, false);
        /*startLabels.setPullLabel("下拉刷新...");
        startLabels.setRefreshingLabel("努力载入中...");
        startLabels.setReleaseLabel("释放刷新...");
       // ILoadingLayout endLabel = search_pull_to_refresh_scrollView.getLoadingLayoutProxy(false, true);
        endLabel.setPullLabel("上拉加载...");
        endLabel.setRefreshingLabel("正在加载...");
        endLabel.setReleaseLabel("释放加载...");*/
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
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                      //  search_pull_to_refresh_scrollView.onRefreshComplete();
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(BaseApplication.getContext(), DetailActivity.class);
        startActivity(intent);
    }


    /*public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        downLoad();
    }


    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        downLoad();
    }*/
}
