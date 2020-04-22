package com.sparksys.mall.admin.service;

import com.sparksys.mall.admin.entity.SecKillOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sparksys.mall.admin.entity.SecKillProduct;
import com.sparksys.mall.admin.entity.UmsAdminBean;
import com.sparksys.mall.core.exception.ApiErrorException;
import com.sparksys.mall.core.result.ApiResult;

import java.util.List;

/**
 * 中文类名：秒杀订单明细 服务类
 * 中文描述：秒杀订单明细 服务类
 *
 * @author zhouxinlei
 * @date 2019-12-13 10:01:23
 */
public interface ISecKillOrderService extends IService<SecKillOrder> {

    /**
     * 开始秒杀商品
     *
     * @param umsAdminBean
     * @param secKillId
     * @return ApiResult
     * @throws
     * @author zhouxinlei
     * @date 2019-12-13 10:50:09
     */
    ApiResult startSecKillOperation(UmsAdminBean umsAdminBean, Long secKillId);

    /**
     * 查询秒杀商品
     *
     * @return List<SecKillProduct>
     * @throws
     * @author zhouxinlei
     * @date 2019-12-13 10:50:09
     */
    void listInitSecKillProduct();

    /**
     * 减库存 下订单 创建秒杀订单
     *
     * @param umsAdminBean
     * @param secKillProduct
     * @return ApiResult
     * @throws ApiErrorException
     * @author zhouxinlei
     * @date 2019-12-13 10:50:09
     */
    void createSecKillOrder(UmsAdminBean umsAdminBean, SecKillProduct secKillProduct) throws ApiErrorException;
}
