package com.soft507.travelsuggest.activity

import android.os.Bundle
import com.bumptech.glide.Glide
import com.soft507.travelsuggest.R
import com.soft507.travelsuggest.base.BaseActivity
import com.soft507.travelsuggest.databinding.ActivityQrCodeBinding
import com.soft507.travelsuggest.http.Api
import com.uuzuche.lib_zxing.activity.CodeUtils

class QrCodeActivity : BaseActivity<ActivityQrCodeBinding>() {

    override fun initWork(savedInstanceState: Bundle?) {
        setSupportActionBar(mDataBinding.qrCodeToolbar)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        mDataBinding.qrCodeToolbar.navigationIcon =
            resources.getDrawable(R.mipmap.ic_left_small_arrow)
        mDataBinding.qrCodeToolbar.setNavigationOnClickListener {
            finish()
        }

        val name = kv.decodeString("_username")
        val city = kv.decodeString("_city")
        val url = kv.decodeString("_url")
        val sex = kv.decodeString("_sex")

        mDataBinding.qrUserCity.text = city
        mDataBinding.qrUserName.text = name
        mDataBinding.qrUserSex.setBackgroundResource(if (sex == "男") R.mipmap.ic_sex_man else R.mipmap.ic_sex_woman)
        Glide.with(mActivity).load("${Api.IMAGE_BASE_URL}${url}").into(mDataBinding.qrUserIcon)

        val mBitmap = CodeUtils.createImage(name, 200, 200, null);
        mDataBinding.qrCodeImage.setImageBitmap(mBitmap);
    }

    override fun getLayoutRes() = R.layout.activity_qr_code
}
