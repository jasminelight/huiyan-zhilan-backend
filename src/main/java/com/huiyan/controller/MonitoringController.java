package com.huiyan.controller;

import com.github.pagehelper.PageHelper;
import com.huiyan.entity.MonitoringData;
import com.huiyan.mapper.MonitoringDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/data")
public class MonitoringController {

    @Autowired
    private MonitoringDataMapper monitoringDataMapper;

    /**
     * 查询最新一条监测数据
     */
    @GetMapping("/latest")
    public MonitoringData getLatest() {
        return monitoringDataMapper.selectLatest();
    }

    /**
     * 查询全部监测数据
     */
    @GetMapping("/list")
    public List<MonitoringData> getList() {
        return monitoringDataMapper.selectAll();
    }

    /**
     * 分页查询监测数据
     */
    @GetMapping("/page")
    public List<MonitoringData> getPage(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {

        PageHelper.startPage(page, size);

        List<MonitoringData> list = monitoringDataMapper.selectPage();

        return list;
    }

    /**
     * 查询指定设备的小时雨量
     */
    @GetMapping("/rain/hourly")
    public List<Map<String, Object>> getHourlyRainfall(
            @RequestParam String deviceId) {

        return monitoringDataMapper.selectHourlyRainfall(deviceId);
    }

    /**
     * 查询指定设备的日雨量
     */
    @GetMapping("/rain/daily")
    public List<Map<String, Object>> getDailyRainfall(
            @RequestParam String deviceId) {

        return monitoringDataMapper.selectDailyRainfall(deviceId);
    }

    /**
     * 查询指定设备的水位趋势
     */
    @GetMapping("/water/trend")
    public List<Map<String, Object>> getWaterLevelTrend(
            @RequestParam String deviceId) {

        return monitoringDataMapper.selectWaterLevelTrend(deviceId);
    }
}