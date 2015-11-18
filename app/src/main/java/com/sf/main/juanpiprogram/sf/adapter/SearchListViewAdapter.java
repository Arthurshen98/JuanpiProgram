package com.sf.main.juanpiprogram.sf.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sf.main.juanpiprogram.R;
import com.sf.main.juanpiprogram.sf.entities.SearchHistoryData;

import java.util.List;

/**
 * 搜索历史记录的自定义适配器
 */
public class SearchListViewAdapter extends BaseAdapter {
   private List<SearchHistoryData> list = null;
   private Context context;

    public SearchListViewAdapter(Context context ,List<SearchHistoryData> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
       int count = 0 ;
        if(list!=null){
            count = list.size();
        }
        return count;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.search_history_data_listview_item, parent, false);
            ViewHolder holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        final  ViewHolder holder = (ViewHolder) convertView.getTag();
        int id = list.get(position).getId();
        String searchdata = list.get(position).getSearchData();
        holder.hisData.setText(searchdata);
        return convertView;
    }

    public static class ViewHolder{
        public TextView hisData;
        public ViewHolder(View view) {
            hisData = (TextView) view.findViewById(R.id.textView_search_his_data_listview);
        }
    }
}
