package com.soft507.travelsuggest.util

import java.text.SimpleDateFormat
import java.util.*

/**
 * @author LRQ-Pro
 * @description:
 * @date : 2020/6/9 15:16
 */
object TimeUtil {
    private const val minute = 60 * 1000 // 1分钟
        .toLong()
    private const val hour = 60 * minute // 1小时
    private const val day = 24 * hour // 1天
    private const val month = 31 * day // 月
    private const val year = 12 * month // 年

    private var sdf :SimpleDateFormat?=null

    /**
     * 返回文字描述的日期
     *
     * @param date
     * @return
     */
    fun getTimeFormatText(date: Date?): String? {
        if (date == null) {
            return null
        }
        val diff = System.currentTimeMillis() - date.time
        var r: Long = 0
        if (diff > year) {
            r = diff / year
            return r.toString() + "年前"
        }
        if (diff > month) {
            r = diff / month
            return r.toString() + "个月前"
        }
        if (diff > day) {
            r = diff / day
            return r.toString() + "天前"
        }
        if (diff > hour) {
            r = diff / hour
            return r.toString() + "个小时前"
        }
        if (diff > minute) {
            r = diff / minute
            return r.toString() + "分钟前"
        }
        return "刚刚"
    }

//    fun DateConvertInt() {
//         sdf= SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//
//    }
}