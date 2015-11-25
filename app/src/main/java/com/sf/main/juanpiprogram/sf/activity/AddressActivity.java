package com.sf.main.juanpiprogram.sf.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.exception.DbException;

import com.sf.main.juanpiprogram.R;
import com.sf.main.juanpiprogram.sf.entities.Address;
import com.sf.main.juanpiprogram.sf.utils.DBHelper;
import com.sf.main.juanpiprogram.sf.utils.SwipeBackActivity;

import java.util.ArrayList;
import java.util.List;

public class AddressActivity extends SwipeBackActivity implements View.OnClickListener {

    private TextView address_save;
    private Button address_back;
    private EditText address_et_people;
    private EditText address_et_num;
    private EditText address_et;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        initView();
        address_back.setOnClickListener(this);
        address_save.setOnClickListener(this);
    }

    private void initView() {
        address_save = ((TextView) findViewById(R.id.address_save));
        address_back = ((Button) findViewById(R.id.address_back));
        address_et_people = ((EditText) findViewById(R.id.address_et_people));
        address_et_num = ((EditText) findViewById(R.id.address_et_num));
        address_et = ((EditText) findViewById(R.id.address_et));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.address_back:
                finish();
                break;
            case R.id.address_save:

                String name=address_et_people.getText().toString().trim();
                String number=address_et_num.getText().toString().trim();
                String address=address_et.getText().toString().trim();
                List<Address> list=new ArrayList<>();
                list.add(new Address("0",name,number,address));
                try {
                    DBHelper.getUtils().saveOrUpdateAll(list);
                } catch (DbException e) {
                    e.printStackTrace();
                }
                if(name.equals("")&&number.equals("")&&address.equals("")){
                    Toast.makeText(AddressActivity.this, "请填全信息", Toast.LENGTH_SHORT).show();
                }else {

                    Toast.makeText(AddressActivity.this, "添加地址成功", Toast.LENGTH_SHORT).show();
                    finish();
                }


                break;
            default:
                break;
        }
    }
}
