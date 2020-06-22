package com.soft507.travelsuggest.http

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
 * @author LRQ-Pro
 * @description:
 * @date : 2020/6/7 16:27
 */
class ServiceCreator public constructor(url: String?) {


    private val retrofit = Retrofit.Builder()
        .baseUrl(url!!)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> create(service: Class<T>): T = retrofit.create(service)

    inline fun <reified T> create(): T = create(T::class.java)

//    companion object {
//
//        private var instance: ServiceCreator? = null
//
//        fun getInstance(url: String): ServiceCreator {
//            if (instance == null) {
//                    if (instance == null) {
//                        instance = ServiceCreator(url)
//                    }
//            }
//            return instance!!
//        }
//    }

}