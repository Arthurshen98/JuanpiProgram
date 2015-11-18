package com.sf.main.juanpiprogram.sf.activity;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;
import com.sf.main.juanpiprogram.R;
import com.sf.main.juanpiprogram.sf.utils.DbHelper;
import com.sf.main.juanpiprogram.sf.entities.SearchHistoryData;
import com.sf.main.juanpiprogram.sf.fragment.SearchDateFragment;
import com.sf.main.juanpiprogram.sf.fragment.SearchHistoryFragment;

public class SearchGoodsActivity extends FragmentActivity {

    /**
     *
     * fragment事物
     */
    private FragmentManager fmanager;
    private FragmentTransaction ftransaction;

    /**
     *
     *  删除文本图标+edittext+textview
     */
    private ImageView deleteTextIcon;
    private EditText searchContent;
    private TextView sousuoText,cancalText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉actionBar
        setContentView(R.layout.left_menu_search_goods);

        //用于删除搜索文本
        deleteEdittextContent();
        //加载第一个含ListView的Fragment
        loadingFirstFragment();
        //对点击搜索进行监听并改变
        onclickSousuoChangeFragment();


    }


    /**
     * 用于删除搜索文本
     */
    private void deleteEdittextContent() {
        deleteTextIcon = (ImageView) findViewById(R.id.imageView_delect_text);
        searchContent = (EditText) findViewById(R.id.editText_search);

        //在一开始icon就是隐藏的
        deleteTextIcon.setVisibility(View.GONE);
        //点击按钮删除文本
        deleteTextIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchContent.setText("");
                sousuoText.setVisibility(View.GONE);
                cancalText.setVisibility(View.VISIBLE);
                loadingFirstFragment();
            }
        });

        //处理添加文字时候的改变监听
        searchContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                sousuoText.setVisibility(View.VISIBLE);
                cancalText.setVisibility(View.GONE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (searchContent.getText().toString().equals("")) {
                    sousuoText.setVisibility(View.GONE);
                    cancalText.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    deleteTextIcon.setVisibility(View.GONE);
                } else {
                    deleteTextIcon.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    /**
     * 加载第一个含ListView的Fragment
     */
    private void loadingFirstFragment() {
        fmanager = getSupportFragmentManager();
        ftransaction = fmanager.beginTransaction();
        SearchHistoryFragment historyFragment = new SearchHistoryFragment();
       ftransaction.replace(R.id.relativeLayout_search_content, historyFragment);
        ftransaction.commit();
    }
    /**
     * 对点击搜索进行监听并改变
     */
    private void onclickSousuoChangeFragment() {
        sousuoText = (TextView) findViewById(R.id.text_search);
        cancalText = (TextView) findViewById(R.id.text_search_cancal);
            sousuoText.setVisibility(View.GONE);
            cancalText.setVisibility(View.VISIBLE);
        //对搜索设置点击监听
        sousuoText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                fmanager = getSupportFragmentManager();
                ftransaction = fmanager.beginTransaction();
                SearchDateFragment dataFragment = new SearchDateFragment();
                ftransaction.replace(R.id.relativeLayout_search_content, dataFragment);
                ftransaction.commit();
                //将输入的数据存入数据库
                DbUtils utils = DbHelper.getUtils();
                // List<SearchHistoryData> list = new ArrayList<SearchHistoryData>();
                try {

                    String str = searchContent.getText().toString();
                    if (str.equals("")) {
                         return;
                    } else {
                        utils.saveOrUpdate(new SearchHistoryData(str));
                    }

                } catch (DbException e) {
                    e.printStackTrace();
                }

                //点击之后就隐藏“搜索”，“取消”显示
                sousuoText.setVisibility(View.GONE);
                cancalText.setVisibility(View.VISIBLE);
            }
        });

        //对取消设置监听
        cancalText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击取消后就关闭页面
                finish();
            }
        });
    }



}
