package com.zzapp.goforit.entity

import net.sf.json.JSONObject

/**
 * 返回体实体
 */
class ResponseResult<T> {

    // 返回码
    var code: Int = 0

    // 描述信息
    var message = ""

    // 数据对象
    var data: T? = null

    /**
     * 设置返回码
     *
     * @param code 返回码
     *
     * @return 实体对象
     */
    fun setCode(code: Int): ResponseResult<T> {
        this.code = code
        return this
    }

    /**
     * 设置描述信息
     *
     * @param message 描述信息
     *
     * @return 实体对象
     */
    fun setMessage(message: String): ResponseResult<T> {
        this.message = message
        return this
    }

    /**
     * 设置数据对象
     *
     * @param data 数据对象
     *
     * @return 实体对象
     */
    fun setData(data: T): ResponseResult<T> {
        this.data = data
        return this
    }

    /**
     * 返回json字符串
     *
     * @return json字符串
     */
    fun toJsonString(): String {
        return JSONObject.fromObject(this).toString()
    }
}