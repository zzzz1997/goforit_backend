package com.zzapp.goforit.controller

import com.zzapp.goforit.annotation.TokenId
import com.zzapp.goforit.annotation.WithoutAuth
import com.zzapp.goforit.entity.ResponseResult
import com.zzapp.goforit.entity.User
import com.zzapp.goforit.repository.UserRepository
import com.zzapp.goforit.util.JWTUtil
import com.zzapp.goforit.util.OssUtil
import com.zzapp.goforit.util.RandomUtilObject
import com.zzapp.goforit.util.ResponseUtilObject
import org.apache.commons.codec.digest.DigestUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

/**
 * 用户控制类
 */
@RestController
@RequestMapping("/user")
class UserController {

    // 用户仓库对象
    @Autowired
    lateinit var userRepository: UserRepository

    // jwt工具
    @Autowired
    lateinit var jwtUtil: JWTUtil

    // oss工具
    @Autowired
    lateinit var ossUtil: OssUtil

    /**
     * 测试
     */
    @WithoutAuth
    @RequestMapping("/test")
    fun test(): ResponseResult<String> {
        return ResponseUtilObject.success("Hello world!")
    }

    /**
     * 获取单一用户
     */
    @RequestMapping("")
    fun user(@TokenId id: Long): ResponseResult<User> {
        println(id)
        return ResponseUtilObject.success(userRepository.findById(id).get())
    }

    /**
     * 用户登录
     */
    @WithoutAuth
    @RequestMapping(value = ["/login"], method = [RequestMethod.POST])
    fun login(username: String, password: String): ResponseResult<User> {
        val user = userRepository.findUserByUsernameAndPassword(username, DigestUtils.sha1Hex(password))
                ?: return ResponseUtilObject.fail("用户名或密码错误")
        val tokenTime = System.currentTimeMillis() + jwtUtil.jwtConfig.expire
        val token = jwtUtil.sign(user.id, user.username, tokenTime) ?: return ResponseUtilObject.fail("系统错误，请重新尝试")
        user.token = token
        user.tokenTime = (tokenTime / 1000).toInt()
        return ResponseUtilObject.success(user)
    }

    /**
     * 用户注册
     */
    @WithoutAuth
    @RequestMapping(value = ["/register"], method = [RequestMethod.POST])
    fun register(username: String, password: String): ResponseResult<User> {
        var user = userRepository.findUserByUsername(username)
        if (user != null) {
            return ResponseUtilObject.fail("用户名已存在")
        } else {
            user = User()
            user.username = username
            user.password = DigestUtils.sha1Hex(password)
            user.avatar = RandomUtilObject.getRandomAvatar()
            user.createdTime = System.currentTimeMillis() / 1000
            user = userRepository.save(user)
            val tokenTime = System.currentTimeMillis() + jwtUtil.jwtConfig.expire
            val token = jwtUtil.sign(user.id, user.username, tokenTime) ?: return ResponseUtilObject.fail("系统错误，请重新尝试")
            user.token = token
            user.tokenTime = (tokenTime / 1000).toInt()
            return ResponseUtilObject.success(user)
        }
    }

    /**
     * 头像上传
     */
    @RequestMapping(value = ["/avatar"], method = [RequestMethod.POST])
    fun avatar(@TokenId id: Long, @RequestParam("avatar") file: MultipartFile): ResponseResult<String> {
        val optional = userRepository.findById(id)
        if (optional.isPresent) {
            if (file.isEmpty) {
                return ResponseUtilObject.fail("文件上传失败")
            }
            val name = DigestUtils.md5Hex(file.name + file.toString().substring(file.toString().length - 8)) + '.' + file.originalFilename!!.split('.')[1]
            val url = ossUtil.upload(ossUtil.AVATAR_PATH + name, file.inputStream)
            val user = optional.get()
            user.avatar = url
            userRepository.save(user)
            return ResponseUtilObject.success(url)
        } else {
            return ResponseUtilObject.fail("用户信息不存在")
        }
    }

    /**
     * 用户设置
     */
    @RequestMapping(value = ["/set"], method = [RequestMethod.POST])
    fun set(@TokenId id: Long, language: Int, startDayOfWeek: Int, checkMode: Int): ResponseResult<Boolean> {
        val optional = userRepository.findById(id)
        return if (optional.isPresent) {
            val user = optional.get()
            user.language = language
            user.startDayOfWeek = startDayOfWeek
            user.checkMode = checkMode
            userRepository.save(user)
            ResponseUtilObject.success()
        } else {
            ResponseUtilObject.fail("用户信息不存在")
        }
    }
}