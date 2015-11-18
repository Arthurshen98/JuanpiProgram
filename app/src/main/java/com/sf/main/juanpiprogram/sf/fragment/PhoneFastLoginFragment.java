package com.sf.main.juanpiprogram.sf.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sf.main.juanpiprogram.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PhoneFastLoginFragment extends Fragment {


    public PhoneFastLoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_phone_fast_login, container, false);
    }


}
