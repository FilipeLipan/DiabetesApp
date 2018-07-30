package com.utfpr.myapplication.utils;

import android.content.Context;
import android.support.v4.content.ContextCompat;

public class ColorUtils {



    public static int getResultTypeColor(Context context, String resultType){
        if(resultType.equalsIgnoreCase(StringUtils.RESULT_NORMAL_DETECTED_TYPE)){
            return ContextCompat.getColor(context, android.R.color.holo_green_dark);
        }

        if(resultType.equalsIgnoreCase(StringUtils.RESULT_DETECTED_TYPE)){
            return ContextCompat.getColor(context, android.R.color.holo_red_dark);
        }

        return ContextCompat.getColor(context, android.R.color.holo_green_dark);
    }
}
