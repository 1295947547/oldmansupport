package com.example.oldmansupport;

import com.example.oldmansupport.Calender.adapter.ShedulePagerAdapter;
import com.example.oldmansupport.Calender.calender.SpecialCalendar;
import com.example.oldmansupport.R;
import com.example.oldmansupport.Calender.adapter.ShedulePagerAdapter;
import com.example.oldmansupport.Calender.calender.SpecialCalendar;
import com.example.oldmansupport.Calender.calender.DateUtil;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.*;
import android.util.Log;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.PagerTabStrip;
import androidx.viewpager.widget.ViewPager;

public class ScheduleActivity extends FragmentActivity {
	private static final String TAG = "ScheduleActivity";
	public static String ACTION_FRAGMENT_SELECTED = "com.example.senior.ACTION_FRAGMENT_SELECTED";
	public static String EXTRA_SELECTED_WEEK = "selected_week";
	public static String ACTION_SHOW_FESTIVAL = "com.example.senior.ACTION_SHOW_FESTIVAL";
	public static String EXTRA_FESTIVAL_RES = "festival_res";

	private LinearLayout ll_schedule;
	private ViewPager vp_schedule;
	private int m_week;
	private int m_resid = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_schedule);
		PagerTabStrip pts_schedule = (PagerTabStrip) findViewById(R.id.pts_schedule);
		pts_schedule.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
		pts_schedule.setTextColor(Color.BLACK);
		ll_schedule = (LinearLayout) findViewById(R.id.ll_schedule);
		vp_schedule = (ViewPager) findViewById(R.id.vp_schedule);
		TextView tv_schedule = (TextView) findViewById(R.id.tv_schedule);
		tv_schedule.setText(DateUtil.getNowYearCN() + " 日程安排");

		ShedulePagerAdapter adapter = new ShedulePagerAdapter(getSupportFragmentManager());
		vp_schedule.setAdapter(adapter);
		m_week = SpecialCalendar.getTodayWeek();
		vp_schedule.setCurrentItem(m_week - 1);
		vp_schedule.addOnPageChangeListener(new SheduleChangeListener());
		mHandler.postDelayed(mFirst, 50);
	}

	private final Handler mHandler = new Handler();
	private Runnable mFirst = new Runnable() {
		@Override
		public void run() {
			/*sendBroadcast(m_week);*/
		}
	};

	/*private void sendBroadcast(int week) {
		Intent intent = new Intent(ACTION_FRAGMENT_SELECTED);
		intent.putExtra(EXTRA_SELECTED_WEEK, week);
		LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
	}*/


	/*public void onStart() {
		super.onStart();
		festivalReceiver = new FestivalControlReceiver();
		LocalBroadcastManager.getInstance(this)
				.registerReceiver(festivalReceiver, new IntentFilter(ACTION_SHOW_FESTIVAL));
	}*/


	/*public void onStop() {
		super.onStop();
		LocalBroadcastManager.getInstance(this).unregisterReceiver(festivalReceiver);
	}*/

	@Override
	protected void onResume() {
		super.onResume();
		if (m_resid != 0) { // 在横屏和竖屏之间翻转时，不会重新onCreate，只会onResume
			ll_schedule.setBackgroundResource(m_resid);
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	private FestivalControlReceiver festivalReceiver;
	private class FestivalControlReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent != null) {
				int resid = intent.getIntExtra(EXTRA_FESTIVAL_RES, 1);
				ll_schedule.setBackgroundResource(resid);
				m_resid = resid;
			}
		}
	}

	public class SheduleChangeListener implements ViewPager.OnPageChangeListener {

		@Override
		public void onPageSelected(int position) {
			Log.d(TAG, "onPageSelected position=" + position + ", m_week=" + m_week);
			m_week = position + 1;
			/*sendBroadcast(m_week);*/
		}

		@Override
		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	}

}
