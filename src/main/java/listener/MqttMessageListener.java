package com.huiyan.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huiyan.entity.MonitoringData;
import com.huiyan.mapper.MonitoringDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MqttMessageListener {

    @Autowired
    private MonitoringDataMapper monitoringDataMapper;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @ServiceActivator(inputChannel = "mqttInputChannel")
    public void handleMessage(Message<?> message) throws MessagingException {
        try {
            String payload = message.getPayload().toString();
            System.out.println("收到消息: " + payload);

            // 解析 JSON
            @SuppressWarnings("unchecked")
            Map<String, Object> data = objectMapper.readValue(payload, Map.class);

            // 封装成实体对象
            MonitoringData entity = new MonitoringData();
            entity.setDeviceId((String) data.get("deviceId"));
            entity.setWaterLevel(toDouble(data.get("waterLevel")));
            entity.setRainfall(toDouble(data.get("rainfall")));
            entity.setTemperature(toDouble(data.get("temperature")));
            entity.setHumidity(toDouble(data.get("humidity")));
            entity.setImageUrl((String) data.get("imageUrl"));

            // 存数据库
            int rows = monitoringDataMapper.insert(entity);
            System.out.println("插入成功，影响行数: " + rows);

        } catch (Exception e) {
            System.err.println("处理消息失败: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private Double toDouble(Object value) {
        if (value == null) return null;
        if (value instanceof Number) return ((Number) value).doubleValue();
        try {
            return Double.valueOf(value.toString());
        } catch (NumberFormatException e) {
            return null;
        }
    }
}