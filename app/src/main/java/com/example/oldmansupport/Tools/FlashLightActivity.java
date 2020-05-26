package com.example.oldmansupport.Tools;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;

import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.oldmansupport.R;

import java.security.Policy;

public class FlashLightActivity extends AppCompatActivity {


    private boolean open=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_light);
        final Button bt_flashlight_switch=(Button)findViewById(R.id.bt_flashlight_switch);



        bt_flashlight_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           if(!open){
               bt_flashlight_switch.setText("打开手电筒");
           }
           else{
               bt_flashlight_switch.setText("关闭手电筒");
           }


            }
        });

    }
}
