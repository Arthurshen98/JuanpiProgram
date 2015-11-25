package com.sf.main.juanpiprogram.sf.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.sf.main.juanpiprogram.R;
import com.sf.main.juanpiprogram.sf.utils.SwipeBackActivity;

public class QualityActivity extends SwipeBackActivity {

    private WebView quality_web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quality);
        quality_web = ((WebView) findViewById(R.id.quality_web));
        Intent intent=getIntent();
        String url = intent.getStringExtra("url");
        HttpUtils utils = new HttpUtils(10000);
        utils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                quality_web.loadDataWithBaseURL(null, responseInfo.result, "text/html", "UTF-8", null);
            }
            @Override
            public void onFailure(HttpException e, String s) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "网络错误", Toast.LENGTH_SHORT).show();

            }
        });
    }

}
