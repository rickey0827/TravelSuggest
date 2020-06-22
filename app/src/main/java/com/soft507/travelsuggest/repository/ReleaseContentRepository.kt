package com.soft507.travelsuggest.repository

import android.net.Uri
import androidx.lifecycle.liveData
import com.luck.picture.lib.config.PictureMimeType
import com.orhanobut.logger.Logger
import com.soft507.travelsuggest.bean.ToPicBean
import com.soft507.travelsuggest.http.Api
import com.soft507.travelsuggest.http.TravelNetwork
import kotlinx.coroutines.Dispatchers
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


/**
 * @author LRQ-Pro
 * @description:
 * @date : 2020/6/14 15:03
 */
object ReleaseContentRepository {

    fun releasePic(partListBean: List<MultipartBody.Part>) = liveData(Dispatchers.IO) {

        try {
            val response =
                TravelNetwork.releaseTopic(partListBean)
            emit(response.code)
        }
        catch (e: Exception) {
            emit(Api.SERVERERROR)
        }
    }
}