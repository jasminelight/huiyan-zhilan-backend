package com.huiyan.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class MonitoringData {
    private Long id;
    private String deviceId;
    private Double waterLevel;
    private Double rainfall;
    private Double temperature;
    private Double humidity;
    private String imageUrl;
    private LocalDateTime createTime;
}