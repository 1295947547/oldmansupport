package com.example.oldmansupport.Desktop;

import androidx.annotation.NonNull;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

public class DesktopSlideAdapter extends FragmentStateAdapter {

    private List<Fragment> mFragments;

    public DesktopSlideAdapter(FragmentActivity fa,List<Fragment> fragments){
        super(fa);
        this.mFragments=fragments;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position){
        return mFragments.get(position);
    }

    @Override
    public int getItemCount(){
        return mFragments.size();
    }
}

