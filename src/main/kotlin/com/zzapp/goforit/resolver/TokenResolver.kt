package com.zzapp.goforit.resolver

import com.zzapp.goforit.annotation.TokenId
import com.zzapp.goforit.util.JWTUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

/**
 * token解析器
 */
@Component
class TokenResolver: HandlerMethodArgumentResolver {

    // jwt工具
    @Autowired
    lateinit var jwtUtil: JWTUtil

    override fun supportsParameter(p0: MethodParameter): Boolean {
        return p0.hasParameterAnnotation(TokenId::class.java)
    }

    override fun resolveArgument(p0: MethodParameter, p1: ModelAndViewContainer?, p2: NativeWebRequest, p3: WebDataBinderFactory?): Any? {
        val token = p2.getHeader("token") ?: return -1
        return jwtUtil.verify(token)
    }
}