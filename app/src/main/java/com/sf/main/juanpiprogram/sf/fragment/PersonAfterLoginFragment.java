package com.sf.main.juanpiprogram.sf.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sf.main.juanpiprogram.R;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonAfterLoginFragment extends Fragment implements View.OnClickListener {

    private TextView textView16;
    private LinearLayout linearLayout_person_center_bg_btn;
    public PersonAfterLoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_person_after_login, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textView16 = (TextView) view.findViewById(R.id.textView16);
        Bundle bundle = getArguments();
        String userName = bundle.getString("userName");
        String phoneNum = bundle.getString("phoneNum");
        if (!userName.equals("")) {
            textView16.setText(userName);
        }else {
            textView16.setText(phoneNum);
        }


        //点击跳转到个人资料
        linearLayout_person_center_bg_btn = (LinearLayout) view.findViewById(R.id.linearLayout_person_center_bg_btn);
        linearLayout_person_center_bg_btn.setOnClickListener(this);
    }

    /**
     * 点击跳转到个人资料
     */
    @Override
    public void onClick(View v) {

    }
}
