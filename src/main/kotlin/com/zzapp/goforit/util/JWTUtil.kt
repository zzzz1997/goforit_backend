package com.zzapp.goforit.util

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.zzapp.goforit.config.JWTConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Component
import java.io.UnsupportedEncodingException
import java.lang.Exception
import java.util.*
import kotlin.collections.HashMap

/**
 * JWT工具类
 */
@Component
class JWTUtil {

    // 配置信息
    @Autowired
    lateinit var jwtConfig: JWTConfig

    /**
     * 创建token
     *
     * @param id 用户id
     * @param username 用户名
     * @param expire 过期时间
     *
     * @return token
     */
    @Retryable
    fun sign(id: Long, username: String, expire: Long): String? {
        return try {
            val algorithm = Algorithm.HMAC256(jwtConfig.secret)
            val header = HashMap<String, Any>(2)
            header["typ"] = "JWT"
            header["alg"] = "HS256"
            JWT.create()
                    .withHeader(header)
                    .withClaim("id", id)
                    .withClaim("username", username)
                    .withExpiresAt(Date(expire))
                    .sign(algorithm)
        } catch (e: UnsupportedEncodingException) {
            null
        }
    }

    /**
     * 获取token中id信息
     *
     * @param token token
     *
     * @return id
     */
    fun verify(token: String): Int {
        return try {
            val algorithm = Algorithm.HMAC256(jwtConfig.secret)
            val verifier = JWT.require(algorithm)
                    .build()
            val decodedJWT = verifier.verify(token)
            return decodedJWT.claims["id"]!!.asInt()
        } catch (e: Exception) {
            0
        }
    }
}