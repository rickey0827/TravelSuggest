package com.soft507.travelsuggest.repository

import androidx.lifecycle.liveData
import com.soft507.travelsuggest.bean.UserRegisterBean
import com.soft507.travelsuggest.http.Api
import com.soft507.travelsuggest.http.TravelNetwork
import kotlinx.coroutines.Dispatchers

/**
 * @author LRQ-Pro
 * @description:
 * @date : 2020/6/16 1:04
 */
object RegisterRepository {

    fun userRegister(userRegisterBean: UserRegisterBean) =
        liveData(Dispatchers.IO) {
            try {
                val username = userRegisterBean.username
                val password = userRegisterBean.password
                val phone = userRegisterBean.phone
                val email = userRegisterBean.email

                val response = TravelNetwork.userRegister(username, password, email, phone)
                emit(response.code)
            } catch (e: Exception) {
                emit(Api.SERVERERROR)
            }
        }

}