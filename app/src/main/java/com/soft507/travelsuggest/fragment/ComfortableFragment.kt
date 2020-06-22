package com.soft507.travelsuggest.fragment


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.soft507.travelsuggest.R
import com.soft507.travelsuggest.activity.ReleaseContentActivity
import com.soft507.travelsuggest.adapter.PageComfortableAdapter
import com.soft507.travelsuggest.base.BaseFragment
import com.soft507.travelsuggest.databinding.FragmentComfortableBinding
import com.soft507.travelsuggest.util.CommonUtil


class ComfortableFragment : BaseFragment<FragmentComfortableBinding>() {

    private var fragments = arrayListOf<Fragment>()

    private var titles = arrayOfNulls<String>(2)


    override fun getLayoutRes() =
        R.layout.fragment_comfortable

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        titles[0] = resources.getString(R.string.comfortable_tab_video)
        titles[1] = resources.getString(R.string.comfortable_tab_chat)
        fragments.add(VideoAreaFragment())
        fragments.add(ChatAreaFragment())
        mDataBinding.comfortableViewPager.adapter =
            PageComfortableAdapter(fragments, titles, childFragmentManager)

        mDataBinding.comfortableTabLayout.setViewPager(
            mDataBinding.comfortableViewPager,
            titles,
            activity,
            fragments
        )
        mDataBinding.comfortableViewPager.currentItem = 0

        mDataBinding.uploadBtn.setOnClickListener {
            CommonUtil.JumpToActivity(activity!!, activity!!, ReleaseContentActivity::class.java)
        }

    }

    override fun initWork(savedInstanceState: Bundle?) {
    }

}
