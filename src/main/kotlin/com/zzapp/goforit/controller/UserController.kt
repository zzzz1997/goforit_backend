package com.zzapp.goforit.controller

import com.zzapp.goforit.annotation.TokenId
import com.zzapp.goforit.annotation.WithoutAuth
import com.zzapp.goforit.entity.ResponseResult
import com.zzapp.goforit.entity.User
import com.zzapp.goforit.repository.UserRepository
import com.zzapp.goforit.util.JWTUtil
import com.zzapp.goforit.util.RandomUtil
import com.zzapp.goforit.util.ResponseUtil
import org.apache.commons.codec.digest.DigestUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

/**
 * 用户控制类
 */
@RestController
@RequestMapping("/user")
class UserController {

    // 用户库对象
    @Autowired
    lateinit var userRepository: UserRepository

    // jwt工具
    @Autowired
    lateinit var jwtUtil: JWTUtil

    /**
     * 测试
     */
    @WithoutAuth
    @RequestMapping("/test")
    fun test(): ResponseResult<String> {
        return ResponseUtil.success("Hello world!")
    }

    /**
     * 获取单一用户
     */
    @RequestMapping("")
    fun getUser(@TokenId id: Long): ResponseResult<User> {
        return ResponseUtil.success(userRepository.findById(id).get())
    }

    /**
     * 用户登录
     */
    @WithoutAuth
    @RequestMapping(value = ["/login"], method = [RequestMethod.POST])
    fun login(username: String, password: String): ResponseResult<User> {
        val user = userRepository.findOneByUsernameAndPassword(username, DigestUtils.sha1Hex(password))
                ?: return ResponseUtil.fail("用户名或密码错误")
        val token = jwtUtil.sign(user.id, user.username) ?: return ResponseUtil.fail("系统错误，请重新尝试")
        user.token = token
        user.tokenTime = (System.currentTimeMillis() / 1000 + jwtUtil.jwtConfig.expire / 1000).toInt()
        return ResponseUtil.success(user)
    }

    /**
     * 用户注册
     */
    @WithoutAuth
    @RequestMapping(value = ["/register"], method = [RequestMethod.POST])
    fun register(username: String, password: String): ResponseResult<User> {
        var user = userRepository.findOneByUsername(username)
        if (user != null) {
            return ResponseUtil.fail("用户名已存在")
        } else {
            user = User()
            user.username = username
            user.password = DigestUtils.sha1Hex(password)
            user.avatar = RandomUtil.getRandomAvatar()
            user = userRepository.save(user)
            val token = jwtUtil.sign(user.id, user.username) ?: return ResponseUtil.fail("系统错误，请重新尝试")
            user.token = token
            user.tokenTime = (System.currentTimeMillis() / 1000 + jwtUtil.jwtConfig.expire / 1000).toInt()
            return ResponseUtil.success(user)
        }
    }
}