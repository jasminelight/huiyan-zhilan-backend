package com.huiyan.service.impl;

import com.huiyan.entity.MonitorPoint;
import com.huiyan.mapper.MonitorPointMapper;
import com.huiyan.service.MonitorPointService;
import com.huiyan.vo.PointRainVO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MonitorPointServiceImpl implements MonitorPointService {

    @Resource
    private MonitorPointMapper monitorPointMapper;

    @Override
    public List<MonitorPoint> findAll() {
        return monitorPointMapper.selectAll();
    }

    @Override
    public MonitorPoint findById(Long id) {
        return monitorPointMapper.selectById(id);
    }

    @Override
    public int add(MonitorPoint monitorPoint) {
        return monitorPointMapper.insert(monitorPoint);
    }

    @Override
    public int update(MonitorPoint monitorPoint) {
        return monitorPointMapper.update(monitorPoint);
    }

    @Override
    public int delete(Long id) {
        return monitorPointMapper.deleteById(id);
    }

    @Override
    public List<PointRainVO> selectRainByPointId(Long pointId) {
        return monitorPointMapper.selectRainByPointId(pointId);
    }
}