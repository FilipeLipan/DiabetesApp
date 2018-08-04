package com.utfpr.myapplication.ui.modules.history;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.utfpr.myapplication.R;
import com.utfpr.myapplication.models.History;
import com.utfpr.myapplication.utils.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HistoryAdapter extends BaseQuickAdapter<History, BaseViewHolder> {

    private SimpleDateFormat mDayFormatter = new SimpleDateFormat("dd", Locale.getDefault());
    private SimpleDateFormat mMonthFormatter = new SimpleDateFormat("MMM", Locale.getDefault());
    private SimpleDateFormat mTimeFormatter = new SimpleDateFormat("hh:mm", Locale.getDefault());
    private Context context;

    HistoryAdapter(Context context, @Nullable List<History> data) {
        super(R.layout.list_item_history, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, History item) {
        helper.addOnClickListener(R.id.backgroud_view);

        Date date;
        try {
            date = StringUtils.transformStringIntoDate(item.getCreatedAt());
        } catch (ParseException e) {
            e.printStackTrace();
            date = Calendar.getInstance().getTime();
        }

        helper.setText(R.id.day_number_textview, mDayFormatter.format(date))
                .setText(R.id.month_name_textview, mMonthFormatter.format(date).toUpperCase())
                .setText(R.id.scan_type_textview, StringUtils.getExamType(context, item.getType()))
                .setText(R.id.scan_fulldate_textview,
                        mContext.getString(R.string.history_second_label,
                        mTimeFormatter.format(date),
                        StringUtils.getResultType(mContext, item.getResult())));


        if(item.getType().equalsIgnoreCase(StringUtils.FOOT_SCAN_TYPE)){
            View view = helper.getView(R.id.root_cardview);
            view.setEnabled(false);
            view.setClickable(false);
            view.setFocusable(false);
        }
    }
}
