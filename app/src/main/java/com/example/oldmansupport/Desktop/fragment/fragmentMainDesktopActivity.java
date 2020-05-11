package com.example.oldmansupport.Desktop.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.oldmansupport.MainActivity;
import com.example.oldmansupport.R;
import com.example.oldmansupport.ScheduleActivity;
import com.example.oldmansupport.weather.gson.Weather;
import com.example.oldmansupport.weather.util.Utility;
import com.example.oldmansupport.weather.weather_main;

import java.util.Calendar;

public class fragmentMainDesktopActivity extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View View = inflater.inflate(R.layout.activity_fragment_main_desktop, container, false);
        //TextView txt_content = (TextView) View.findViewById(R.id.tvInfo4);
        //txt_content.setText("第一个Fragment");




        Button bt_calendar=(Button)View.findViewById(R.id.bt_top_calendar);
        Drawable da_calendar=getResources().getDrawable(R.drawable.calendar);
        da_calendar.setBounds(0,60,300,300);
        bt_calendar.setTextColor(Color.parseColor("#FFFFFF"));
        bt_calendar.setTextSize(25);
        bt_calendar.setCompoundDrawables(null,da_calendar,null,null);

        //修改图标的大小
        Button bt_wechat=(Button)View.findViewById(R.id.bt_top_wechat);
        Drawable da_wechat=getResources().getDrawable(R.drawable.wechat);
        da_wechat.setBounds(0,40,280,300);
        bt_wechat.setTextColor(Color.parseColor("#FFFFFF"));
        bt_wechat.setTextSize(25);
        bt_wechat.setCompoundDrawables(null,da_wechat,null,null);

        //修改图标的大小
        Button bt_camera=(Button)View.findViewById(R.id.bt_top_camera);
        Drawable da_camera=getResources().getDrawable(R.drawable.camera);
        da_camera.setBounds(0,60,250,300);
        bt_camera.setTextColor(Color.parseColor("#FFFFFF"));
        bt_camera.setTextSize(25);
        bt_camera.setCompoundDrawables(null,da_camera,null,null);
        //修改图标的大小
        Button bt_gallery=(Button)View.findViewById(R.id.bt_top_gallery);
        Drawable da_gallery=getResources().getDrawable(R.drawable.gallery);
        da_gallery.setBounds(0,60,280,300);
        bt_gallery.setTextColor(Color.parseColor("#FFFFFF"));
        bt_gallery.setTextSize(25);
        bt_gallery.setCompoundDrawables(null,da_gallery,null,null);



        bt_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                Intent intent1=new Intent();
                intent1.setClass(getActivity(),ScheduleActivity.class);
                startActivity(intent1);

            }
        });


        Calendar calendar = Calendar.getInstance();
        TextView tv_hms=(TextView)View.findViewById(R.id.tv_timehms);
        TextView tv_ymd=(TextView)View.findViewById(R.id.tv_timeymd);
        TextView tv_weatherinfo=(TextView)View.findViewById(R.id.tv_weatherinfo);
        TextView tv_weatherdegree=(TextView)View.findViewById(R.id.tv_weatherdegree);

        int year = calendar.get(Calendar.YEAR);
//月
        int month = calendar.get(Calendar.MONTH)+1;
//日
        int day = calendar.get(Calendar.DAY_OF_MONTH);
//小时
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
//分钟
        int minute = calendar.get(Calendar.MINUTE);
//秒
        int week = calendar.get(Calendar.DAY_OF_WEEK);
        String strweek="    ";
        switch (week){
            case 1:
                strweek="周一";
                break;
            case 2:
                strweek="周二";
                break;
            case 3:
                strweek="周三";
                break;
            case 4:
                strweek="周四";
                break;
            case 5:
                strweek="周五";
                break;
            case 6:
                strweek="周六";
                break;
            case 7:
                strweek="周日";
                break;
            default:
                break;

        }
        String hms=String.valueOf(hour)+":"+String.valueOf(minute);
        String ymd=strweek+"  "+String.valueOf(year)+"年"+String.valueOf(month)+"月"+String.valueOf(day)+"日";

        tv_hms.setText(hms);
        tv_ymd.setText(ymd);





        LinearLayout llyt_weather_jump=(LinearLayout)View.findViewById(R.id.llyt_weather_jump);
        llyt_weather_jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                Intent intent1=new Intent();
                intent1.setClass(getActivity(), weather_main.class);
                startActivity(intent1);

            }
        });




        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String weatherString = prefs.getString("weather", null);
        if (weatherString != null) {
            // 有缓存时直接解析天气数据
            Weather weather = Utility.handleWeatherResponse(weatherString);

            tv_weatherinfo.setText(weather.now.more.info);
            tv_weatherdegree.setText(weather.now.temperature+"℃");

        }
        else {
            tv_weatherinfo.setText("");
            tv_weatherdegree.setText("");

        }




        return View;
    }


//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        Button button = (Button) getActivity().findViewById(R.id.bt_top_camera);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
//            }
//        });
//
//    }

}
