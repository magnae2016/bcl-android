package com.magnae.bcl;


import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.Toolbar;


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
