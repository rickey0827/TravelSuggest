package com.soft507.travelsuggest.adapter

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.soft507.travelsuggest.R
import com.soft507.travelsuggest.bean.CollectPlayBean
import com.soft507.travelsuggest.http.Api

/**
 * @author LRQ-Pro
 * @description:
 * @date : 2020/6/16 2:26
 */
class CollectPlayAdapter(recourse: Int, data: MutableList<CollectPlayBean>) :
    BaseQuickAdapter<CollectPlayBean, BaseViewHolder>(recourse, data) {

    override fun convert(holder: BaseViewHolder, item: CollectPlayBean) {
        holder.setText(R.id.collect_ry_count, item.videoCount)
        Glide.with(context).load(Api.VIDEO_BASE_URL + item.videoUrl)
            .placeholder(R.mipmap.personal_icon_selected)
            .into(holder.getView(R.id.collect_ry_image)) //加载视频图片到蒙版上
    }

}