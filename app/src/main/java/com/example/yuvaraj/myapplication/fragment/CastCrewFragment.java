package com.example.yuvaraj.myapplication.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yuvaraj.myapplication.NavDrawerInterface;
import com.example.yuvaraj.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class CastCrewFragment extends Fragment {


    NavDrawerInterface interfacelistener;

    ViewPagerAdapter adapter;
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();
    AppCompatActivity activity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cast_crew, container, false);

        Toolbar toolbar = (Toolbar)view.findViewById(R.id.toolbar);
         activity = ((AppCompatActivity) getActivity());
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                interfacelistener.showNavDrawer();
            }
        });

        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        setHasOptionsMenu(false);
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            RelativeLayout relativeLayout = (RelativeLayout)
                    LayoutInflater.from(activity).inflate(R.layout.tab_layout, tabLayout, false);

            TextView tabTextView = (TextView) relativeLayout.findViewById(R.id.tab_title);
            tabTextView.setText(tab.getText());
            tab.setCustomView(relativeLayout);

                //tab.select();

        }
        tabLayout.getTabAt(0).getCustomView().setSelected(true);
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            interfacelistener = (NavDrawerInterface) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement NavDrawer");
        }
    }



    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFrag(new DummyFragment(), "CAST");
        adapter.addFrag(new DummyFragment(), "CREW");
        viewPager.setAdapter(adapter);
    }



    class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }
        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }
        @Override
        public int getCount() {
            return mFragmentList.size();
        }
        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

    }

}


