package com.soft507.travelsuggest.http

/**
 * @author LRQ-Pro
 * @description:   api url
 * @date : 2020/6/1 20:32
 */
object Api {

    //追书神器
    const val BOOK_BASE_URL = "http://api.zhuishushenqi.com"

    //公网ip
    private const val IP = "47.110.128.63:80"

    //内网ip
    private const val insideIp = "192.168.1.100"

    //后台url
    const val DATA_BASE_URL = "http://${insideIp}/Travel%20Suggest/api/"

    //图像url
    const val IMAGE_BASE_URL = "http://${insideIp}/Travel%20Suggest/"

    //视频url
    const val VIDEO_BASE_URL = "http://${insideIp}/Travel%20Suggest/uploadfiles/videos/"

    //FACE 旷世
    const val FACE_BASE_URL = "https://api-cn.faceplusplus.com/"

    const val API_KEY = "e74j63ne3dFidioxB9ekEq-U9uBp58DR"
    const val API_SECRET = "9_9r0LRFzAGAGrwhDke3fhnb4u5m-VDS"

    const val SUCCESS = 200
    const val FAILED = 400
    const val ERROR = 402
    const val SERVERERROR = 404


}