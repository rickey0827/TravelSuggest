package com.soft507.travelsuggest.activity

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.widget.PopupWindow
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.orhanobut.logger.Logger
import com.soft507.travelsuggest.R
import com.soft507.travelsuggest.adapter.GridImageAdapter
import com.soft507.travelsuggest.base.BaseActivity
import com.soft507.travelsuggest.databinding.ActivityReleaseContentBinding
import com.soft507.travelsuggest.http.Api
import com.soft507.travelsuggest.model.ReleaseContentViewModel
import com.soft507.travelsuggest.util.FullyGridLayoutManager
import com.soft507.travelsuggest.util.GlideEngine
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


class ReleaseContentActivity : BaseActivity<ActivityReleaseContentBinding>() {


    private val maxSelectNum = 1
    private var selectList = arrayListOf<LocalMedia>()
    private var adapter: GridImageAdapter? = null
    private var pop: PopupWindow? = null

    private val viewModel: ReleaseContentViewModel by lazy {
        ViewModelProviders.of(this).get(ReleaseContentViewModel::class.java)
    }


    override fun initWork(savedInstanceState: Bundle?) {
        initWidget()
        mDataBinding.releaseFinal.setOnClickListener { finish() }
        mDataBinding.releaseBtn.setOnClickListener { v ->

            loadDialog(R.string.release_loading)

            val content = mDataBinding.releaseInputContent.text.toString().trim()

            if (content.isEmpty()) {
                shortSnackbar(v, R.string.release_no_null)
                return@setOnClickListener
            }

            val author = kv.decodeString("_username")
            val url = kv.decodeString("_url")
            val userMark = mDataBinding.releaseAddTag.text.toString()

            val imageUrl: String? = if (selectList.isEmpty()) {
                null
            } else {
                selectList[0].path
            }


            val builder = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                //在这里添加服务器除了文件之外的其他参数
                .addFormDataPart("author", author)
                .addFormDataPart("content", content)
                .addFormDataPart("url", url)
                .addFormDataPart("userMark", "#$userMark")

            val file = File(imageUrl)

            val imageBody: RequestBody =
                RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)

            //添加文件(uploadfile就是你服务器中需要的文件参数)
            builder.addFormDataPart("file", file.name, imageBody)

            val parts = builder.build().parts
            viewModel.setPicBean(parts)

            viewModel.picLiveData.observe(this, Observer { picLiveData ->

                if (Api.SUCCESS == picLiveData) {
                    dissDialog()
                    shortSnackbar(v, R.string.release_success)
                    finish()
                } else {
                    dissDialog()
                    shortSnackbar(v, R.string.release_failed)
                }
            })
        }
    }


    private fun initWidget() {
        val manager =
            FullyGridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false)
        mDataBinding.recycler.layoutManager = manager
        adapter = GridImageAdapter(this, onAddPicClickListener)
        adapter!!.setList(selectList)
        adapter!!.setSelectMax(maxSelectNum)
        mDataBinding.recycler.adapter = adapter
        adapter!!.setOnItemClickListener { v, position ->
            val selectList: List<LocalMedia> = adapter!!.data
            if (selectList.size > 0) {
                val media = selectList[position]
                val mimeType = media.mimeType
                when (PictureMimeType.getMimeType(mimeType)) {
                    PictureConfig.TYPE_VIDEO ->                         // 预览视频
                        PictureSelector.create(this@ReleaseContentActivity)
                            .themeStyle(R.style.picture_default_style)
                            .externalPictureVideo(if (TextUtils.isEmpty(media.androidQToPath)) media.path else media.androidQToPath)
                    PictureConfig.TYPE_AUDIO ->                         // 预览音频
                        PictureSelector.create(this@ReleaseContentActivity)
                            .externalPictureAudio(if (PictureMimeType.isContent(media.path)) media.androidQToPath else media.path)

                }
            }
        }

    }


    private val onAddPicClickListener: GridImageAdapter.onAddPicClickListener =
        GridImageAdapter.onAddPicClickListener {
            showPop()
        }


    private fun showPop() {
        val bottomView =
            View.inflate(this@ReleaseContentActivity, R.layout.layout_bottom_dialog, null)
        val mAlbum = bottomView.findViewById<TextView>(R.id.tv_album)
        val mCamera = bottomView.findViewById<TextView>(R.id.tv_camera)
        val mCancel = bottomView.findViewById<TextView>(R.id.tv_cancel)
        pop = PopupWindow(bottomView, -1, -2)
        pop!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        pop!!.setOutsideTouchable(true)
        pop!!.setFocusable(true)
        val lp = window.attributes
        lp.alpha = 0.5f
        window.attributes = lp
        pop!!.setOnDismissListener {
            val lp = window.attributes
            lp.alpha = 1f
            window.attributes = lp
        }
        pop!!.setAnimationStyle(R.style.main_menu_photo_anim)
        pop!!.showAtLocation(window.decorView, Gravity.BOTTOM, 0, 0)
        val clickListener =
            View.OnClickListener { view ->
                when (view.id) {
                    R.id.tv_album ->                         //相册
                    {
                        PictureSelector.create(this)
                            .openGallery(PictureMimeType.ofImage())
                            .loadImageEngine(GlideEngine.createGlideEngine()) // 请参考Demo GlideEngine.java
                            .forResult(PictureConfig.CHOOSE_REQUEST);
                    }
                    R.id.tv_camera ->                         //拍照
                        PictureSelector.create(this@ReleaseContentActivity)
                            .openCamera(PictureMimeType.ofImage())
                            .forResult(PictureConfig.CHOOSE_REQUEST)
                    R.id.tv_cancel -> {
                    }
                }
                closePopupWindow()
            }
        mAlbum.setOnClickListener(clickListener)
        mCamera.setOnClickListener(clickListener)
        mCancel.setOnClickListener(clickListener)
    }


    fun closePopupWindow() {
        if (pop != null && pop!!.isShowing) {
            pop!!.dismiss()
            pop = null
        }
    }


    override fun getLayoutRes() = R.layout.activity_release_content

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        val images: List<LocalMedia>
        if (resultCode == RESULT_OK) {
            if (requestCode == PictureConfig.CHOOSE_REQUEST) { // 图片选择结果回调
                images = PictureSelector.obtainMultipleResult(data)
                selectList.addAll(images)


                //selectList = PictureSelector.obtainMultipleResult(data);

                // 例如 LocalMedia 里面返回三种path
                // 1.media.getPath(); 为原图path
                // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                adapter!!.setList(selectList)
                adapter!!.notifyDataSetChanged()
            }
        }
    }


}
