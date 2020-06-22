package com.soft507.travelsuggest.listener;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @author LRQ-Pro
 * @description:
 * @date : 2020/6/12 10:14
 */
public interface OnItemLongClickListener {
    void onItemLongClick(RecyclerView.ViewHolder holder, int position, View v);
}
