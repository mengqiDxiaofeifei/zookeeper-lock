package com.sparksys.mall.admin.constant;


/**
 * 中文类名：redis属性常量
 * 概要说明：redis属性常量
 *
 * @author zhouxinlei
 * @date 2019/5/29 0029
 */
public class RedisConstant {

    public static final String RSA_PRIVATE_KEY = "rsa_private_key";

    public static final String RSA_PUBLIC_KEY = "rsa_public_key";

    /**
     * 中文类名: redis登录业务实体缓存key值常量类
     * 中文描述: redis登录业务实体缓存key值常量类
     *
     * @author zhouxinlei
     * @date 2019-09-20 10:34:41
     */
    public static final class LoginKeyConstant {
        /**
         * 系统用户
         */
        public static final String USER = "tuser:";
    }

    /**
     * 中文类名: 用户缓存前缀
     * 中文描述: 用户缓存前缀
     *
     * @author zhouxinlei
     * @date 2019-10-11 14:15:55
     */
    public static final class UmsAdminConstant {
        /**
         * 用户缓存前缀
         */
        public static final String UMS_ADMIN_KEY = "ums_admin:";

        /**
         * 用户角色缓存
         */
        public static final String UMS_ADMIN_ROLE_KEY = "ums_admin_role:";

        /**
         * 用户权限缓存
         */
        public static final String UMS_ADMIN_PERMISSION_KEY = "ums_admin_permission:";
    }

    /**
     * 中文类名：秒杀商品key
     * 中文描述：
     *
     * @author zhouxinlei
     * @date 2019-12-13 14:36:53
     */
    public static final class SecKillConstant {

        public static final String SEC_KILL_LOCK_KEY = "sec_kill_lock:";
        /**
         * 秒杀商品库存前缀
         */
        public static final String STOCK_COUNT_KEY = "stock_count:";

    }

    public static void main(String[] args) {
        Long adminId = 0L;
        Long permissionId = 12L;
        String str = null;
        str = String.format(RedisConstant.UmsAdminConstant.UMS_ADMIN_PERMISSION_KEY, adminId, permissionId);
        System.out.println(str);
    }
}
