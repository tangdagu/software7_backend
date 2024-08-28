package com.edu.cqupt.software7.controller;

import com.edu.cqupt.software7.common.Result;
import com.edu.cqupt.software7.mapper.CategoryMapper;
import com.edu.cqupt.software7.mapper.TableDescribeMapper;
import com.edu.cqupt.software7.mapper.UserMapper;
import com.edu.cqupt.software7.entity.tTable;
import com.edu.cqupt.software7.service.tTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
@RequestMapping("/tTable")
public class tTableController {

    @Autowired
    private tTableService tTableServcie;

    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private TableDescribeMapper tableDescribeMapper;

    private final ApplicationContext context;

    @Autowired
    public tTableController(ApplicationContext context) {
        this.context = context;
    }

    @Transactional
    @PostMapping("/insertTableManager")
    public Result insertTableManager(@RequestBody tTable tTable) {
        try {
            System.out.println(tTable);
            tTableServcie.insertTable(tTable);
           return Result.success(200,"插入成功");
        } catch (Exception e) {
            categoryMapper.deleteById(tTable.getNode().getId());
            tableDescribeMapper.deleteByTableId(tTable.getTableDescribe().getTableId());
            userMapper.recoveryUpdateUserColumnById(tTable.getUserId(),tTable.getSize());
           return Result.fail(500,"插入失败"+e);
        }
    }
}
