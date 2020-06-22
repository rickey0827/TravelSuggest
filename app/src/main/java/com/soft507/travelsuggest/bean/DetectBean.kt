package com.soft507.travelsuggest.bean

/**
 * @author LRQ-Pro
 * @description:
 * @date : 2020/6/16 4:29
 */
data class DetectBean(
    val face_num: Int,
    val faces: List<Face>,
    val image_id: String,
    val request_id: String,
    val time_used: Int
)
{

    data class Face(
        val attributes: Attributes,
        val face_rectangle: FaceRectangle,
        val face_token: String
    )

    data class Attributes(
        val skinstatus: Skinstatus
    )

    data class FaceRectangle(
        val height: Int,
        val left: Int,
        val top: Int,
        val width: Int
    )

    data class Skinstatus(
        val acne: Double,
        val dark_circle: Double,
        val health: Double,
        val stain: Double
    )
}