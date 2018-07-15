package com.utfpr.myapplication.ui.modules.history;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.utfpr.myapplication.R;

import java.util.List;

public class HistoryAdapter extends BaseQuickAdapter<HistoryItem, BaseViewHolder> {

    public HistoryAdapter(@Nullable List<HistoryItem> data) {
        super(R.layout.list_item_history, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HistoryItem item) {
        helper.addOnClickListener(R.id.backgroud_view);
    }
}
