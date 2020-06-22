package com.soft507.travelsuggest.fragment


import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.amap.api.maps.AMap
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.UiSettings
import com.amap.api.maps.model.*
import com.amap.api.services.core.AMapException
import com.amap.api.services.core.LatLonPoint
import com.amap.api.services.core.PoiItem
import com.amap.api.services.core.SuggestionCity
import com.amap.api.services.poisearch.PoiResult
import com.amap.api.services.poisearch.PoiSearch
import com.amap.api.services.route.DistanceItem
import com.amap.api.services.route.DistanceResult
import com.amap.api.services.route.DistanceSearch
import com.amap.api.services.route.DistanceSearch.DistanceQuery
import com.kongzue.dialog.interfaces.OnDialogButtonClickListener
import com.kongzue.dialog.v3.InputDialog
import com.kongzue.dialog.v3.MessageDialog
import com.nanchen.compresshelper.CompressHelper
import com.orhanobut.logger.Logger
import com.soft507.travelsuggest.R
import com.soft507.travelsuggest.base.BaseFragment
import com.soft507.travelsuggest.bean.DetectBean
import com.soft507.travelsuggest.databinding.FragmentTravelSuggestBinding
import com.soft507.travelsuggest.http.Api
import com.soft507.travelsuggest.model.TravelSuggestViewModel
import com.soft507.travelsuggest.util.AMapUtil
import com.soft507.travelsuggest.util.PoiOverlay
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit


class TravelSuggestFragment : BaseFragment<FragmentTravelSuggestBinding>(),
    AMapLocationListener, PoiSearch.OnPoiSearchListener, AMap.OnMarkerClickListener,
    AMap.OnMapLoadedListener, DistanceSearch.OnDistanceSearchListener {


    private val viewModel by lazy {
        ViewModelProviders.of(activity!!).get(TravelSuggestViewModel::class.java)
    }

    private var aMap: AMap? = null
    private var uiSettings: UiSettings? = null
    private var myLocationStyle: MyLocationStyle? = null
    private var aMapLocationClient: AMapLocationClient? = null
    private var aMapLocationClientOption: AMapLocationClientOption? = null
    private var scheduledExecutorService: ScheduledExecutorService? = null

    val takePhoto = 1

    lateinit var city: String
    lateinit var imageUrl: Uri
    lateinit var outputImage: File
    lateinit var lp: LatLonPoint
    lateinit var poiSearch: PoiSearch
    lateinit var query: PoiSearch.Query
    private var poiResult: PoiResult? = null
    private var poiItems // poi数据
            : List<PoiItem>? = null
    private var mlastMarker: Marker? = null

    private var poiOverlay // poi图层
            : myPoiOverlay? = null
    private var detailMarker: Marker? = null

    private val keyWordSearch = 1

    private val citySearch = 2

    private var searchStatus = 0

    private var distanceSearch: DistanceSearch? = null
    private var mDistanceText = arrayListOf<Text>()

    private var items = arrayListOf<PoiItem>()


    private val markers = intArrayOf(
        R.drawable.ic_poi_marker,
        R.drawable.ic_poi_marker,
        R.drawable.ic_poi_marker,
        R.drawable.ic_poi_marker,
        R.drawable.ic_poi_marker,
        R.drawable.ic_poi_marker,
        R.drawable.ic_poi_marker,
        R.drawable.ic_poi_marker,
        R.drawable.ic_poi_marker,
        R.drawable.ic_poi_marker
    )

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mDataBinding.suggestMapView.onSaveInstanceState(outState)
    }

    override fun getLayoutRes() = R.layout.fragment_travel_suggest

    override fun initWork(savedInstanceState: Bundle?) {
        mDataBinding.suggestMapView.onCreate(savedInstanceState)
        if (aMap == null) {
            aMap = mDataBinding.suggestMapView.map
            uiSettings = aMap?.uiSettings
            uiSettings?.isZoomControlsEnabled = false
            myLocationStyle = MyLocationStyle()
            myLocationStyle?.interval(2000)
            aMap!!.myLocationStyle = myLocationStyle
            aMap!!.setOnMarkerClickListener(this)
            aMap!!.setOnMapLoadedListener(this)
            aMapLocationClient = AMapLocationClient(activity)
            aMapLocationClientOption = AMapLocationClientOption()
            aMapLocationClientOption?.locationMode =
                AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
            aMapLocationClientOption?.isOnceLocation = true
            aMapLocationClientOption?.httpTimeOut = 20000
            aMapLocationClient?.setLocationListener(this)
            aMapLocationClient?.setLocationOption(aMapLocationClientOption)
            aMapLocationClient?.startLocation()
        }
        //开启定位
        uiSettings?.isMyLocationButtonEnabled = true // 是否显示默认的定位按钮
        aMap!!.isMyLocationEnabled = true


        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor()
        scheduledExecutorService?.scheduleAtFixedRate(
            FatigueDriveRunnable(),
            10,
            500,
            TimeUnit.SECONDS
        )

        mDataBinding.suggestFloatButton.setOnClickListener {

            InputDialog.show(activity as AppCompatActivity, "提示", "请输入Poi地址", "确定", "取消")
                .setOnOkButtonClickListener { baseDialog, v, inputStr ->
                    val currentPage = 0
                    query = PoiSearch.Query(
                        inputStr,
                        "",
                        city
                    ) // 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）

                    query.pageSize = 10 // 设置每页最多返回多少条poiitem

                    query.pageNum = currentPage // 设置查第一页
                    searchStatus = keyWordSearch
                    poiSearch = PoiSearch(activity!!, query)
                    poiSearch.setOnPoiSearchListener(this)
                    poiSearch.searchPOIAsyn()

                    baseDialog.doDismiss()
                    return@setOnOkButtonClickListener true
                }

        }

    }


    inner class FatigueDriveRunnable : Runnable {
        override fun run() {
            activity!!.runOnUiThread {
                MessageDialog.show(
                    activity as AppCompatActivity, "警告", "请打开相机进行疲劳监测", "我知道了"
                ).onOkButtonClickListener =
                    OnDialogButtonClickListener { baseDialog, v ->

                        outputImage = File(activity?.externalCacheDir, "output_image.jpg")
                        if (outputImage.exists()) {
                            outputImage.delete()
                        }
                        outputImage.createNewFile()
                        imageUrl = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            FileProvider.getUriForFile(
                                activity!!,
                                "com.soft507.travelsuggest.fileprovider",
                                outputImage
                            )
                        } else {
                            Uri.fromFile(outputImage)
                        }
                        val intent = Intent("android.media.action.IMAGE_CAPTURE")
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUrl)
                        startActivityForResult(intent, takePhoto)

                        baseDialog.doDismiss()
                        return@OnDialogButtonClickListener true
                    }
            }
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            takePhoto ->
                if (resultCode == Activity.RESULT_OK) {

                    val newFile: File =
                        CompressHelper.getDefault(activity).compressToFile(outputImage)


                    val imageBody: RequestBody =
                        RequestBody.create("multipart/form-data".toMediaTypeOrNull(), newFile)


                    val builder = MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        //在这里添加服务器除了文件之外的其他参数
                        .addFormDataPart("api_key", Api.API_KEY)
                        .addFormDataPart("api_secret", Api.API_SECRET)
                        .addFormDataPart("return_attributes", "skinstatus")

                    //添加文件(uploadfile就是你服务器中需要的文件参数)
                    builder.addFormDataPart("image_file", newFile.name, imageBody)

                    val parts = builder.build().parts

                    viewModel.setTravelBean(parts)


                    viewModel.travelLiveData.observe(
                        viewLifecycleOwner,
                        Observer { travelLiveData ->

                            Logger.d(travelLiveData)
                            shortToast(activity!!, "脸部信息:${travelLiveData}")

                            val response = travelLiveData as DetectBean.Skinstatus

                            if (response.dark_circle > 2.0) {
                                longToast(activity!!, resources.getString(R.string.bad_health))

                                searchStatus = citySearch
                                doSearchHostel()
                            } else {
                                shortToast(activity!!, resources.getString(R.string.great_health))
                            }
                        })

                }
        }
    }

    //搜索附近旅馆
    fun doSearchHostel() {
        query = PoiSearch.Query("旅馆", "", city)
        val currentPage = 0
        query.pageSize = 20 // 设置每页最多返回多少条poiitem
        query.pageNum = currentPage // 设置查第一页
        if (lp != null) {
            poiSearch = PoiSearch(activity!!, query)
            poiSearch.setOnPoiSearchListener(this)
            poiSearch.bound = PoiSearch.SearchBound(lp, 5000, true) //
            // 设置搜索区域为以lp点为圆心，其周围5000米范围
            // 设置搜索区域为以lp点为圆心，其周围5000米范围
            poiSearch.searchPOIAsyn() // 异步搜索

        }
    }


    /**
     * 定位检测对话框
     */
    private fun showCurrentCityDialog(city: String, district: String) {
        MessageDialog.show(
            activity as
                    AppCompatActivity, "提示", "检测到你当前定位城市是${city}${district},已帮你开启Poi搜索", "我知道了"
        )
    }

    override fun onResume() {
        super.onResume()
        mDataBinding.suggestMapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mDataBinding.suggestMapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mDataBinding.suggestMapView.onDestroy()
        aMapLocationClient?.onDestroy()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (!scheduledExecutorService!!.isShutdown) {
            scheduledExecutorService!!.shutdownNow()
        }
    }

    //定位回调
    override fun onLocationChanged(aMapLocation: AMapLocation?) {
        lp = LatLonPoint(aMapLocation!!.latitude, aMapLocation!!.longitude)
        city = aMapLocation!!.city
        showCurrentCityDialog(aMapLocation!!.city, aMapLocation.district)
    }

    override fun onPoiItemSearched(p0: PoiItem?, p1: Int) {
    }

    override fun onPoiSearched(result: PoiResult?, rcode: Int) {
        if (searchStatus == citySearch) {
            if (rcode == AMapException.CODE_AMAP_SUCCESS) {
                if (result != null && result.query != null) { // 搜索poi的结果
                    if (result.query == query) { // 是否是同一条
                        poiResult = result
                        poiItems = poiResult?.pois // 取得第一页的poiitem数据，页数从数字0开始
                        val suggestionCities: List<SuggestionCity> = poiResult!!
                            .searchSuggestionCitys // 当搜索不到poiitem数据时，会返回含有搜索关键字的城市信息
                        if (poiItems != null && poiItems?.size!! > 0) {
                            //清除POI信息显示
                            whetherToShowDetailInfo(false)
                            //并还原点击marker样式
                            if (mlastMarker != null) {
                                resetlastmarker()
                            }
                            //清理之前搜索结果的marker
                            if (poiOverlay != null) {
                                poiOverlay?.removeFromMap()
                            }
                            aMap!!.clear()
                            poiOverlay = myPoiOverlay(
                                aMap,
                                poiItems
                            )
                            poiOverlay!!.addToMap()
                            poiOverlay!!.zoomToSpan()
                            aMap!!.addMarker(
                                MarkerOptions()
                                    .anchor(0.5f, 0.5f)
                                    .icon(
                                        BitmapDescriptorFactory
                                            .fromBitmap(
                                                BitmapFactory.decodeResource(
                                                    resources, R.drawable.point4
                                                )
                                            )
                                    )
                                    .position(LatLng(lp.latitude, lp.longitude))
                            )
                            aMap!!.addCircle(
                                CircleOptions()
                                    .center(
                                        LatLng(
                                            lp.latitude,
                                            lp.longitude
                                        )
                                    ).radius(5000.0)
                                    .strokeColor(Color.BLUE)
                                    .fillColor(Color.argb(50, 1, 1, 1))
                                    .strokeWidth(2f)
                            )
                        } else if (suggestionCities != null
                            && suggestionCities.size > 0
                        ) {
                            showSuggestCity(suggestionCities)
                        } else {
                            shortToast(activity!!, "没有结果")
                        }
                    }
                } else {
                    shortToast(activity!!, "没有结果")
                }
            } else {
                shortToast(activity!!, "ERROR")
            }
        }
        if (searchStatus == keyWordSearch) {
            if (rcode == AMapException.CODE_AMAP_SUCCESS) {
                if (result != null && result.query != null) { // 搜索poi的结果
                    if (result.query == query) { // 是否是同一条
                        poiResult = result
                        // 取得搜索到的poiitems有多少页
                        val poiItems: List<PoiItem>? =
                            poiResult!!.pois // 取得第一页的poiitem数据，页数从数字0开始
                        items.addAll(poiItems!!)
                        val suggestionCities = poiResult!!
                            .searchSuggestionCitys // 当搜索不到poiitem数据时，会返回含有搜索关键字的城市信息
                        if (poiItems != null && poiItems.size > 0) {
                            aMap!!.clear() // 清理之前的图标
                            val poiOverlay = PoiOverlay(aMap, poiItems)
                            poiOverlay.removeFromMap()
                            poiOverlay.addToMap()
                            //展示出所有点
                            val builder = LatLngBounds.Builder()
                            poiItems.forEach {
                                builder.include(AMapUtil.convertToLatLng(it.latLonPoint))
                            }
                            builder.include(AMapUtil.convertToLatLng(lp))
                            aMap!!.moveCamera(
                                CameraUpdateFactory.newLatLngBounds(
                                    builder.build(),
                                    100
                                )
                            )

                            distanceSearch = DistanceSearch(activity)
                            distanceSearch!!.setDistanceSearchListener(this)

                            searchDistanceResult(DistanceSearch.TYPE_DRIVING_DISTANCE, poiItems)

                            setFromAndTo(poiItems)
//                            poiOverlay.zoomToSpan()
                        } else if (suggestionCities != null
                            && suggestionCities.size > 0
                        ) {
                            showSuggestCity(suggestionCities)
                        } else {
                            shortToast(activity!!, "NO DATA")
                        }
                    }
                } else {
                    shortToast(activity!!, "NO DATA")
                }
            } else {
                shortToast(activity!!, "error")
            }
        }

    }

    private fun setFromAndTo(poiItems: List<PoiItem>) {


        poiItems.forEach {
            aMap!!.addPolyline(
                PolylineOptions().add(
                    AMapUtil.convertToLatLng(it.latLonPoint),
                    AMapUtil.convertToLatLng(lp)
                )
                    .color(Color.GREEN)
            )
            mDistanceText.add(
                aMap!!.addText(
                    TextOptions().position(
                        getMidLatLng(
                            AMapUtil.convertToLatLng(it.latLonPoint),
                            AMapUtil.convertToLatLng(lp)
                        )
                    )
                        .text("cal distance ...")
                )
            )
        }
    }


    /**
     * 开始搜索路径规划方案
     */
    private fun searchDistanceResult(
        mode: Int,
        poiItems: List<PoiItem>
    ) {
        val distanceQuery = DistanceQuery()

        val latLonPoints: MutableList<LatLonPoint> =
            ArrayList()

        poiItems.forEach {
            latLonPoints.add(it.latLonPoint)
        }

        distanceQuery.origins = latLonPoints
        distanceQuery.destination = lp
        distanceQuery.type = mode

        distanceSearch!!.calculateRouteDistanceAsyn(distanceQuery)
    }


    /**
     * 求两个经纬度的中点
     * @param l1
     * @param l2
     * @return
     */
    private fun getMidLatLng(l1: LatLng, l2: LatLng): LatLng? {
        return LatLng((l1.latitude + l2.latitude) / 2, (l1.longitude + l2.longitude) / 2)
    }


    private fun whetherToShowDetailInfo(isToShow: Boolean) {
        if (isToShow) {
            mDataBinding.poiDetail.visibility = View.VISIBLE
        } else {
            mDataBinding.poiDetail.visibility = View.GONE
        }
    }

    // 将之前被点击的marker置为原来的状态
    private fun resetlastmarker() {
        val index: Int = mlastMarker?.let { poiOverlay?.getPoiIndex(it) }!!
        if (index < 10) {
            mlastMarker!!.setIcon(
                BitmapDescriptorFactory
                    .fromBitmap(
                        BitmapFactory.decodeResource(
                            resources,
                            markers.get(index)
                        )
                    )
            )
        } else {
            mlastMarker!!.setIcon(
                BitmapDescriptorFactory.fromBitmap(
                    BitmapFactory.decodeResource(resources, R.drawable.marker_other_highlight)
                )
            )
        }
        mlastMarker = null
    }


    /**
     * poi没有搜索到数据，返回一些推荐城市的信息
     */
    private fun showSuggestCity(cities: List<SuggestionCity>) {
        var infomation = "推荐城市\n"
        for (i in cities.indices) {
            infomation += """
                城市名称:${cities[i].cityName}城市区号:${cities[i].cityCode}城市编码:${cities[i].adCode}
                
                """.trimIndent()
        }
        shortToast(activity!!, infomation)
    }


    /**
     * 自定义PoiOverlay
     *
     */
    inner class myPoiOverlay(
        private val mamap: AMap?,
        private val mPois: List<PoiItem>?
    ) {
        private val mPoiMarks = ArrayList<Marker>()

        /**
         * 添加Marker到地图中。
         * @since V2.1.0
         */
        fun addToMap() {
            if (mPois != null) {
                val size = mPois.size
                for (i in 0 until size) {
                    val marker = mamap!!.addMarker(getMarkerOptions(i))
                    val item = mPois[i]
                    marker.setObject(item)
                    mPoiMarks.add(marker)
                }
            }
        }

        /**
         * 去掉PoiOverlay上所有的Marker。
         *
         * @since V2.1.0
         */
        fun removeFromMap() {
            for (mark in mPoiMarks) {
                mark.remove()
            }
        }

        /**
         * 移动镜头到当前的视角。
         * @since V2.1.0
         */
        fun zoomToSpan() {
            if (mPois != null && mPois.size > 0) {
                if (mamap == null) return
                val bounds = latLngBounds
                mamap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100))
            }
        }

        private val latLngBounds: LatLngBounds
            private get() {
                val b = LatLngBounds.builder()
                if (mPois != null) {
                    val size = mPois.size
                    for (i in 0 until size) {
                        b.include(
                            LatLng(
                                mPois[i].latLonPoint.latitude,
                                mPois[i].latLonPoint.longitude
                            )
                        )
                    }
                }
                return b.build()
            }

        private fun getMarkerOptions(index: Int): MarkerOptions {
            return MarkerOptions()
                .position(
                    LatLng(
                        mPois!![index].latLonPoint
                            .latitude, mPois[index]
                            .latLonPoint.longitude
                    )
                )
                .title(getTitle(index)).snippet(getSnippet(index))
                .icon(getBitmapDescriptor(index))
        }

        protected fun getTitle(index: Int): String {
            return mPois!![index].title
        }

        protected fun getSnippet(index: Int): String {
            return mPois!![index].snippet
        }

        /**
         * 从marker中得到poi在list的位置。
         *
         * @param marker 一个标记的对象。
         * @return 返回该marker对应的poi在list的位置。
         * @since V2.1.0
         */
        fun getPoiIndex(marker: Marker): Int {
            for (i in mPoiMarks.indices) {
                if (mPoiMarks[i] == marker) {
                    return i
                }
            }
            return -1
        }

        /**
         * 返回第index的poi的信息。
         * @param index 第几个poi。
         * @return poi的信息。poi对象详见搜索服务模块的基础核心包（com.amap.api.services.core）中的类 **[PoiItem](../../../../../../Search/com/amap/api/services/core/PoiItem.html)**。
         * @since V2.1.0
         */
        fun getPoiItem(index: Int): PoiItem? {
            return if (index < 0 || index >= mPois!!.size) {
                null
            } else mPois[index]
        }

        protected fun getBitmapDescriptor(arg0: Int): BitmapDescriptor {
            return if (arg0 < 10) {
                BitmapDescriptorFactory.fromBitmap(
                    BitmapFactory.decodeResource(resources, markers.get(arg0))
                )
            } else {
                BitmapDescriptorFactory.fromBitmap(
                    BitmapFactory.decodeResource(resources, R.drawable.marker_other_highlight)
                )
            }
        }

    }

    override fun onMarkerClick(marker: Marker?): Boolean {
        if (marker!!.getObject() != null) {
            whetherToShowDetailInfo(true)
            try {
                val mCurrentPoi =
                    marker!!.getObject() as PoiItem
                mlastMarker = if (mlastMarker == null) {
                    marker
                } else {
                    // 将之前被点击的marker置为原来的状态
                    resetlastmarker()
                    marker
                }
                detailMarker = marker
                detailMarker?.setIcon(
                    BitmapDescriptorFactory
                        .fromBitmap(
                            BitmapFactory.decodeResource(
                                resources,
                                R.drawable.ic_poi_marker_pressed
                            )
                        )
                )
                setPoiItemDisplayContent(mCurrentPoi)
            } catch (e: Exception) {
                // TODO: handle exception
            }
        } else {
            whetherToShowDetailInfo(false)
            resetlastmarker()
        }
        return true
    }

    private fun setPoiItemDisplayContent(mCurrentPoi: PoiItem) {
        mDataBinding.poiName.text = mCurrentPoi.title
        mDataBinding.poiAddress.text = mCurrentPoi.snippet + mCurrentPoi.distance
        mDataBinding.poiInfo.text = "距离：${mCurrentPoi.distance}米"
    }

    override fun onMapLoaded() {

    }

    override fun onDistanceSearched(distanceResult: DistanceResult?, errorCode: Int) {
        if (errorCode == AMapException.CODE_AMAP_SUCCESS) {
            try {
                Log.i("amap", "onDistanceSearched $distanceResult")
                val distanceItems: List<DistanceItem> =
                    distanceResult!!.distanceResults
                val distanceQuery: DistanceQuery = distanceResult!!.distanceQuery
                val origins = distanceQuery.origins
                val destLatlon = distanceQuery.destination
                if (distanceItems == null) {
                    return
                }
                var index = 1

                var shortDistance = 10000

                var address = ""

                var duration = 0

                for (item in distanceItems) {
                    val stringBuffer = StringBuffer()
                    //item.getOriginId() - 1 是因为 下标从1开始
                    stringBuffer.append("\n\torid: ").append(item.originId).append(" ")
                        .append(origins[item.originId - 1]).append("\n")
                    stringBuffer.append("\tdeid: ").append(item.destId).append(" ")
                        .append(destLatlon).append("\n")
                    stringBuffer.append("\tdis: ").append(item.distance).append(" , ")
                    stringBuffer.append("\tdur: ").append(item.duration)
                    if (item.errorInfo != null) {
                        stringBuffer.append(" , ").append("err: ").append(item.errorCode)
                            .append(" ").append(item.errorInfo)
                    }
                    stringBuffer.append("\n")

                    Log.i(
                        "amap",
                        "onDistanceSearched $index : $stringBuffer"
                    )

                    if (item.distance < shortDistance) {
                        shortDistance = item.distance.toInt()
                        address = items[index - 1].title
                        duration = item.duration.toInt()
                    }

                    mDistanceText[index - 1].text =
                        items[index - 1].title + " " + item.distance.toString() + " 米 " + item.duration + " 秒"
                    index++
                }

                MessageDialog.show(
                    activity as AppCompatActivity, "提示",
                    "亲！，已帮你找到最短行程，这边建议你去${address},全程行程${shortDistance}米，大概需要${duration / 60}分钟。"
                    , "确定"
                );

            } catch (e: Throwable) {
                e.printStackTrace()
            }
        }
    }

}
