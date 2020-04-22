package com.sparksys.mall.admin.sender;

import com.alibaba.fastjson.JSON;
import com.sparksys.mall.admin.constant.RabbitConstant;
import com.sparksys.mall.admin.entity.SecKillMessage;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 中文类名：Topic模式发送类
 * 中文描述：Topic模式发送类
 *
 * @author zhouxinlei
 * @time 2019-07-28 05:13
 */
@Component
public class TopicSender {

    private final AmqpTemplate rabbitTemplate;

    @Autowired
    public TopicSender(AmqpTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * 第一个参数：TopicExchange名字
     * 第二个参数：Route-Key
     * 第三个参数：要发送的内容
     *
     * @param secKillMessage
     * @return void
     * @throws
     * @author zhouxinlei
     * @date 2019-09-27 17:06:58
     */
    public void sendMessage(SecKillMessage secKillMessage) {
        this.rabbitTemplate.convertAndSend(RabbitConstant.TOPIC_EXCHANGE, "sparksys.seckill", JSON.toJSONString(secKillMessage));
    }
}
