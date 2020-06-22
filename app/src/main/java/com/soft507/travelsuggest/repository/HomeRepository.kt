package com.soft507.travelsuggest.repository

import androidx.lifecycle.liveData
import com.soft507.travelsuggest.bean.UserInfoBean
import com.soft507.travelsuggest.http.Api
import com.soft507.travelsuggest.http.TravelNetwork
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

/**
 * @author LRQ-Pro
 * @description:
 * @date : 2020/6/8 9:16
 */
object HomeRepository {

    fun getUserInfo(username: String) =
        liveData(Dispatchers.IO) {
            try {
                val response = TravelNetwork.getUserInfo(username)
                emit(response.data)
            } catch (e: Exception) {
                emit(Api.SERVERERROR)
            }
        }
}