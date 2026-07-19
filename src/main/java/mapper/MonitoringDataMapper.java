package com.huiyan.mapper;

import com.huiyan.entity.MonitoringData;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MonitoringDataMapper {

    // 插入数据
    @Insert("INSERT INTO t_monitoring_data(device_id, water_level, rainfall, temperature, humidity, image_url) " +
            "VALUES(#{deviceId}, #{waterLevel}, #{rainfall}, #{temperature}, #{humidity}, #{imageUrl})")
    int insert(MonitoringData data);

    // 查询最新一条数据
    @Select("SELECT * FROM t_monitoring_data ORDER BY create_time DESC LIMIT 1")
    MonitoringData selectLatest();

    // 查询所有数据（按时间倒序）
    @Select("SELECT * FROM t_monitoring_data ORDER BY create_time DESC")
    List<MonitoringData> selectAll();
}