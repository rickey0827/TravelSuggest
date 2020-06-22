package com.soft507.travelsuggest.bean

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @author LRQ-Pro
 * @description:
 * @date : 2020/6/8 1:13
 */
data class UserInfoBean(
    val code: Int,
    val `data`: List<Data>,
    val msg: String
)

@SuppressLint("ParcelCreator")
@Parcelize
data class Data(
    val _city: String,
    val _email: String,
    val _id: String,
    val _password: String,
    val _phone: String,
    val _sex: String,
    val _speak: String,
    val _userkey: String,
    val _username: String,
    val _url: String
) : Parcelable
