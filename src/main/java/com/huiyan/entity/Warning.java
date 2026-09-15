package com.huiyan.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Warning {
    private Long id;
    private String deviceId;
    private Long monitoringDataId;
    private Double waterLevel;
    private Double rainfall;
    private String warningType;
    private Integer warningLevel;
    private String warningDesc;
    private Boolean isSent;
    private String status;
    private String pushChannels;
    private LocalDateTime pushedTime;
    private String handledBy;
    private String handleRemark;
    private LocalDateTime handledTime;
    private LocalDateTime createTime;
}
