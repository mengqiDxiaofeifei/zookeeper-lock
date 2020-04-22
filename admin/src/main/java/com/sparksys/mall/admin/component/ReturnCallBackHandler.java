package com.sparksys.mall.admin.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * 中文类名: RabbitMq交换器发送到对应队列失败时回调处理类
 * 中文描述: 如果消息从交换器发送到对应队列失败时触发（比如根据发送消息时指定的routingKey找不到队列时会触发）
 *
 * @author zhouxinlei
 * @date 2019-12-13 20:32:44
 */
@Slf4j
public class ReturnCallBackHandler implements RabbitTemplate.ReturnCallback {
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        log.info("消息主体 message：{}", message);
        log.info("应答码 replyCode: ：{}", replyCode);
        log.info("原因描述 replyText：{}", replyText);
        log.info("交换机 exchange：{}", exchange);
        log.info("消息使用的路由键 routingKey：{}", routingKey);
    }
}
