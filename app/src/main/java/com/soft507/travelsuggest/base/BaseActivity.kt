package com.soft507.travelsuggest.base

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.snackbar.Snackbar
import com.kongzue.dialog.v3.TipDialog
import com.kongzue.dialog.v3.WaitDialog
import com.soft507.travelsuggest.util.ActivityManager
import com.tencent.mmkv.MMKV


open abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity() {

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

    fun shortToast(recourse: Int) {
        Toast.makeText(this, recourse, Toast.LENGTH_SHORT).show()
    }


    fun loadDialog(recourse: Int) {
        WaitDialog.show(this, resources.getString(recourse))
    }

    fun dissDialog() {
        WaitDialog.dismiss()
    }

    fun comDialog(recourse: Int, type: TipDialog.TYPE) {
        TipDialog.show(this, resources.getString(recourse), type)
    }

    abstract fun initWork(savedInstanceState: Bundle?)

    abstract fun getLayoutRes(): Int

    override fun onDestroy() {
        super.onDestroy()
        activityManager.removeActivity(this)
    }
}
