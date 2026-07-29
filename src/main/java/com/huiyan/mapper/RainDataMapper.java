package com.huiyan.mapper;

import com.huiyan.entity.RainData;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface RainDataMapper {
    List<RainData> selectAll();
    RainData selectById(Long id);
    int insert(RainData rainData);
    int update(RainData rainData);
    int deleteById(Long id);
    Double sumRainByPointId(Long pointId);
}