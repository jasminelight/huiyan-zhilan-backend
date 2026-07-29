package com.huiyan.service;

import com.huiyan.entity.RainData;
import java.util.List;

public interface RainDataService {
    List<RainData> findAll();
    RainData findById(Long id);
    int add(RainData rainData);
    int update(RainData rainData);
    int delete(Long id);
    Double sumRainByPointId(Long pointId);
}