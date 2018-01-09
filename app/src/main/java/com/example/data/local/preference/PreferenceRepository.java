package com.example.data.local.preference;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by hanzai.peng on 2018/1/8.
 */

public class PreferenceRepository {

    private SharedPreferences mPreferences;
    private PreferenceRepository mPreferenceRepository;

    public PreferenceRepository(Context context, String name){
        mPreferences = context.getSharedPreferences(name,Context.MODE_PRIVATE);
    }

    public void put(String key,String value){
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(key,value);
        editor.commit();
        //editor.apply(); //异步提交
    }

    public String get(String key,String defaultValue){
        return mPreferences.getString(key,defaultValue);
    }
}
