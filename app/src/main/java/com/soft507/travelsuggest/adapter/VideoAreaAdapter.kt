package com.soft507.travelsuggest.adapter

import android.app.Activity
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.emoji.text.EmojiCompat
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.soft507.travelsuggest.R
import com.soft507.travelsuggest.bean.AllVideosBean
import com.soft507.travelsuggest.http.Api
import com.soft507.travelsuggest.util.TimeUtil
import com.xiao.nicevideoplayer.NiceVideoPlayer
import com.xiao.nicevideoplayer.TxVideoPlayerController
import java.text.SimpleDateFormat
import java.util.*


/**
 * @author LRQ-Pro
 * @description:
 * @date : 2020/6/9 1:48
 */
class VideoAreaAdapter(recourse: Int, data: MutableList<AllVideosBean.Data>, context: Activity) :
    BaseQuickAdapter<AllVideosBean.Data, BaseViewHolder>(recourse, data) {

    override fun convert(holder: BaseViewHolder, item: AllVideosBean.Data) {
        holder.setText(R.id.video_ry_author, item.sc_author)
        Glide.with(context).load(Api.IMAGE_BASE_URL + item.sc_url)
            .into(holder.getView(R.id.video_ry_icon))
        holder.setText(R.id.video_ry_speak, item.sc_content)
        holder.setText(R.id.video_ry_dianzan_count, item.sc_videocount)
        holder.setText(R.id.video_ry_talk_count, item.sc_videotalkcount)
        holder.setText(R.id.video_ry_response_count, item.sc_videoresponse)


        val niceVideoPlayer = holder.getView<NiceVideoPlayer>(R.id.video_ry_videoPlayer)
        niceVideoPlayer.setPlayerType(NiceVideoPlayer.TYPE_IJK)
        niceVideoPlayer.setUp(Api.VIDEO_BASE_URL + item.sc_videoname, null)

        val controller = TxVideoPlayerController(context)
        controller.setTitle(item.sc_author)
        controller.setLenght(98000);  //时间以mm为单位计算
        Glide.with(context).load(Api.VIDEO_BASE_URL + item.sc_videoname)
            .placeholder(R.mipmap.personal_icon_selected)
            .into(controller.imageView()) //加载视频图片到蒙版上

        niceVideoPlayer.setController(controller)

    }


}