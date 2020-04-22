package com.sparksys.mall.admin.controller;


import com.sparksys.mall.admin.entity.UmsAdminBean;
import com.sparksys.mall.admin.service.ISecKillOrderService;
import com.sparksys.mall.core.result.ApiResult;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 中文类名：秒杀 前端控制器
 * 中文描述：秒杀 前端控制器
 *
 * @author zhouxinlei
 * @date 2019-12-13 10:12:02
 */
@RestController
@RequestMapping("/secKill")
public class SecKillController implements InitializingBean {

    private final ISecKillOrderService secKillOrderService;

    @Autowired
    public SecKillController(ISecKillOrderService secKillOrderService) {
        this.secKillOrderService = secKillOrderService;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        secKillOrderService.listInitSecKillProduct();
    }

    /**
     * 开始秒杀订单（redis分布式锁）
     *
     * @param umsAdminBean
     * @param secKillId
     * @return boolean
     * @throw
     * @date 2019-12-13 10:50:35
     */
    @GetMapping("/secKillOrder")
    public ApiResult createSecKillOrder(UmsAdminBean umsAdminBean, Long secKillId) {
        return secKillOrderService.startSecKillOperation(umsAdminBean, secKillId);
    }
}
