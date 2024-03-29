package com.intacta.doctoring.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.intacta.doctoring.fragments.ServicesFragment;
import com.intacta.doctoring.fragments.ClientFragment;
import com.intacta.doctoring.fragments.HomeFragment;
import com.intacta.doctoring.fragments.NotificationsFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {



    public ViewPagerAdapter(@NonNull FragmentManager fm ) {
        super(fm);

    }


    @Override
    public int getCount() {
       return 4;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new HomeFragment();
            case 1: return new ClientFragment();
            case 2: return new NotificationsFragment();
            case 3: return new ServicesFragment();
            default: return new HomeFragment();
        }
    }





 }
