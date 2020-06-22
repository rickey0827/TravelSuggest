package com.soft507.travelsuggest.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.soft507.travelsuggest.R
import com.soft507.travelsuggest.activity.AuthorActivity
import com.soft507.travelsuggest.activity.CollectActivity
import com.soft507.travelsuggest.activity.UserInfoActivity
import com.soft507.travelsuggest.adapter.PersonalCenterAdapter
import com.soft507.travelsuggest.base.BaseFragment
import com.soft507.travelsuggest.bean.PersonalBean
import com.soft507.travelsuggest.databinding.FragmentPersonalCenterBinding
import com.soft507.travelsuggest.http.Api
import com.soft507.travelsuggest.util.CommonUtil

class PersonalCenterFragment : BaseFragment<FragmentPersonalCenterBinding>() {

    private lateinit var personalCenterAdapter: PersonalCenterAdapter
    private var data = arrayListOf<PersonalBean>()

    override fun getLayoutRes() =
        R.layout.fragment_personal_center

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Glide.with(mContext!!).load("${Api.IMAGE_BASE_URL}${kv.decodeString("_url")}")
            .into(mDataBinding.personalUserIcon)
        mDataBinding.personalUsername.text = kv.decodeString("_username")
        mDataBinding.personalUserKey.text =
            "${resources.getString(R.string.app_user_key)}${kv.decodeString("_userkey")}"
        mDataBinding.personalUserInfo.setOnClickListener { v ->
            CommonUtil.JumpToActivity(mContext!!, activity!!, UserInfoActivity::class.java)
        }
        data.clear()
        data.add(PersonalBean(R.mipmap.ic_personal_files, "我的收藏"))
        data.add(PersonalBean(R.mipmap.ic_personal_me, "关于作者"))
        data.add(PersonalBean(R.mipmap.ic_personal_setting, "设置"))

        personalCenterAdapter = PersonalCenterAdapter(R.layout.personalcenter_ry_layout, data)
        mDataBinding.personalRecyclerView.layoutManager = LinearLayoutManager(activity)
        mDataBinding.personalRecyclerView.adapter = personalCenterAdapter

        personalCenterAdapter.setOnItemClickListener { adapter, _, position ->
            when (position) {
                1 ->
                    CommonUtil.JumpToActivity(activity!!, activity!!, AuthorActivity::class.java)
                0 ->
                    CommonUtil.JumpToActivity(activity!!, activity!!, CollectActivity::class.java)
            }
        }
    }

    override fun initWork(savedInstanceState: Bundle?) {
    }
}
