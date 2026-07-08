package com.huiyan.listener;

import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

@Component
public class MqttMessageListener {

    @ServiceActivator(inputChannel = "mqttInputChannel")
    public void handleMessage(Message<?> message) throws MessagingException {
        String payload = message.getPayload().toString();
        System.out.println("========== 收到 MQTT 消息 ==========");
        System.out.println("主题: " + message.getHeaders().get("mqtt_receivedTopic"));
        System.out.println("内容: " + payload);
        System.out.println("====================================");
    }
}