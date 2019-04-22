package com.zzapp.goforit.repository

import com.zzapp.goforit.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * 用户库
 */
@Repository
interface UserRepository: JpaRepository<User, Long> {

    /**
     * 根据用户名和密码查询用户信息
     *
     * @param username 用户名
     * @param password 密码
     *
     * @return 用户信息
     */
    fun findOneByUsernameAndPassword(username: String, password: String): User?

    /**
     * 根据用户名查询用户信息
     *
     * @param username 用户名
     *
     * @return 用户信息
     */
    fun findOneByUsername(username: String): User?
}