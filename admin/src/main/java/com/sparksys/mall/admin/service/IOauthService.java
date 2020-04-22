package com.sparksys.mall.admin.service;

import com.sparksys.mall.admin.bo.AdminUserDetails;
import com.sparksys.mall.admin.entity.UmsAdmin;
import com.sparksys.mall.admin.entity.UserToken;
import com.sparksys.mall.admin.vo.UmsAdminLoginVO;
import com.sparksys.mall.core.entity.RsaEntity;
import com.sparksys.mall.core.exception.ApiErrorException;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 中文类名: 系统用户管理 服务类
 * 中文描述: 系统用户管理 服务类
 *
 * @author zhouxinlei
 * @date 2019-09-12 10:24:14
 */
public interface IOauthService {

    /**
     * 登录
     *
     * @param umsAdminLoginVO
     * @param response
     * @return java.lang.String
     * @throws Exception
     * @author zhouxinlei
     * @date 2019/5/26 0026
     */
    UserToken login(UmsAdminLoginVO umsAdminLoginVO, HttpServletResponse response) throws Exception;

    /**
     * 根据用户名获取用户信息
     *
     * @param username
     * @return ApiResult
     * @author zhouxinlei
     * @date 2019/5/26 0026
     */
    UmsAdmin getOauthUserInfo(String username);

    /**
     * 根据用户名获取用户信息
     *
     * @param userName
     * @return AdminUserDetails
     * @throws
     * @author zhouxinlei
     * @date 2019-10-15 11:59:55
     */
    AdminUserDetails getAdminUserDetail(String userName) throws ApiErrorException;

    /**
     * 获取用户权限
     *
     * @param adminId
     * @return List<String>
     * @author zhouxinlei
     * @date 2019-09-11 17:46:56
     */
    List<String> listOauthPermission(Long adminId);

    /**
     * 获取公钥
     *
     * @param
     * @return RsaEntity
     * @throws
     * @author zhouxinlei
     * @date 2019-10-04 13:10:12
     */
    RsaEntity getPublicKey();

}
