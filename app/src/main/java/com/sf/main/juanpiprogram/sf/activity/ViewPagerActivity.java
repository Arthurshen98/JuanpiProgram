package com.sf.main.juanpiprogram.sf.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
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

public class ViewPagerActivity extends AppCompatActivity {

    private WebView webView;
    private String url;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        webView = ((WebView) findViewById(R.id.webView));
        url = getIntent().getStringExtra("url");
        HttpUtils utils = new HttpUtils(10000);
        dialog = new ProgressDialog(this);
        dialog.setMessage("加载中");
        dialog.show();
        utils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                //InfoEntity object = JSON.parseObject(responseInfo.result, InfoEntity.class);
                webView.loadDataWithBaseURL(null, responseInfo.result, "text/html", "UTF-8", null);
                dialog.dismiss();

            }
            @Override
            public void onFailure(HttpException e, String s) {
                e.printStackTrace();
                Toast.makeText(ViewPagerActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }
}
