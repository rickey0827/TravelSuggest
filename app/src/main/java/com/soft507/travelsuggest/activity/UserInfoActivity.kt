package com.soft507.travelsuggest.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.lzy.imagepicker.ImagePicker
import com.lzy.imagepicker.bean.ImageItem
import com.lzy.imagepicker.ui.ImageGridActivity
import com.lzy.imagepicker.view.CropImageView
import com.orhanobut.logger.Logger
import com.soft507.travelsuggest.R
import com.soft507.travelsuggest.adapter.UserListRecyclerViewAdapter
import com.soft507.travelsuggest.base.BaseActivity
import com.soft507.travelsuggest.bean.UserListBean
import com.soft507.travelsuggest.databinding.ActivityUserInfoBinding
import com.soft507.travelsuggest.http.Api
import com.soft507.travelsuggest.util.CommonUtil
import com.soft507.travelsuggest.util.GlideImageLoader


class UserInfoActivity : BaseActivity<ActivityUserInfoBinding>() {


    private var arrayList = arrayListOf<UserListBean>()
    private lateinit var userLisAdapter: UserListRecyclerViewAdapter
    private val imagePicker by lazy { ImagePicker.getInstance() }

    override fun initWork(savedInstanceState: Bundle?) {

        setSupportActionBar(mDataBinding.userInfoToolbar)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        mDataBinding.userInfoToolbar.navigationIcon =
            resources.getDrawable(R.mipmap.ic_left_small_arrow)
        mDataBinding.userInfoToolbar.setNavigationOnClickListener {
            finish()
        }


        imagePicker.imageLoader = GlideImageLoader() //设置图片加载器
        imagePicker.isShowCamera = true //显示拍照按钮
        imagePicker.isCrop = true //允许裁剪（单选才有效）
        imagePicker.isSaveRectangle = true //是否按矩形区域保存
        imagePicker.selectLimit = 1 //选中数量限制
        imagePicker.style = CropImageView.Style.RECTANGLE //裁剪框的形状
        imagePicker.focusWidth = 800 //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.focusHeight = 800 //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.outPutX = 1000 //保存文件的宽度。单位像素
        imagePicker.outPutY = 1000 //保存文件的高度。单位像素


        arrayList.add(UserListBean("头像", 0, Api.IMAGE_BASE_URL + kv.decodeString("_url"), null))
        arrayList.add(UserListBean("名字", 1, null, kv.decodeString("_username")))
        arrayList.add(UserListBean("识别码", 1, null, kv.decodeString("_userkey")))
        arrayList.add(UserListBean("我的二维码", 1, "mark", null))
        arrayList.add(UserListBean("更多", 1, "display", null))

        userLisAdapter = UserListRecyclerViewAdapter(arrayList, mActivity)
        mDataBinding.userInfoRecycleView.layoutManager = LinearLayoutManager(this)
        mDataBinding.userInfoRecycleView.itemAnimator = DefaultItemAnimator()
        mDataBinding.userInfoRecycleView.adapter = userLisAdapter
        mDataBinding.userInfoRecycleView.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )
        userLisAdapter.setOnItemClickListener { adapter, view, position ->
            when (position) {
                0 -> {
                    val intent = Intent(this, ImageGridActivity::class.java)
                    startActivityForResult(intent, 0)
                }
                3 -> {
                    CommonUtil.JumpToActivity(
                        this,
                        mActivity,
                        QrCodeActivity::class.java
                    )
                }
                4 -> {
                    CommonUtil.JumpToActivity(this, mActivity, MoreActivity::class.java)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == 0) {
                val result =
                    data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS) as (ArrayList<ImageItem>)
                arrayList[0].url = result[0].path
                userLisAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show()
            }
        }
    }


    override fun getLayoutRes() = R.layout.activity_user_info


}
