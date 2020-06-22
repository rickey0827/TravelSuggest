package com.soft507.travelsuggest

import android.R
import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.emoji.bundled.BundledEmojiCompatConfig
import androidx.emoji.text.EmojiCompat
import com.kongzue.dialog.util.DialogSettings
import com.kongzue.dialog.util.DialogSettings.STYLE
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.FormatStrategy
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.tencent.mmkv.MMKV


/**
 * @author LRQ-Pro
 * @description:
 * @date : 2020/5/25 20:17
 */
class TravelApp : Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
        private lateinit var formatStrategy: FormatStrategy

        init {
            //设置全局的Header构建器
            SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
                layout.setPrimaryColorsId(
                    R.color.holo_blue_dark,
                    R.color.white
                ) //全局设置主题颜色
                ClassicsHeader(context) //.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
            //设置全局的Footer构建器
            SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout -> //指定为经典Footer，默认是 BallPulseFooter
                ClassicsFooter(context).setDrawableSize(20f)
            }
        }

    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        formatStrategy = PrettyFormatStrategy.newBuilder().tag("travel_test").build()
        Logger.addLogAdapter(object : AndroidLogAdapter(formatStrategy) {
            override fun isLoggable(priority: Int, tag: String?) = BuildConfig.DEBUG
        })
        DialogSettings.isUseBlur = true                 //设置是否启用模糊
        DialogSettings.modalDialog =
            (false);                 //是否开启模态窗口模式，一次显示多个对话框将以队列形式一个一个显示，默认关闭
        DialogSettings.style =
            STYLE.STYLE_IOS;          //全局主题风格，提供三种可选风格，STYLE_MATERIAL, STYLE_KONGZUE, STYLE_IOS

        //检查 Renderscript 兼容性，若设备不支持，DialogSettings.isUseBlur 会自动关闭；
        val renderscriptSupport = DialogSettings.checkRenderscriptSupport(context)

        DialogSettings.init()

        MMKV.initialize(this);

        //EmojiCompat init
        val config = BundledEmojiCompatConfig(this)
            .setReplaceAll(true)

        EmojiCompat.init(config)

    }

}