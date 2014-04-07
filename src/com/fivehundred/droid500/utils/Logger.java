package com.fivehundred.droid500.utils;

import android.util.Log;
import com.fivehundred.droid500.BuildConfig;

public class Logger{
        
    public static void log(String message){        
        if(BuildConfig.DEBUG){
            Log.d(ApplicationConstants.TAG, message);
        }
    }
}