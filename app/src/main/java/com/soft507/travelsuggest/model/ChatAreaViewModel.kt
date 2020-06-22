package com.soft507.travelsuggest.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.soft507.travelsuggest.repository.ChatAreaRepository

/**
 * @author LRQ-Pro
 * @description:
 * @date : 2020/6/9 21:53
 */
class ChatAreaViewModel : ViewModel() {

    private val key = MutableLiveData<String>()

    val chatLiveData = Transformations.switchMap(key) { key ->
        ChatAreaRepository.getAllTopic(key)
    }


    fun setKey(key: String) {
        this.key.value = key
    }
}