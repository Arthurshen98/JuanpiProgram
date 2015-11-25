package com.sf.main.juanpiprogram.sf.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;
import com.sf.main.juanpiprogram.R;
import com.sf.main.juanpiprogram.sf.activity.DetailActivity;
import com.sf.main.juanpiprogram.sf.entities.Good;
import com.sf.main.juanpiprogram.sf.utils.BitmapHelper;
import com.sf.main.juanpiprogram.sf.utils.DBHelper;


import java.util.Collection;
import java.util.List;

/**
 * Created by Administrator on 2015/11/21.
 */
public class BagAdapter extends BaseAdapter {
    private Context context;
    private List<Good> list;
    private String url;
    private AlertDialog dialog;
    private CallBacka callBacka;

    public BagAdapter(Context context, List<Good> list, String url, CallBacka callBacka) {
        this.context = context;
        this.list = list;
        this.url = url;
        this.callBacka = callBacka;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.listview_item, parent, false);
            convertView.setTag(new ViewHolder(convertView));
        }
        final ViewHolder holder = (ViewHolder) convertView.getTag();
        final Good good = list.get(position);
        holder.bag_title.setText(good.getTitle());
        holder.bag_cprice.setText("￥" + good.getCprice());
        holder.bag_oprice.setText("￥" + good.getOprice());
        holder.bag_oprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        BitmapHelper.getUtils().display(holder.bag_imag, url);
        getTotalPrice(holder, good);
        holder.bag_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                context.startActivity(intent);
            }
        });
        holder.bag_subtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (good.getNum() > 0) {
                    good.setNum(good.getNum() - 1);
                }else {
                    good.setNum(0);
                }

                getNum(good, holder);
                callBacka.abc();
            }
        });
        holder.bag_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                good.setNum(good.getNum() + 1);
                getNum(good, holder);
                callBacka.abc();
            }
        });
        holder.bag_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                initDialog();
                dialog.show();
            }
        });


        return convertView;
    }

    //获取总价格
    private void getTotalPrice(ViewHolder holder, Good good) {
        float total = (good.getNum()) * Float.parseFloat(good.getCprice());
        String result = String.format("%.2f", total);
        holder.bag_total_price.setText("总价" + "￥" + result);
        holder.bag_num.setText(good.getNum() + "");
    }

    //获取商品数量
    private void getNum(Good good, ViewHolder holder) {
        DbUtils utils = DBHelper.getUtils();
        try {
            utils.delete(Good.class, WhereBuilder.b("goods_id", "=", good.getGoods_id()));
            utils.saveOrUpdate(good);
            holder.bag_num.setText(good.getNum() + "");
        } catch (DbException e) {
            e.printStackTrace();
        }
        getTotalPrice(holder, good);
    }

    public void addAll(Collection<? extends Good> collection) {
        list.addAll(collection);
        notifyDataSetChanged();

    }

    public void clear() {
        list.clear();
        notifyDataSetChanged();
    }

    private void initDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        //使用对话框的构建器对象来设置对话框的属性
        builder.setTitle("提示")
                .setMessage("宝贝库存有限，确定要删除吗？")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)//设置点击对话框之外的区域时，是否取消该对话框
                .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            DBHelper.getUtils().deleteAll(Good.class);
                            callBacka.abc();
                        } catch (DbException e) {
                            e.printStackTrace();
                        }
                    }
                })
                .setNegativeButton("取消", null);

        //生成对话框
        dialog = builder.create();
    }

    public static class ViewHolder {


        private final ImageView bag_imag;
        private final TextView bag_title;
        private final TextView bag_cprice;
        private final TextView bag_oprice;
        private final TextView bag_del;
        private final TextView bag_total_price;
        private final Button bag_num;
        private final Button bag_subtract;
        private final Button bag_add;


        public ViewHolder(View itemView) {
            bag_imag = ((ImageView) itemView.findViewById(R.id.bag_imag));
            bag_title = ((TextView) itemView.findViewById(R.id.bag_title));
            bag_cprice = ((TextView) itemView.findViewById(R.id.bag_cprice));
            bag_oprice = ((TextView) itemView.findViewById(R.id.bag_oprice));
            bag_del = ((TextView) itemView.findViewById(R.id.bag_del));
            bag_total_price = ((TextView) itemView.findViewById(R.id.bag_total_price));
            bag_num = ((Button) itemView.findViewById(R.id.bag_num));
            bag_subtract = ((Button) itemView.findViewById(R.id.bag_subtract));
            bag_add = ((Button) itemView.findViewById(R.id.bag_add));
        }
    }

    public interface CallBacka {
        public void abc();
    }
}
