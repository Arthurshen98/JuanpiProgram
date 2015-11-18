package com.sf.main.juanpiprogram.sf.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.sf.main.juanpiprogram.R;
import com.sf.main.juanpiprogram.sf.activity.LoginActivity;
import com.sf.main.juanpiprogram.sf.activity.LogisticActivity;
import com.sf.main.juanpiprogram.sf.activity.RegisterActivity;
import com.sf.main.juanpiprogram.sf.utils.BaseApplication;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonBeforeLoginFragment extends Fragment implements View.OnClickListener {

    private Button loginbtn,registerbtn;
    public PersonBeforeLoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_person_before_login, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loginbtn = (Button) view.findViewById(R.id.button_person_login);
        registerbtn = (Button) view.findViewById(R.id.button_person_resiger);
        loginbtn.setOnClickListener(this);
        registerbtn.setOnClickListener(this);
    }

    /**
     * 登录，注册
     */
    @Override
    public void onClick(View v) {
        Intent log,res;
        switch (v.getId()) {
            case R.id.button_person_login:
                log = new Intent(BaseApplication.getContext(), LoginActivity.class);
                startActivity(log);
                break;
            case R.id.button_person_resiger:
                res = new Intent(BaseApplication.getContext(), RegisterActivity.class);
                startActivity(res);
                break;
        }
    }
}
