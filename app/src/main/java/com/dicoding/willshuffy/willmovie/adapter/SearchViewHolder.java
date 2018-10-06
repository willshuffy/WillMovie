package com.dicoding.willshuffy.willmovie.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dicoding.willshuffy.willmovie.R;
import com.dicoding.willshuffy.willmovie.mvp.model.upcoming.ResultsItem;
import com.dicoding.willshuffy.willmovie.utils.CustomTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.img_poster)
    ImageView img_poster;

    @BindView(R.id.tv_title)
    CustomTextView tv_title;

    @BindView(R.id.tv_overview)
    TextView tv_overview;

    @BindView(R.id.tv_release_date)
    TextView tv_release_date;

    public SearchViewHolder(View itemView){
        super(itemView);

        ButterKnife.bind(this, itemView);
    }

    public void bind(ResultsItem item){
        tv_title.setText(item.getTitle());
        tv_overview.setText(item.getOverview());
        tv_release_date.setText(item.getReleaseDate());
    }

}
