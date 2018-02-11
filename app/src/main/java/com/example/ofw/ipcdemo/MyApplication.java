package com.example.ofw.ipcdemo;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.util.Log;

/**
 * Created by ofw on 2018/2/4.
 */

public class MyApplication extends Application {

    private static final String TAG = "MyApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        String processName = "";
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager)getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        for(ActivityManager.RunningAppProcessInfo rapi : activityManager.getRunningAppProcesses()){
            if(pid == rapi.pid){
                processName = rapi.processName;
            }
        }
        Log.d(TAG, "onCreate: application start "+processName);
    }
}
