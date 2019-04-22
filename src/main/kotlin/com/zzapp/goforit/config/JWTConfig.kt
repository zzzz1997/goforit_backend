package com.zzapp.goforit.config

import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * JWT配置类
 */
@ConfigurationProperties(prefix = "jwt")
class JWTConfig {

    // 有效期时长
    var expire: Long = 0

    // 加密密钥
    var secret = ""
}