package com.zzapp.goforit.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

/**
 * 用户实体类
 */
@Entity
@Table(name = "user")
class User {

    // id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    // 用户名
    var username = ""

    // 密码
    @JsonIgnore
    var password = ""

    // 头像
    var avatar = ""

    // 星期开始日期
    @Column(name = "start_day_of_week")
    var startDayOfWeek = 0

    // 语言
    var language = 0

    // token
    var token = ""

    // token有效期
    var tokenTime = 0

    // 注册时间
    @Column(name = "created_time")
    var createdTime: Long = 0
}