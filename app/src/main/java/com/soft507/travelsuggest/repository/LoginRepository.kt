package com.soft507.travelsuggest.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.soft507.travelsuggest.bean.LoginBean
import com.soft507.travelsuggest.http.Api
import com.soft507.travelsuggest.http.TravelNetwork
import kotlinx.coroutines.Dispatchers
import java.lang.Exception
import java.lang.RuntimeException

/**
 * @author LRQ-Pro
 * @description:
 * @date : 2020/6/7 16:17
 */
object LoginRepository {

    fun userLogin(username: String?, password: String?) =
        liveData(Dispatchers.IO) {
            try {
                val response = TravelNetwork.userLogin(username, password)
                emit(response.code)
            } catch (e: Exception) {
                emit(Api.SERVERERROR)
            }
        }

    fun emailLogin(email: String?, password: String?) =
        liveData(Dispatchers.IO) {
            try {
                val response = TravelNetwork.emailLogin(email, password)
                emit(response.code)
            } catch (e: Exception) {
                emit(Api.SERVERERROR)
            }
        }
}