package com.czk.testapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.widget.TextView;

/**
 * Created by 陈忠凯 on 2017/10/25.
 */

public class Activity4 extends Activity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2);
        TextView textView = findViewById(R.id.tv);
        textView.setText("Activity4");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Activity4.this,Activity2.class);
                startActivity(intent);
            }
        },3500);
    }
}
