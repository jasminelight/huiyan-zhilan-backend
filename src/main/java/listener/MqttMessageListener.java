package com.huiyan.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huiyan.entity.MonitoringData;
import com.huiyan.entity.Warning;
import com.huiyan.mapper.MonitoringDataMapper;
import com.huiyan.mapper.WarningMapper;
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

    @Autowired
    private WarningMapper warningMapper;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @ServiceActivator(inputChannel = "mqttInputChannel")
    public void handleMessage(Message<?> message) throws MessagingException {
        try {
            String payload = message.getPayload().toString();
            System.out.println("收到消息: " + payload);

            @SuppressWarnings("unchecked")
            Map<String, Object> data = objectMapper.readValue(payload, Map.class);

            // 1. 解析并存入监测数据表
            MonitoringData entity = new MonitoringData();
            entity.setDeviceId((String) data.get("deviceId"));
            entity.setWaterLevel(toDouble(data.get("waterLevel")));
            entity.setRainfall(toDouble(data.get("rainfall")));
            entity.setTemperature(toDouble(data.get("temperature")));
            entity.setHumidity(toDouble(data.get("humidity")));
            entity.setImageUrl((String) data.get("imageUrl"));

            int rows = monitoringDataMapper.insert(entity);
            System.out.println("监测数据插入成功，影响行数: " + rows);

            // 2. 判断是否需要预警（水位 >= 15cm 触发警示，>= 20cm 触发危险）
            Double waterLevel = entity.getWaterLevel();
            if (waterLevel != null && waterLevel >= 15) {
                Warning warning = new Warning();
                warning.setDeviceId(entity.getDeviceId());
                warning.setWaterLevel(waterLevel);

                if (waterLevel >= 20) {
                    warning.setWarningLevel(2);
                    warning.setWarningDesc("危险！水位已超过20cm，建议立即封路");
                } else {
                    warning.setWarningLevel(1);
                    warning.setWarningDesc("警示！水位已超过15cm，请注意观察");
                }
                warning.setIsSent(false);

                int warnRows = warningMapper.insert(warning);
                System.out.println("预警记录插入成功，影响行数: " + warnRows);
            } else {
                System.out.println("水位正常，无需预警");
            }

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