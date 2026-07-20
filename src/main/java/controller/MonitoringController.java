package com.huiyan.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.huiyan.entity.MonitoringData;
import com.huiyan.mapper.MonitoringDataMapper;
import com.huiyan.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    // 分页查询
    @GetMapping("/page")
    public PageResult<MonitoringData> getPage(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        // 开始分页
        PageHelper.startPage(page, size);
        // 查询（自动分页）
        List<MonitoringData> list = monitoringDataMapper.selectPage();
        // 封装分页信息
        PageInfo<MonitoringData> pageInfo = new PageInfo<>(list);
        return new PageResult<>(
                pageInfo.getTotal(),
                pageInfo.getPageNum(),
                pageInfo.getPageSize(),
                pageInfo.getList()
        );
    }
}