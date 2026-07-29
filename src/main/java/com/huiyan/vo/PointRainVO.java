package com.huiyan.vo;

import java.time.LocalDateTime;

public class PointRainVO {
    private Long id;
    private String areaName;
    private String pointName;
    private String deviceNo;
    private Integer status;
    private Double longitude;
    private Double latitude;
    private LocalDateTime createTime;
    private Long rainId;
    private Double rainAmount;
    private LocalDateTime recordTime;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getAreaName() { return areaName; }
    public void setAreaName(String areaName) { this.areaName = areaName; }
    public String getPointName() { return pointName; }
    public void setPointName(String pointName) { this.pointName = pointName; }
    public String getDeviceNo() { return deviceNo; }
    public void setDeviceNo(String deviceNo) { this.deviceNo = deviceNo; }
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }
    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }
    public LocalDateTime getCreateTime() { return createTime; }
    public void setCreateTime(LocalDateTime createTime) { this.createTime = createTime; }
    public Long getRainId() { return rainId; }
    public void setRainId(Long rainId) { this.rainId = rainId; }
    public Double getRainAmount() { return rainAmount; }
    public void setRainAmount(Double rainAmount) { this.rainAmount = rainAmount; }
    public LocalDateTime getRecordTime() { return recordTime; }
    public void setRecordTime(LocalDateTime recordTime) { this.recordTime = recordTime; }
}