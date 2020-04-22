package com.sparksys.mall.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sparksys.mall.admin.constant.RedisConstant;
import com.sparksys.mall.admin.dao.SecKillOrderDao;
import com.sparksys.mall.admin.dao.SecKillProductDao;
import com.sparksys.mall.admin.entity.SecKillMessage;
import com.sparksys.mall.admin.entity.SecKillOrder;
import com.sparksys.mall.admin.entity.SecKillProduct;
import com.sparksys.mall.admin.entity.UmsAdminBean;
import com.sparksys.mall.admin.sender.TopicSender;
import com.sparksys.mall.admin.service.ISecKillOrderService;
import com.sparksys.mall.admin.util.RedisUtil;
import com.sparksys.mall.admin.util.RedissonUtil;
import com.sparksys.mall.core.exception.ApiErrorException;
import com.sparksys.mall.core.result.ApiResult;
import com.sparksys.mall.core.result.ErrorResult;
import com.sparksys.mall.core.utils.ListUtils;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RMapCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 中文类名：秒杀订单明细 服务实现类
 * 中文描述：秒杀订单明细 服务实现类
 *
 * @author zhouxinlei
 * @date 2019-12-13 10:08:17
 */
@Service
@Slf4j
@DependsOn("redissonUtil")
public class SecKillOrderServiceImpl extends ServiceImpl<SecKillOrderDao, SecKillOrder> implements ISecKillOrderService {

    private final SecKillProductDao secKillProductDao;
    private final RedisUtil redisUtil;
    private TopicSender topicSender;

    private RMapCache<Long, Boolean> localCachedMap = RedissonUtil.getRMapCache("sparksys-kill_count");

    @Autowired
    public SecKillOrderServiceImpl(SecKillProductDao secKillProductDao,
                                   RedisUtil redisUtil,
                                   TopicSender topicSender) {
        this.secKillProductDao = secKillProductDao;
        this.redisUtil = redisUtil;
        this.topicSender = topicSender;
    }

    @Override
    public ApiResult startSecKillOperation(UmsAdminBean umsAdminBean, Long secKillId) {
        boolean result = false;
        final long killId = secKillId;
        boolean over = localCachedMap.get(secKillId);
        if (over) {
            return ApiResult.failed(ErrorResult.END_KILL);
        }
        try {
            /**
             *  尝试获取锁，最多等待3秒，
             * 上锁以后20秒自动解锁（实际项目中推荐这;/ 种，以防出现死锁）、
             * 这里根据预估秒杀人数，设定自动释放锁时间.
             */
            result = RedissonUtil.tryLock(RedisConstant.SecKillConstant.SEC_KILL_LOCK_KEY + killId
                    , TimeUnit.SECONDS, 3, 20);
            if (result) {
                //预减库存
                long stockCount = redisUtil.decrement(RedisConstant.SecKillConstant.STOCK_COUNT_KEY + +killId
                        , 1);
                if (stockCount >= 0) {
                    SecKillMessage secKillMessage = new SecKillMessage();
                    secKillMessage.setUmsAdminBean(umsAdminBean);
                    secKillMessage.setSecKillId(killId);
                    topicSender.sendMessage(secKillMessage);
                } else {
                    localCachedMap.put(secKillId, true);
                    redisUtil.set(RedisConstant.SecKillConstant.STOCK_COUNT_KEY + killId, 0);
                    return ApiResult.failed(ErrorResult.END_KILL);
                }
            } else {
                return ApiResult.failed(ErrorResult.MUCH_KILL);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResult.success(ErrorResult.FAILED);
        } finally {
            if (result) {
                RedissonUtil.unlock(RedisConstant.SecKillConstant.SEC_KILL_LOCK_KEY + killId);
            }
        }
        return ApiResult.success(ErrorResult.SUCCESS_KILL);
    }

    @Override
    public void listInitSecKillProduct() {
        List<SecKillProduct> killProductList = secKillProductDao.selectList(null);
        if (ListUtils.isEmpty(killProductList)) {
            return;
        }
        for (SecKillProduct secKillProduct : killProductList) {
            redisUtil.set(RedisConstant.SecKillConstant.STOCK_COUNT_KEY + secKillProduct.getSecKillId()
                    , secKillProduct.getStockCount());
            localCachedMap.put(secKillProduct.getSecKillId(), false);
        }
    }

    @Override
    public void createSecKillOrder(UmsAdminBean umsAdminBean, SecKillProduct secKillProduct) throws ApiErrorException {
        Long secKillId = secKillProduct.getSecKillId();
        int updateCount = secKillProductDao.reduceStock(secKillProduct);
        if (updateCount == 1) {
            SecKillOrder secKillOrder = new SecKillOrder();
            secKillOrder.setSecKillId(secKillId);
            secKillOrder.setUserId(umsAdminBean.getId());
            secKillOrder.setCreateTime(new Date());
            secKillOrder.setState(0);
            save(secKillOrder);
        } else {
            throw new ApiErrorException("操作异常");
        }

    }
}
