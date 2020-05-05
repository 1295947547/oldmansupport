package com.example.oldmansupport.Desktop.fragment;

import android.content.Intent;
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





        return View;
    }
}
