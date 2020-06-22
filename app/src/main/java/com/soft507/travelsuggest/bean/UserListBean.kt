package com.soft507.travelsuggest.bean

import com.chad.library.adapter.base.entity.MultiItemEntity

/**
 * @author LRQ-Pro
 * @description:
 * @date : 2020/6/8 12:22
 */
data class UserListBean(val name: String, val type: Int = 0, var url: String?, val text: String?) :
    MultiItemEntity {


    override val itemType: Int
        get() = type
}