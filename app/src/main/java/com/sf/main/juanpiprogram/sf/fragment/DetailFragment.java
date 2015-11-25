package com.sf.main.juanpiprogram.sf.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.sf.main.juanpiprogram.R;
import com.sf.main.juanpiprogram.sf.entities.Information;
import com.sf.main.juanpiprogram.sf.urls.Url;


import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {


    private WebView detail_webView;
    private List<Information> tab_table;
    private String path="";
    public DetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        detail_webView = ((WebView) view.findViewById(R.id.detail_webView));

        getData();


    }
    private void getData() {
        //json解析
        HttpUtils utils = new HttpUtils(10000);
        utils.send(HttpRequest.HttpMethod.GET, Url.url5, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                JSONObject object = JSON.parseObject(responseInfo.result);
                JSONObject jsonObject = JSON.parseObject(object.getString("data"));
                JSONObject object1 = JSON.parseObject(jsonObject.getString("info"));
                tab_table = JSON.parseArray(object1.getString("tab_table"), Information.class);
                //Log.d("++++",tab_table.toString());
                Bundle bundle = getArguments();
                int index = bundle.getInt("index");
                switch (index){
                    case 0:
                        path=tab_table.get(0).getH5_url();
                        break;
                    case 1:
                        path=tab_table.get(1).getH5_url();
                        break;
                    default:
                        break;
                }
                getWeb();
            }

            @Override
            public void onFailure(HttpException e, String s) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "网络错误", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getWeb() {
        HttpUtils utils = new HttpUtils(10000);
        utils.send(HttpRequest.HttpMethod.GET, path, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                detail_webView.loadDataWithBaseURL(null, responseInfo.result, "text/html", "UTF-8", null);
            }
            @Override
            public void onFailure(HttpException e, String s) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "网络错误", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
