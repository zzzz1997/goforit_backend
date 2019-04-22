package com.zzapp.goforit.interceptor

import com.zzapp.goforit.annotation.WithoutAuth
import com.zzapp.goforit.util.JWTUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * token拦截器
 */
@Component
class TokenInterceptor: HandlerInterceptorAdapter() {

    // jwt工具
    @Autowired
    lateinit var jwtUtil: JWTUtil

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        // 获取方法
        val method = (handler as HandlerMethod).method
        // 获取auth拦截
        val withoutAuth = method.getAnnotation(WithoutAuth::class.java)
        if (withoutAuth == null) {
            System.out.println("接受auth")
            val token = request.getHeader("token") ?: return false
            val id = jwtUtil.verify(token)
            if (id == 0) {
                return false
            }
        } else {
            System.out.println("不接受auth")
        }
        return super.preHandle(request, response, handler)
    }
}