package com.soft507.travelsuggest.activity

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.soft507.travelsuggest.R
import com.soft507.travelsuggest.adapter.PageHomeAdapter
import com.soft507.travelsuggest.base.BaseActivity
import com.soft507.travelsuggest.bean.Data
import com.soft507.travelsuggest.databinding.ActivityHomeBinding
import com.soft507.travelsuggest.fragment.ComfortableFragment
import com.soft507.travelsuggest.fragment.PersonalCenterFragment
import com.soft507.travelsuggest.fragment.TravelSuggestFragment
import com.soft507.travelsuggest.model.HomeViewModel
import com.xiao.nicevideoplayer.NiceVideoPlayerManager
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : BaseActivity<ActivityHomeBinding>() {

    private var fragments = arrayListOf<Fragment>()
    private var pageHomeAdapter: PageHomeAdapter? = null

    private val viewModel: HomeViewModel by lazy {
        ViewModelProviders.of(this).get(HomeViewModel::class.java)
    }

    override fun initWork(savedInstanceState: Bundle?) {

        fragments.add(TravelSuggestFragment())
        fragments.add(ComfortableFragment())
        fragments.add(PersonalCenterFragment())

        pageHomeAdapter = PageHomeAdapter(fragments, supportFragmentManager)
        mDataBinding.actHomeViewpager.adapter = pageHomeAdapter
        mDataBinding.actHomeViewpager.addOnPageChangeListener(object :
            ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                top_navigation_constraint.setCurrentActiveItem(position)
            }
        })
        mDataBinding.topNavigationConstraint.setNavigationChangeListener { view, position ->
            mDataBinding.actHomeViewpager.setCurrentItem(position, true)
        }


        viewModel.setUserInfo(kv.decodeString("username"))
        viewModel.userLiveData.observe(this, Observer { username ->
            val result = username as List<*>
            result.forEach {
                val data = it as Data
                kv.encode("_username", data._username)
                kv.encode("_userkey", data._userkey)
                kv.encode("_speak", data._speak)
                kv.encode("_sex", data._sex)
                kv.encode("_phone", data._phone)
                kv.encode("_password", data._password)
                kv.encode("_id", data._id)
                kv.encode("_email", data._email)
                kv.encode("_city", data._city)
                kv.encode("_url", data._url)
            }
        })

    }

    override fun onBackPressed() {
        if (NiceVideoPlayerManager.instance().onBackPressd()) return
        super.onBackPressed()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    override fun getLayoutRes() = R.layout.activity_home

}
