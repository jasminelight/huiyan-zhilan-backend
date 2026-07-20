package com.huiyan.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Warning {
    private Long id;
    private String deviceId;
    private Double waterLevel;
    private Integer warningLevel;  // 1=警示，2=危险
    private String warningDesc;
    private Boolean isSent;
    private LocalDateTime createTime;
}