package com.huiyan.mapper;

import com.huiyan.entity.Warning;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface WarningMapper {

    @Insert("""
            INSERT INTO t_warning(
                device_id,
                monitoring_data_id,
                water_level,
                rainfall,
                warning_type,
                warning_level,
                warning_desc,
                is_sent,
                status,
                push_channels
            )
            VALUES(
                #{deviceId},
                #{monitoringDataId},
                #{waterLevel},
                #{rainfall},
                #{warningType},
                #{warningLevel},
                #{warningDesc},
                #{isSent},
                #{status},
                #{pushChannels}
            )
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Warning warning);

    @Select("""
            SELECT *
            FROM t_warning
            ORDER BY create_time DESC
            """)
    List<Warning> selectAll();

    @Select("""
            SELECT *
            FROM t_warning
            WHERE id = #{id}
            """)
    Warning selectById(@Param("id") Long id);

    @Select("""
            SELECT *
            FROM t_warning
            WHERE status = #{status}
            ORDER BY create_time DESC
            """)
    List<Warning> selectByStatus(@Param("status") String status);

    @Update("""
            UPDATE t_warning
            SET
                is_sent = true,
                status = CASE
                    WHEN status = 'PENDING' THEN 'PROCESSING'
                    ELSE status
                END,
                push_channels = #{channels},
                pushed_time = CURRENT_TIMESTAMP
            WHERE id = #{id}
            """)
    int markPushed(
            @Param("id") Long id,
            @Param("channels") String channels
    );

    @Update("""
            UPDATE t_warning
            SET
                status = 'CLOSED',
                handled_by = #{handledBy},
                handle_remark = #{remark},
                handled_time = CURRENT_TIMESTAMP
            WHERE id = #{id}
            """)
    int archive(
            @Param("id") Long id,
            @Param("handledBy") String handledBy,
            @Param("remark") String remark
    );

    /**
     * 查询指定设备、指定预警类型下是否存在未关闭的预警
     */
    @Select("""
            SELECT *
            FROM t_warning
            WHERE device_id = #{deviceId}
              AND warning_type = #{warningType}
              AND status IN ('PENDING', 'PROCESSING')
            ORDER BY id DESC
            LIMIT 1
            """)
    Warning selectActiveWarning(
            @Param("deviceId") String deviceId,
            @Param("warningType") String warningType
    );

    /**
     * 统计预警总数量
     */
    @Select("""
            SELECT COUNT(*)
            FROM t_warning
            """)
    Long countAll();

    /**
     * 统计水位预警数量
     */
    @Select("""
            SELECT COUNT(*)
            FROM t_warning
            WHERE warning_type = 'WATER_LEVEL'
            """)
    Long countWaterLevelWarnings();

    /**
     * 统计雨量预警数量
     */
    @Select("""
            SELECT COUNT(*)
            FROM t_warning
            WHERE warning_type = 'RAINFALL'
            """)
    Long countRainfallWarnings();

    /**
     * 统计待处理预警数量
     */
    @Select("""
            SELECT COUNT(*)
            FROM t_warning
            WHERE status = 'PENDING'
            """)
    Long countPendingWarnings();

    /**
     * 统计处理中预警数量
     */
    @Select("""
            SELECT COUNT(*)
            FROM t_warning
            WHERE status = 'PROCESSING'
            """)
    Long countProcessingWarnings();

    /**
     * 统计已关闭预警数量
     */
    @Select("""
            SELECT COUNT(*)
            FROM t_warning
            WHERE status = 'CLOSED'
            """)
    Long countClosedWarnings();
}