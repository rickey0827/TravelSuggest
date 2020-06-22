package com.soft507.travelsuggest.adapter

import android.view.View
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.soft507.travelsuggest.R
import com.soft507.travelsuggest.bean.UserMoreBean

/**
 * @author LRQ-Pro
 * @description:
 * @date : 2020/6/8 16:15
 */
class UserMoreRecyclerViewAdapter(resource: Int, data: MutableList<UserMoreBean>) :
    BaseQuickAdapter<UserMoreBean, BaseViewHolder>(resource, data) {


    override fun convert(holder: BaseViewHolder, item: UserMoreBean) {
        holder.getView<ImageView>(R.id.personal_qrCode_ry).visibility = View.GONE
        holder.getView<ImageView>(R.id.personal_image_ry).visibility = View.GONE
        holder.getView<TextView>(R.id.personal_text_ry).visibility = View.VISIBLE
        holder.setText(R.id.personal_name_ry, item.name)
        holder.setText(R.id.personal_text_ry, item.prams)
    }


}