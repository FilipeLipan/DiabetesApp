package com.utfpr.myapplication.ui.modules.history;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.utfpr.myapplication.R;
import com.utfpr.myapplication.models.History;

import java.util.List;

public class HistoryAdapter extends BaseQuickAdapter<History, BaseViewHolder> {

    HistoryAdapter(@Nullable List<History> data) {
        super(R.layout.list_item_history, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, History item) {
        helper.addOnClickListener(R.id.backgroud_view);
    }
}
