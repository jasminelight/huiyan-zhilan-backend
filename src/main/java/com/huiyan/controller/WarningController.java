package com.huiyan.controller;

import com.huiyan.entity.Warning;
import com.huiyan.entity.WarningThreshold;
import com.huiyan.service.WarningService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/warning")
public class WarningController {
    @Resource private WarningService warningService;

    /** Existing endpoint remains compatible; status can be PENDING/PROCESSING/CLOSED. */
    @GetMapping("/list")
    public List<Warning> getList(@RequestParam(required = false) String status) {
        return warningService.findAll(status);
    }

    @GetMapping("/{id}")
    public Warning getById(@PathVariable Long id) { return warningService.findById(id); }

    @PostMapping("/{id}/push")
    public Map<String, Object> push(@PathVariable Long id, @RequestBody(required = false) Map<String, String> body) {
        warningService.push(id, body == null ? null : body.get("channels"));
        return Map.of("success", true, "message", "推送已记录");
    }

    @PutMapping("/{id}/handle")
    public Map<String, Object> archive(@PathVariable Long id, @RequestBody Map<String, String> body) {
        warningService.archive(id, body == null ? null : body.get("handledBy"), body == null ? null : body.get("remark"));
        return Map.of("success", true, "message", "预警已归档");
    }

    @GetMapping({"/threshold", "/thresholds"})
    public List<WarningThreshold> thresholds() { return warningService.findThresholds(); }

    @PostMapping({"/threshold", "/thresholds"})
    public WarningThreshold addThreshold(@RequestBody WarningThreshold threshold) { return warningService.addThreshold(threshold); }

    @PutMapping({"/threshold/{id}", "/thresholds/{id}"})
    public WarningThreshold updateThreshold(@PathVariable Long id, @RequestBody WarningThreshold threshold) {
        threshold.setId(id); return warningService.updateThreshold(threshold);
    }

    @DeleteMapping({"/threshold/{id}", "/thresholds/{id}"})
    public Map<String, Object> deleteThreshold(@PathVariable Long id) {
        warningService.deleteThreshold(id); return Map.of("success", true);
    }
}
