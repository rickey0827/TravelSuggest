package com.soft507.travelsuggest.fragment


import android.annotation.SuppressLint
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import androidx.emoji.text.EmojiCompat
import androidx.emoji.widget.EmojiEditText
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.orhanobut.logger.Logger
import com.scwang.smart.refresh.footer.BallPulseFooter
import com.scwang.smart.refresh.header.BezierRadarHeader
import com.scwang.smart.refresh.layout.constant.SpinnerStyle
import com.soft507.travelsuggest.R
import com.soft507.travelsuggest.UserLike
import com.soft507.travelsuggest.adapter.UserSpeakAdapter
import com.soft507.travelsuggest.adapter.VideoAreaAdapter
import com.soft507.travelsuggest.base.BaseFragment
import com.soft507.travelsuggest.bean.AllVideosBean
import com.soft507.travelsuggest.bean.UserCommentBean
import com.soft507.travelsuggest.bean.WorksInfoBean
import com.soft507.travelsuggest.databinding.FragmentVideoAreaBinding
import com.soft507.travelsuggest.model.VideoAreaViewModel
import com.xiao.nicevideoplayer.NiceVideoPlayer
import com.xiao.nicevideoplayer.NiceVideoPlayerManager
import java.security.Key
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class VideoAreaFragment : BaseFragment<FragmentVideoAreaBinding>() {

    private var popupWindow: PopupWindow? = null
    private var contentView: View? = null
    private lateinit var videoPlayer: VideoAreaAdapter
    private var data = arrayListOf<AllVideosBean.Data>()
    private var comments = arrayListOf<WorksInfoBean.Comment>()
    private var worksInfoBean = arrayListOf<WorksInfoBean.WorksInfo>()

    private lateinit var recyclerView: RecyclerView
    private lateinit var speakAdapter: UserSpeakAdapter
    private lateinit var pop_discuss_close: ImageView
    private lateinit var pop_discuss_count: TextView
    private lateinit var pop_discuss_send: EmojiEditText

    private var position = 0

    private val viewModel by lazy {
        ViewModelProviders.of(this).get(VideoAreaViewModel::class.java)
    }


    @SuppressLint("SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mDataBinding.videoAreaRefreshLayout.setRefreshHeader(
            BezierRadarHeader(activity).setEnableHorizontalDrag(
                true
            )
        )
        mDataBinding.videoAreaRefreshLayout.setRefreshFooter(
            BallPulseFooter(activity).setSpinnerStyle(
                SpinnerStyle.Scale
            )
        )

        mDataBinding.videoAreaRefreshLayout.setOnRefreshListener { _ ->
            mDataBinding.videoAreaRefreshLayout.finishRefresh(true)
        }

        mDataBinding.videoAreaRecyclerView.layoutManager = LinearLayoutManager(activity)

        videoPlayer = VideoAreaAdapter(
            R.layout.fragment_video_ry_layout,
            data
            , activity!!
        )

        mDataBinding.videoAreaRecyclerView.adapter = videoPlayer

        mDataBinding.videoAreaRecyclerView.setRecyclerListener { holder ->
            val viewModel = holder as BaseViewHolder
            val niceVideoPlayer =
                viewModel.getView<NiceVideoPlayer>(R.id.video_ry_videoPlayer)
            if (niceVideoPlayer == NiceVideoPlayerManager.instance()
                    .currentNiceVideoPlayer
            ) {
                NiceVideoPlayerManager.instance().releaseNiceVideoPlayer()
            }
        }

        videoPlayer.addChildClickViewIds(R.id.video_ry_talk_panel, R.id.video_ry_dianZan_Panel)
        videoPlayer.setOnItemChildClickListener { _, view, position ->
            when (view.id) {
                R.id.video_ry_talk_panel -> {
                    this.position = position
                    initPop(data[position].sc_videoname)
                }
                R.id.video_ry_dianZan_Panel -> {
                    view.findViewById<ImageView>(R.id.video_ry_dianzan)
                        .setImageResource(R.mipmap.ic_selected_dianzan)
                    UserLike.data.add(data[position])
                    shortToast(activity!!, resources.getString(R.string.video_ry_like))
                }
            }
        }

        contentView = LayoutInflater.from(activity).inflate(R.layout.pop_discuss_layout, null)

        speakAdapter = UserSpeakAdapter(R.layout.pop_user_speak_layout, comments)
        recyclerView = contentView!!.findViewById<RecyclerView>(R.id.pop_discuss_ry)
        pop_discuss_close = contentView!!.findViewById<ImageView>(R.id.pop_discuss_close)
        pop_discuss_count = contentView!!.findViewById<TextView>(R.id.pop_discuss_count)
        pop_discuss_send = contentView!!.findViewById<EmojiEditText>(R.id.pop_discuss_send)

        pop_discuss_send.setOnEditorActionListener { textView, actionId, keyEvent ->

            if (actionId == EditorInfo.IME_ACTION_SEND) {
                val username = kv.decodeString("_username")
                val url = kv.decodeString("_url")
                val content = textView.text.toString()
                Logger.d(content)
                val videoName = data[position].sc_videoname
                val sdf = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
                val time = sdf.format(Date(System.currentTimeMillis()))
                viewModel.setUserComment(
                    UserCommentBean(
                        username,
                        videoName,
                        time,
                        content,
                        url
                    )
                )
                viewModel.commentsLiveData.observe(
                    viewLifecycleOwner,
                    Observer { commentsLiveData ->
                        Logger.d(commentsLiveData)
                        if (commentsLiveData == true) {
                            shortToast(
                                activity!!,
                                resources.getString(R.string.comment_success)
                            )

                            speakAdapter.addData(
                                WorksInfoBean.Comment(
                                    content,
                                    "0",
                                    time,
                                    url,
                                    username,
                                    videoName
                                )
                            )

                        } else {
                            shortToast(
                                activity!!,
                                resources.getString(R.string.comment_error)
                            )
                        }
                    })
                return@setOnEditorActionListener false
            }
            return@setOnEditorActionListener false
        }


        pop_discuss_close.setOnClickListener { v ->
            if (popupWindow!!.isShowing) {
                popupWindow!!.dismiss()
            }
        }


        recyclerView.layoutManager = LinearLayoutManager(activity)

        recyclerView.adapter = speakAdapter

        popupWindow = PopupWindow(
            contentView,
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        popupWindow!!.isFocusable = true
        popupWindow!!.setBackgroundDrawable(BitmapDrawable())
        popupWindow!!.isOutsideTouchable = true
        popupWindow!!.isTouchable = true
        popupWindow!!.animationStyle = R.style.PopWindow_anim_style


        viewModel.setNoData("mark")
        viewModel.allVideosLiveData.observe(viewLifecycleOwner, Observer { allVideosLiveData ->
            data.addAll(allVideosLiveData as ArrayList<AllVideosBean.Data>)
            videoPlayer.notifyDataSetChanged()
        })


    }

    private fun initPop(scVideoname: String) {
        Logger.d(scVideoname)

        viewModel.setVideoName(scVideoname)

        viewModel.worksVideosLiveData.observe(viewLifecycleOwner, Observer { worksVideosLiveData ->
            val data = worksVideosLiveData as WorksInfoBean.Data
            comments.clear()
            worksInfoBean.clear()
            comments.addAll(data.comments as ArrayList<WorksInfoBean.Comment>)
            worksInfoBean.addAll(data.works_info as ArrayList<WorksInfoBean.WorksInfo>)
            pop_discuss_count.text = worksInfoBean[0].sc_videocount + "条评论"
            speakAdapter.notifyDataSetChanged()
            popupWindow!!.showAtLocation(
                contentView,
                Gravity.BOTTOM,
                0,
                0
            )
        })


    }


    override fun onStop() {
        super.onStop()
        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer()
    }

    override fun getLayoutRes() = R.layout.fragment_video_area
    override fun initWork(savedInstanceState: Bundle?) {
    }


}
