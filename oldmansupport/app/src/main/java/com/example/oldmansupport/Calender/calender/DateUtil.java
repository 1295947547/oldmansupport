package com.example.oldmansupport.Calender.calender;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@SuppressLint("SimpleDateFormat")
public class DateUtil {
    @SuppressLint("SimpleDateFormat")
    public static String getNowDateTime(String formatStr) {
    	String format = formatStr;
    	if (format==null || format.length()<=0) {
    		format = "yyyyMMddHHmmss";
    	}
        SimpleDateFormat s_format = new SimpleDateFormat(format);
        return s_format.format(new Date());
    }

    @SuppressLint("SimpleDateFormat")
    public static String getNowTime() {
        SimpleDateFormat s_format = new SimpleDateFormat("HH:mm:ss");
        return s_format.format(new Date());
    }

    @SuppressLint("SimpleDateFormat")
    public static String getNowTimeDetail() {
        SimpleDateFormat s_format = new SimpleDateFormat("HH:mm:ss.SSS");
        return s_format.format(new Date());
    }

	public static String getNowDate() {
		SimpleDateFormat s_format = new SimpleDateFormat("yyyyMMdd");
		Date d_date = new Date();
		String s_date = "";
		s_date = s_format.format(d_date);
		return s_date;
	}

	public static String getNowYearCN() {
		SimpleDateFormat s_format = new SimpleDateFormat("yyyy年");
		Date d_date = new Date();
		String s_date = "";
		s_date = s_format.format(d_date);
		return s_date;
	}

	public static int getNowMonth() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.MONTH)+1;
	}

	public static String getAddDate(String str, long day_num) {
		SimpleDateFormat s_format = new SimpleDateFormat("yyyyMMdd");
		Date old_date = null;
		try {
			old_date = s_format.parse(str);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long time = old_date.getTime();
		long diff_time = day_num * 24 * 60 * 60 * 1000;
//		LogUtil.debug(TAG, "day_num="+day_num+", diff_time="+diff_time);
		time += diff_time;
		Date new_date = new Date(time);
		String s_date = s_format.format(new_date);
		return s_date;
	}

	public static int getWeekIndex(String s_date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		Date d_date = null;
		try {
			d_date = format.parse(s_date);
		} catch (Exception e) {
			e.printStackTrace();
			return 1;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(d_date);
		int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (week_index == 0) {
			week_index = 7;
		}
		return week_index;
	}

	public static boolean checkHoliday(String text) {
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
	
}
