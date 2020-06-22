package com.soft507.travelsuggest.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.tencent.mmkv.MMKV

/**
 * @author LRQ-Pro
 * @description:用心写好每一行代码
 * @date : 2020/6/1 15:09
 */
open abstract class BaseFragment<T : ViewDataBinding> : Fragment() {

    protected lateinit var mDataBinding: T
    protected var mContext: Context? = null
    protected var kv = MMKV.defaultMMKV()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mDataBinding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false)
        initWork(savedInstanceState)
        return mDataBinding.root
    }

    abstract fun initWork(savedInstanceState: Bundle?)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mContext = context
    }

    fun shortToast(context: Context, msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    fun longToast(context: Context, msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }

    abstract fun getLayoutRes(): Int
}