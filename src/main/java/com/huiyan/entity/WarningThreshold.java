package com.huiyan.entity;

import lombok.Data;
import java.time.LocalDateTime;

/** A configurable threshold for one warning type and level. */
@Data
public class WarningThreshold {
    private Long id;
    private String name;
    private String warningType;
    private Integer warningLevel;
    private Double threshold;
    private String unit;
    private Boolean enabled;
    private String pushChannels;
    private String updatedBy;
    private LocalDateTime updateTime;
}
