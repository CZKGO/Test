package com.czk.testapi;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Window;

/**
 * Created by 陈忠凯 on 2017/10/20.
 */

public class TransitionActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.activity_transiton);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(TransitionActivity.this, Transition2Activity.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(TransitionActivity.this).toBundle());
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        Log.d(getComponentName().getClassName(), "isActivityTransitionRunning:" + isActivityTransitionRunning());
                    }
                }
            }
        }, 3500);



    }


    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);
    }

}
