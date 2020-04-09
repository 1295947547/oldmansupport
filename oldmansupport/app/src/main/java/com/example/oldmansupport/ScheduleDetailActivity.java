package com.example.oldmansupport;

import java.util.Calendar;
import java.util.List;

import com.example.oldmansupport.R;
import com.example.oldmansupport.database.ScheduleArrange;
import com.example.oldmansupport.database.DbHelper;
import com.example.oldmansupport.database.ScheduleArrangeHelper;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.*;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import androidx.appcompat.app.AppCompatActivity;

public class ScheduleDetailActivity extends AppCompatActivity implements OnClickListener, OnTimeSetListener {
	private static final String TAG = "ScheduleDetailActivity";
	private Button btn_back, btn_edit, btn_save;
	private TextView schedule_date, schedule_time;
	private Spinner schedule_alarm;
	private EditText schedule_title, schedule_content;
	private String month, day, week, holiday, solar_date, lunar_date, detail_date;
	private ScheduleArrange arrange;
	private ScheduleArrangeHelper arrangeHelper;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_schedule_detail);
		btn_back = (Button) findViewById(R.id.btn_back);
		btn_edit = (Button) findViewById(R.id.btn_edit);
		btn_save = (Button) findViewById(R.id.btn_save);
		btn_back.setOnClickListener(this);
		btn_edit.setOnClickListener(this);
		btn_save.setOnClickListener(this);
		
		schedule_date = (TextView) findViewById(R.id.schedule_date);
		schedule_time = (TextView) findViewById(R.id.schedule_time);
		schedule_title = (EditText) findViewById(R.id.schedule_title);
		schedule_content = (EditText) findViewById(R.id.schedule_content);
		schedule_time.setText("00:00");
		schedule_time.setOnClickListener(this);

		Bundle req = this.getIntent().getExtras();
		day = req.getString("day");
		solar_date = req.getString("solar_date");
		lunar_date = req.getString("lunar_date");
		month = day.substring(0, 6);
		week = req.getString("week");
		holiday = req.getString("holiday");
		detail_date = String.format("%s %s\n%s", solar_date, lunar_date, week);
		if (holiday!= null && holiday.length()>0) {
			detail_date = String.format("%s，今天是 %s", detail_date, holiday);
		}
		schedule_date.setText(detail_date);
		Log.d(TAG, "month="+month+",day="+day+",solar_date="+solar_date+",lunar_date="
				+lunar_date+",week="+week+",holiday="+holiday);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.item_select, alarmArray);
		schedule_alarm = (Spinner) findViewById(R.id.schedule_alarm);
		schedule_alarm.setPrompt("请选择提醒间隔");
		schedule_alarm.setAdapter(adapter);
		schedule_alarm.setSelection(0);
		schedule_alarm.setOnItemSelectedListener(new AlarmSelectedListener());
	}

	private String[] alarmArray = {"不提醒", "提前5分钟", "提前10分钟", 
			"提前15分钟", "提前半小时", "提前1小时", "当前时间后10秒"};
	private int[] advanceArray = {0, 5, 10, 15, 30, 60, 10};
	private int alarmType = 0;
	class AlarmSelectedListener implements OnItemSelectedListener {
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			alarmType = arg2;
		}

		public void onNothingSelected(AdapterView<?> arg0) {
		}
	}

	@Override  
	protected void onResume() {  
	    super.onResume();
	    arrange = new ScheduleArrange();
	    arrangeHelper = new ScheduleArrangeHelper(this, DbHelper.db_name, null, 1);
		List<ScheduleArrange> arrangeList = (List<ScheduleArrange>) arrangeHelper.queryInfoByDay(day);
		if (arrangeList.size() >= 1) {
			enableEdit(false);
			arrange = arrangeList.get(0);
			schedule_time.setText(arrange.hour+":"+arrange.minute);
			schedule_alarm.setSelection(arrange.alarm_type);
			schedule_title.setText(arrange.title);
			schedule_content.setText(arrange.content);
		} else {
			enableEdit(true);
		}
	}

	@Override  
	protected void onPause() {   
	    super.onPause();
	    arrangeHelper.close();
	}
	
	private void enableEdit(boolean enabled) {
		schedule_time.setEnabled(enabled);
		schedule_alarm.setEnabled(enabled);
		schedule_title.setEnabled(enabled);
		schedule_content.setEnabled(enabled);
		if (enabled) {
			schedule_time.setBackgroundResource(R.drawable.editext_selector);
			schedule_title.setBackgroundResource(R.drawable.editext_selector);
			schedule_content.setBackgroundResource(R.drawable.editext_selector);
		} else {
			schedule_time.setBackgroundDrawable(null);
			schedule_title.setBackgroundDrawable(null);
			schedule_content.setBackgroundDrawable(null);
		}
		btn_edit.setVisibility(enabled?View.GONE:View.VISIBLE);
		btn_save.setVisibility(enabled?View.VISIBLE:View.GONE);
	}

	@Override
	public void onClick(View v) {
		int resid = v.getId();
		if (resid == R.id.schedule_time) {
			Calendar calendar = Calendar.getInstance();
			TimePickerDialog dialog = new TimePickerDialog(this, this,
					calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
			dialog.show();
		} else if (resid == R.id.btn_back) {
			finish();
		} else if (resid == R.id.btn_edit) {
			enableEdit(true);
		} else if (resid == R.id.btn_save) {
			if (schedule_title.getText().toString().equals("") == true) {
				Toast.makeText(this, "请输入日程标题", Toast.LENGTH_SHORT).show();
				return;
			}
			enableEdit(false);
			String[] time_split = schedule_time.getText().toString().split(":");
			arrange.hour = time_split[0];
			arrange.minute = time_split[1];
			arrange.alarm_type = alarmType;
			arrange.title = schedule_title.getText().toString();
			arrange.content = schedule_content.getText().toString();
			if (arrange.xuhao <= 0) {
				arrange.month = month;
				arrange.day = day;
				arrangeHelper.add(arrange);
			} else {
				arrangeHelper.update(arrange);
			}
			Toast.makeText(this, "保存日程成功", Toast.LENGTH_SHORT).show();
			//设置提醒闹钟
			if (alarmType > 0) {
				Intent intent = new Intent(ALARM_EVENT);
				PendingIntent pIntent = PendingIntent.getBroadcast(this, 0, intent,
						PendingIntent.FLAG_UPDATE_CURRENT);
				AlarmManager alarmMgr = (AlarmManager) getSystemService(ALARM_SERVICE);
				Calendar calendar = Calendar.getInstance();
				if (alarmType == 6) {
					calendar.setTimeInMillis(System.currentTimeMillis());
					calendar.add(Calendar.SECOND, advanceArray[alarmType]);
				} else {
					int day_int = Integer.parseInt(day);
					calendar.set(day_int/10000, day_int%10000/100-1, day_int%100, 
							Integer.parseInt(arrange.hour), Integer.parseInt(arrange.minute), 0);
					calendar.add(Calendar.SECOND, -advanceArray[alarmType]*60);
				}
				alarmMgr.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pIntent);
			}
		}
	}

	@Override
	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		String time = String.format("%02d:%02d", hourOfDay, minute);
		schedule_time.setText(time);
	}

	private String ALARM_EVENT = "com.example.senior.ScheduleDetailActivity.AlarmReceiver";
    public static class AlarmReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
            	Log.d(TAG, "AlarmReceiver onReceive");
    			Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
    			vibrator.vibrate(3000);   //默认震动3秒
            }
        }
    }

}
