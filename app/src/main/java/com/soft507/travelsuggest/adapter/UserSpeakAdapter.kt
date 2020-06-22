package com.soft507.travelsuggest.adapter

import android.annotation.SuppressLint
import android.app.Activity
import androidx.emoji.text.EmojiCompat
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.soft507.travelsuggest.R
import com.soft507.travelsuggest.bean.WorksInfoBean
import com.soft507.travelsuggest.http.Api
import com.soft507.travelsuggest.util.TimeUtil
import java.text.SimpleDateFormat

/**
 * @author LRQ-Pro
 * @description:
 * @date : 2020/6/9 5:57
 */
class UserSpeakAdapter(recourse: Int, data: MutableList<WorksInfoBean.Comment>) :
    BaseQuickAdapter<WorksInfoBean.Comment, BaseViewHolder>(recourse, data) {

    @SuppressLint("SimpleDateFormat")
    override fun convert(holder: BaseViewHolder, item: WorksInfoBean.Comment) {
        holder.setText(R.id.pop_user_speak_author, item.sc_user)
        holder.setText(R.id.pop_user_speak_content, item.sc_content)
        holder.setText(R.id.pop_user_likeCount, item.sc_likecount)
        Glide.with(context).load(Api.IMAGE_BASE_URL + item.sc_url)
            .into(holder.getView(R.id.pop_user_speak_icon))

        val date =
            TimeUtil.getTimeFormatText(SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(item.sc_time))
        holder.setText(R.id.pop_user_speak_time, date)

    }

}