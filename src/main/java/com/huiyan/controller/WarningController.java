package com.huiyan.controller;

import com.huiyan.entity.Warning;
import com.huiyan.entity.WarningThreshold;
import com.huiyan.service.WarningService;
import com.huiyan.mapper.WarningMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/warning")
public class WarningController {

    @Autowired
    private WarningService warningService;

    @Autowired
    private WarningMapper warningMapper;

    /**
     * 查询预警列表
     */
    @GetMapping("/list")
    public List<Warning> getList(
            @RequestParam(required = false) String status) {

        return warningService.findAll(status);
    }

    /**
     * 查询单条预警
     */
    @GetMapping("/{id}")
    public Warning getById(@PathVariable Long id) {
        return warningService.findById(id);
    }

    /**
     * 推送预警
     */
    @PostMapping("/{id}/push")
    public Map<String, Object> push(
            @PathVariable Long id,
            @RequestBody(required = false) Map<String, String> body) {

        warningService.push(
                id,
                body == null ? null : body.get("channels")
        );

        return Map.of(
                "success", true,
                "message", "推送已记录"
        );
    }

    /**
     * 处理并关闭预警
     */
    @PutMapping("/{id}/handle")
    public Map<String, Object> archive(
            @PathVariable Long id,
            @RequestBody(required = false) Map<String, String> body) {

        warningService.archive(
                id,
                body == null ? null : body.get("handledBy"),
                body == null ? null : body.get("remark")
        );

        return Map.of(
                "success", true,
                "message", "预警已归档"
        );
    }

    /**
     * 查询预警阈值
     */
    @GetMapping({"/threshold", "/thresholds"})
    public List<WarningThreshold> thresholds() {
        return warningService.findThresholds();
    }

    /**
     * 新增预警阈值
     */
    @PostMapping({"/threshold", "/thresholds"})
    public WarningThreshold addThreshold(
            @RequestBody WarningThreshold threshold) {

        return warningService.addThreshold(threshold);
    }

    /**
     * 修改预警阈值
     */
    @PutMapping({"/threshold/{id}", "/thresholds/{id}"})
    public WarningThreshold updateThreshold(
            @PathVariable Long id,
            @RequestBody WarningThreshold threshold) {

        threshold.setId(id);

        return warningService.updateThreshold(threshold);
    }

    /**
     * 删除预警阈值
     */
    @DeleteMapping({"/threshold/{id}", "/thresholds/{id}"})
    public Map<String, Object> deleteThreshold(
            @PathVariable Long id) {

        warningService.deleteThreshold(id);

        return Map.of(
                "success", true,
                "message", "阈值删除成功"
        );
    }

    /**
     * 预警统计
     */
    @GetMapping("/statistics")
    public Map<String, Object> statistics() {

        Map<String, Object> result = new HashMap<>();

        result.put("total", warningMapper.countAll());
        result.put("waterLevel", warningMapper.countWaterLevelWarnings());
        result.put("rainfall", warningMapper.countRainfallWarnings());
        result.put("pending", warningMapper.countPendingWarnings());
        result.put("processing", warningMapper.countProcessingWarnings());
        result.put("closed", warningMapper.countClosedWarnings());

        return result;
    }
}