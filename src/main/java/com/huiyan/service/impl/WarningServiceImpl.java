package com.huiyan.service.impl;

import com.huiyan.entity.MonitoringData;
import com.huiyan.entity.Warning;
import com.huiyan.entity.WarningThreshold;
import com.huiyan.mapper.WarningMapper;
import com.huiyan.mapper.WarningThresholdMapper;
import com.huiyan.service.WarningService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class WarningServiceImpl implements WarningService {
    private static final String WATER_LEVEL = "WATER_LEVEL";
    private static final String RAINFALL = "RAINFALL";
    @Resource private WarningMapper warningMapper;
    @Resource private WarningThresholdMapper thresholdMapper;

    @Override public List<Warning> findAll(String status) {
        return status == null || status.isBlank() ? warningMapper.selectAll() : warningMapper.selectByStatus(status);
    }
    @Override public Warning findById(Long id) { return warningMapper.selectById(id); }
    @Override public List<Warning> evaluate(MonitoringData data) {
        List<Warning> result = new ArrayList<>();
        evaluateValue(data, WATER_LEVEL, data.getWaterLevel(), "cm", result);
        evaluateValue(data, RAINFALL, data.getRainfall(), "mm", result);
        return result;
    }
    private void evaluateValue(MonitoringData data, String type, Double value, String unit, List<Warning> result) {
        if (value == null || value < 0) return;
        List<WarningThreshold> thresholds;
        try { thresholds = thresholdMapper.selectEnabledByType(type); } catch (RuntimeException ignored) { thresholds = List.of(); }
        if (thresholds.isEmpty()) thresholds = defaultThresholds(type, unit);
        WarningThreshold matched = thresholds.stream().filter(t -> Boolean.TRUE.equals(t.getEnabled()) && t.getThreshold() != null && value >= t.getThreshold()).max(Comparator.comparing(WarningThreshold::getThreshold)).orElse(null);
        if (matched == null) return;
        Warning warning = new Warning();
        warning.setDeviceId(data.getDeviceId()); warning.setMonitoringDataId(data.getId());
        warning.setWaterLevel(data.getWaterLevel()); warning.setRainfall(data.getRainfall()); warning.setWarningType(type);
        warning.setWarningLevel(matched.getWarningLevel()); warning.setWarningDesc((matched.getName() == null ? type : matched.getName()) + " reached " + value + unit + ", please handle");
        warning.setIsSent(false); warning.setStatus("PENDING"); warning.setPushChannels(matched.getPushChannels());
        warningMapper.insert(warning); result.add(warning);
    }
    private List<WarningThreshold> defaultThresholds(String type, String unit) {
        double[] values = WATER_LEVEL.equals(type) ? new double[]{15, 20} : new double[]{20, 40};
        List<WarningThreshold> defaults = new ArrayList<>();
        for (int i = 0; i < values.length; i++) {
            WarningThreshold t = new WarningThreshold(); t.setWarningType(type); t.setWarningLevel(i + 1); t.setThreshold(values[i]);
            t.setUnit(unit); t.setEnabled(true); t.setName(i == 0 ? "NOTICE" : "DANGER"); t.setPushChannels("WEB"); defaults.add(t);
        }
        return defaults;
    }
    @Override public void push(Long id, String channels) {
        if (warningMapper.selectById(id) == null) throw new IllegalArgumentException("Warning record not found");
        warningMapper.markPushed(id, channels == null || channels.isBlank() ? "WEB" : channels);
    }
    @Override public void archive(Long id, String handledBy, String remark) {
        if (warningMapper.selectById(id) == null) throw new IllegalArgumentException("Warning record not found");
        warningMapper.archive(id, handledBy, remark);
    }
    @Override public List<WarningThreshold> findThresholds() { return thresholdMapper.selectAll(); }
    @Override public WarningThreshold addThreshold(WarningThreshold threshold) { validateThreshold(threshold); thresholdMapper.insert(threshold); return threshold; }
    @Override public WarningThreshold updateThreshold(WarningThreshold threshold) {
        if (threshold.getId() == null) throw new IllegalArgumentException("Threshold id is required");
        validateThreshold(threshold); thresholdMapper.update(threshold); return threshold;
    }
    @Override public void deleteThreshold(Long id) { thresholdMapper.deleteById(id); }
    private void validateThreshold(WarningThreshold t) {
        if (t == null || t.getWarningType() == null || t.getWarningType().isBlank()) throw new IllegalArgumentException("Warning type is required");
        if (!WATER_LEVEL.equals(t.getWarningType()) && !RAINFALL.equals(t.getWarningType())) throw new IllegalArgumentException("Unsupported warning type");
        if (t.getWarningLevel() == null || t.getWarningLevel() < 1 || t.getThreshold() == null || t.getThreshold() < 0) throw new IllegalArgumentException("Invalid level or threshold");
        if (t.getEnabled() == null) t.setEnabled(true);
    }
}
