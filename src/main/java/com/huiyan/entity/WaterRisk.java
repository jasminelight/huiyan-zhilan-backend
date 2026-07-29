package com.huiyan.entity;

import java.time.LocalDateTime;

public class WaterRisk {
    private Long id;
    private Long pointId;
    private Double waterDepth;
    private Integer riskLevel;
    private LocalDateTime riskTime;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getPointId() { return pointId; }
    public void setPointId(Long pointId) { this.pointId = pointId; }
    public Double getWaterDepth() { return waterDepth; }
    public void setWaterDepth(Double waterDepth) { this.waterDepth = waterDepth; }
    public Integer getRiskLevel() { return riskLevel; }
    public void setRiskLevel(Integer riskLevel) { this.riskLevel = riskLevel; }
    public LocalDateTime getRiskTime() { return riskTime; }
    public void setRiskTime(LocalDateTime riskTime) { this.riskTime = riskTime; }
}