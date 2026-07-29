package com.huiyan.mapper;

import com.huiyan.entity.MonitorPoint;
import com.huiyan.vo.PointRainVO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface MonitorPointMapper {
    List<MonitorPoint> selectAll();
    MonitorPoint selectById(Long id);
    int insert(MonitorPoint monitorPoint);
    int update(MonitorPoint monitorPoint);
    int deleteById(Long id);
    List<PointRainVO> selectRainByPointId(Long pointId);
}