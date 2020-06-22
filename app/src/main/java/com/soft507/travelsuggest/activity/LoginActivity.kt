package com.soft507.travelsuggest.activity

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.kongzue.dialog.v3.TipDialog
import com.orhanobut.logger.Logger
import com.romainpiel.shimmer.Shimmer
import com.soft507.travelsuggest.R
import com.soft507.travelsuggest.base.BaseActivity
import com.soft507.travelsuggest.bean.EmailBean
import com.soft507.travelsuggest.bean.UserBean
import com.soft507.travelsuggest.databinding.ActivityLoginBinding
import com.soft507.travelsuggest.http.Api
import com.soft507.travelsuggest.model.LoginViewModel
import com.soft507.travelsuggest.util.CommonUtil
import com.soft507.travelsuggest.util.Preference
import com.soft507.travelsuggest.util.SPUtils


class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    private val viewModel by lazy { ViewModelProviders.of(this).get(LoginViewModel::class.java) }

    private val shimmer: Shimmer = Shimmer()

    override fun initWork(savedInstanceState: Bundle?) {
        shimmer.start(mDataBinding.loginShimmerTextView)
        mDataBinding.loginVersion.text = CommonUtil.getVersion(this)
        mDataBinding.jumpRegister.setOnClickListener {
            CommonUtil.JumpToActivity(this, this, RegisterActivity::class.java)
        }
    }

    override fun onStart() {
        super.onStart()
        shimmer.start(mDataBinding.loginShimmerTextView)
    }

    override fun onDestroy() {
        super.onDestroy()
        shimmer.cancel()
    }

    override fun onStop() {
        super.onStop()
        shimmer.cancel()
    }

    fun click(view: View?) {

        val username = mDataBinding.loginUsername.text?.trim()
        val password = mDataBinding.loginPassword.text?.trim()

        if (TextUtils.isEmpty(username)) {
            shortSnackbar(view, R.string.login_username_and_password_null)
            return
        }
        if (TextUtils.isEmpty(password)) {
            shortSnackbar(view, R.string.login_password_null)
            return
        }

        loadDialog(R.string.login_loading)

        val find = username!!.find { it == '@' }
        if (find == null) {
            viewModel.setUserInfo(UserBean(username.toString(), password?.toString()))
            viewModel.userLiveData.observe(this, Observer { userLiveData ->
                dissDialog()
                when (userLiveData) {
                    Api.SUCCESS -> {
                        comDialog(R.string.login_success, TipDialog.TYPE.SUCCESS)
                        CommonUtil.JumpToActivity(
                            this@LoginActivity,
                            this@LoginActivity,
                            HomeActivity::class.java
                        )
                        kv.encode("username", username.toString())
                        finish()
                    }
                    Api.FAILED -> comDialog(R.string.login_failed, TipDialog.TYPE.WARNING)
                    Api.SERVERERROR -> comDialog(
                        R.string.login_server_error,
                        TipDialog.TYPE.WARNING
                    )
                }
                Logger.d("userLogin")
            })
        } else {
            viewModel.setEmailBean(EmailBean(username.toString(), password?.toString()))
            viewModel.emailLiveData.observe(this, Observer { emailLiveData ->
                dissDialog()
                when (emailLiveData) {
                    Api.SUCCESS -> {
                        comDialog(R.string.login_success, TipDialog.TYPE.SUCCESS)
                        CommonUtil.JumpToActivity(
                            this@LoginActivity,
                            this@LoginActivity,
                            HomeActivity::class.java
                        )
                        kv.encode("username", username.toString())
                        finish()
                    }
                    Api.FAILED -> comDialog(R.string.login_failed, TipDialog.TYPE.WARNING)
                    Api.SERVERERROR -> comDialog(
                        R.string.login_server_error,
                        TipDialog.TYPE.WARNING
                    )
                }
                Logger.d("emailLogin")
            })
        }
    }


    override fun getLayoutRes() = R.layout.activity_login
}

