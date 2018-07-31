package com.utfpr.myapplication.ui;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.utfpr.myapplication.R;
import com.utfpr.myapplication.data.local.LocalPreferences;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class ReminderWidget extends AppWidgetProvider {

    public static long MAX_DAY_WITHOUT_EXAM = 10;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        for (int appWidgetId: appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    public static void updateAppWidget(Context context,AppWidgetManager appWidgetManager,int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.reminder_widget);

        LocalPreferences localPreferences = new LocalPreferences(context);
        Long lastExamDateInMillis = localPreferences.getLastExamDate();

        long diff = Calendar.getInstance().getTimeInMillis() - lastExamDateInMillis;
        Log.d("Days: ", TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) + "");

        SimpleDateFormat sdfDate = new SimpleDateFormat(context.getString(R.string.widget_date_format), Locale.getDefault());
        Date lastExamDate = new Date(lastExamDateInMillis);
        views.setTextViewText(R.id.title_textview, context.getString(R.string.last_exam_date, sdfDate.format(lastExamDate)));

        if(MAX_DAY_WITHOUT_EXAM >= TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)){
            views.setTextColor(R.id.status_textview, ContextCompat.getColor(context, android.R.color.holo_green_dark));
            views.setTextViewText(R.id.status_textview, context.getString(R.string.ok_widget_status));
        }else {
            views.setTextColor(R.id.status_textview, ContextCompat.getColor(context, android.R.color.holo_red_dark));
            views.setTextViewText(R.id.status_textview, context.getString(R.string.make_another_exam_widget_status));
        }


        // Instruct the widget manager to update the widget
         appWidgetManager.updateAppWidget(appWidgetId, views);
    }

}
