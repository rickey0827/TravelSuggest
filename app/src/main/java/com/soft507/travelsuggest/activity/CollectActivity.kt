package com.soft507.travelsuggest.activity

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.soft507.travelsuggest.R
import com.soft507.travelsuggest.UserLike
import com.soft507.travelsuggest.adapter.CollectPlayAdapter
import com.soft507.travelsuggest.base.BaseActivity
import com.soft507.travelsuggest.bean.CollectPlayBean
import com.soft507.travelsuggest.databinding.ActivityCollectBinding

class CollectActivity : BaseActivity<ActivityCollectBinding>() {


    private var data = arrayListOf<CollectPlayBean>()

    private var adapter: CollectPlayAdapter? = null

    override fun initWork(savedInstanceState: Bundle?) {
        setSupportActionBar(mDataBinding.collectToolbar)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        mDataBinding.collectToolbar.navigationIcon =
            resources.getDrawable(R.mipmap.ic_left_small_arrow)
        mDataBinding.collectToolbar.setNavigationOnClickListener {
            finish()
        }

        data.clear()
        UserLike.data.forEach {
            data.add(CollectPlayBean(it.sc_videoname, it.sc_videocount))
        }

        adapter = CollectPlayAdapter(R.layout.collect_ry_layout, data)
        mDataBinding.collectRy.layoutManager = GridLayoutManager(this, 3)
        mDataBinding.collectRy.adapter = adapter
    }

    override fun getLayoutRes() = R.layout.activity_collect
}

