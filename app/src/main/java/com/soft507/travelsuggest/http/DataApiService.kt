package com.soft507.travelsuggest.http

import com.soft507.travelsuggest.bean.*
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

/**
 * @author LRQ-Pro
 * @description:
 * @date : 2020/6/7 15:47
 */
interface DataApiService {

    /**
     * 登录
     * @param username
     * @param password
     */
    @FormUrlEncoded
    @POST("login.php")
    fun userLogin(
        @Field("username") username: String?,
        @Field("password") password: String?
    ): Call<LoginBean>


    /**
     * 注册
     * @param username
     * @param password
     * @param phone
     * @param email
     */
    @FormUrlEncoded
    @POST("register.php")
    fun userRegister(
        @Field("username") username: String?,
        @Field("password") password: String?,
        @Field("_phone") phone: String?,
        @Field("_email") email: String?
    ): Call<RegisterBean>


    /**
     * 邮箱登录
     * @param email
     * @param password
     */
    @FormUrlEncoded
    @POST("email_login.php")
    fun emailLogin(
        @Field("email") email: String?,
        @Field("password") password: String?
    ): Call<LoginBean>

    /**
     * 用户信息查询
     * @param username
     */
    @FormUrlEncoded
    @POST("userquery.php")
    fun getUserInfo(
        @Field("username") username: String?
    ): Call<UserInfoBean>

    /**
     * 获取所有关于视频的信息
     */
    @FormUrlEncoded
    @POST("allVideos.php")
    fun getAllVideos(@Field("noData") noData: String?): Call<AllVideosBean>

    /**
     * 获取某个视频的喜欢量等等
     * @param videoname
     */
    @FormUrlEncoded
    @POST("worksinfo.php")
    fun getWorksInfo(@Field("videoname") videoname: String): Call<WorksInfoBean>

    /**
     * @param sc_content
     * @param sc_time
     * @param sc_user
     * @param sc_url
     * @param sc_videoname
     * 发表评论
     */
    @FormUrlEncoded
    @POST("publishedComments.php")
    fun comments(
        @Field("sc_user") sc_user: String,
        @Field("sc_videoname") sc_videoname: String,
        @Field("sc_time") sc_time: String,
        @Field("sc_content") sc_content: String,
        @Field("sc_url") sc_url: String
    ): Call<CommentStatusBean>

    /**
     * 获取话题里所有数据
     * @param key
     */
    @FormUrlEncoded
    @POST("queryTopic.php")
    fun allToPic(@Field("key") key: String): Call<AllTopicBean>


    /**
     * 用户上传api
     * @param partList
     */
    @Multipart
    @POST("uploadTopic.php")
    fun upLoadPic(
        @Part
        partList: List<MultipartBody.Part>
    ): Call<UpLoadTopicBean>




}