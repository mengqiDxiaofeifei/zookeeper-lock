package com.sparksys.mall.admin.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sparksys.mall.admin.bo.AdminUserDetails;
import com.sparksys.mall.admin.constant.RedisConstant;
import com.sparksys.mall.admin.dao.UmsAdminDao;
import com.sparksys.mall.admin.entity.UmsAdmin;
import com.sparksys.mall.admin.entity.UmsAdminBean;
import com.sparksys.mall.admin.entity.UserToken;
import com.sparksys.mall.admin.service.IOauthService;
import com.sparksys.mall.admin.util.JwtTokenUtil;
import com.sparksys.mall.admin.util.RedisUtil;
import com.sparksys.mall.admin.vo.UmsAdminLoginVO;
import com.sparksys.mall.core.constant.CoreConstant;
import com.sparksys.mall.core.entity.RsaEntity;
import com.sparksys.mall.core.exception.ApiErrorException;
import com.sparksys.mall.core.exception.DefinedValidationException;
import com.sparksys.mall.core.utils.AesUtils;
import com.sparksys.mall.core.utils.Base64Utils;
import com.sparksys.mall.core.utils.ListUtils;
import com.sparksys.mall.core.utils.RsaUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletResponse;
import java.security.KeyPair;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 中文类名: 系统用户管理 服务实现类
 * 中文描述: 系统用户管理 服务实现类
 *
 * @author zhouxinlei
 * @date 2019-09-12 10:24:14
 */
@Service
@Slf4j
public class OauthServiceImpl implements IOauthService {

    @Autowired
    private UmsAdminDao adminDao;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public UserToken login(UmsAdminLoginVO umsAdminLoginVO,
                           HttpServletResponse response) throws Exception {
        String userName = umsAdminLoginVO.getUserName();
        String password = umsAdminLoginVO.getPassword();
        AdminUserDetails adminUserDetails = getAdminUserDetail(userName);
        log.info("前端加密密码 = {}", password);
        if (StringUtils.isEmpty(userName)) {
            throw new DefinedValidationException("用户名不能为空");
        }
        if (StringUtils.isEmpty(password)) {
            throw new DefinedValidationException("密码不能为空");
        }
        String token;

        if (ObjectUtils.isEmpty(adminUserDetails)) {
            throw new ApiErrorException("账户不存在");
        }
        UmsAdmin umsAdmin = adminUserDetails.getUmsAdmin();
        String privateKey = redisUtil.get(RedisConstant.RSA_PRIVATE_KEY);
        //解密加密数据
        String decryptInputPassword = RsaUtils.decrypt(password, privateKey);
        log.info("前端密码解密后的数据 = {}", decryptInputPassword);
        String encryptPassword = AesUtils.encryptAES(decryptInputPassword);
        String dataPassword = umsAdmin.getPassword();
        log.info("密码加密 = {}，数据库密码={}", encryptPassword, dataPassword);
        //数据库密码比对
        if (!StringUtils.equals(encryptPassword, dataPassword)) {
            throw new BadCredentialsException("密码不正确");
        }
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(adminUserDetails,
                null, adminUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        umsAdmin.setLoginTime(new Date());
        adminDao.updateById(umsAdmin);
        token = JwtTokenUtil.generateToken(adminUserDetails);
        UserToken userToken = UserToken.builder().token(token).
                tokenHead(CoreConstant.JwtTokenConstant.JWT_TOKEN_HEAD).
                expiration(CoreConstant.JwtTokenConstant.JWT_EXPIRATION).build();
        //设置header token
        setResponseHeader(response, userToken, umsAdmin);
        return userToken;
    }

    private void setResponseHeader(HttpServletResponse response, UserToken userToken, UmsAdmin umsAdmin) {
        String token = userToken.getToken();
        UmsAdminBean umsAdminBean = new UmsAdminBean();
        BeanUtils.copyProperties(umsAdmin, umsAdminBean);
        response.setHeader(CoreConstant.JwtTokenConstant.JWT_TOKEN_HEADER,
                CoreConstant.JwtTokenConstant.JWT_TOKEN_HEAD.concat(token));
        redisUtil.set(RedisConstant.LoginKeyConstant.USER + token, JSON.toJSONString(umsAdminBean));
        redisUtil.expire(RedisConstant.LoginKeyConstant.USER + token, userToken.getExpiration(), TimeUnit.SECONDS);
    }

    @Override
    public UmsAdmin getOauthUserInfo(String userName) {
        String adminKey = RedisConstant.UmsAdminConstant.UMS_ADMIN_KEY.concat(userName);
        UmsAdmin umsAdmin = redisUtil.getBean(adminKey, UmsAdmin.class);
        if (ObjectUtils.isEmpty(umsAdmin)) {
            QueryWrapper<UmsAdmin> wrapper = new QueryWrapper();
            wrapper.eq("username", userName);
            umsAdmin = adminDao.selectOne(wrapper);
            String roleKey = RedisConstant.UmsAdminConstant.UMS_ADMIN_ROLE_KEY.concat(umsAdmin.getId().toString());
            List<String> roleList = redisUtil.getBean(roleKey, List.class);
            if (ListUtils.isEmpty(roleList)) {
                roleList = adminDao.listOauthUserRoleCode(umsAdmin.getId());
                redisUtil.set(roleKey, roleList);
            }
            umsAdmin.setRoles(roleList);
            String permissionKey =
                    RedisConstant.UmsAdminConstant.UMS_ADMIN_PERMISSION_KEY.concat(umsAdmin.getId().toString());
            List<String> permissionList = redisUtil.getBean(roleKey, List.class);
            if (ListUtils.isEmpty(roleList)) {
                permissionList = listOauthPermission(umsAdmin.getId());
                redisUtil.set(permissionKey, permissionList);
            }
            umsAdmin.setPermissions(permissionList);
            redisUtil.set(adminKey, umsAdmin);
        }
        return umsAdmin;
    }

    @Override
    public AdminUserDetails getAdminUserDetail(String username) throws ApiErrorException {
        AdminUserDetails adminUserDetails = new AdminUserDetails();
        UmsAdmin admin = getOauthUserInfo(username);
        if (admin != null) {
            adminUserDetails.setUmsAdmin(admin);
            return adminUserDetails;
        }
        throw new ApiErrorException("账户不存在");
    }

    @Override
    public List<String> listOauthPermission(Long adminId) {
        return adminDao.listOauthPermission(adminId);
    }

    @Override
    public RsaEntity getPublicKey() {
        try {
            String publicKey = redisUtil.get(RedisConstant.RSA_PUBLIC_KEY);
            String privateKey = redisUtil.get(RedisConstant.RSA_PRIVATE_KEY);
            if (StringUtils.isAnyBlank(publicKey, privateKey)) {
                KeyPair keyPair = RsaUtils.getKeyPair();
                privateKey = new String(Base64Utils.encoder(keyPair.getPrivate().getEncoded()));
                publicKey = new String(Base64Utils.encoder(keyPair.getPublic().getEncoded()));
                redisUtil.set(RedisConstant.RSA_PUBLIC_KEY, publicKey);
                redisUtil.set(RedisConstant.RSA_PRIVATE_KEY, privateKey);
            }
            String password = "123456";
            String encryptPassword = RsaUtils.encrypt(password, publicKey);
            log.info("加密后的数据 = {}", encryptPassword);
            RsaEntity rsaEntity = new RsaEntity();
            rsaEntity.setPublicKey(publicKey);
            return rsaEntity;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
