package com.utfpr.myapplication.ui.modules.main;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.utfpr.myapplication.R;
import com.utfpr.myapplication.models.News;

import java.util.List;

public class NewsAdapter extends BaseQuickAdapter<News,BaseViewHolder> {
    private final Context context;

    public NewsAdapter(Context context,@Nullable List<News> data) {
        super(R.layout.item_news, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, News news) {
        Glide.with(context)
                .load(news.getImage())
                .thumbnail(Glide.with(context)
                        .load(R.drawable.news_placeholder))
                .into((ImageView) helper.getView(R.id.news_banner_imageview));

        helper.setText(R.id.news_title_textview, news.getTitle());
    }
}
