package com.example.oldmansupport.Calender.fragment;

import java.util.ArrayList;

import com.example.oldmansupport.ScheduleActivity;
import com.example.oldmansupport.Calender.adapter.CalendarGridAdapter;
import com.example.oldmansupport.Calender.adapter.ScheduleListAdapter;
import com.example.oldmansupport.Calender.calender.Constant;
import com.example.oldmansupport.Calender.calender.DateUtil;
import com.example.oldmansupport.Calender.calender.SpecialCalendar;
import com.example.oldmansupport.R;
import com.example.oldmansupport.database.CalendarTransfer;
import com.example.oldmansupport.database.DbHelper;
import com.example.oldmansupport.database.ScheduleArrange;
import com.example.oldmansupport.database.ScheduleArrangeHelper;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

public class ScheduleFragment extends Fragment {
	private static final String TAG = "ScheduleFragment";
	protected View mView;
	protected Context mContext;

	private int m_week = 0;
	private int m_nowWeek = 0;
	private ListView lv_shedule;
	private com.example.oldmansupport.Calender.adapter.CalendarGridAdapter calV = null;
	private static int jumpMonth = 0;
	private static int jumpYear = 0;
	private int year_c = 0;
	private int month_c = 0;
	private int day_c = 0;
	private int first_pos = 0;
	private ArrayList<com.example.oldmansupport.database.CalendarTransfer> tranArray = new ArrayList<CalendarTransfer>();
	private String thisDate;


	private com.example.oldmansupport.database.ScheduleArrangeHelper arrangeHelper;
	private ArrayList<com.example.oldmansupport.database.ScheduleArrange> arrangeList = new ArrayList<ScheduleArrange>();

	public static ScheduleFragment newInstance(int seq) {
		ScheduleFragment fragment = new ScheduleFragment();
		Bundle bundle = new Bundle();
		bundle.putInt("week", seq);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mContext = getActivity();
		if (getArguments() != null) {
			m_week = getArguments().getInt("week", 1);
		}
		m_nowWeek = SpecialCalendar.getTodayWeek();
		initDate(m_week-m_nowWeek);
		Log.d(TAG, "thisDate="+thisDate+",fisrt_pos="+first_pos);
		Log.d(TAG, "jumpMonth="+jumpMonth+",jumpYear="+jumpYear+",year_c="+year_c+",month_c="+month_c+",day_c="+day_c);
		calV = new CalendarGridAdapter(mContext, jumpMonth, jumpYear, year_c, month_c, day_c);
		for (int i=first_pos; i<first_pos+7; i++) {
			CalendarTransfer trans = calV.getCalendarList(i);
			Log.d(TAG, "trans.solar_month="+trans.solar_month+",trans.solar_day="+trans.solar_day
					+",trans.lunar_month="+trans.lunar_month+",trans.lunar_day="+trans.lunar_day);
			tranArray.add(trans);
		}
		mView = inflater.inflate(R.layout.fragment_schedule, container, false);
		lv_shedule = (ListView) mView.findViewById(R.id.lv_shedule);
		return mView;
	}
	
	@SuppressLint("SimpleDateFormat")
	private void initDate(int diff_weeks) {
		String currentDate = DateUtil.getNowDateTime("yyyy-MM-dd");
		year_c = Integer.parseInt(currentDate.split("-")[0]);
		month_c = Integer.parseInt(currentDate.split("-")[1]);
		day_c = Integer.parseInt(currentDate.split("-")[2]);
		
		String nowDate = DateUtil.getNowDate();
		thisDate = DateUtil.getAddDate(nowDate, diff_weeks*7);
		int thisYear = Integer.valueOf(thisDate.substring(0, 4));
		int thisMonth = Integer.valueOf(thisDate.substring(4, 6));
		int thisDay = Integer.valueOf(thisDate.substring(6, 8));
		jumpYear = thisYear - year_c;
		if (jumpYear < 0) {
			jumpMonth = thisMonth - 12 - month_c;
		} else {
			jumpMonth = thisMonth - month_c;
		}
		int weekIndex = DateUtil.getWeekIndex(thisDate);
		int week_count = (int) Math.ceil((thisDay-weekIndex+0.5)/7.0);
		if ((thisDay-weekIndex) % 7 > 0) {
			//需要计算当天所在周是当月的第几周
			week_count ++;
		}
		if (thisDay-weekIndex < 0) {
			week_count ++;
		}
		first_pos = week_count*7;
	}
	
	private void checkFestival() {
		int i=0;
		for (; i<tranArray.size(); i++) {
			CalendarTransfer trans = tranArray.get(i);
			int j=0;
			for (; j< Constant.festivalArray.length; j++) {
				if (trans.day_name.indexOf(Constant.festivalArray[j]) >= 0) {
					sendFestival(Constant.festivalResArray[j]);
					break;
				}
			}
			if (j < Constant.festivalArray.length) {
				break;
			}
		}
		if (i >= tranArray.size()) {
			sendFestival(R.drawable.normal_day);
		}
	}
	
	private void sendFestival(int resid) {
		Intent intent = new Intent(ScheduleActivity.ACTION_SHOW_FESTIVAL);
		intent.putExtra(ScheduleActivity.EXTRA_FESTIVAL_RES, resid);
		/*LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);*/
	}

	private void startRefresh() {
		checkFestival();
	}

	@Override
	public void onStart() {
        scrollControlReceiver = new ScrollControlReceiver();
        /*BroadcastManager.getInstance(mContext).registerReceiver(scrollControlReceiver,
                new IntentFilter(ScheduleActivity.ACTION_FRAGMENT_SELECTED));*/
        
		super.onStart();
		arrangeHelper = new ScheduleArrangeHelper(mContext, DbHelper.db_name, null, 1);
		CalendarTransfer begin_trans = tranArray.get(0);
		String begin_day = String.format("%s%02d%02d", begin_trans.solar_year, begin_trans.solar_month, begin_trans.solar_day);
		CalendarTransfer end_trans = tranArray.get(tranArray.size()-1);
		String end_day = String.format("%s%02d%02d", end_trans.solar_year, end_trans.solar_month, end_trans.solar_day);
		arrangeList = (ArrayList<ScheduleArrange>) arrangeHelper.queryInfoByDayRange(begin_day, end_day);
		ScheduleListAdapter listAdapter = new ScheduleListAdapter(mContext, tranArray, arrangeList);
		lv_shedule.setAdapter(listAdapter);
		lv_shedule.setOnItemClickListener(listAdapter);
	}

	@Override
	public void onStop() {
		super.onStop();
        /*LocalBroadcastManager.getInstance(mContext).unregisterReceiver(scrollControlReceiver);*/
        arrangeHelper.close();
	}

    private ScrollControlReceiver scrollControlReceiver;
    private class ScrollControlReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                int selectedWeek = intent.getIntExtra(ScheduleActivity.EXTRA_SELECTED_WEEK, 1);
                Log.d(TAG, "onReceive selectedWeek="+selectedWeek+", m_week="+m_week);
                if (m_week == selectedWeek) {
                	startRefresh();
                }
            }
        }
    }
    
}
