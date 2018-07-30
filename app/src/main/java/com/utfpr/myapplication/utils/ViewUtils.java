package com.utfpr.myapplication.utils;

import android.app.Activity;
import android.view.View;

public class ViewUtils {

    public static View inflateView(Activity activity, int view){
        return activity.getLayoutInflater().inflate(view,activity.findViewById(android.R.id.content), false );
    }

}
