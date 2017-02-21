package com.ghf.eshop.feature;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by Administrator on 2017/2/21 0021.
 */

public class EShopApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // 这个是用于分析内存的线程，我们不能再这里面初始化我们项目
            return;
        }
        LeakCanary.install(this);//真正的初始化

    }
}
