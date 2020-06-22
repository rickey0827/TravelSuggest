package com.soft507.travelsuggest.activity

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import com.soft507.travelsuggest.R
import com.soft507.travelsuggest.base.BaseActivity
import com.soft507.travelsuggest.databinding.ActivityAuthorBinding

class AuthorActivity : BaseActivity<ActivityAuthorBinding>() {

    override fun initWork(savedInstanceState: Bundle?) {
        mDataBinding.acWebView.loadUrl(resources.getString(R.string.app_author_github_address))
        mDataBinding.acWebView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url)
                return true
            }
        }
    }


    override fun getLayoutRes() = R.layout.activity_author

}
