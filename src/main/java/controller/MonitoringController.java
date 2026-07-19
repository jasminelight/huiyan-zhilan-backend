package com.huiyan.controller;

import com.huiyan.entity.MonitoringData;
import com.huiyan.mapper.MonitoringDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/data")
public class MonitoringController {

    @Autowired
    private MonitoringDataMapper monitoringDataMapper;

    // 查询最新一条数据
    @GetMapping("/latest")
    public MonitoringData getLatest() {
        return monitoringDataMapper.selectLatest();
    }

    // 查询所有历史数据（按时间倒序）
    @GetMapping("/list")
    public List<MonitoringData> getList() {
        return monitoringDataMapper.selectAll();
    }
}