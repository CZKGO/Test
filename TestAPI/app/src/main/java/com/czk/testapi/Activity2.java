package com.czk.testapi;

import android.app.Activity;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.WindowManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by 陈忠凯 on 2017/10/19.
 */

public class Activity2 extends Activity {
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity2);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//            Intent intent = getParentActivityIntent();
//            intent = new Intent(Activity2.this,Activity4.class);
//            final Intent finalIntent = intent;
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    startActivity(finalIntent);
//                }
//            },3500);
//        }
//        Log.d("laiba", "Activity2:" + getParent());
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            MediaController controller = getMediaController();
//        }
//                TaskActivity


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//                    Intent intent = getParentActivityIntent();
//                    navigateUpTo(intent);
                    onNavigateUp();
                }
            }
        }, 3500);
//        getPreferences(MODE_WORLD_WRITEABLE);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d(getComponentName().getClassName(), "onConfigurationChanged");
    }

    @Override
    public boolean onNavigateUp() {
        shouldUpRecreateTask(getParentActivityIntent());
        return super.onNavigateUp();
    }

    @Override
    public void onCreateNavigateUpTaskStack(TaskStackBuilder builder) {
        super.onCreateNavigateUpTaskStack(builder);
    }

    @Override
    public boolean onCreateThumbnail(Bitmap outBitmap, Canvas canvas) {
        saveImgToPng(this,outBitmap,"1234.png");
        return super.onCreateThumbnail(outBitmap, canvas);
    }

    public void saveImgToPng(final Context mContext, final Bitmap bitmap, final String bitName) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                File dir = new File("abcdefg");
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                File file = new File("abcdefg" + File.separator + bitName);
                if (file.exists()) {
                    file.delete();
                }
                FileOutputStream out;
                try {
                    out = new FileOutputStream(file);
                    if (bitmap.compress(Bitmap.CompressFormat.PNG, 0, out)) {
                        out.flush();
                        out.close();
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
