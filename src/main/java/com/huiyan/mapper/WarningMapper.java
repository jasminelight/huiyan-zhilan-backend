package com.huiyan.mapper;

import com.huiyan.entity.Warning;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import java.util.List;

@Mapper
public interface WarningMapper {
    @Insert("INSERT INTO t_warning(device_id, monitoring_data_id, water_level, rainfall, warning_type, warning_level, warning_desc, is_sent, status, push_channels) VALUES(#{deviceId}, #{monitoringDataId}, #{waterLevel}, #{rainfall}, #{warningType}, #{warningLevel}, #{warningDesc}, #{isSent}, #{status}, #{pushChannels})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Warning warning);

    @Select("SELECT * FROM t_warning ORDER BY create_time DESC")
    List<Warning> selectAll();

    @Select("SELECT * FROM t_warning WHERE id = #{id}")
    Warning selectById(@Param("id") Long id);

    @Select("SELECT * FROM t_warning WHERE status = #{status} ORDER BY create_time DESC")
    List<Warning> selectByStatus(@Param("status") String status);

    @Update("UPDATE t_warning SET is_sent = true, status = CASE WHEN status = 'PENDING' THEN 'PROCESSING' ELSE status END, push_channels = #{channels}, pushed_time = CURRENT_TIMESTAMP WHERE id = #{id}")
    int markPushed(@Param("id") Long id, @Param("channels") String channels);

    @Update("UPDATE t_warning SET status = 'CLOSED', handled_by = #{handledBy}, handle_remark = #{remark}, handled_time = CURRENT_TIMESTAMP WHERE id = #{id}")
    int archive(@Param("id") Long id, @Param("handledBy") String handledBy, @Param("remark") String remark);
}
