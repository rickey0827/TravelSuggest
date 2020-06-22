package com.soft507.travelsuggest.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.os.Bundle


/**
 * @author LRQ-SoftWare
 * @description:
 * @date : 2020/6/1 11:01
 */
object CommonUtil {

    private const val BundleName = "data"

    /**
     * @param fromActivity
     * @param toActivity
     */
    fun JumpToActivity(context: Context, fromActivity: Activity, toActivity: Class<*>) {
        this.JumpToActivity(context, fromActivity, toActivity, null)
    }

    /**
     * @param fromActivity
     * @param toActivity
     * @param bundle
     */
    fun JumpToActivity(
        context: Context,
        fromActivity: Activity,
        toActivity: Class<*>,
        bundle: Bundle?
    ) {
        val intent = Intent()
        intent.setClass(fromActivity, toActivity)
        intent.putExtra(BundleName, bundle)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent)
    }


    /**
     * @return 手机型号
     * @return 手机版本号
     */
    fun getPhoneInfo(): Array<String> {
        return arrayOf(
            android.os.Build.MODEL,
            android.os.Build.VERSION.RELEASE
        )
    }

    /**
     * @return app当前版本
     */
    fun getVersion(context: Context): String {
        val packageManager = context.packageManager
        return "v" + packageManager.getPackageInfo(context.packageName, 0).versionName
    }


    fun getNetVideoBitmap(videoUrl: String): Bitmap {
        var bitmap: Bitmap? = null
        val retriever = MediaMetadataRetriever()
        try {
            retriever.setDataSource(videoUrl, HashMap())
            bitmap = retriever.getFrameAtTime()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            retriever.release()
        }
        return bitmap!!
    }



}