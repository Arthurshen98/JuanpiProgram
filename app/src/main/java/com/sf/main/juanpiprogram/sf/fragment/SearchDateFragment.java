package com.sf.main.juanpiprogram.sf.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sf.main.juanpiprogram.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchDateFragment extends Fragment {


    public SearchDateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_date, container, false);
    }

}
