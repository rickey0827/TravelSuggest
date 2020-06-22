package com.soft507.travelsuggest.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.soft507.travelsuggest.bean.UserBean

/**
 * @author LRQ-Pro
 * @description:
 * @date : 2020/6/6 2:46
 */
class PersonalCenterViewModel : ViewModel() {

    val userBean = MutableLiveData<UserBean>()

    fun getUserInfo() {

    }
}