package com.soft507.travelsuggest.activity

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.romainpiel.shimmer.Shimmer
import com.soft507.travelsuggest.R
import com.soft507.travelsuggest.base.BaseActivity
import com.soft507.travelsuggest.bean.UserRegisterBean
import com.soft507.travelsuggest.databinding.ActivityRegisterBinding
import com.soft507.travelsuggest.http.Api
import com.soft507.travelsuggest.model.RegisterModel
import com.soft507.travelsuggest.util.CommonUtil
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : BaseActivity<ActivityRegisterBinding>() {

    private val viewModel by lazy { ViewModelProviders.of(this).get(RegisterModel::class.java) }

    private val shimmer: Shimmer = Shimmer()

    override fun initWork(savedInstanceState: Bundle?) {

        shimmer.start(mDataBinding.registerShimmerTextView)

        register_btn.setOnClickListener {

            val username = mDataBinding.registerUsername.text.toString().trim()
            val password = mDataBinding.registerPassword.text.toString().trim()
            val email = mDataBinding.registerEmail.text.toString().trim()
            val phone = mDataBinding.registerPhone.text.toString().trim()

            if (isRightFormat(username, password, phone, email)) {

                viewModel.setUserRegisterBean(UserRegisterBean(username, password, email, phone))
                viewModel.userRegisterLiveData.observe(this, Observer { userRegisterLiveData ->
                    when (userRegisterLiveData) {
                        Api.SUCCESS -> {
                            shortToast(R.string.register_success)
                            finish()
                            CommonUtil.JumpToActivity(this, this, LoginActivity::class.java)
                        }
                        Api.FAILED -> shortToast(R.string.register_failed)
                        Api.SERVERERROR -> shortToast(R.string.login_server_error)
                    }
                })
            }
        }
    }

    override fun onStart() {
        super.onStart()
        shimmer.start(mDataBinding.registerShimmerTextView)
    }

    override fun onDestroy() {
        super.onDestroy()
        shimmer.cancel()
    }

    override fun onStop() {
        super.onStop()
        shimmer.cancel()
    }


    private fun isRightFormat(
        username: String,
        password: String,
        phone: String,
        email: String
    ): Boolean {

        if (username.isEmpty()) {
            shortToast(R.string.register_input_username)
            return false
        }

        if (password.isEmpty()) {
            shortToast(R.string.register_input_password)
            return false
        }

        if (phone.isEmpty()) {
            shortToast(R.string.register_input_phone)
            return false
        }

        if (email.isEmpty()) {
            shortToast(R.string.register_input_email)
            return false
        }

        val find = email.find { it == '@' }

        if (find == null) {
            shortToast(R.string.register_input_right_format_email)
            return false
        }

        return true
    }


    override fun getLayoutRes() = R.layout.activity_register

}
