package com.example.basecommonlibrary;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.basecommonlibrary.router.IInterceptor.InterruptCallback;
import com.example.basecommonlibrary.utils.SharedPrefersUtils;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2017 Tuandai Inc. All rights reserved.
 * @date: 2017/4/25 15:06
 */

public class RouterCommonUtil {
    private static final String TAG = "RouterCommonUtil";
    private static void toastInterruptInfo(final Activity activity, final Postcard postcard) {
        if (postcard.getTag() != null && postcard.getTag() instanceof String) {
            Observable.create(new Observable.OnSubscribe<String>() {
                @Override
                public void call(Subscriber<? super String> subscriber) {
                    subscriber.onNext((String) postcard.getTag());
                }
            }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<String>() {
                @Override
                public void call(String s) {
                    if (!TextUtils.isEmpty(s) && activity != null) {
                        Toast.makeText(activity, (String) postcard.getTag(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public static void startMainActivity(final Activity activity) {
        ARouter.getInstance().build("/ui/主页").navigation(activity, new InterruptCallback() {
            @Override
            public void onInterrupt(Postcard postcard) {
                recordPath(postcard, activity);
            }

            @Override
            public void onArrival(Postcard postcard) {
                super.onArrival(postcard);
                recordPath(postcard, activity);
            }
        });
    }

    public static void startMainTextActivity(final Activity activity, String value) {
        ARouter.getInstance().build("/ui/text", "文本").withString("arg1", value).navigation(activity, 1001, new InterruptCallback() {
            @Override
            public void onInterrupt(Postcard postcard) {
                toastInterruptInfo(activity, postcard);
            }

            @Override
            public void onArrival(Postcard postcard) {
                super.onArrival(postcard);
                recordPath(postcard, activity);
            }
        });
    }

    public static void startMainImageActivity(final Activity activity, String value) {
        ARouter.getInstance().build("/ui/image", "图片").withString("arg1", value).navigation(activity, new InterruptCallback() {
            @Override
            public void onInterrupt(Postcard postcard) {
                toastInterruptInfo(activity, postcard);
            }

            @Override
            public void onArrival(Postcard postcard) {
                super.onArrival(postcard);
                recordPath(postcard, activity);
            }
        });
    }

    public static void startLibraryOneActivity(final Activity activity) {
        ARouter.getInstance().build("/libraryOne/主页").navigation(activity, new InterruptCallback() {
            @Override
            public void onInterrupt(Postcard postcard) {
                toastInterruptInfo(activity, postcard);
            }

            @Override
            public void onArrival(Postcard postcard) {
                super.onArrival(postcard);
                recordPath(postcard, activity);
            }
        });
    }

    private static void recordPath(Postcard postcard, Activity activity) {
        if (activity != null) {
            String name = activity.getClass().getName();
            String o = (String) SharedPrefersUtils.get(activity, name, "");
            Log.i(TAG, "activity 前一个页面:" + o + "|activity 后一个页面:" + postcard.getPath());
            if (!SharedPrefersUtils.contains(activity, postcard.getDestination().getName())) {
                SharedPrefersUtils.put(activity, postcard.getDestination().getName(), postcard.getPath());
            }
        }
    }

    public static void startLibraryTwoActivity(final Activity activity) {
        ARouter.getInstance().build("/libraryTwo/主页").navigation(activity, new InterruptCallback() {
            @Override
            public void onInterrupt(Postcard postcard) {
                toastInterruptInfo(activity, postcard);
            }

            @Override
            public void onArrival(Postcard postcard) {
                super.onArrival(postcard);
                recordPath(postcard, activity);
            }
        });
    }
}
