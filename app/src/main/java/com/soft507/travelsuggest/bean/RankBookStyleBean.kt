package com.soft507.travelsuggest.bean

/**
 * @author LRQ-Pro
 * @description:
 * @date : 2020/6/1 21:02
 */
data class RankBookStyleBean(
    val epub: List<Epub>,
    val female: List<Female>,
    val male: List<Male>,
    val ok: Boolean,
    val picture: List<Picture>
)

{
    data class Epub(
        val _id: String,
        val collapse: Boolean,
        val cover: String,
        val shortTitle: String,
        val title: String
    )

    data class Female(
        val _id: String,
        val collapse: Boolean,
        val cover: String,
        val monthRank: String,
        val shortTitle: String,
        val title: String,
        val totalRank: String
    )

    data class Male(
        val _id: String,
        val collapse: Boolean,
        val cover: String,
        val monthRank: String,
        val shortTitle: String,
        val title: String,
        val totalRank: String
    )

    data class Picture(
        val _id: String,
        val collapse: Boolean,
        val cover: String,
        val shortTitle: String,
        val title: String
    )
}