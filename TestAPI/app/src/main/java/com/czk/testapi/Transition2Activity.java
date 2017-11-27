package com.czk.testapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by 陈忠凯 on 2017/10/20.
 */

public class Transition2Activity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transiton2);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finishAfterTransition();
            }
        }, 3500);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.d(getComponentName().getClassName(), "isActivityTransitionRunning:" + isActivityTransitionRunning());
        }
        setResult(1231);
    }

    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);
    }
}
