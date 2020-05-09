package com.example.oldmansupport.Desktop.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.oldmansupport.MainActivity;
import com.example.oldmansupport.R;
import com.example.oldmansupport.ScheduleActivity;

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
