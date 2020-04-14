package com.example.oldmansupport.Calender.adapter;

import java.util.ArrayList;

import com.example.oldmansupport.ScheduleDetailActivity;
import com.example.oldmansupport.Calender.calender.Constant;
import com.example.oldmansupport.Calender.calender.DateUtil;
import com.example.oldmansupport.R;
import com.example.oldmansupport.database.CalendarTransfer;
import com.example.oldmansupport.database.ScheduleArrange;
import com.example.oldmansupport.Calender.calender.LunarCalendar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

@SuppressLint("DefaultLocale")
public class ScheduleListAdapter extends BaseAdapter implements OnItemClickListener {
	private static final String TAG = "ScheduleListAdapter";
	private Context mContext;
	private LayoutInflater mInflater;
	private ArrayList<CalendarTransfer> tranArray = new ArrayList<CalendarTransfer>();
	private ArrayList<ScheduleArrange> arrangeList = new ArrayList<ScheduleArrange>();

	public ScheduleListAdapter(Context context, ArrayList<CalendarTransfer> tranArray,
			ArrayList<ScheduleArrange> arrangeList) {
		this.mInflater = LayoutInflater.from(context);
		this.mContext = context;
		this.tranArray = tranArray;
		this.arrangeList = arrangeList;
	}

	@Override
	public int getCount() {
		return tranArray.size();
	}

	@Override
	public Object getItem(int position) {
		return tranArray.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_schedule, null);
			holder.week_number = (TextView) convertView.findViewById(R.id.week_number);
			holder.week_shedule = (TextView) convertView.findViewById(R.id.week_shedule);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.week_number.setText(Constant.weekArray[position]);
		if (position < 5) {
			holder.week_number.setTextColor(Color.BLACK);
		} else {
			holder.week_number.setTextColor(Color.RED);
		}
		CalendarTransfer trans = tranArray.get(position);
		String day = String.format("%s%02d%02d", trans.solar_year, trans.solar_month, trans.solar_day);
		String arrangeTitle = "";
		int i = 0;
		for (; i<arrangeList.size(); i++) {
			if (arrangeList.get(i).day.equals(day) == true) {
				ScheduleArrange item = arrangeList.get(i);
				arrangeTitle = String.format("%s时%s分：%s", item.hour, item.minute, item.title);
				break;
			}
		}
		if (i >= arrangeList.size()) {
			arrangeTitle = "今日暂无日程安排";
		}
		String solar_date = String.format("%d月%d日", trans.solar_month, trans.solar_day);
		String lunar_date = String.format("农历%s月%s", LunarCalendar.chineseNumber[trans.lunar_month-1], LunarCalendar.getChinaDayString(trans.lunar_day));
		String holiday = "";
		if (DateUtil.checkHoliday(trans.day_name) == true) {
			holiday = trans.day_name;
		}
		String content = String.format("%s %s %s\n%s", solar_date, lunar_date, holiday, arrangeTitle);
		holder.week_shedule.setText(content);
		String now = DateUtil.getNowDate();
		if (now.equals(day) == true) {
			holder.week_shedule.setTextColor(Color.BLUE);
		} else {
			holder.week_shedule.setTextColor(Color.BLACK);
		}
		return convertView;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Log.d(TAG, "onItemClick position="+position);
		CalendarTransfer trans = tranArray.get(position);
		String day2 = String.format("%s%02d%02d", trans.solar_year, trans.solar_month, trans.solar_day);
		String solar_date2 = String.format("%s年%d月%d日", trans.solar_year, trans.solar_month, trans.solar_day);
		String lunar_date2 = String.format("农历%s月%s", LunarCalendar.chineseNumber[trans.lunar_month-1], LunarCalendar.getChinaDayString(trans.lunar_day));
		String holiday2 = "";
		if (DateUtil.checkHoliday(trans.day_name) == true) {
			holiday2 = trans.day_name;
		}
		Intent intent = new Intent(mContext, ScheduleDetailActivity.class);
		Bundle bundle = new Bundle();
		bundle.putString("day", day2);
		bundle.putString("solar_date", solar_date2);
		bundle.putString("lunar_date", lunar_date2);
		bundle.putString("week", Constant.weekArray[position]);
		bundle.putString("holiday", holiday2);
		intent.putExtras(bundle);
		mContext.startActivity(intent);
	}

	public final class ViewHolder {
		public TextView week_number;
		public TextView week_shedule;
	}

}
