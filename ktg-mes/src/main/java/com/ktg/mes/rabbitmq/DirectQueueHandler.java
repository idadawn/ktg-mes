package com.ktg.mes.rabbitmq;

import com.ktg.mes.websocket.MesWebSocket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * 直连队列 处理器
 *
 * @author raft
 */
@Slf4j
@Component
@ConditionalOnProperty(prefix = "spring.rabbitmq", name = "enabled", havingValue = "true")
public class DirectQueueHandler {

    @RabbitListener(queues = "iot.original.mes")
    public void directHandlerRegister_origin(String message) {
        try {
//            log.info("Mes:消息队列，拉取原始数据：{}", message);
            MesWebSocket.pushData(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
