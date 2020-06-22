package com.soft507.travelsuggest.bean

/**
 * @author LRQ-Pro
 * @description:
 * @date : 2020/6/9 5:12
 */
 data class WorksInfoBean(
    val code: Int,
    val `data`: Data,
    val msg: String
)

{
    data class Data(
        val comments: List<Comment>,
        val works_info: List<WorksInfo>
    )

    data class Comment(
        val sc_content: String,
        val sc_likecount: String,
        val sc_time: String,
        val sc_url: String,
        val sc_user: String,
        val sc_videoname: String
    )

    data class WorksInfo(
        val sc_author: String,
        val sc_content: String,
        val sc_url: String,
        val sc_videocount: String,
        val sc_videoname: String,
        val sc_videoresponse: String,
        val sc_videotalkcount: String
    )
}