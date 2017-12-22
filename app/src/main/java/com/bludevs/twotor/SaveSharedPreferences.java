package com.bludevs.twotor;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class SaveSharedPreferences {
    public static Boolean IS_LOGGED_IN = false;
    static final String PREF_USER = "username";
    static final String PREF_NAME = "name";
    static final String PREF_PROF = "URL";
    static SharedPreferences getSharedPreferences(Context ctx){
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }
    public static void setPrefUser(Context ctx, String username){
        IS_LOGGED_IN = true;
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER,username);
        editor.commit();
    }
    public static String getUserName(Context ctx){
        return getSharedPreferences(ctx).getString(PREF_USER,"");
    }

    public static void setName(Context ctx, String name){
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_NAME, name);
        editor.commit();
    }
    public static String getName(Context ctx){
        return getSharedPreferences(ctx).getString(PREF_NAME,"");
    }

    public static void setProf(Context ctx, String Prof){
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_PROF,Prof);
        editor.commit();
    }
    public static String getProf(Context ctx){
        return getSharedPreferences(ctx).getString(PREF_PROF,"");
    }


    public static void LogOut(Context ctx){
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.clear();
        editor.commit();
        IS_LOGGED_IN = false;
    }
}
