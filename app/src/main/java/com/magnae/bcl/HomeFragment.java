package com.magnae.bcl;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        AppCompatActivity activity = (AppCompatActivity) getActivity();

        TabLayout tabItems = view.findViewById(R.id.tabItems);
        ViewPager fragmentsPager = view.findViewById(R.id.fragmentsPager);
        fragmentsPager.setAdapter(new PagerAdapter(activity.getSupportFragmentManager()));
        tabItems.setupWithViewPager(fragmentsPager);

        return view;
    }

}
