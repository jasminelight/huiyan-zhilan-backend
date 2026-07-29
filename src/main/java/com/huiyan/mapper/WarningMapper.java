package com.huiyan.mapper;

import com.huiyan.entity.Warning;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface WarningMapper {

    @Insert("INSERT INTO t_warning(device_id, water_level, warning_level, warning_desc, is_sent) " +
            "VALUES(#{deviceId}, #{waterLevel}, #{warningLevel}, #{warningDesc}, #{isSent})")
    int insert(Warning warning);

    @Select("SELECT * FROM t_warning ORDER BY create_time DESC")
    List<Warning> selectAll();
}