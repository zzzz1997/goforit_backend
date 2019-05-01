package com.zzapp.goforit.repository

import com.zzapp.goforit.entity.Backup
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * 备份仓库
 */
@Repository
interface BackupRepository: JpaRepository<Backup, Long> {

    /**
     * 查询用户所有备份
     *
     * @param userId 用户id
     *
     * @return 备份数据列表
     */
    fun findBackupsByUserId(userId: Long): List<Backup>

    /**
     *
     */
    fun findBackupsByIdAndUserId(id: Long, userId: Long): Backup?
}