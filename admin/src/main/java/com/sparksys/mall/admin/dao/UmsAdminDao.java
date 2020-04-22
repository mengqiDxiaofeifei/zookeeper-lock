package com.sparksys.mall.admin.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sparksys.mall.admin.entity.UmsAdmin;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 中文类名：后台用户表Dao接口
 * 概要说明：后台用户表Dao接口
 *
 * @author zhouxinlei
 * @date 2019/5/26 0026
 */
@Repository
public interface UmsAdminDao extends BaseMapper<UmsAdmin> {

    /**
     * 获取用户权限
     *
     * @param adminId
     * @return List<UmsPermission>
     * @author zhouxinlei
     * @date 2019-09-12 11:44:02
     */
    @Select("select " +
            " distinct(up.permission_value) permissionValue" +
            "        from ums_permission up " +
            "        inner join ums_role_permission urp on urp.permission_id = up.id" +
            "        inner join ums_admin_role uar on urp.role_id = uar.role_id" +
            "        where uar.admin_id = #{adminId}")
    List<String> listOauthPermission(Long adminId);

    /**
     * 获取用户角色权限
     *
     * @param adminId
     * @return List<String>
     * @author zhouxinlei
     * @date 2019-09-25 16:09:12
     */
    @Select("select" +
            "   distinct(ur.role_code) roleCode" +
            "   from ums_admin_role uar" +
            "   inner join ums_role ur on uar.role_id = ur.id" +
            "   where uar.admin_id = #{adminId}")
    List<String> listOauthUserRoleCode(Long adminId);

    /**
     * 获取用户名
     *
     * @param adminId
     * @return String
     * @throws
     * @author zhouxinlei
     * @date 2019-10-11 14:11:55
     */
    @Select(" select username from ums_admin where id = #{adminId}")
    String getUserName(Long adminId);

}
