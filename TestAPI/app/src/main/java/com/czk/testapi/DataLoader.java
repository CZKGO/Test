package com.czk.testapi;

import android.content.AsyncTaskLoader;
import android.content.Context;

/**
 * Created by 陈忠凯 on 2017/11/13.
 */
public class DataLoader extends AsyncTaskLoader<String[]> {

    public DataLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public String[] loadInBackground() {
        return new String[]{
                "菜单1",
                "菜单1",
                "菜单1",
                "菜单1",
                "菜单1",
                "菜单1",
                "菜单1",
                "菜单1",
                "菜单1",
                "菜单1",
                "菜单1",
                "菜单1",
                "菜单1",
                "菜单1",
                "菜单1",
                "菜单1",
                "菜单1",
                "菜单1",
                "菜单1",
                "菜单1",
                "菜单1"
        };
    }

}
