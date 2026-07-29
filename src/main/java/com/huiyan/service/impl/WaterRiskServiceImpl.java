package com.huiyan.service.impl;

import com.huiyan.entity.WaterRisk;
import com.huiyan.mapper.WaterRiskMapper;
import com.huiyan.service.WaterRiskService;
import com.huiyan.vo.RiskPointVO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class WaterRiskServiceImpl implements WaterRiskService {

    @Resource
    private WaterRiskMapper waterRiskMapper;

    @Override
    public List<WaterRisk> findAll() {
        return waterRiskMapper.selectAll();
    }

    @Override
    public WaterRisk findById(Long id) {
        return waterRiskMapper.selectById(id);
    }

    @Override
    public int add(WaterRisk waterRisk) {
        return waterRiskMapper.insert(waterRisk);
    }

    @Override
    public int update(WaterRisk waterRisk) {
        return waterRiskMapper.update(waterRisk);
    }

    @Override
    public int delete(Long id) {
        return waterRiskMapper.deleteById(id);
    }

    @Override
    public List<RiskPointVO> selectRiskByLevel(Integer riskLevel) {
        return waterRiskMapper.selectRiskByLevel(riskLevel);
    }
}