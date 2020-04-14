package com.example.oldmansupport.Calender.adapter;

import com.example.oldmansupport.Calender.calender.Constant;
import com.example.oldmansupport.Calender.calender.Constant;
import com.example.oldmansupport.Calender.fragment.ScheduleFragment;

import android.support.v4.app.*;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ShedulePagerAdapter extends FragmentStatePagerAdapter {

	public ShedulePagerAdapter(FragmentManager fm) {
		super(fm);
	}

	public int getCount() {
		return 52;
	}

	public androidx.fragment.app.Fragment getItem(int position) {
		return  ScheduleFragment.newInstance(position +1);
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return new String("第" + Constant.xuhaoArray[position + 1] + "周");
	}

}
