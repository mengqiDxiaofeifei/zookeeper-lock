package com.sparksys.mall.admin.controller;

import com.sparksys.mall.admin.entity.UmsAdminBean;
import com.sparksys.mall.admin.entity.UserToken;
import com.sparksys.mall.admin.service.IOauthService;
import com.sparksys.mall.admin.vo.UmsAdminLoginVO;
import com.sparksys.mall.core.annotation.ResponseResult;
import com.sparksys.mall.core.controller.CommonController;
import com.sparksys.mall.core.entity.RsaEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 中文类名：授权认证controller 概要说明：授权认证controller
 *
 * @author zhouxinlei
 * @date 2019/5/25 0025
 */
@Slf4j
@RestController
@ResponseResult
@RequestMapping("/oauth/")
@Api(tags = "OauthController", description = "后台登录管理")
public class OauthController extends CommonController {

    /**
     *
     */
    private static final long serialVersionUID = 807150539758056074L;
    private final IOauthService oauthService;

    @Autowired
    public OauthController(IOauthService oauthService) {
        this.oauthService = oauthService;
    }

    @ApiOperation("获取公钥")
    @GetMapping("/getPublicKey")
    public RsaEntity getPublicKey() {
        return oauthService.getPublicKey();
    }

    @ApiOperation("系统登录")
    @PostMapping("login")
    public UserToken login(@RequestBody UmsAdminLoginVO umsAdminLoginVO) throws Exception {
        return oauthService.login(umsAdminLoginVO, getResponse());
    }

    @ApiOperation("获取登录用户信息")
    @GetMapping("getOauthInfo")
    public UmsAdminBean getUserInfo(UmsAdminBean umsAdminBean) {
        log.info("当前登录用户 umsAdmin={}", umsAdminBean);
        return umsAdminBean;
    }

}
