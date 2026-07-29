package com.huiyan.vo;

import java.time.LocalDateTime;

public class RiskPointVO {
    private Long id;
    private Long pointId;
    private Double waterDepth;
    private Integer riskLevel;
    private LocalDateTime riskTime;
    private String areaName;
    private String pointName;
    private String deviceNo;

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
    public String getAreaName() { return areaName; }
    public void setAreaName(String areaName) { this.areaName = areaName; }
    public String getPointName() { return pointName; }
    public void setPointName(String pointName) { this.pointName = pointName; }
    public String getDeviceNo() { return deviceNo; }
    public void setDeviceNo(String deviceNo) { this.deviceNo = deviceNo; }
}