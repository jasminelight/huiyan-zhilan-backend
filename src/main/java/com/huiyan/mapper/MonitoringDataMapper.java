package com.huiyan.mapper;

import com.huiyan.entity.MonitoringData;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Options;

import java.util.List;
import java.util.Map;

@Mapper
public interface MonitoringDataMapper {

    // 插入数据
    @Insert("INSERT INTO t_monitoring_data(device_id, water_level, rainfall, temperature, humidity, image_url) " +
            "VALUES(#{deviceId}, #{waterLevel}, #{rainfall}, #{temperature}, #{humidity}, #{imageUrl})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(MonitoringData data);

    // 查询最新一条数据
    @Select("SELECT * FROM t_monitoring_data ORDER BY create_time DESC LIMIT 1")
    MonitoringData selectLatest();

    // 查询所有数据（按时间倒序）
    @Select("SELECT * FROM t_monitoring_data ORDER BY create_time DESC")
    List<MonitoringData> selectAll();

    // 分页查询（配合 PageHelper 使用）
    @Select("SELECT * FROM t_monitoring_data ORDER BY create_time DESC")
    List<MonitoringData> selectPage();

    /**
     * 查询指定设备的小时雨量
     */
    @Select("""
            SELECT
                DATE_FORMAT(create_time, '%Y-%m-%d %H:00:00') AS time,
                SUM(rainfall) AS rainfall
            FROM t_monitoring_data
            WHERE device_id = #{deviceId}
              AND rainfall IS NOT NULL
            GROUP BY DATE_FORMAT(create_time, '%Y-%m-%d %H:00:00')
            ORDER BY time ASC
            """)
    List<Map<String, Object>> selectHourlyRainfall(String deviceId);

    /**
     * 查询指定设备的日雨量
     */
    @Select("""
            SELECT
                DATE_FORMAT(create_time, '%Y-%m-%d') AS time,
                SUM(rainfall) AS rainfall
            FROM t_monitoring_data
            WHERE device_id = #{deviceId}
              AND rainfall IS NOT NULL
            GROUP BY DATE_FORMAT(create_time, '%Y-%m-%d')
            ORDER BY time ASC
            """)
    List<Map<String, Object>> selectDailyRainfall(String deviceId);

    /**
     * 查询指定设备的水位变化趋势
     */
    @Select("""
            SELECT
                create_time AS time,
                water_level AS waterLevel
            FROM t_monitoring_data
            WHERE device_id = #{deviceId}
              AND water_level IS NOT NULL
            ORDER BY create_time ASC
            """)
    List<Map<String, Object>> selectWaterLevelTrend(String deviceId);
}