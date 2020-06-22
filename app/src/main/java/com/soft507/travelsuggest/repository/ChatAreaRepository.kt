package com.soft507.travelsuggest.repository

import androidx.lifecycle.liveData
import com.soft507.travelsuggest.http.Api
import com.soft507.travelsuggest.http.TravelNetwork
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

/**
 * @author LRQ-Pro
 * @description:
 * @date : 2020/6/9 21:52
 */
object ChatAreaRepository {

    fun getAllTopic(key: String) =
        liveData(Dispatchers.IO) {
            try {
                val response = TravelNetwork.getAllTopic(key)
                emit(response.data)
            } catch (e: Exception) {
                emit(Api.SERVERERROR)
            }
        }

}