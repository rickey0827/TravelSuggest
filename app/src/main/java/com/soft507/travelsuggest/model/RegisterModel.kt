package com.soft507.travelsuggest.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.soft507.travelsuggest.bean.UserRegisterBean
import com.soft507.travelsuggest.repository.RegisterRepository

/**
 * @author LRQ-Pro
 * @description:
 * @date : 2020/6/16 1:04
 */
class RegisterModel : ViewModel() {

    private val userRegisterBean = MutableLiveData<UserRegisterBean>()

    val userRegisterLiveData = Transformations.switchMap(userRegisterBean) { userRegisterBean ->
        RegisterRepository.userRegister(userRegisterBean)
    }


    fun setUserRegisterBean(userRegisterBean: UserRegisterBean) {
        this.userRegisterBean.value = userRegisterBean
    }

}