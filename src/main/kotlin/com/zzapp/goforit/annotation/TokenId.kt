package com.zzapp.goforit.annotation

/**
 * 解析token里的id信息
 */
@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class TokenId(val value: Int = -2)
