package com.soft507.travelsuggest.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.soft507.travelsuggest.bean.EmailBean
import com.soft507.travelsuggest.bean.LoginBean
import com.soft507.travelsuggest.bean.UserBean
import com.soft507.travelsuggest.repository.LoginRepository

/**
 * @author LRQ-Pro
 * @description:
 * @date : 2020/6/7 15:44
 */
class LoginViewModel : ViewModel() {

    private val userInfo = MutableLiveData<UserBean>()
    private val emailBean = MutableLiveData<EmailBean>()


    val userLiveData = Transformations.switchMap(userInfo) { userInfo ->
        LoginRepository.userLogin(userInfo.username, userInfo.password)
    }

    val emailLiveData = Transformations.switchMap(emailBean) { emailBean ->
        LoginRepository.emailLogin(emailBean.email, emailBean.password)
    }

    fun setUserInfo(userInfo: UserBean) {
        this.userInfo.value = userInfo
    }

    fun setEmailBean(emailBean: EmailBean) {
        this.emailBean.value = emailBean
    }
}