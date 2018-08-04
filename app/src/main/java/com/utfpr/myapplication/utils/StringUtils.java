package com.utfpr.myapplication.utils;

import android.content.Context;

import com.utfpr.myapplication.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class StringUtils {

    public static final String HEART_BEAT_TYPE = "heart_beat";
    public static final String FOOT_SCAN_TYPE = "foot_scan";
    public static final String RESULT_NORMAL_DETECTED_TYPE = "normal";
    public static final String RESULT_DETECTED_TYPE = "detected";

    public static String transformDateIntoString(Date date){
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.getDefault());
        return df.format(date);
    }

    public static Date transformStringIntoDate(String date) throws ParseException {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.getDefault());
        return df.parse(date);
    }

    public static String getExamType(Context context, String scanType){
        if(scanType.equalsIgnoreCase(HEART_BEAT_TYPE)){
            return context.getString(R.string.heart_beat_exam);
        }

        if(scanType.equalsIgnoreCase(FOOT_SCAN_TYPE)){
            return context.getString(R.string.foot_scan);
        }

        return "";
    }

    public static String getResultType(Context context, String resultType){
        if(resultType.equalsIgnoreCase(RESULT_NORMAL_DETECTED_TYPE)){
            return context.getString(R.string.normal);
        }

        if(resultType.equalsIgnoreCase(RESULT_DETECTED_TYPE)){
            return context.getString(R.string.detected);
        }

        return "";
    }

    public static String getWhatToDoTest(Context context, String resultType){
        if(resultType.equalsIgnoreCase(RESULT_NORMAL_DETECTED_TYPE)){
            return context.getString(R.string.normal_what_to_do_text);
        }

        if(resultType.equalsIgnoreCase(RESULT_DETECTED_TYPE)){
            return context.getString(R.string.detected_what_to_do_text);
        }

        return "";
    }
}
