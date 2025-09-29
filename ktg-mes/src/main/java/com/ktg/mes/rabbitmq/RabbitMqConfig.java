package com.ktg.mes.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.DirectRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class RabbitMqConfig {

    /**
     * 设备事件上报队列
     */
    @Bean
    public Queue deviceEventQueue() {
        return new Queue("iot.original.mes");
    }

    /**
     * RabbitMQ默认的topic交换机
     * @return
     */
    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("amq.topic");
    }

    /**
     * 绑定队列和交换机
     * @param topicExchange
     * @param deviceEventQueue
     * @return
     */
    @Bean
    public Binding bindingDeviceEventQueue(TopicExchange topicExchange, Queue deviceEventQueue) {
        return BindingBuilder.bind(deviceEventQueue).to(topicExchange).with("iot.origin"); // 替换为实际的路由键
    }
}
