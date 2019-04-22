package com.zzapp.goforit

import com.zzapp.goforit.interceptor.TokenInterceptor
import com.zzapp.goforit.resolver.TokenResolver
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

/**
 * 应用配置
 */
@Configuration
class WebAppConfigurer: WebMvcConfigurer {

    // token拦截器
    @Autowired
    lateinit var tokenInterceptor: TokenInterceptor

    // token解析器
    @Autowired
    lateinit var tokenResolver: TokenResolver

    override fun addInterceptors(registry: InterceptorRegistry) {
        super.addInterceptors(registry)
        // 设置token拦截器
        registry.addInterceptor(tokenInterceptor)
    }

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        super.addArgumentResolvers(resolvers)
        // 设置token解析器
        resolvers.add(tokenResolver)
    }
}