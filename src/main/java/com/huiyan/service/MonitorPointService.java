package com.huiyan.service;

import com.huiyan.entity.MonitorPoint;
import com.huiyan.vo.PointRainVO;
import java.util.List;

public interface MonitorPointService {
    List<MonitorPoint> findAll();
    MonitorPoint findById(Long id);
    int add(MonitorPoint monitorPoint);
    int update(MonitorPoint monitorPoint);
    int delete(Long id);
    List<PointRainVO> selectRainByPointId(Long pointId);
}