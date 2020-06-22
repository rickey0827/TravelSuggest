package com.soft507.travelsuggest.http

import com.soft507.travelsuggest.bean.UserRegisterBean
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.await
import java.io.File

/**
 * @author LRQ-Pro
 * @description:
 * @date : 2020/6/7 16:25
 */
object TravelNetwork {

    private val faceService =
        ServiceCreator(Api.FACE_BASE_URL).create(FaceApiService::class.java)
    private val dataService =
        ServiceCreator(Api.DATA_BASE_URL).create(DataApiService::class.java)
    private val bookService =
        ServiceCreator(Api.BOOK_BASE_URL).create(BookApiService::class.java)

    suspend fun userLogin(username: String?, password: String?) =
        dataService.userLogin(username, password).await()

    suspend fun emailLogin(email: String?, password: String?) =
        dataService.emailLogin(email, password).await()

    suspend fun getUserInfo(username: String?) =
        dataService.getUserInfo(username).await()

    suspend fun getAllVideos(noData: String) =
        dataService.getAllVideos(noData).await()

    suspend fun getWorksInfo(videoName: String) =
        dataService.getWorksInfo(videoName).await()

    suspend fun getCommentStatus(
        sc_user: String,
        sc_videoname: String,
        sc_time: String,
        sc_content: String,
        sc_url: String
    ) = dataService.comments(sc_user, sc_videoname, sc_time, sc_content, sc_url).await()

    suspend fun getAllTopic(key: String) =
        dataService.allToPic(key).await()

    suspend fun releaseTopic(partList: List<MultipartBody.Part>) =
        dataService.upLoadPic(partList).await()

    suspend fun userRegister(username: String, password: String, email: String, phone: String) =
        dataService.userRegister(username, password, phone, email).await()

    suspend fun skinStatus(
        partList: List<MultipartBody.Part>
    ) =
        faceService.skinStatus(partList).await()

}