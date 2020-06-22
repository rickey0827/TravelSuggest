package com.soft507.travelsuggest.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.soft507.travelsuggest.bean.UserInfoBean
import com.soft507.travelsuggest.repository.HomeRepository

/**
 * @author LRQ-Pro
 * @description:
 * @date : 2020/6/1 21:20
 */
class HomeViewModel : ViewModel() {

    private val username = MutableLiveData<String>()

    val userLiveData = Transformations.switchMap(username) { username ->
        HomeRepository.getUserInfo(username)
    }

    fun setUserInfo(username: String) {
        this.username.value = username
    }
}