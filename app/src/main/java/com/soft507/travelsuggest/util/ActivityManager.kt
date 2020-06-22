package com.soft507.travelsuggest.util

import android.app.Activity

/**
 * @author LRQ-Pro
 * @description: Activity管理类
 * @date : 2019/11/17 13:52
 */

class ActivityManager {

    private val activities = ArrayList<Activity>()

    companion object {
        private var INSTANCE: ActivityManager? = null
        @Synchronized
        fun getInstance(): ActivityManager {
            if (INSTANCE == null) {
                INSTANCE = ActivityManager()
            }
            return INSTANCE!!
        }
    }

    fun addActivity(activity: Activity) {
        activities.add(activity)
    }

    fun removeActivity(activity: Activity) {
        activities.remove(activity)
    }

    fun finishAll() {
        for (activity in activities) {
            if (!activity.isFinishing) {
                activity.finish()
            }
        }
    }

    fun exitApp() {
        finishAll()
        android.os.Process.killProcess(android.os.Process.myPid())
    }
}
