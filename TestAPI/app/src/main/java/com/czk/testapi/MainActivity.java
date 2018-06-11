package com.czk.testapi;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.app.VoiceInteractor;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.KeyboardShortcutGroup;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "MainActivity";
    private ListView listView;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.list);
        registerForContextMenu(listView);

        Intent intent = new Intent(this, Activity2.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack( Activity2.class );
        stackBuilder.addNextIntent(intent);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0,
                PendingIntent.FLAG_UPDATE_CURRENT);
        //
        NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(this)
                  /*设置small icon*/
                .setSmallIcon(R.mipmap.ic_launcher)
            /*设置title*/
                .setContentTitle("这只是一个测试")
                .setContentText("对的测试")
                .setAutoCancel(true)
                .setGroup("Warning")
                .setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(1, notifyBuilder.build());

//        showDialog(1023);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                dismissDialog(1023);
//            }
//        }, 5000);
        listView.setOnHoverListener(new View.OnHoverListener() {
            @Override
            public boolean onHover(View v, MotionEvent event) {
                Log.d("laiba", "" + event.getAction());
                return false;
            }
        });

        Log.d("laiba", "" + getParent());
        /**
         * 将dump命令结果放入屏幕输出中
         */
        FileDescriptor fdout = new FileDescriptor();
        PrintWriter pw = new PrintWriter(System.out, true);
        String[] args = {"-a", "a"};
        dump("实验", fdout, pw, args);
        args[1] = "p";
        dump("实验", fdout, pw, args);

//        getChangingConfigurations();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getSearchEvent();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            JobScheduler mJobScheduler = (JobScheduler)
                    getSystemService(Context.JOB_SCHEDULER_SERVICE);
            JobInfo.Builder builder = new JobInfo.Builder(1,
                    new ComponentName(getPackageName(),
                            MyJobSchedulerService.class.getName()));
            builder.setRequiresCharging(true);
            if (mJobScheduler.schedule(builder.build()) <= 0) {
                Log.d("错误", "任务");
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (isVoiceInteraction()) {
                startVoiceTrigger();
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            Log.d("immersive", ""+isImmersive());
        }

        getLoaderManager().initLoader(0, null, new DataLoaderCallback());
    }

    @Override
    public boolean onCreatePanelMenu(int featureId, Menu menu) {
        menu.add(1, 0, 0, "菜单一");
        menu.add(1, 1, 1, "菜单二");
        return super.onCreatePanelMenu(featureId, menu);
    }

    @Override
    public boolean onCreateThumbnail(Bitmap outBitmap, Canvas canvas) {
        Log.d(getComponentName().getClassName(),"onCreateThumbnail");
        saveImgToPng(this,outBitmap,"123.png");
        return super.onCreateThumbnail(outBitmap, canvas);
    }

    @Nullable
    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        Log.d(getComponentName().getClassName(),"onCreateView1");
        return super.onCreateView(name, context, attrs);
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        Log.d(getComponentName().getClassName(),"onCreateView2："+name);
        return super.onCreateView(parent, name, context, attrs);
    }

    @Override
    public void onEnterAnimationComplete() {
        Log.d(getComponentName().getClassName(),"onEnterAnimationComplete");
        super.onEnterAnimationComplete();
    }

    @Override
    public boolean onGenericMotionEvent(MotionEvent event) {
        Log.d("测试", "onGenericMotionEvent:" + event.getAction());
        return super.onGenericMotionEvent(event);
    }

    @Override
    public void onDetachedFromWindow() {
        Log.d(getComponentName().getClassName(),"onDetachedFromWindow");
        super.onDetachedFromWindow();
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

    //    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
//            View decorView = getWindow().getDecorView();
//            decorView.setSystemUiVisibility(
//                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
//        }
//    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.d(getComponentName().getClassName(),"onPause");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        return super.onKeyLongPress(keyCode, event);
    }

    @Override
    public boolean onKeyMultiple(int keyCode, int repeatCount, KeyEvent event) {
        return super.onKeyMultiple(keyCode, repeatCount, event);
    }

    @Override
    public boolean onKeyShortcut(int keyCode, KeyEvent event) {
        return super.onKeyShortcut(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return super.onKeyUp(keyCode, event);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(getComponentName().getClassName(), "isChangingConfigurations:" + isChangingConfigurations());
    }

    @Nullable
    @Override
    public CharSequence onCreateDescription() {
        Log.d(getComponentName().getClassName(),"onCreateDescription");
        CharSequence description = super.onCreateDescription();
        Log.d(getComponentName().getClassName(), "" + description);
        return description;
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void startVoiceTrigger() {
        Log.d(getComponentName().getClassName(), "startVoiceTrigger: ");
        VoiceInteractor.PickOptionRequest.Option option = new VoiceInteractor.PickOptionRequest.Option("cheese", 1);
        option.addSynonym("ready");
        option.addSynonym("go");
        option.addSynonym("take it");
        option.addSynonym("ok");

        VoiceInteractor.Prompt prompt = new VoiceInteractor.Prompt("Say Cheese");
        getVoiceInteractor()
                .submitRequest(new VoiceInteractor.PickOptionRequest(prompt, new VoiceInteractor.PickOptionRequest.Option[]{option}, null) {
                    @Override
                    public void onPickOptionResult(boolean finished, Option[] selections, Bundle result) {
                        if (finished && selections.length == 1) {
                            Message message = Message.obtain();
                            message.obj = result;
                            Log.d(getComponentName().getClassName(), "onPickOptionResult");
                        } else {
                            getActivity().finish();
                            Log.d(getComponentName().getClassName(), "onPickOptionResult-else");
                        }
                    }

                    @Override
                    public void onCancel() {
                        getActivity().finish();
                        Log.d(getComponentName().getClassName(), "onCancel");
                    }
                });
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        Dialog dialog = new AlertDialog.Builder(this)
                .setTitle("实验")
                .setPositiveButton("确定",
                        null)
                .setNegativeButton("取消",
                        null).create();
        return dialog;
    }

    @Override
    public void invalidateOptionsMenu() {
        super.invalidateOptionsMenu();

        Log.d(getComponentName().getClassName(), "invalidateOptionsMenu");
    }

    @Override
    public void onProvideKeyboardShortcuts(List<KeyboardShortcutGroup> data, Menu menu, int deviceId) {
        super.onProvideKeyboardShortcuts(data, menu, deviceId);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(1, 0, 0, "菜单一");
        menu.add(1, 1, 1, "菜单二");
        menu.add(1, 2, 2, "菜单三");
        menu.add(1, 3, 3, "菜单四");
        menu.add(1, 4, 4, "菜单五");
        menu.add(1, 5, 6, "菜单六");
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        menu.add(1, 0, 0, "菜单一");
//        menu.add(1, 1, 1, "菜单二");
//        return super.onCreateOptionsMenu(menu);
//    }

    @Override
    public boolean dispatchGenericMotionEvent(MotionEvent ev) {
        Log.d("测试", "GenericMotionEvent:" + ev.getAction());
        return super.dispatchGenericMotionEvent(ev);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        Log.d("测试", "KeyEvent:" + event.getAction());
        return super.dispatchKeyEvent(event);
    }

    @Override
    public boolean dispatchKeyShortcutEvent(KeyEvent event) {
        Log.d("测试", "KeyShortcutEvent:" + event.getAction());
        return super.dispatchKeyShortcutEvent(event);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent event) {
        Log.d("测试", "PopulateAccessibilityEvent:" + event.getAction());
        return super.dispatchPopulateAccessibilityEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d("测试", "TouchEvent:" + ev.getAction());
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean dispatchTrackballEvent(MotionEvent ev) {
        Log.d("测试", "TrackballEvent:" + ev.getAction());
        return super.dispatchTrackballEvent(ev);
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                startActivityForResult(new Intent(this, Activity2.class), 1025);
//                finish();
//                finishAndRemoveTask();
                break;
            case 1:
                startActivityForResult(new Intent(this, Activity3.class), 1024);
                break;
            case 2:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    try {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            Log.d(getLocalClassName(), "" + getMaxNumPictureInPictureActions());
                        }
                        enterPictureInPictureMode();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    Log.d(getLocalClassName(), "" + getMaxNumPictureInPictureActions());
                                }
                            }
                        }, 3500);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case 3:
                startActivityForResult(new Intent(this, TransitionActivity.class), 1026);
                break;
            case 4:
//                if (Build.VERSION.SDK_INT >= 24) {
//                    requestShowKeyboardShortcuts();
//                }


                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                break;
            case 5:
                startActionMode(new ActionMode.Callback() {
                    @Override
                    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                        mode.getMenuInflater().inflate(R.menu.action_mode_menu, menu);
                        return true;
                    }

                    @Override
                    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                        return false;
                    }

                    @Override
                    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                        return false;
                    }

                    @Override
                    public void onDestroyActionMode(ActionMode mode) {

                    }
                });
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onActionModeFinished(ActionMode mode) {
        super.onActionModeFinished(mode);
    }

    @Override
    public void onActionModeStarted(ActionMode mode) {
        super.onActionModeStarted(mode);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
    }

    @Override
    public void finish() {
        super.finish();
        Log.e("dsaff", "asdfasdfsfd");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(TAG, "onActivityResult(requestCode:" + requestCode + ", resultCode:" + resultCode);

    }

    private class DataLoaderCallback implements android.app.LoaderManager.LoaderCallbacks<String[]> {
        @Override
        public Loader<String[]> onCreateLoader(int id, Bundle args) {

            return new DataLoader(MainActivity.this);
        }

        @Override
        public void onLoadFinished(Loader<String[]> loader, String[] data) {
            adapter = new ArrayAdapter<String>(
                    getApplicationContext(),
                    android.R.layout.simple_list_item_1,
                    data
                    );
            adapter.notifyDataSetChanged();
            listView.setAdapter(adapter);
        }

        @Override
        public void onLoaderReset(Loader<String[]> loader) {

        }
    }
    @Override
    public boolean onNavigateUp() {
        return super.onNavigateUp();
    }

    @Override
    public void onCreateNavigateUpTaskStack(TaskStackBuilder builder) {
        super.onCreateNavigateUpTaskStack(builder);
    }
}
