package com.soft507.travelsuggest.repository

import androidx.lifecycle.liveData
import com.soft507.travelsuggest.bean.UserCommentBean
import com.soft507.travelsuggest.http.Api
import com.soft507.travelsuggest.http.TravelNetwork
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

/**
 * @author LRQ-Pro
 * @description:
 * @date : 2020/6/9 1:51
 */
object VideoAreaRepository {

    fun getAllVideos(noData: String) =
        liveData(Dispatchers.IO) {
            try {
                val response = TravelNetwork.getAllVideos(noData)
                emit(response.data)
            } catch (e: Exception) {
                emit(Api.SERVERERROR)
            }
        }

    fun getWorksInfo(videoName: String) =
        liveData(Dispatchers.IO) {
            try {
                val response = TravelNetwork.getWorksInfo(videoName)
                emit(response.data)
            } catch (e: Exception) {
                emit(Api.SERVERERROR)
            }
        }

    fun getCommentStatus(userCommentBean: UserCommentBean) = liveData(Dispatchers.IO) {
        try {
            val scUser = userCommentBean.userName
            val scVideoName = userCommentBean.videoName
            val scTime = userCommentBean.time
            val scContent = userCommentBean.content
            val scUrl = userCommentBean.url

            val response =
                TravelNetwork.getCommentStatus(scUser, scVideoName, scTime, scContent, scUrl)
            emit(response.data)
        } catch (e: Exception) {
            emit(Api.SERVERERROR)
        }
    }

}