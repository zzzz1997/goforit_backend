package com.zzapp.goforit.entity

import com.fasterxml.jackson.annotation.JsonView
import javax.persistence.*

/**
 * 备份实体类
 */
@Entity
@Table(name = "backup")
class Backup {

    /**
     * 备份详情数据接口
     */
    interface BackupDetail

    // id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    // 用户id
    @Column(name = "user_id")
    var userId: Long = 0

    // 备份名
    var name = ""

    // 备份数据
    @JsonView(BackupDetail::class)
    var backup = ""

    // 注册时间
    @Column(name = "created_time")
    var createdTime: Long = 0
}