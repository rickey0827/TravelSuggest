package com.soft507.travelsuggest.bean

import android.net.Uri
import java.io.File

/**
 * @author LRQ-Pro
 * @description:
 * @date : 2020/6/16 5:07
 */
data class DetectPramsBean(
    val api_key: String,
    val api_secret: String,
    val return_attributes: String,
    val path: File
)