package com.huiyan.controller;

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

    /**
     * 查询全部雨情数据
     *
     * GET /api/rain/list
     */
    @GetMapping("/list")
    public List<RainData> list() {
        return rainDataService.findAll();
    }

    /**
     * 根据ID查询雨情数据
     *
     * GET /api/rain/{id}
     */
    @GetMapping("/{id}")
    public RainData getById(@PathVariable Long id) {
        return rainDataService.findById(id);
    }

    /**
     * 新增雨情数据
     *
     * POST /api/rain
     */
    @PostMapping
    public String add(@RequestBody RainData rainData) {
        rainDataService.add(rainData);
        return "新增成功";
    }

    /**
     * 修改雨情数据
     *
     * PUT /api/rain
     */
    @PutMapping
    public String update(@RequestBody RainData rainData) {
        rainDataService.update(rainData);
        return "修改成功";
    }

    /**
     * 删除雨情数据
     *
     * DELETE /api/rain/{id}
     */
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        rainDataService.delete(id);
        return "删除成功";
    }

    /**
     * 查询指定监测点的累计雨量
     *
     * GET /api/rain/sum/{pointId}
     *
     * pointId 现在是 String，
     * 例如：pi-water-01
     */
    @GetMapping("/sum/{pointId}")
    public Double sumRain(@PathVariable String pointId) {
        return rainDataService.sumRainByPointId(pointId);
    }
}