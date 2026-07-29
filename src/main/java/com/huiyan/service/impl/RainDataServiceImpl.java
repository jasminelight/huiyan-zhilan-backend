package com.huiyan.service.impl;

import com.huiyan.entity.RainData;
import com.huiyan.mapper.RainDataMapper;
import com.huiyan.service.RainDataService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RainDataServiceImpl implements RainDataService {

    @Resource
    private RainDataMapper rainDataMapper;

    @Override
    public List<RainData> findAll() {
        return rainDataMapper.selectAll();
    }

    @Override
    public RainData findById(Long id) {
        return rainDataMapper.selectById(id);
    }

    @Override
    public int add(RainData rainData) {
        return rainDataMapper.insert(rainData);
    }

    @Override
    public int update(RainData rainData) {
        return rainDataMapper.update(rainData);
    }

    @Override
    public int delete(Long id) {
        return rainDataMapper.deleteById(id);
    }

    @Override
    public Double sumRainByPointId(Long pointId) {
        return rainDataMapper.sumRainByPointId(pointId);
    }
}