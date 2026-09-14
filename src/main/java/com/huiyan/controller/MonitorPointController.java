package com.huiyan.controller;

import com.huiyan.entity.MonitorPoint;
import com.huiyan.service.MonitorPointService;   // ⬅️ 新增这一行
import com.huiyan.vo.PointRainVO;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.huiyan.vo.ApiResponse;

@RestController
@RequestMapping("/api/point")
public class MonitorPointController {

    @Resource
    private MonitorPointService monitorPointService;

    @GetMapping("/list")
    public ApiResponse<List<MonitorPoint>> list() {
        return ApiResponse.success(monitorPointService.findAll());
    }

    @GetMapping("/{id}")
    public MonitorPoint getById(@PathVariable Long id) {
        return monitorPointService.findById(id);
    }

    @PostMapping
    public String add(@RequestBody MonitorPoint monitorPoint) {
        monitorPointService.add(monitorPoint);
        return "新增成功";
    }

    @PutMapping
    public String update(@RequestBody MonitorPoint monitorPoint) {
        monitorPointService.update(monitorPoint);
        return "修改成功";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        monitorPointService.delete(id);
        return "删除成功";
    }

    @GetMapping("/{id}/rain")
    public List<PointRainVO> getPointRain(@PathVariable Long id) {
        return monitorPointService.selectRainByPointId(id);
    }
}