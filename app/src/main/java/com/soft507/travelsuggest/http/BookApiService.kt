package com.soft507.travelsuggest.http

import com.soft507.travelsuggest.bean.AllStyleBookBean
import com.soft507.travelsuggest.bean.RankBookStyleBean
import retrofit2.Response
import retrofit2.http.GET

/**
 * @author LRQ-Pro
 * @description:retrofit  about book info api
 * @date : 2020/6/1 20:10
 */
interface BookApiService {

    //http://api.zhuishushenqi.com/

    /**
     * 获取所有分类
     */
    @GET("/cats/lv2/statistics")
    suspend fun getAllStyleBook(): AllStyleBookBean

    /**
     * 获取排行榜类型
     */
    @GET("/ranking/gender")
    suspend fun getRankStyleBook(): RankBookStyleBean
}