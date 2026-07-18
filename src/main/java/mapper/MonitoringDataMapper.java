package com.huiyan.mapper;

import com.huiyan.entity.MonitoringData;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MonitoringDataMapper {

    @Insert("INSERT INTO t_monitoring_data(device_id, water_level, rainfall, temperature, humidity, image_url) " +
            "VALUES(#{deviceId}, #{waterLevel}, #{rainfall}, #{temperature}, #{humidity}, #{imageUrl})")
    int insert(MonitoringData data);
}