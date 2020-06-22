package com.soft507.travelsuggest.bean

/**
 * @author LRQ-Pro
 * @description:
 * @date : 2020/6/9 21:49
 */
 data class AllTopicBean(
    val code: Int,
    val `data`: List<Data>,
    val msg: String
)
{

    data class Data(
        val sc_author: String,
        val sc_content: String,
        val sc_id: String,
        val sc_image_url: String,
        val sc_sharecount: String,
        val sc_url: String,
        val sc_userLikecount: String,
        val sc_userdisLikecount: String,
        val sc_usermark: String,
        val sc_usertalkcount: String
    )
}