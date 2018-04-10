package com.chenyee.stephenlau.floatingball;

import android.annotation.SuppressLint;
import android.app.Application;
import android.support.v4.view.LayoutInflaterCompat;

import com.squareup.leakcanary.LeakCanary;

import java.lang.ref.SoftReference;


/**
 * Created by stephenlau on 18-2-24.
 */

public class App extends Application {
//    private static App mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
//        mInstance = this;

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        // Normal app init code...

    }

}
