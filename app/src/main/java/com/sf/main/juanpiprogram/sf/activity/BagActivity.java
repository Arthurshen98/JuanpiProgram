package com.sf.main.juanpiprogram.sf.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;

import com.sf.main.juanpiprogram.R;
import com.sf.main.juanpiprogram.sf.adapter.BagAdapter;
import com.sf.main.juanpiprogram.sf.entities.Good;
import com.sf.main.juanpiprogram.sf.utils.DBHelper;
import com.sf.main.juanpiprogram.sf.utils.SwipeBackActivity;

import java.util.List;

public class BagActivity extends SwipeBackActivity implements View.OnClickListener {

    private List<Good> goods = null;
    private ListView bag_listview;
    private BagAdapter adapter;
    private DbUtils utils;
    private Button bag_back;
    private TextView totalprice;
    private TextView shengqian;
    private Button jiesuan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bag);
        initView();
        bag_back.setOnClickListener(this);
        jiesuan.setOnClickListener(this);
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        utils = DBHelper.getUtils();
        try {
            goods = utils.findAll(Good.class);
            getTotalPrice();
        } catch (DbException e) {
            e.printStackTrace();
        }
        adapter = new BagAdapter(this, goods, url, new BagAdapter.CallBacka() {
            @Override
            public void abc() {
                try {
                    adapter.clear();
                    goods = utils.findAll(Good.class);
                    adapter.addAll(goods);
                    getTotalPrice();
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }
        });
        bag_listview.setAdapter(adapter);
    }

    private void initView() {
        bag_listview = ((ListView) findViewById(R.id.bag_listview));
        bag_back = ((Button) findViewById(R.id.bag_back));
        totalprice = ((TextView) findViewById(R.id.totalprice));
        shengqian = ((TextView) findViewById(R.id.shengqian));
        jiesuan = ((Button) findViewById(R.id.jiesuan));
    }

    private void getTotalPrice(){
        for (int i = 0; i < goods.size(); i++) {
            Good good = goods.get(i);
            int num = good.getNum();
            String cprice = good.getCprice();
            String oprice = good.getOprice();
            float total = (num) * Float.parseFloat(cprice);
            String result = String.format("%.2f", total);
            totalprice.setText("￥"+result);
            float v = (Float.parseFloat(oprice) - Float.parseFloat(cprice)) * num;
            String r = String.format("%.2f",v);
            shengqian.setText("(已省￥"+r+")");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bag_back:
                ActivityCompat.finishAfterTransition(this);
                overridePendingTransition(0, R.anim.base_slide_right_out);
                break;
            case R.id.jiesuan:
                if ("0.0".equals(totalprice.getText())) {
                    Toast.makeText(BagActivity.this, "没有商品，不能结算，请去选购商品", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent=new Intent(this,PayActivity.class);
                    startActivity(intent); 
                }
                
                break;
            default:
                break;
        }
    }
}
