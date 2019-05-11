package com.zzapp.goforit.util

/**
 * 随机工具
 */
object RandomUtilObject {
    /**
     * 获取随机头像
     *
     * @return 头像链接
     */
    fun getRandomAvatar(): String {
        return "https://zzzz1997.oss-cn-beijing.aliyuncs.com/assets/avatar/animal${(Math.random() * 9 + 1).toInt()}.png"
    }
}