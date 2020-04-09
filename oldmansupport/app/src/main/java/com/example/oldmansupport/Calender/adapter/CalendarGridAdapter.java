package com.example.oldmansupport.Calender.adapter;

import java.util.ArrayList;

import com.example.oldmansupport.R;
import com.example.oldmansupport.database.CalendarTransfer;
import com.example.oldmansupport.Calender.calender.LunarCalendar;
import com.example.oldmansupport.Calender.calender.SpecialCalendar;
import com.example.oldmansupport.Calender.calender.DateUtil;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

@SuppressLint({ "SimpleDateFormat", "InflateParams", "DefaultLocale" })
public class CalendarGridAdapter extends BaseAdapter {
	private static final String TAG = "CalendarGridAdapter";
	private boolean isLeapyear = false;  //是否为闰年
	private int daysOfMonth = 0;      //某月的天数
	private int dayOfWeek = 0;        //具体某一天是星期几
	private int lastDaysOfMonth = 0;  //上一个月的总天数
	private Context mContext;
	private String[] dayNumber = new String[49];  //一个gridview中的日期存入此数组中
	private ArrayList<CalendarTransfer> transArray = new ArrayList<CalendarTransfer>();
	private static String week[] = {"周一","周二","周三","周四","周五","周六", "周日"};
	private LunarCalendar lc = null; 
	
	private String currentYear = "";
	private String currentMonth = "";
	private String currentDay = "";
	private int currentFlag = -1;     //用于标记当天
	
	//系统当前时间
	private String sysDate = "";  
	private String sys_year = "";
	private String sys_month = "";
	private String sys_day = "";
	
	public CalendarGridAdapter(Context context,int jumpMonth,int jumpYear,int year_c,int month_c,int day_c){
		mContext = context;
		sysDate = DateUtil.getNowDateTime("yyyy-M-d");
		sys_year = sysDate.split("-")[0];
		sys_month = sysDate.split("-")[1];
		sys_day = sysDate.split("-")[2];
		lc = new LunarCalendar();
		int stepYear = year_c+jumpYear;
		int stepMonth = month_c+jumpMonth ;
		Log.d(TAG, year_c+" ===  "+jumpYear+" ===  "+month_c+" ===   "+jumpMonth);
		if (stepMonth > 0){
			//往下一个月滑动
			if (stepMonth%12 == 0){
				stepYear = year_c + stepMonth/12 -1;
				stepMonth = 12;
			} else {
				stepYear = year_c + stepMonth/12;
				stepMonth = stepMonth%12;
			}
		} else {
			//往上一个月滑动
			stepYear = year_c - 1 + stepMonth/12;
			stepMonth = stepMonth%12 + 12;
			if (stepMonth%12 == 0){
				
			}
		}
		Log.d(TAG, year_c+" ===  "+jumpYear+" ===  "+month_c+" ===   "+jumpMonth);
		currentYear = String.valueOf(stepYear);;  //得到当前的年份
		currentMonth = String.valueOf(stepMonth);  //得到本月 （jumpMonth为滑动的次数，每滑动一次就增加一月或减一月）
		currentDay = String.valueOf(day_c);  //得到当前日期是哪天
		getCalendar(Integer.parseInt(currentYear),Integer.parseInt(currentMonth));
		Log.d(TAG, "end getCalendar");
	}
	
	@Override
	public int getCount() {
		return dayNumber.length;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (convertView == null){
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_calendar, null);
		}
		TextView tv_day = (TextView) convertView.findViewById(R.id.tv_day);
		String day = dayNumber[position].split("\\.")[0];
		String festival = dayNumber[position].split("\\.")[1];
		String itemText = day;
		if (position >= 7) {
			itemText = itemText + "\n" +festival;
		}
		tv_day.setText(itemText);
		tv_day.setTextColor(Color.GRAY);
		if (position<7){
			//设置周
			tv_day.setTextColor(Color.BLACK);
			tv_day.setBackgroundColor(Color.LTGRAY);
		} else {
			tv_day.setBackgroundColor(Color.WHITE);
		}
		if (position < daysOfMonth + dayOfWeek+7 -1 && position >= dayOfWeek+7 -1) {
			// 当前月信息显示
			if (checkHoliday(festival) == true) {
				tv_day.setTextColor(Color.BLUE);// 节日字体标蓝
			} else if ((position+1)%7==6 || (position+1)%7==0) {
				tv_day.setTextColor(Color.RED);// 周末字体标红
			} else {
				tv_day.setTextColor(Color.BLACK);// 当月字体设黑
			}
		}
		if (currentFlag == position){ 
			//设置当天的背景
			tv_day.setBackgroundColor(Color.GREEN);
		}
		return convertView;
	}
	
	public boolean checkHoliday(String text) {
		boolean result = true;
		if ((text.length() == 2 && (text.indexOf("月")>0 
					|| text.indexOf("初")>=0 
					|| text.indexOf("十")>=0 
					|| text.indexOf("廿")>=0 
					|| text.indexOf("卅")>=0 )) 
				|| ( text.length() == 3 && text.indexOf("月") > 0 )) {
			result = false;
		}
		return result;
	}
	
	//得到某年的某月的天数且这月的第一天是星期几
	public void getCalendar(int year, int month){
		isLeapyear = SpecialCalendar.isLeapYear(year);              //是否为闰年
		daysOfMonth = SpecialCalendar.getDaysOfMonth(isLeapyear, month);  //某月的总天数
		dayOfWeek = SpecialCalendar.getWeekdayOfMonth(year, month);      //某月第一天为星期几
		lastDaysOfMonth = SpecialCalendar.getDaysOfMonth(isLeapyear, month-1);  //上一个月的总天数
		Log.d(TAG, isLeapyear+" ======  "+daysOfMonth+"  ============  "+dayOfWeek+"  =========   "+lastDaysOfMonth);
		getweek(year,month);
	}
	
	//将一个月中的每一天的值添加入数组dayNumber中
	private void getweek(int year, int month) {
		int j = 1;
		String lunarDay = "";
		Log.d(TAG, "begin getweek");
		for (int i = 0; i < dayNumber.length; i++) {
			CalendarTransfer trans = new CalendarTransfer();
			int weekday = (i-7)%7+1;
			// 周一
			if (i<7){
				dayNumber[i]=week[i]+"."+" ";
			} else if (i < dayOfWeek+7-1){  //前一个月
				int temp = lastDaysOfMonth - dayOfWeek+1-7+1;
				trans = lc.getSubDate(trans, year, month-1, temp+i, weekday, false);
				lunarDay = trans.day_name;
				dayNumber[i] = (temp + i)+"."+lunarDay;
			} else if (i < daysOfMonth + dayOfWeek+7-1){   //本月
				int temp = i - dayOfWeek+1-7+1;
				String day = String.valueOf(temp);   //得到的日期
				trans = lc.getSubDate(trans, year, month, temp, weekday, false);
				trans = lc.getSubDate(trans, year,month,temp,weekday,false);
				lunarDay = trans.day_name;
				dayNumber[i] = temp+"."+lunarDay;
				//对于当前月才去标记当前日期
				if (sys_year.equals(String.valueOf(year)) && sys_month.equals(String.valueOf(month)) && sys_day.equals(day)){
					currentFlag = i;
				}
			} else {   //下一个月
				int next_month = month+1;
				int next_year = year;
				if (next_month >= 13) {
					next_month = 1;
					next_year++;
				}
				trans = lc.getSubDate(trans, next_year, next_month, j, weekday, false);
				lunarDay = trans.day_name;
				dayNumber[i] = j+"."+lunarDay;
				j++;
			}
			transArray.add(trans);
		}
        
        String abc = "";
        for(int i = 0; i < dayNumber.length; i++){
        	 abc = abc+dayNumber[i]+":";
        }
        Log.d(TAG, abc);
	}
	
	public CalendarTransfer getCalendarList(int pos) {
		return transArray.get(pos);
	}
}
