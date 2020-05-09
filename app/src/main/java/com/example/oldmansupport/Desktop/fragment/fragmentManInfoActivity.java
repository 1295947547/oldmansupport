package com.example.oldmansupport.Desktop.fragment;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.oldmansupport.R;

public class fragmentManInfoActivity extends Fragment {

    LinearLayout linearLayout;
    FragmentManager fragmentManager;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View View = inflater.inflate(R.layout.activity_fragment_man_info, container, false);
        //TextView txt_content = (TextView) View.findViewById(R.id.tvInfo4);
        //txt_content.setText("第一个Fragment");
       linearLayout=View.findViewById(R.id.llayout_fragment_maninfo); //通过getActivity()获取activity_main.xml文件中id号为line1的LinearLayout布局

        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(getActivity().getWindowManager().getDefaultDisplay().getWidth(),
                1794));
//        LinearLayout.LayoutParams params=(LinearLayout.LayoutParams)View.getLayoutParams();
//        int hei=getActivity().getWindowManager().getDefaultDisplay().getHeight();
//        String height=String.valueOf(hei);
//        Log.i("test999",height);
//        params.height=getActivity().getWindowManager().getDefaultDisplay().getHeight();
//        View.setLayoutParams(params);
//        FragmentTransaction transaction=fragmentManager.beginTransaction();



        Button bt_health=(Button)View.findViewById(R.id.bt_top_health_reminder);
        Drawable da_health=getResources().getDrawable(R.drawable.health);
        da_health.setBounds(0,60,300,300);
        bt_health.setTextColor(Color.parseColor("#FFFFFF"));
        bt_health.setTextSize(25);
        bt_health.setCompoundDrawables(null,da_health,null,null);

        //修改图标的大小
        Button bt_emergency=(Button)View.findViewById(R.id.bt_top_emergency_call);
        Drawable da_emergencyt=getResources().getDrawable(R.drawable.emergency);
        da_emergencyt.setBounds(0,40,280,300);
        bt_emergency.setTextColor(Color.parseColor("#FFFFFF"));
        bt_emergency.setTextSize(25);
        bt_emergency.setCompoundDrawables(null,da_emergencyt,null,null);

        //修改图标的大小
        Button bt_phonefinder=(Button)View.findViewById(R.id.bt_top_click_position);
        Drawable da_phonefinder=getResources().getDrawable(R.drawable.oneclick);
        da_phonefinder.setBounds(0,60,250,300);
        bt_phonefinder.setTextColor(Color.parseColor("#FFFFFF"));
        bt_phonefinder.setTextSize(25);
        bt_phonefinder.setCompoundDrawables(null,da_phonefinder,null,null);
        //修改图标的大小
        Button bt_fall=(Button)View.findViewById(R.id.bt_top_fall_detection);
        Drawable da_fall=getResources().getDrawable(R.drawable.fall);
        da_fall.setBounds(0,60,280,300);
        bt_fall.setTextColor(Color.parseColor("#FFFFFF"));
        bt_fall.setTextSize(25);
        bt_fall.setCompoundDrawables(null,da_fall,null,null);




        return View;
    }

//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        LinearLayout layout = (LinearLayout) getView().findViewById(R.id.llayout_fragment_maninfo);
//        layout.setMinimumHeight(720);
//
//    }
}
