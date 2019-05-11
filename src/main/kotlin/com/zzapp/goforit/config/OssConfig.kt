package com.zzapp.goforit.config

import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * oss配置类
 */
@ConfigurationProperties(prefix = "oss")
class OssConfig {

    // 文件链接
    var fileUrl = ""

    // 地域节点
    var endpoint = ""

    // 访问id
    var accessKeyId = ""

    // 访问密钥
    var accessKeySecret = ""

    // 空间名
    var bucketName = ""
}