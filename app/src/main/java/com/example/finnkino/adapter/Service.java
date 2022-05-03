package com.example.finnkino.adapter;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

public class Service extends Application {
    @SuppressLint("StaticFieldLeak")
    private static Context context;

    public void onCreate() {
        super.onCreate();
        Service.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return Service.context;
    }
}
