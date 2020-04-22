package com.sparksys.mall.admin.receiver;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import com.sparksys.mall.admin.constant.RabbitConstant;
import com.sparksys.mall.admin.dao.SecKillProductDao;
import com.sparksys.mall.admin.entity.SecKillMessage;
import com.sparksys.mall.admin.entity.SecKillProduct;
import com.sparksys.mall.admin.entity.UmsAdminBean;
import com.sparksys.mall.admin.enums.RabbitMqOperationEnum;
import com.sparksys.mall.admin.service.ISecKillOrderService;
import com.sparksys.mall.core.exception.ApiErrorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 中文类名：rabbitmq秒杀接收类
 * 中文描述：rabbitmq秒杀接收类
 *
 * @author zhouxinlei
 * @time 2019-07-28 05:14
 */
@Component
@Slf4j
public class TopicReceiver {

    private final SecKillProductDao secKillProductDao;
    private final ISecKillOrderService secKillOrderService;

    @Autowired
    public TopicReceiver(SecKillProductDao secKillProductDao
            , ISecKillOrderService secKillOrderService) {
        this.secKillProductDao = secKillProductDao;
        this.secKillOrderService = secKillOrderService;
    }

    @RabbitListener(queues = RabbitConstant.TOPIC_QUEUE)
    public void receiveTopicMessage(Message message, Channel channel) {
        RabbitMqOperationEnum operationEnum = RabbitMqOperationEnum.ACCEPT;
        log.info("【receiveTopic1监听到消息】= {}", message);
        try {
            SecKillMessage secKillMessage = JSON.parseObject(message.getBody(), SecKillMessage.class);
            UmsAdminBean umsAdminBean = secKillMessage.getUmsAdminBean();
            SecKillProduct secKillProduct = secKillProductDao.selectById(secKillMessage.getSecKillId());
            int stockCount = secKillProduct.getStockCount();
            if (stockCount <= 0) {
                return;
            }
            secKillOrderService.createSecKillOrder(umsAdminBean, secKillProduct);
        } catch (ApiErrorException e) {
            e.printStackTrace();
            operationEnum = RabbitMqOperationEnum.RETRY;
        } finally {
            try {
                if (operationEnum.equals(RabbitMqOperationEnum.ACCEPT)) {
                    channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                } else if (operationEnum.equals(RabbitMqOperationEnum.RETRY)) {
                    channel.basicNack(message.getMessageProperties().getDeliveryTag(), true, true);
                } else if (operationEnum.equals(RabbitMqOperationEnum.REJECT)) {
                    channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}

