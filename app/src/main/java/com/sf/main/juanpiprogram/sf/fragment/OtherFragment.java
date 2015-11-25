package com.sf.main.juanpiprogram.sf.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.sf.main.juanpiprogram.sf.activity.DetailActivity;
import com.sf.main.juanpiprogram.sf.adapter.GridAdapter;
import com.sf.main.juanpiprogram.sf.entities.InfoEntity;
import com.sf.main.juanpiprogram.sf.urls.Url;
import com.sf.main.juanpiprogram.sf.utils.BaseApplication;


import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class OtherFragment extends Fragment implements PullToRefreshBase.OnRefreshListener2, AdapterView.OnItemClickListener {


    private PullToRefreshScrollView otherscrollView;
    private GridView other_gridView;
    private List<InfoEntity> entities;
    private GridAdapter gridAdapter;
    private String path = "";

    public OtherFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_other, container, false);
        otherscrollView = ((PullToRefreshScrollView) view.findViewById(R.id.other_pull_to_refresh_scrollView));
        other_gridView = ((GridView) view.findViewById(R.id.other_gridView));
        entities = new ArrayList<>();
        gridAdapter = new GridAdapter(entities, BaseApplication.getContext());
        other_gridView.setAdapter(gridAdapter);
        Bundle bundle = getArguments();
        if (bundle != null) {
            int index = Integer.parseInt(bundle.getString("index"));
            switch (index) {
                case 1:
                    path = Url.url3;
                    break;
                case 2:
                    path = Url.url4;
                    break;
                case 3:
                    path = Url.url1;
                    break;
                /*case 4:
                    path = Urls.DATAPAGER;
                    break;*/
                default:
                    break;
            }
        }
        downLoad();
        other_gridView.setOnItemClickListener(this);
        otherscrollView.setOnRefreshListener(this);
        otherscrollView.setMode(PullToRefreshBase.Mode.BOTH);

        //设置刷新文本
        ILoadingLayout startLabels = otherscrollView.getLoadingLayoutProxy(true, false);
        startLabels.setPullLabel("下拉刷新...");
        startLabels.setRefreshingLabel("努力载入中...");
        startLabels.setReleaseLabel("释放刷新...");
        ILoadingLayout endLabel = otherscrollView.getLoadingLayoutProxy(false, true);
        endLabel.setPullLabel("上拉加载...");
        endLabel.setRefreshingLabel("正在加载...");
        endLabel.setReleaseLabel("释放加载...");
        return view;
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
                        otherscrollView.onRefreshComplete();
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
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        downLoad();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        downLoad();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(BaseApplication.getContext(), DetailActivity.class);
        startActivity(intent);
    }
}
