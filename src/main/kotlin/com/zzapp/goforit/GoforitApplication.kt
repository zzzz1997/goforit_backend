package com.zzapp.goforit

import com.zzapp.goforit.config.JWTConfig
import com.zzapp.goforit.config.OssConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

/**
 * 应用入口类
 */
@SpringBootApplication
@EnableConfigurationProperties(JWTConfig::class, OssConfig::class)
class GoforitApplication

/**
 * 入口方法
 */
fun main(args: Array<String>) {
	runApplication<GoforitApplication>(*args)
}
