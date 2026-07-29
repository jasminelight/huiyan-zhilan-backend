package com.huiyan.mapper;

import com.huiyan.entity.WaterRisk;
import com.huiyan.vo.RiskPointVO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface WaterRiskMapper {
    List<WaterRisk> selectAll();
    WaterRisk selectById(Long id);
    int insert(WaterRisk waterRisk);
    int update(WaterRisk waterRisk);
    int deleteById(Long id);
    List<RiskPointVO> selectRiskByLevel(Integer riskLevel);
}