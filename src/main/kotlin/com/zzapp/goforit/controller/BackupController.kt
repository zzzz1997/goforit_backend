package com.zzapp.goforit.controller

import com.fasterxml.jackson.annotation.JsonView
import com.zzapp.goforit.annotation.TokenId
import com.zzapp.goforit.entity.Backup
import com.zzapp.goforit.entity.ResponseResult
import com.zzapp.goforit.repository.BackupRepository
import com.zzapp.goforit.util.ResponseUtil
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

/**
 * 备份控制类
 */
@RestController
@RequestMapping("/backup")
class BackupController {

    // 备份仓库对象
    lateinit var backupRepository: BackupRepository

    /**
     * 用户备份列表
     */
    @RequestMapping("/list")
    fun list(@TokenId userId: Long): ResponseResult<List<Backup>> {
        return ResponseUtil.success(backupRepository.findBackupsByUserId(userId))
    }

    /**
     * 备份详情
     */
    @JsonView(Backup.BackupDetail::class)
    @RequestMapping("/detail/{id}")
    fun detail(@PathVariable id: Long, @TokenId userId: Long): ResponseResult<Backup> {
        val backup = backupRepository.findBackupsByIdAndUserId(id, userId)
        return if (backup != null) {
            ResponseUtil.success(backup)
        } else {
            ResponseUtil.fail("备份信息不存在")
        }
    }

    /**
     * 备份数据
     */
    @RequestMapping(value = ["/backup"], method = [RequestMethod.POST])
    fun backup(@TokenId userId: Long, name: String, backup: String): ResponseResult<Backup> {
        val backupData = Backup()
        backupData.userId = userId
        backupData.name = name
        backupData.backup = backup
        backupData.createdTime = System.currentTimeMillis() / 1000
        return ResponseUtil.success(backupRepository.save(backupData))
    }

    /**
     * 删除备份
     */
    @RequestMapping(value = ["/delete"], method = [RequestMethod.POST])
    fun delete(@TokenId userId: Long, id: Long): ResponseResult<Boolean> {
        val backup = Backup()
        backup.id = id
        backup.userId = userId
        backupRepository.delete(backup)
        return ResponseUtil.success()
    }
}