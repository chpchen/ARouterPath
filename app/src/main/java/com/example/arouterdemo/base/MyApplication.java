package com.example.arouterdemo.base;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.arouterdemo.BuildConfig;
import com.example.arouterdemo.utils.SharedPrefersUtils;
import com.example.arouterdemo.utils.TimeUtils;

import java.util.LinkedList;

/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2017 Tuandai Inc. All rights reserved.
 * @date: 2017/4/19 10:56
 */

public class MyApplication extends Application implements Application.ActivityLifecycleCallbacks {
    private static final String TAG = "MyApplication";
    private static MyApplication sInstance;
    private LinkedList<Activity> mExistedActivitys = new LinkedList<>();
    private static int resumed;
    private static int paused;
    private static int started;
    private static int stopped;

    String currentPage, currentPath, nextPage = "", nextPath = "";

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this);
        sInstance = this;
        registerActivityLifecycleCallbacks(this);
    }

    public static MyApplication getApplication() {
        return sInstance;
    }


    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        if (null != mExistedActivitys && null != activity) {
            // 把新的 activity 添加到最前面，和系统的 activity 堆栈保持一致
            mExistedActivitys.offerFirst(activity);

            Log.i(TAG, "onActivityCreated: " + activity.getClass().getName() + "/Time:" + TimeUtils.getNow() + "/size:" + mExistedActivitys.size());
        }
    }

    @Override
    public void onActivityStarted(Activity activity) {
        ++started;
    }

    @Override
    public void onActivityResumed(Activity activity) {
        Log.i(TAG, "onActivityResumed: " + activity.getClass().getName() + "/Time:" + TimeUtils.getNow() + "/size:" + mExistedActivitys.size());
        ++resumed;
    }

    @Override
    public void onActivityPaused(Activity activity) {
        Log.i(TAG, "onActivityPaused: " + activity.getClass().getName() + "/Time:" + TimeUtils.getNow() + "/size:" + mExistedActivitys.size());
        ++paused;
        android.util.Log.w("test", "application is in foreground: " + (resumed > paused));

    }

    @Override
    public void onActivityStopped(Activity activity) {
        ++stopped;
        android.util.Log.w("test", "application is visible: " + (started > stopped));
//        Log.i(TAG, "onActivityStopped: " + activity.getClass().getName() + "/Time:" + TimeUtils.getNow() + "/size:" + mExistedActivitys.size());
        if (activity != null) {
            //当前页面
            currentPage = activity.getClass().getName();
            currentPath = (String) SharedPrefersUtils.get(currentPage, "");

            //去向
            if (mExistedActivitys.getFirst() != null) {
                nextPage = mExistedActivitys.getFirst().getClass().getName();
                if (nextPage.equals(currentPage)) {
                    if (mExistedActivitys.size() > 1) {
                        nextPage = mExistedActivitys.get(1).getClass().getName();
                    }
                }
                nextPath = (String) SharedPrefersUtils.get(nextPage, "");
                if (!currentPath.equals(nextPath)) {
                    Log.i(TAG, "onActivityStopped: 离开页面：" + currentPath + " ***** 去项：" + nextPath);
                }
            }

        }


    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        if (null != mExistedActivitys && null != activity) {
//            ActivityInfo info = findActivityInfo(activity);
            if (null != activity) {
                mExistedActivitys.remove(activity);
                Log.i(TAG, "onActivityDestroyed: " + activity.getClass().getName() + "/Time:" + TimeUtils.getNow() + "/size:" + mExistedActivitys.size());
            }

        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }


}
