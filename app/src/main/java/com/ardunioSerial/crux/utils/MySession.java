package com.ardunioSerial.crux.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class MySession {

    Context context;

    private String ISPurchase = "IsPurchase";

    public MySession(Context context) {
        this.context = context;
    }


    public void setUserPurchase(boolean isPurchase) {
        if (context != null) {

            SharedPreferences sharedPreferences = context.getSharedPreferences("inAppPrefs", context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(ISPurchase, isPurchase);
            editor.apply();
        }

    }

    public boolean isUserPurchased() {

        if (context != null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("inAppPrefs", context.MODE_PRIVATE);
            return sharedPreferences.getBoolean(ISPurchase, false);
        } else {
            return false;
        }
    }
}
