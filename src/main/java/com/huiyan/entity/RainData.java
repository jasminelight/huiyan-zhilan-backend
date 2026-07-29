package com.huiyan.entity;

import java.time.LocalDateTime;

public class RainData {
    private Long id;
    private Long pointId;
    private Double rainAmount;
    private LocalDateTime recordTime;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getPointId() { return pointId; }
    public void setPointId(Long pointId) { this.pointId = pointId; }
    public Double getRainAmount() { return rainAmount; }
    public void setRainAmount(Double rainAmount) { this.rainAmount = rainAmount; }
    public LocalDateTime getRecordTime() { return recordTime; }
    public void setRecordTime(LocalDateTime recordTime) { this.recordTime = recordTime; }
}