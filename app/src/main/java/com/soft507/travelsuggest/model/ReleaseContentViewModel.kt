package com.soft507.travelsuggest.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.soft507.travelsuggest.bean.ToPicBean
import com.soft507.travelsuggest.repository.ReleaseContentRepository
import okhttp3.MultipartBody

/**
 * @author LRQ-Pro
 * @description:
 * @date : 2020/6/14 15:05
 */
class ReleaseContentViewModel : ViewModel() {


    private val partListBean = MutableLiveData<List<MultipartBody.Part>>()

    val picLiveData = Transformations.switchMap(partListBean) { partListBean ->
        ReleaseContentRepository.releasePic(partListBean)
    }

    fun setPicBean(partList: List<MultipartBody.Part>) {
        this.partListBean.value = partList
    }

}