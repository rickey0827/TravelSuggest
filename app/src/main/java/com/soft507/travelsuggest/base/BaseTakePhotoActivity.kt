package com.soft507.travelsuggest.base

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.snackbar.Snackbar
import com.jph.takephoto.app.TakePhotoActivity
import com.jph.takephoto.app.TakePhotoFragmentActivity
import com.kongzue.dialog.v3.TipDialog
import com.kongzue.dialog.v3.WaitDialog
import com.soft507.travelsuggest.util.ActivityManager
import com.tencent.mmkv.MMKV

/**
 * @author LRQ-Pro
 * @description:
 * @date : 2020/6/9 22:43
 */
open abstract class BaseTakePhotoActivity<T : ViewDataBinding>:TakePhotoFragmentActivity()  {

    private val activityManager: ActivityManager by lazy { ActivityManager.getInstance() }
    protected lateinit var mActivity: Activity
    protected lateinit var mDataBinding: T
    protected var kv = MMKV.defaultMMKV()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity = this
        mDataBinding = DataBindingUtil.setContentView(mActivity, getLayoutRes())
        activityManager.addActivity(this)
        initWork(savedInstanceState)
    }


    fun shortSnackbar(view: View?, recourse: Int) {
        Snackbar.make(
            view!!,
            resources.getString(recourse),
            Snackbar.LENGTH_SHORT
        ).show()
    }

    fun loadDialog(recourse: Int) {
        WaitDialog.show(this as AppCompatActivity, resources.getString(recourse))
    }

    fun dissDialog() {
        WaitDialog.dismiss()
    }

    fun comDialog(recourse: Int, state: Int) {
        TipDialog.show(this as AppCompatActivity, resources.getString(recourse), state)
    }

    abstract fun initWork(savedInstanceState: Bundle?)

    abstract fun getLayoutRes(): Int

    override fun onDestroy() {
        super.onDestroy()
        activityManager.removeActivity(this)
    }
}