package com.soft507.travelsuggest.adapter

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.FutureTarget
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.davemorrissey.labs.subscaleview.ImageSource
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView
import com.soft507.travelsuggest.R
import com.soft507.travelsuggest.bean.AllTopicBean
import com.soft507.travelsuggest.http.Api
import java.io.File


/**
 * @author LRQ-Pro
 * @description:
 * @date : 2020/6/9 21:21
 */
class ChatAreaAdapter(recourse: Int, data: MutableList<AllTopicBean.Data>) :

    BaseQuickAdapter<AllTopicBean.Data, BaseViewHolder>(recourse, data) {

    override fun convert(holder: BaseViewHolder, item: AllTopicBean.Data) {

        if (item.sc_image_url == null) {
            holder.getView<SubsamplingScaleImageView>(R.id.chat_scaleImageView).visibility =
                View.GONE
        } else {
            holder.getView<SubsamplingScaleImageView>(R.id.chat_scaleImageView).visibility =
                View.VISIBLE
        }

        Glide.with(context).load(Api.IMAGE_BASE_URL + item.sc_url)
            .into(holder.getView(R.id.chat_NiceImageView))
        holder.setText(R.id.chat_author, item.sc_author)
        holder.setText(R.id.chat_content, item.sc_content)
        holder.setText(R.id.chat_mark, item.sc_usermark)
        holder.setText(R.id.chat_ry_share_count, item.sc_sharecount)
        holder.setText(R.id.chat_ry_chat_count, item.sc_usertalkcount)
        holder.setText(R.id.chat_ry_chat_dianZan, item.sc_userLikecount)
        holder.setText(R.id.chat_ry_chat_cai, item.sc_userdisLikecount)


//        val bitmap: FutureTarget<Bitmap> = Glide.with(context)
//            .asBitmap()
//            .load(Api.IMAGE_BASE_URL + item.sc_image_url)
//            .submit()
//
//        holder.getView<SubsamplingScaleImageView>(R.id.chat_scaleImageView)
//            .setImage(ImageSource.bitmap(bitmap.get()))

        Glide.with(context)
            .download(Api.IMAGE_BASE_URL + item.sc_image_url).into(
                object : SimpleTarget<File>() {

                    override fun onLoadFailed(errorDrawable: Drawable?) {
                        super.onLoadFailed(errorDrawable)
                    }

                    override fun onResourceReady(resource: File, transition: Transition<in File>?) {
                        holder.getView<SubsamplingScaleImageView>(R.id.chat_scaleImageView)
                            .setImage(ImageSource.uri(resource.absolutePath))
                        holder.getView<SubsamplingScaleImageView>(R.id.chat_scaleImageView).maxScale =
                            10f
                    }
                }
            )

    }


}