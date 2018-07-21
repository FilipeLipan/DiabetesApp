package com.utfpr.myapplication.utils;

import android.content.Context;

import com.utfpr.myapplication.R;

public class StringUtils {

    private static final String HEART_BEAT_TYPE = "heart_beat";
    private static final String FOOT_SCAN_TYPE = "foot_scan";
    private static final String RESULT_NORMAL_DETECTED_TYPE = "normal";
    private static final String RESULT_DETECTED_TYPE = "detected";

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
}
