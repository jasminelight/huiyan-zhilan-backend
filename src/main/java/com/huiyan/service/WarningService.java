package com.huiyan.service;

import com.huiyan.entity.MonitoringData;
import com.huiyan.entity.Warning;
import com.huiyan.entity.WarningThreshold;
import java.util.List;

public interface WarningService {
    List<Warning> findAll(String status);
    Warning findById(Long id);
    List<Warning> evaluate(MonitoringData data);
    void push(Long id, String channels);
    void archive(Long id, String handledBy, String remark);
    List<WarningThreshold> findThresholds();
    WarningThreshold addThreshold(WarningThreshold threshold);
    WarningThreshold updateThreshold(WarningThreshold threshold);
    void deleteThreshold(Long id);
}
