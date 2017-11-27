package com.czk.testapi;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

/**
 * Created by 陈忠凯 on 2017/10/19.
 */

public class Activity3 extends TabActivity {
    //定义TabHost对象
    private TabHost tabHost;

    //定义一个布局
    private LayoutInflater layoutInflater;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity3);
        Log.d("laiba", "Activity3:" + getParent());
        initView();
    }

    /**
     * 初始化组件
     */
    private void initView(){
        //实例化TabHost对象，得到TabHost
        tabHost = getTabHost();

        //实例化布局对象
        layoutInflater = LayoutInflater.from(this);

        //得到Activity的个数
        int count = ConValue.mTabClassArray.length;

        for(int i = 0; i < count; i++){
            //为每一个Tab按钮设置图标、文字和内容
            TabHost.TabSpec tabSpec = tabHost.newTabSpec(ConValue.mTextviewArray[i]).setIndicator(getTabItemView(i)).setContent(getTabItemIntent(i));
            //将Tab按钮添加进Tab选项卡中
            tabHost.addTab(tabSpec);
            //设置Tab按钮的背景
        }
    }

    /**
     * 给Tab按钮设置图标和文字
     */
    private View getTabItemView(int index){
        View view = layoutInflater.inflate(R.layout.tab_item_view, null);

        ImageView imageView = (ImageView) view.findViewById(R.id.imageview);

        if (imageView != null){
            imageView.setImageResource(ConValue.mImageViewArray[index]);
        }
        TextView textView = (TextView) view.findViewById(R.id.textview);

        textView.setText(ConValue.mTextviewArray[index]);

        return view;
    }

    /**
     * 给Tab选项卡设置内容（每个内容都是一个Activity）
     */
    private Intent getTabItemIntent(int index){
        Intent intent = new Intent(this, ConValue.mTabClassArray[index]);

        return intent;
    }

    public static final class ConValue{

        /**
         * Tab选项卡的图标
         */
        public static int   mImageViewArray[] = {R.mipmap.ic_launcher};

        /**
         * Tab选项卡的文字
         */
        public static String mTextviewArray[] = {"测试"};


        /**
         * 每一个Tab界面
         */
        public static Class mTabClassArray[]= {
                Activity2.class};
    }

    @Override
    public void finishActivityFromChild(@NonNull Activity child, int requestCode) {
        super.finishActivityFromChild(child, requestCode);
        Log.e("sdafsa","finishActivityFromChild");
    }

    @Override
    public void finishFromChild(Activity child) {
        super.finishFromChild(child);
        Log.e("sdafsa","finishFromChild");
    }
}