package com.practice.snaperhelperpractice;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.LinkedList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhuyakun on 2018/1/2.
 */

public class MyAdapter extends BaseQuickAdapter<String, MyAdapter.ViewHolder> {


    public MyAdapter() {
        super(R.layout.item_view, new LinkedList<String>());
    }

    @Override
    protected void convert(ViewHolder helper, String item) {
        helper.bindData(item);
    }

    public static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.itemIv)
        ImageView itemIv;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bindData(String data) {
            Glide.with(itemView.getContext())
                    .load(data)
                    .apply(RequestOptions.circleCropTransform())
                    .into(itemIv);
        }
    }

}
