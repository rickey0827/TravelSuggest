package com.soft507.travelsuggest.activity

import android.os.Bundle
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.soft507.travelsuggest.R
import com.soft507.travelsuggest.adapter.UserMoreRecyclerViewAdapter
import com.soft507.travelsuggest.base.BaseActivity
import com.soft507.travelsuggest.bean.UserMoreBean
import com.soft507.travelsuggest.databinding.ActivityMoreBinding

class MoreActivity : BaseActivity<ActivityMoreBinding>() {

    private lateinit var userMoreRecyclerViewAdapter: UserMoreRecyclerViewAdapter
    private var dataList = arrayListOf<UserMoreBean>()

    override fun initWork(savedInstanceState: Bundle?) {
        setSupportActionBar(mDataBinding.moreToolbar)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        mDataBinding.moreToolbar.navigationIcon =
            resources.getDrawable(R.mipmap.ic_left_small_arrow)
        mDataBinding.moreToolbar.setNavigationOnClickListener {
            finish()
        }

        val sex = kv.decodeString("_sex")
        val city = kv.decodeString("_city")
        val speak = kv.decodeString("_speak")

        dataList.add(UserMoreBean("性别", sex))
        dataList.add(UserMoreBean("地区", city))
        dataList.add(UserMoreBean("个性签名", speak))

        userMoreRecyclerViewAdapter =
            UserMoreRecyclerViewAdapter(R.layout.person_layout_recycleview, dataList)

        mDataBinding.moreRecyclerview.layoutManager = LinearLayoutManager(this)
        mDataBinding.moreRecyclerview.itemAnimator = DefaultItemAnimator()
        mDataBinding.moreRecyclerview.adapter = userMoreRecyclerViewAdapter
        mDataBinding.moreRecyclerview.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )
    }

    override fun getLayoutRes() = R.layout.activity_more

}
