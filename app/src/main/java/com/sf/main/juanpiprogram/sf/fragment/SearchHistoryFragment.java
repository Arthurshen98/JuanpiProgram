package com.sf.main.juanpiprogram.sf.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;
import com.sf.main.juanpiprogram.R;
import com.sf.main.juanpiprogram.sf.adapter.SearchListViewAdapter;
import com.sf.main.juanpiprogram.sf.utils.BaseApplication;
import com.sf.main.juanpiprogram.sf.utils.DbHelper;
import com.sf.main.juanpiprogram.sf.entities.SearchHistoryData;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchHistoryFragment extends Fragment {

    private DbUtils utils = DbHelper.getUtils();
    private List<SearchHistoryData> list ;
    private ListView searchListView;

    /**
     *
     *  清除数据按钮
     */
    private Button clearButton;
    private SearchListViewAdapter searchAdapter;
    public SearchHistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        //条件查询
        try {
          //  WhereBuilder builder = WhereBuilder.b("id", "=", "?");
          //  Selector selector = Selector.from(SearchHistoryData.class).where(builder);
         //   list = utils.findAll(selector);

            list = utils.findAll(SearchHistoryData.class);

        } catch (DbException e) {
            e.printStackTrace();
        }
        //给ListView配置数据
        initListViewData(view);

        //清除历史数据
        clearHistoryData(view);

        //最后对ListView进行监听
        onclickListview(view);
        
    }

    /**
     * 清除历史数据
     */
    private void clearHistoryData(final View view) {
        clearButton = (Button) view.findViewById(R.id.button_clear_history_data);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    utils.deleteAll(SearchHistoryData.class);
                } catch (DbException e) {
                    e.printStackTrace();
                }

                list.clear();
                initListViewData(view);
                searchAdapter.notifyDataSetChanged();
            }

        });

    }

    /**
     * 给ListView配置数据
     */
    private void initListViewData(View view) {
        searchListView = (ListView) view.findViewById(R.id.listView_search_history);
        searchAdapter = new SearchListViewAdapter(BaseApplication.getContext(), list);
        searchListView.setAdapter(searchAdapter);
      // ArrayAdapter<SearchHistoryData> adatper = new ArrayAdapter<SearchHistoryData>(BaseApplication.getContext(),android.R.layout.simple_list_item_1,list);
     //  searchListView.setAdapter(adatper);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_history, container, false);

    }

    /**
     * 最后对ListView进行监听
     */
    private static int listId  ;
    private FragmentManager fmanager;
    private FragmentTransaction ftransaction;
    private void onclickListview(View view) {

        searchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listId = list.get(position).getId();
                fmanager = getFragmentManager();
                ftransaction = fmanager.beginTransaction();
                SearchDateFragment dataFragment = new SearchDateFragment();
                ftransaction.replace(R.id.relativeLayout_search_content, dataFragment);
                ftransaction.commit();

            }
        });
    }



}
