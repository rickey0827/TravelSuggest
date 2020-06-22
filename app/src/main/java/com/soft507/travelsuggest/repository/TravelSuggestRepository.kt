package com.soft507.travelsuggest.repository

import androidx.lifecycle.liveData
import com.soft507.travelsuggest.bean.DetectPramsBean
import com.soft507.travelsuggest.http.TravelNetwork
import kotlinx.coroutines.Dispatchers
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody


/**
 * @author LRQ-Pro
 * @description:
 * @date : 2020/6/16 4:32
 */
object TravelSuggestRepository {

    fun skinStatus(travelBean: List<MultipartBody.Part>) = liveData(Dispatchers.IO) {

        try {
            val response =
                TravelNetwork.skinStatus(travelBean)
            emit(response.faces[0].attributes.skinstatus)
        } catch (e: Exception) {
            emit(e.toString())
        }
    }
}