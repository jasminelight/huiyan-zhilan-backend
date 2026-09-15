package com.huiyan.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huiyan.entity.MonitoringData;
import com.huiyan.mapper.MonitoringDataMapper;
import com.huiyan.service.WarningService;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MqttMessageListener {
    private final MonitoringDataMapper monitoringDataMapper;
    private final WarningService warningService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public MqttMessageListener(MonitoringDataMapper monitoringDataMapper, WarningService warningService) {
        this.monitoringDataMapper = monitoringDataMapper;
        this.warningService = warningService;
    }

    @ServiceActivator(inputChannel = "mqttInputChannel")
    public void handleMessage(Message<?> message) throws MessagingException {
        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> data = objectMapper.readValue(message.getPayload().toString(), Map.class);
            MonitoringData entity = new MonitoringData();
            entity.setDeviceId(stringValue(data.get("deviceId")));
            entity.setWaterLevel(toDouble(data.get("waterLevel")));
            entity.setRainfall(toDouble(data.get("rainfall")));
            entity.setTemperature(toDouble(data.get("temperature")));
            entity.setHumidity(toDouble(data.get("humidity")));
            entity.setImageUrl(stringValue(data.get("imageUrl")));
            monitoringDataMapper.insert(entity);
            warningService.evaluate(entity);
        } catch (Exception e) {
            throw new MessagingException(message, "处理监测消息失败", e);
        }
    }

    private String stringValue(Object value) { return value == null ? null : value.toString(); }

    private Double toDouble(Object value) {
        if (value == null) return null;
        if (value instanceof Number number) return number.doubleValue();
        try { return Double.valueOf(value.toString()); }
        catch (NumberFormatException ignored) { return null; }
    }
}
