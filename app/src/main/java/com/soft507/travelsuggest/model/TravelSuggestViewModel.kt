package com.soft507.travelsuggest.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.soft507.travelsuggest.bean.DetectBean
import com.soft507.travelsuggest.bean.DetectPramsBean
import com.soft507.travelsuggest.repository.TravelSuggestRepository
import okhttp3.MultipartBody

/**
 * @author LRQ-Pro
 * @description:
 * @date : 2020/6/16 4:32
 */
class TravelSuggestViewModel : ViewModel() {

    private val travelBean = MutableLiveData<List<MultipartBody.Part>>()

    val travelLiveData = Transformations.switchMap(travelBean) { travelBean ->
        TravelSuggestRepository.skinStatus(travelBean)
    }

    fun setTravelBean(travelBean: List<MultipartBody.Part>) {
        this.travelBean.value = travelBean
    }

}