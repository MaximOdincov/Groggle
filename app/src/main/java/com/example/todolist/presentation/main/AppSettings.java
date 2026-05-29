package com.example.todolist.presentation.main;

import android.content.Context;
import android.content.SharedPreferences;

public class AppSettings {
    private static final String PREFS_NAME = "app_settings";
    private static final String KEY_CURRENCY = "selected_currency";
    private static final String KEY_SHOW_LOCAL = "show_local_currency";

    public static String getSelectedCurrency(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getString(KEY_CURRENCY, "RUB");
    }

    public static void setSelectedCurrency(Context context, String currency) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit().putString(KEY_CURRENCY, currency).apply();
    }

    public static boolean isShowLocalCurrency(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getBoolean(KEY_SHOW_LOCAL, false);
    }

    public static void setShowLocalCurrency(Context context, boolean show) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit().putBoolean(KEY_SHOW_LOCAL, show).apply();
    }
}
