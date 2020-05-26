package com.example.oldmansupport.Desktop.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.oldmansupport.R;
import com.example.oldmansupport.ScheduleActivity;

public class fragmentExtraToolsActivity extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View View=inflater.inflate(R.layout.activity_fragment_extra_tools,container,false);
        //TextView txt_content = (TextView) View.findViewById(R.id.tvInfo4);
        //txt_content.setText("第一个Fragment");


        Button bt_flow=(Button)View.findViewById(R.id.bt_top_flow_monitor);
        Drawable da_flow=getResources().getDrawable(R.drawable.flow);
        da_flow.setBounds(0,60,300,300);
        bt_flow.setTextColor(Color.parseColor("#FFFFFF"));
        bt_flow.setTextSize(25);
        bt_flow.setCompoundDrawables(null,da_flow,null,null);

        //修改图标的大小
        Button bt_add2=(Button)View.findViewById(R.id.bt_top_tobeadded2);
        Drawable da_add2=getResources().getDrawable(R.drawable.add);
        da_add2.setBounds(0,150,150,300);
        bt_add2.setCompoundDrawables(null,da_add2,null,null);

        //修改图标的大小
        Button bt_add3=(Button)View.findViewById(R.id.bt_top_tobeadded3);
        Drawable da_add3=getResources().getDrawable(R.drawable.add);
        da_add3.setBounds(0,150,150,300);
        bt_add3.setCompoundDrawables(null,da_add3,null,null);

        //修改图标的大小
        Button bt_add4=(Button)View.findViewById(R.id.bt_top_tobeadded4);
        Drawable da_add4=getResources().getDrawable(R.drawable.add);
        da_add4.setBounds(0,150,150,300);
        bt_add4.setCompoundDrawables(null,da_add4,null,null);

        //修改图标的大小
        Button bt_add5=(Button)View.findViewById(R.id.bt_top_tobeadded5);
        Drawable da_add5=getResources().getDrawable(R.drawable.add);
        da_add5.setBounds(0,150,150,300);
        bt_add5.setCompoundDrawables(null,da_add5,null,null);

        //修改图标的大小
        Button bt_add6=(Button)View.findViewById(R.id.bt_top_tobeadded6);
        Drawable da_add6=getResources().getDrawable(R.drawable.add);
        da_add6.setBounds(0,150,150,300);
        bt_add6.setCompoundDrawables(null,da_add6,null,null);



      //  Bundle bundle=getArguments();



        return View;
    }
}
