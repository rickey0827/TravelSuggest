package com.soft507.travelsuggest.fragment


import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.scwang.smart.refresh.footer.BallPulseFooter
import com.scwang.smart.refresh.header.BezierRadarHeader
import com.scwang.smart.refresh.layout.constant.SpinnerStyle
import com.soft507.travelsuggest.R
import com.soft507.travelsuggest.adapter.ChatAreaAdapter
import com.soft507.travelsuggest.base.BaseFragment
import com.soft507.travelsuggest.bean.AllTopicBean
import com.soft507.travelsuggest.bean.AllVideosBean
import com.soft507.travelsuggest.databinding.FragmentChatAreaBinding
import com.soft507.travelsuggest.model.ChatAreaViewModel


class ChatAreaFragment : BaseFragment<FragmentChatAreaBinding>() {

    private var data = arrayListOf<AllTopicBean.Data>()
    private lateinit var chatAdapter: ChatAreaAdapter

    private var viewStatus = false

    private val viewModel by lazy {
        ViewModelProviders.of(this).get(ChatAreaViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mDataBinding.videoChatRefreshLayout.setRefreshHeader(
            BezierRadarHeader(activity).setEnableHorizontalDrag(
                true
            )
        )
        mDataBinding.videoChatRefreshLayout.setRefreshFooter(
            BallPulseFooter(activity).setSpinnerStyle(
                SpinnerStyle.Scale
            )
        )

        mDataBinding.videoChatRefreshLayout.setOnRefreshListener { _ ->
            mDataBinding.videoChatRefreshLayout.finishRefresh(true)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mDataBinding.videoChatRecyclerView.layoutManager = LinearLayoutManager(activity)
        chatAdapter = ChatAreaAdapter(R.layout.chat_layout_recycleview, data)
        mDataBinding.videoChatRecyclerView.adapter = chatAdapter
        refreshUI()
    }

    private fun refreshUI() {
        data.clear()
        viewModel.setKey("admin")
        viewModel.chatLiveData.observe(viewLifecycleOwner, Observer { chatLiveData ->
            data.addAll(chatLiveData as ArrayList<AllTopicBean.Data>)
            chatAdapter.notifyDataSetChanged()
        })
    }

    override fun getLayoutRes() = R.layout.fragment_chat_area
    override fun initWork(savedInstanceState: Bundle?) {
    }


}
