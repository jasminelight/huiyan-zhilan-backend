package com.huiyan.controller;

import com.huiyan.service.WaterRiskService;
import com.huiyan.entity.WaterRisk;
import com.huiyan.service.WaterRiskService;
import com.huiyan.vo.RiskPointVO;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/risk")
public class WaterRiskController {

    @Resource
    private WaterRiskService waterRiskService;

    @GetMapping("/list")
    public List<WaterRisk> list() {
        return waterRiskService.findAll();
    }

    @GetMapping("/{id}")
    public WaterRisk getById(@PathVariable Long id) {
        return waterRiskService.findById(id);
    }

    @PostMapping
    public String add(@RequestBody WaterRisk waterRisk) {
        waterRiskService.add(waterRisk);
        return "新增成功";
    }

    @PutMapping
    public String update(@RequestBody WaterRisk waterRisk) {
        waterRiskService.update(waterRisk);
        return "修改成功";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        waterRiskService.delete(id);
        return "删除成功";
    }

    @GetMapping("/level/{level}")
    public List<RiskPointVO> riskFilter(@PathVariable Integer level) {
        return waterRiskService.selectRiskByLevel(level);
    }
}