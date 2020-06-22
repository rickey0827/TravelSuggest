package com.soft507.travelsuggest.bean

/**
 * @author LRQ-Pro
 * @description:
 * @date : 2020/6/9 0:07
 */
data class AllVideosBean(
    val code: Int,
    val `data`: List<Data>,
    val msg: String
) {
    data class Data(
        val sc_author: String,
        val sc_content: String,
        val sc_url: String,
        val sc_videocount: String,
        val sc_videoname: String,
        val sc_videoresponse: String,
        val sc_videotalkcount: String
    )
}