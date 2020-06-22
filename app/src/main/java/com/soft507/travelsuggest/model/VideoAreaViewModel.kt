package com.soft507.travelsuggest.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.soft507.travelsuggest.bean.UserCommentBean
import com.soft507.travelsuggest.repository.VideoAreaRepository

/**
 * @author LRQ-Pro
 * @description:
 * @date : 2020/6/9 1:51
 */

class VideoAreaViewModel : ViewModel() {

    private val noData = MutableLiveData<String>()

    private val videoName = MutableLiveData<String>()

    private val userComment = MutableLiveData<UserCommentBean>()

    val allVideosLiveData = Transformations.switchMap(noData) { noData ->
        VideoAreaRepository.getAllVideos(noData)
    }
    val worksVideosLiveData = Transformations.switchMap(videoName) { videoName ->
        VideoAreaRepository.getWorksInfo(videoName)
    }

    val commentsLiveData = Transformations.switchMap(userComment) { userComment ->
        VideoAreaRepository.getCommentStatus(userComment) as LiveData<Any>?
    }

    fun setNoData(noData: String) {
        this.noData.value = noData
    }

    fun setVideoName(videoName: String) {
        this.videoName.value = videoName
    }

    fun setUserComment(userCommentBean: UserCommentBean) {
        this.userComment.value = userCommentBean
    }

}