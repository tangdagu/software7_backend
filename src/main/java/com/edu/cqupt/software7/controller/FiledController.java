package com.edu.cqupt.software7.controller;

import com.edu.cqupt.software7.common.Result;
import com.edu.cqupt.software7.entity.FieldManagementEntity;
import com.edu.cqupt.software7.service.*;
import com.edu.cqupt.software7.vo.QueryFiledVO;
import com.edu.cqupt.software7.vo.UpdateFiledVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/filed")
public class FiledController {

    @Autowired
    TableDataService tableDataService;
    @Autowired
    UserService userService;

    @Autowired
    CategoryService categoryService;
    @Autowired
    TableDescribeService tableDescribeService;

    @Autowired
    private FieldManagementService fieldManagementService;


    /**
     *
     * 通过关联疾病名称展示字段信息
     * @param
     * @return 字段信息表
     */
    @PostMapping("/getAllFiled")
    public Result getAllFiled(@RequestBody QueryFiledVO queryFiledVO){
        System.out.println(queryFiledVO.getDiseaseName());
        List<FieldManagementEntity> res = fieldManagementService.getFiledByDiseaseName(queryFiledVO.getDiseaseName());
        return Result.success(res);
    }

    @GetMapping("/getAllFiledByTableName/{tableName}")
    public Result getAllFiledByTableName(@PathVariable String tableName){
        List<FieldManagementEntity> features = fieldManagementService.getFeaturesByTableName(tableName);
        return Result.success(features);
    }


    /**
     *
     * 新建特征表 根据动态选择来更新字段表
     *
     * 接收病种名字 和 字段列表
     */
    @PostMapping("/updateFiled")
    public Result updateFiled(@RequestBody UpdateFiledVO updateFiledVO){
        String diseaseName = updateFiledVO.getDiseaseName();
        List<String> fields = updateFiledVO.getFileds();
        // 更新字段表信息
        fieldManagementService.updateFieldsByDiseaseName(diseaseName, fields);
        return Result.success(null);
    }







}
