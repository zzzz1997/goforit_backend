package com.zzapp.goforit.util

import com.zzapp.goforit.entity.ResponseResult
import com.zzapp.goforit.constant.ResponseCode

/**
 * 返回体模板工具
 */
object ResponseUtilObject {

    // 成功信息
    val SUCCESS = "success"

    /**
     * 简单成功信息
     *
     * @return 返回体对象
     */
    fun success(): ResponseResult<Boolean> {
        return ResponseResult<Boolean>().setCode(ResponseCode.SUCCESS)
                .setMessage(SUCCESS)
                .setData(true)
    }

    /**
     * 带数据成功信息
     *
     * @param data 数据对象
     *
     * @return 返回体对象
     */
    fun <T> success(data: T): ResponseResult<T> {
        return ResponseResult<T>().setCode(ResponseCode.SUCCESS)
                .setMessage(SUCCESS)
                .setData(data)
    }

    /**
     * 简单失败信息
     *
     * @param message 描述信息
     *
     * @return 返回体对象
     */
    fun <T> fail(message: String): ResponseResult<T> {
        return ResponseResult<T>().setCode(ResponseCode.FAIL)
                .setMessage(message)
    }

    /**
     * 返回信息
     *
     * @param code 返回码
     * @param message 描述信息
     *
     * @return 返回体对象
     */
    fun <T> response(code: Int, message: String): ResponseResult<T> {
        return ResponseResult<T>().setCode(code)
                .setMessage(message)
    }

    /**
     * 返回信息
     *
     * @param code 返回码
     * @param message 描述信息
     * @param data 数据对象
     *
     * @return 返回体对象
     */
    fun <T> response(code: Int, message: String, data: T): ResponseResult<T> {
        return ResponseResult<T>().setCode(code)
                .setMessage(message)
                .setData(data)
    }
}