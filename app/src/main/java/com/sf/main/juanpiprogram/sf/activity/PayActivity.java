package com.sf.main.juanpiprogram.sf.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;

import com.sf.main.juanpiprogram.R;
import com.sf.main.juanpiprogram.sf.alipay.PayResult;
import com.sf.main.juanpiprogram.sf.alipay.SignUtils;
import com.sf.main.juanpiprogram.sf.entities.Address;
import com.sf.main.juanpiprogram.sf.entities.Good;
import com.sf.main.juanpiprogram.sf.utils.DBHelper;
import com.sf.main.juanpiprogram.sf.utils.SwipeBackActivity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class PayActivity extends SwipeBackActivity implements Handler.Callback,View.OnClickListener {

    private Button pay_back;
    private Button pay_btn_alipay;
    private Button pay_btn_weixin;
    private Button pay_btn_weixin_friend;
    private Button pay_btn_yilian;
    private TextView pay_totalprice;
    private Button pay;
    private DbUtils utils;
    private List<Good> goods;

    //支付
    public static final String PARTNER = "2088911272543691";
    public static final String SELLER = "zhifubao01@0071515.com";
    public static final String RSA_PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKRqS6q0N/W1hjln87ijcQtFJNjUpMn/mUtcedLt7hGK74rwpo33LH5WIB/1Zv1lUTHTixbb0D9z/XA7bOPc8A9aForWZ6bteR+OeFR7CGvCYSamOhQE1QBIBzG72c/viqLsQtWoUVttpKE+0w8w5oC1rKJhPJ9vgXHHXFoVNfZLAgMBAAECgYEAg7TOxoE1rrPqQQQ/3bG39bI+8R9uno4NK6x5vnlw00a7204p7eGKF+5AL7my9dv6rfJrIjWV3a653/UKpoIvHqRdpQflvUZwZZ5P+AgE533K8beFFMwk2dzPZ/W6M28t/QyFnRga/p87N51Y3oKwAmyhOM2ymyERmq1btygTbpkCQQDSS6ttU3Wie3VU5lxyJ4QyQxOk7ztf6A1Mw75ImdMTmiV7zfiM9rMyzfGv3drZ7KuwWATqYwFHan3a16qWTNDNAkEAyCX0cbEHEX3IvLmHS4mi1o2i4KWJ6SHCXxlk51HAUXctPI9TBEnzy+dphIKuRfwBor0aG3ntLGlOA29W60iDdwJAO+VFfCvrM04KixXZesH6iv5D2BQzSwuizhxqU+9MCSc8SdjOVAfn3i5+CeMcdDlOZTmMnN15/cc89Vm7wnc6eQJAF50CvfcY0mN4r2tkHvFaGN4U/VQKdUraV8XzNadbfUEGQULXlIX1EXV36X5ReUhGGSEeV2eHJ9o0rgsQwimH+QJAf0FQC9DuHUxkIl+1fTig3NXkkqKLpxjsvChSFy3A5E4HkyOAQT1zThAihY1prpwieK8YMAmQBNYzjR2ob9MWIA==".replace(" ", "");
    private Handler handler=new Handler(this);
    private String result;
    private TextView pay_tv_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        initView();
        utils = DBHelper.getUtils();
        try {
            goods = utils.findAll(Good.class);
            getTotalPrice();
        } catch (DbException e) {
            e.printStackTrace();
        }
        pay_btn_alipay.setSelected(true);
        pay_back.setOnClickListener(this);
        pay_btn_alipay.setOnClickListener(this);
        pay_btn_weixin.setOnClickListener(this);
        pay_btn_weixin_friend.setOnClickListener(this);
        pay_btn_yilian.setOnClickListener(this);
        pay_tv_address.setOnClickListener(this);
        pay.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            Address address = DBHelper.getUtils().findById(Address.class, 0);
            if(address!=null){
                pay_tv_address.setText("收货地址："+"\n");
                pay_tv_address.append(address.getName());
                pay_tv_address.append(address.getNumber());
                pay_tv_address.append(address.getAddress());
            }

        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        pay_back = ((Button) findViewById(R.id.pay_back));
        pay_btn_alipay = ((Button) findViewById(R.id.pay_btn_alipay));
        pay_btn_weixin = ((Button) findViewById(R.id.pay_btn_weixin));
        pay_btn_weixin_friend = ((Button) findViewById(R.id.pay_btn_weixin_friend));
        pay_btn_yilian = ((Button) findViewById(R.id.pay_btn_yilian));
        pay_totalprice = ((TextView) findViewById(R.id.pay_totalprice));
        pay = ((Button) findViewById(R.id.pay));
        pay_tv_address = ((TextView) findViewById(R.id.pay_tv_address));
        pay_tv_address.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG );
    }
    private void getTotalPrice(){
        for (int i = 0; i < goods.size(); i++) {
            Good good = goods.get(i);
            int num = good.getNum();
            String cprice = good.getCprice();
            float total = (num) * Float.parseFloat(cprice);
            result = String.format("%.2f", total);
            pay_totalprice.setText("￥"+ result);

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.pay_back:
                ActivityCompat.finishAfterTransition(this);
                overridePendingTransition(0, R.anim.base_slide_right_out);
                break;
            case R.id.pay_btn_alipay:
                pay_btn_alipay.setSelected(true);
                pay_btn_weixin.setSelected(false);
                pay_btn_weixin_friend.setSelected(false);
                pay_btn_yilian.setSelected(false);
                break;
            case R.id.pay_btn_weixin:
                pay_btn_alipay.setSelected(false);
                pay_btn_weixin.setSelected(true);
                pay_btn_weixin_friend.setSelected(false);
                pay_btn_yilian.setSelected(false);
                break;
            case R.id.pay_btn_weixin_friend:
                pay_btn_alipay.setSelected(false);
                pay_btn_weixin.setSelected(false);
                pay_btn_weixin_friend.setSelected(true);
                pay_btn_yilian.setSelected(false);
                break;
            case R.id.pay_btn_yilian:
                pay_btn_alipay.setSelected(false);
                pay_btn_weixin.setSelected(false);
                pay_btn_weixin_friend.setSelected(false);
                pay_btn_yilian.setSelected(true);
                break;
            case R.id.pay:
                if ("请填写收货地址".equals(pay_tv_address.getText().toString())) {
                    Toast.makeText(PayActivity.this, "请填写收货地址", Toast.LENGTH_SHORT).show();
                }else {
                    String orderInfo = getOrderInfo(goods.get(0).getTitle(),null,result);
                    String sign = sign(orderInfo);
                    try {
                        sign= URLEncoder.encode(sign, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    orderInfo=orderInfo+"&sign=\""+sign+"\"&"+getSignType();
                    final String finalOrderInfo = orderInfo;
                    new Thread(){
                        @Override
                        public void run() {
                            PayTask payTask = new PayTask(PayActivity.this);
                            String pay = payTask.pay(finalOrderInfo);
                            handler.obtainMessage(0,pay).sendToTarget();
                        }
                    }.start();

                }
                
                break;

            case R.id.pay_tv_address:
                Intent intent=new Intent(this,AddressActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    /**
     * create the order info. 创建订单信息
     */
    public String getOrderInfo(String subject, String body, String price) {

        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + "http://notify.msp.hk/notify.htm"
                + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }


    /**
     * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
     */
    public String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss",
                Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);

        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 15);
        return key;
    }

    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content 待签名订单信息
     */
    public String sign(String content) {
        return SignUtils.sign(content, RSA_PRIVATE);
    }

    /**
     * get the sign type we use. 获取签名方式
     */
    public String getSignType() {
        return "sign_type=\"RSA\"";
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what){
            case 0:
                PayResult result = new PayResult((String) msg.obj);
                switch (result.getResultStatus()) {
                    case "9000":
                        Toast.makeText(this, "订单支付成功", Toast.LENGTH_SHORT).show();

                        break;
                    case "8000":
                        Toast.makeText(this, "正在处理中", Toast.LENGTH_SHORT).show();
                        break;
                    case "4000":
                        Toast.makeText(this, "订单支付失败", Toast.LENGTH_SHORT).show();
                        break;
                    case "6001":
                        Toast.makeText(this, "用户中途取消", Toast.LENGTH_SHORT).show();
                        break;
                    case "6002":
                        Toast.makeText(this, "网络出现错误", Toast.LENGTH_SHORT).show();
                        break;
                }
                break;
        }
        return true;
    }
}
