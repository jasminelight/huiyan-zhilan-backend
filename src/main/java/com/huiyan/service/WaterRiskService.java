package com.huiyan.service;

import com.huiyan.entity.WaterRisk;
import com.huiyan.vo.RiskPointVO;
import java.util.List;

public interface WaterRiskService {
    List<WaterRisk> findAll();
    WaterRisk findById(Long id);
    int add(WaterRisk waterRisk);
    int update(WaterRisk waterRisk);
    int delete(Long id);
    List<RiskPointVO> selectRiskByLevel(Integer riskLevel);
}
