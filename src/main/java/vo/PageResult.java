package com.huiyan.vo;

import lombok.Data;
import java.util.List;

@Data
public class PageResult<T> {
    private Long total;        // 总记录数
    private Integer page;      // 当前页码
    private Integer size;      // 每页大小
    private Integer totalPages;// 总页数
    private List<T> records;   // 数据列表

    public PageResult(Long total, Integer page, Integer size, List<T> records) {
        this.total = total;
        this.page = page;
        this.size = size;
        this.totalPages = (int) Math.ceil((double) total / size);
        this.records = records;
    }
}