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

    /**
     * 积水预警类型
     */
    private static final String WATER_LEVEL = "WATER_LEVEL";

    /**
     * 雨量预警类型
     */
    private static final String RAINFALL = "RAINFALL";

    @Resource
    private WarningMapper warningMapper;

    @Resource
    private WarningThresholdMapper thresholdMapper;

    /**
     * 查询预警列表
     */
    @Override
    public List<Warning> findAll(String status) {

        if (status == null || status.isBlank()) {
            return warningMapper.selectAll();
        }

        return warningMapper.selectByStatus(status);
    }

    /**
     * 根据ID查询预警
     */
    @Override
    public Warning findById(Long id) {
        return warningMapper.selectById(id);
    }

    /**
     * 根据监测数据进行预警判断
     */
    @Override
    public List<Warning> evaluate(MonitoringData data) {

        List<Warning> result = new ArrayList<>();

        // 判断积水水位
        evaluateValue(
                data,
                WATER_LEVEL,
                data.getWaterLevel(),
                "cm",
                result
        );

        // 判断降雨量
        evaluateValue(
                data,
                RAINFALL,
                data.getRainfall(),
                "mm",
                result
        );

        return result;
    }

    /**
     * 判断某一个监测指标是否达到预警阈值
     */
    private void evaluateValue(
            MonitoringData data,
            String type,
            Double value,
            String unit,
            List<Warning> result
    ) {

        // 没有数据或者数据小于0，不进行预警判断
        if (value == null || value < 0) {
            return;
        }

        /*
         * 第一步：
         * 查询数据库中管理员配置的启用阈值
         */
        List<WarningThreshold> thresholds;

        try {
            thresholds = thresholdMapper.selectEnabledByType(type);
        } catch (RuntimeException e) {
            thresholds = List.of();
        }

        /*
         * 如果数据库没有配置阈值，
         * 暂时使用系统默认阈值。
         */
        if (thresholds.isEmpty()) {
            thresholds = defaultThresholds(type, unit);
        }

        /*
         * 第二步：
         * 找到当前数值能够达到的最高等级阈值。
         *
         * 例如：
         *
         * 15cm -> 一级
         * 20cm -> 二级
         * 23cm -> 二级
         */
        WarningThreshold matched = thresholds.stream()
                .filter(t ->
                        Boolean.TRUE.equals(t.getEnabled())
                                && t.getThreshold() != null
                                && value >= t.getThreshold()
                )
                .max(
                        Comparator.comparing(
                                WarningThreshold::getThreshold
                        )
                )
                .orElse(null);

        // 没有达到任何阈值
        if (matched == null) {
            return;
        }

        /*
         * 第三步：
         * 检查当前设备是否已经存在
         * 同类型的“未关闭预警”。
         *
         * PENDING     = 待处理
         * PROCESSING  = 处理中
         *
         * 如果已经存在，就不重复创建。
         */
        Warning activeWarning =
                warningMapper.selectActiveWarning(
                        data.getDeviceId(),
                        type
                );

        if (activeWarning != null) {
            return;
        }

        /*
         * 第四步：
         * 创建新的预警记录
         */
        Warning warning = new Warning();

        warning.setDeviceId(data.getDeviceId());

        warning.setMonitoringDataId(data.getId());

        warning.setWaterLevel(data.getWaterLevel());

        warning.setRainfall(data.getRainfall());

        warning.setWarningType(type);

        warning.setWarningLevel(
                matched.getWarningLevel()
        );

        warning.setWarningDesc(
                (matched.getName() == null
                        ? type
                        : matched.getName())
                        + " reached "
                        + value
                        + unit
                        + ", please handle"
        );

        // 初始状态：还没有推送
        warning.setIsSent(false);

        // 初始状态：待处理
        warning.setStatus("PENDING");

        // 使用阈值配置中的推送渠道
        warning.setPushChannels(
                matched.getPushChannels()
        );

        /*
         * 第五步：
         * 保存预警
         */
        warningMapper.insert(warning);

        result.add(warning);
    }

    /**
     * 系统默认阈值
     *
     * 正式项目中建议最终全部使用数据库配置。
     * 这里暂时保留作为兜底。
     */
    private List<WarningThreshold> defaultThresholds(
            String type,
            String unit
    ) {

        double[] values;

        if (WATER_LEVEL.equals(type)) {
            values = new double[]{15, 20};
        } else {
            values = new double[]{20, 40};
        }

        List<WarningThreshold> defaults =
                new ArrayList<>();

        for (int i = 0; i < values.length; i++) {

            WarningThreshold threshold =
                    new WarningThreshold();

            threshold.setWarningType(type);

            threshold.setWarningLevel(i + 1);

            threshold.setThreshold(values[i]);

            threshold.setUnit(unit);

            threshold.setEnabled(true);

            threshold.setName(
                    i == 0
                            ? "NOTICE"
                            : "DANGER"
            );

            threshold.setPushChannels("WEB");

            defaults.add(threshold);
        }

        return defaults;
    }

    /**
     * 推送预警
     */
    @Override
    public void push(Long id, String channels) {

        Warning warning =
                warningMapper.selectById(id);

        if (warning == null) {
            throw new IllegalArgumentException(
                    "Warning record not found"
            );
        }

        String pushChannels =
                channels == null || channels.isBlank()
                        ? "WEB"
                        : channels;

        warningMapper.markPushed(
                id,
                pushChannels
        );
    }

    /**
     * 处理并归档预警
     */
    @Override
    public void archive(
            Long id,
            String handledBy,
            String remark
    ) {

        Warning warning =
                warningMapper.selectById(id);

        if (warning == null) {
            throw new IllegalArgumentException(
                    "Warning record not found"
            );
        }

        warningMapper.archive(
                id,
                handledBy,
                remark
        );
    }

    /**
     * 查询全部阈值
     */
    @Override
    public List<WarningThreshold> findThresholds() {
        return thresholdMapper.selectAll();
    }

    /**
     * 新增阈值
     */
    @Override
    public WarningThreshold addThreshold(
            WarningThreshold threshold
    ) {

        validateThreshold(threshold);

        thresholdMapper.insert(threshold);

        return threshold;
    }

    /**
     * 修改阈值
     */
    @Override
    public WarningThreshold updateThreshold(
            WarningThreshold threshold
    ) {

        if (threshold.getId() == null) {
            throw new IllegalArgumentException(
                    "Threshold id is required"
            );
        }

        validateThreshold(threshold);

        thresholdMapper.update(threshold);

        return threshold;
    }

    /**
     * 删除阈值
     */
    @Override
    public void deleteThreshold(Long id) {
        thresholdMapper.deleteById(id);
    }

    /**
     * 验证阈值参数
     */
    private void validateThreshold(
            WarningThreshold t
    ) {

        if (t == null
                || t.getWarningType() == null
                || t.getWarningType().isBlank()) {

            throw new IllegalArgumentException(
                    "Warning type is required"
            );
        }

        if (!WATER_LEVEL.equals(t.getWarningType())
                && !RAINFALL.equals(t.getWarningType())) {

            throw new IllegalArgumentException(
                    "Unsupported warning type"
            );
        }

        if (t.getWarningLevel() == null
                || t.getWarningLevel() < 1
                || t.getThreshold() == null
                || t.getThreshold() < 0) {

            throw new IllegalArgumentException(
                    "Invalid level or threshold"
            );
        }

        if (t.getEnabled() == null) {
            t.setEnabled(true);
        }
    }
}