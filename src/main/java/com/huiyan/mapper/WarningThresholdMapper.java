package com.huiyan.mapper;

import com.huiyan.entity.WarningThreshold;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface WarningThresholdMapper {
    @Select("SELECT * FROM warning_threshold ORDER BY warning_type, warning_level, threshold")
    List<WarningThreshold> selectAll();

    @Select("SELECT * FROM warning_threshold WHERE id = #{id}")
    WarningThreshold selectById(@Param("id") Long id);

    @Select("SELECT * FROM warning_threshold WHERE warning_type = #{warningType} AND enabled = true ORDER BY threshold")
    List<WarningThreshold> selectEnabledByType(@Param("warningType") String warningType);

    @Insert("INSERT INTO warning_threshold(name, warning_type, warning_level, threshold, unit, enabled, push_channels, updated_by) VALUES(#{name}, #{warningType}, #{warningLevel}, #{threshold}, #{unit}, #{enabled}, #{pushChannels}, #{updatedBy})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(WarningThreshold threshold);

    @Update("UPDATE warning_threshold SET name=#{name}, warning_type=#{warningType}, warning_level=#{warningLevel}, threshold=#{threshold}, unit=#{unit}, enabled=#{enabled}, push_channels=#{pushChannels}, updated_by=#{updatedBy}, update_time=CURRENT_TIMESTAMP WHERE id=#{id}")
    int update(WarningThreshold threshold);

    @Delete("DELETE FROM warning_threshold WHERE id = #{id}")
    int deleteById(@Param("id") Long id);
}
