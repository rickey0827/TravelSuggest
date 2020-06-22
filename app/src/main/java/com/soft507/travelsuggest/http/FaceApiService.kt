package com.soft507.travelsuggest.http

import com.soft507.travelsuggest.bean.DetectBean
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

/**
 * @author LRQ-Pro
 * @description:
 * @date : 2020/6/16 4:26
 */
interface FaceApiService {

    @Multipart
    @POST("facepp/v3/detect")
    fun skinStatus(
        @Part
        partList: List<MultipartBody.Part>
    ): Call<DetectBean>

}