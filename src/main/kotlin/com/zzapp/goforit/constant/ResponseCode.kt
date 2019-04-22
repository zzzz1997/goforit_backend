package com.zzapp.goforit.constant

/**
 * 返回码常量类
 */
class ResponseCode {

    companion object {

        // 成功
        val SUCCESS = 200

        // 失败
        val FAIL = 400

        // 未授权
        val UNAUTHORIZED = 401

        // 接口不存在
        val NOT_FOUND = 404

        // 服务器错误
        val SERVER_ERROR = 500
    }
}