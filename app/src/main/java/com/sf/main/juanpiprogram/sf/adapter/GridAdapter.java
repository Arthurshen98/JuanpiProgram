package com.sf.main.juanpiprogram.sf.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.sf.main.juanpiprogram.R;
import com.sf.main.juanpiprogram.sf.entities.InfoEntity;
import com.sf.main.juanpiprogram.sf.utils.BitmapHelper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Administrator on 15-11-17.
 */
public class GridAdapter extends BaseAdapter {
    private Context context;
    private List<InfoEntity> list;

    public GridAdapter(List<InfoEntity> list, Context context) {
        if (list != null) {
            this.list = list;
        }else{
            this.list=new ArrayList<>();
        }
        this.context = context;
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
        return list.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView==null) {
            convertView= LayoutInflater.from(context).inflate(R.layout.grid_item,parent,false);
            convertView.setTag(new ViewHolder(convertView));
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        InfoEntity entity = list.get(position);
        if (TextUtils.isEmpty(entity.getTabname())) {
            holder.grid_title.setText(entity.getTitle());
            if (TextUtils.isEmpty(entity.getGoods_type())||"0".equals(entity.getGoods_type())) {
                holder.grid_cprice.setText(entity.getCoupon_tips());
                holder.grid_oprice.setVisibility(View.GONE);
            }else {
                holder.grid_cprice.setText("￥"+entity.getCprice());
            }
            holder.grid_oprice.setText(entity.getOprice());
            holder.grid_time_left.setText(entity.getTime_left());
            BitmapHelper.getUtils().display(holder.grid_imageView, entity.getPic_url());
            if (!TextUtils.isEmpty(entity.getPinpai_icon())) {
                BitmapHelper.getUtils().display(holder.grid_pinpai_icon,entity.getPinpai_icon());
            }else {
                holder.grid_pinpai_icon.setVisibility(View.GONE);
            }
        }


        return convertView;
    }
    public void addAll(Collection<? extends InfoEntity> collection){
        list.addAll(collection);
        notifyDataSetChanged();

    }
    public void clear(){
        list.clear();
        notifyDataSetChanged();
    }

    public static class ViewHolder{
        private final TextView grid_title;
        private final TextView grid_time_left;
        private final ImageView grid_imageView;
        private final ImageView grid_pinpai_icon;
        private final TextView grid_cprice;
        private final TextView grid_oprice;

        public ViewHolder(View itemView) {
            grid_cprice = ((TextView) itemView.findViewById(R.id.grid_cprice));
            grid_oprice = ((TextView) itemView.findViewById(R.id.grid_oprice));
            grid_oprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            grid_title = ((TextView) itemView.findViewById(R.id.grid_title));
            grid_time_left = ((TextView) itemView.findViewById(R.id.grid_time_left));
            grid_imageView = ((ImageView) itemView.findViewById(R.id.grid_imageView));
            grid_pinpai_icon = ((ImageView) itemView.findViewById(R.id.grid_pinpai_icon));
        }
    }
}
