package com.huiyan.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RainData {

    private Long id;

    /**
     * 监测点/设备ID
     * 例如：pi-water-01
     */
    private String pointId;

    /**
     * 雨量
     */
    private Double rainAmount;

    /**
     * 记录时间
     */
    private LocalDateTime recordTime;
}