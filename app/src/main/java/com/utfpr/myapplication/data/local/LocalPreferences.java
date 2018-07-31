package com.utfpr.myapplication.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Calendar;

/**
 * Created by lispa on 20/05/2018.
 */

public class LocalPreferences {

    private static final String LOCAL_PREFERENCES = "local-preferences";
    private static final String SAW_TUTORIAL = "saw-tutorial";
    private static final String LAST_EXAM_DATE = "last-exam-date";
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

    public void saveLastExamDate(Long date){
        mPreferences.edit()
                .putLong(LAST_EXAM_DATE, date)
                .apply();
    }


    public Long getLastExamDate(){
        return mPreferences.getLong(LAST_EXAM_DATE, Calendar.getInstance().getTimeInMillis());
    }
}
