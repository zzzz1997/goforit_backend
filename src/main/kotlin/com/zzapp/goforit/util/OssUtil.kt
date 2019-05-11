package com.zzapp.goforit.util

import com.aliyun.oss.OSSClient
import com.zzapp.goforit.config.OssConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.io.InputStream

/**
 * oss工具
 */
@Component
class OssUtil {

    // 头像地址
    val AVATAR_PATH = "avatar/"

    // 配置信息
    @Autowired
    lateinit var ossConfig: OssConfig

    // oss实例
    private val ossClient by lazy {
        OSSClient(ossConfig.endpoint, ossConfig.accessKeyId, ossConfig.accessKeySecret)
    }

    /**
     * 上传用户头像
     *
     * @param name 存储位置
     * @param inputStream 文件输入流
     *
     * @return 文件访问链接
     */
    fun upload(name: String, inputStream: InputStream): String {
        ossClient.putObject(ossConfig.bucketName, name, inputStream)
        return ossConfig.fileUrl + name
    }
}