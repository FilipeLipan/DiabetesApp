package com.utfpr.myapplication.data.local;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by lispa on 20/05/2018.
 */

public class LocalPreferences {

    public static final String LOCAL_PREFERENCES = "local-preferences";
    public static final String SAW_TUTORIAL = "saw-tutorial";
    private Context mContext;
    private SharedPreferences mPreferences;

    public LocalPreferences(Context context){
        mContext = context;
        mPreferences = mContext.getSharedPreferences(LOCAL_PREFERENCES,Context.MODE_PRIVATE);
    }


    public void sawTutorial(){
        mPreferences.edit()
                .putBoolean(SAW_TUTORIAL, true)
                .apply();

    }

    public Boolean didSawTutorial(){
        return mPreferences.getBoolean(SAW_TUTORIAL, false);
    }
}
