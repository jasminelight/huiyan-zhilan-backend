package com.huiyan.controller;

import com.huiyan.service.RainDataService;
import com.huiyan.entity.RainData;
import com.huiyan.service.RainDataService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/rain")
public class RainDataController {

    @Resource
    private RainDataService rainDataService;

    @GetMapping("/list")
    public List<RainData> list() {
        return rainDataService.findAll();
    }

    @GetMapping("/{id}")
    public RainData getById(@PathVariable Long id) {
        return rainDataService.findById(id);
    }

    @PostMapping
    public String add(@RequestBody RainData rainData) {
        rainDataService.add(rainData);
        return "新增成功";
    }

    @PutMapping
    public String update(@RequestBody RainData rainData) {
        rainDataService.update(rainData);
        return "修改成功";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        rainDataService.delete(id);
        return "删除成功";
    }

    @GetMapping("/sum/{pointId}")
    public Double sumRain(@PathVariable Long pointId) {
        return rainDataService.sumRainByPointId(pointId);
    }
}