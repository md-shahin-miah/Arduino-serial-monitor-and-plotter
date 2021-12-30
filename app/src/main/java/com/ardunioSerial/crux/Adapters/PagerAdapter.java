package com.ardunioSerial.crux.Adapters;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.ardunioSerial.crux.AdWebViewFragment;
import com.ardunioSerial.crux.AdWebViewJlcPcbFragment;
import com.ardunioSerial.crux.ui.Fragment.FirstFragment;
import com.ardunioSerial.crux.ui.Fragment.SecondFragment;
import com.ardunioSerial.crux.ui.Fragment.ThirdFragment;

//import com.mshahinx.myapplicationcruxone.ui.Fragment.FirstFragment;
//import com.mshahinx.myapplicationcruxone.ui.Fragment.SecondFragment;
//import com.mshahinx.myapplicationcruxone.ui.Fragment.ThirdFragment;

public class PagerAdapter extends FragmentPagerAdapter {
    private int tabsNumber;

    public PagerAdapter(@NonNull FragmentManager fm, int behavior, int tabs) {
        super(fm, behavior);
        this.tabsNumber = tabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
               return new FirstFragment();
            case 1:
            return new SecondFragment();
            case 2 :
               return new ThirdFragment();
            case 3 :
                return new AdWebViewJlcPcbFragment();
            case 4 :
                return new AdWebViewFragment();
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return tabsNumber;
    }
}