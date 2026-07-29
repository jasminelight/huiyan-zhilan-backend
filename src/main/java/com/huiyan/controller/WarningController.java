package com.huiyan.controller;

import com.huiyan.entity.Warning;
import com.huiyan.mapper.WarningMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/warning")
public class WarningController {

    @Autowired
    private WarningMapper warningMapper;

    @GetMapping("/list")
    public List<Warning> getList() {
        return warningMapper.selectAll();
    }
}