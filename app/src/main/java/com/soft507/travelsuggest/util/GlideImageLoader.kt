package com.soft507.travelsuggest.util

import android.app.Activity
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.lzy.imagepicker.loader.ImageLoader

/**
 * @author LRQ-Pro
 * @description:
 * @date : 2020/6/9 23:42
 */
class GlideImageLoader : ImageLoader {

    override fun clearMemoryCache() {
    }

    override fun displayImagePreview(
        activity: Activity?,
        path: String?,
        imageView: ImageView?,
        width: Int,
        height: Int
    ) {
        Glide.with(activity!!).load(path).into(imageView!!)
    }

    override fun displayImage(
        activity: Activity?,
        path: String?,
        imageView: ImageView?,
        width: Int,
        height: Int
    ) {
    }
}