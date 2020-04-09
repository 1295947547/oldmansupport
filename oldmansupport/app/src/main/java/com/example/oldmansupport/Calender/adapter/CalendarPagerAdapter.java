package com.example.oldmansupport.Calender.adapter;

import android.support.v4.app.*;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.oldmansupport.Calender.calender.Constant;
import com.example.oldmansupport.Calender.fragment.CalendarFragment;
import com.example.oldmansupport.Calender.calender.Constant;
import com.example.oldmansupport.Calender.fragment.CalendarFragment;

public class CalendarPagerAdapter extends FragmentStatePagerAdapter {

	public CalendarPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	public int getCount() {
		return 12;
	}

	public Fragment getItem(int position) {
		return CalendarFragment.newInstance(position + 1);
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return new String(Constant.xuhaoArray[position + 1] + "月");
	}

}
