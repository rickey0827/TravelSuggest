package com.soft507.travelsuggest.adapter

import android.app.Activity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.soft507.travelsuggest.R
import com.soft507.travelsuggest.TravelApp
import com.soft507.travelsuggest.activity.MoreActivity
import com.soft507.travelsuggest.activity.QrCodeActivity
import com.soft507.travelsuggest.bean.UserListBean
import com.soft507.travelsuggest.http.Api
import com.soft507.travelsuggest.util.CommonUtil


/**
 * @author LRQ-Pro
 * @description:
 * @date : 2020/6/8 12:10
 */
class UserListRecyclerViewAdapter(data: MutableList<UserListBean>, mActivity: Activity) :
    BaseMultiItemQuickAdapter<UserListBean, BaseViewHolder>(data) {

    private var mActivity: Activity

    init {
        this.mActivity = mActivity
        addItemType(0, R.layout.person_layout_recycleview_first)
        addItemType(1, R.layout.person_layout_recycleview)
    }


    override fun convert(holder: BaseViewHolder, item: UserListBean) {
        when (holder.itemViewType) {
            0 -> {
                holder.setText(R.id.personal_name_ry_first, item.name)
                Glide.with(TravelApp.context).load(item.url)
                    .into(holder.getView(R.id.personal_url_ry_first) as ImageView)
            }

            1 -> {
                holder.setText(R.id.personal_name_ry, item.name)
                if (item.url == null) {
                    holder.getView<ImageView>(R.id.personal_qrCode_ry).visibility =
                        View.GONE
                    holder.getView<ImageView>(R.id.personal_image_ry).visibility =
                        View.GONE
                    holder.getView<TextView>(R.id.personal_text_ry).visibility =
                        View.VISIBLE

                    holder.setText(R.id.personal_text_ry, item.text)
                } else {
                    if ("mark" == item.url) {
                        holder.getView<ImageView>(R.id.personal_qrCode_ry).visibility =
                            View.VISIBLE
                        holder.getView<ImageView>(R.id.personal_image_ry).visibility =
                            View.VISIBLE
                        holder.getView<TextView>(R.id.personal_text_ry).visibility =
                            View.GONE
                    }
                    if ("display" == item.url) {
                        holder.getView<ImageView>(R.id.personal_qrCode_ry).visibility =
                            View.GONE
                        holder.getView<ImageView>(R.id.personal_image_ry).visibility =
                            View.VISIBLE
                        holder.getView<TextView>(R.id.personal_text_ry).visibility =
                            View.GONE
                    }
                }
            }
        }
    }
}