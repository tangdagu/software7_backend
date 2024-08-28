package com.edu.cqupt.software7.controller;


import com.alibaba.fastjson.JSON;
import com.edu.cqupt.software7.common.FeatureType;
import com.edu.cqupt.software7.common.Result;
import com.edu.cqupt.software7.entity.FeatureEntity;
import com.edu.cqupt.software7.entity.FieldManagementEntity;
import com.edu.cqupt.software7.service.FeatureManageService;
import com.edu.cqupt.software7.service.FieldManagementService;
import com.edu.cqupt.software7.vo.FeatureListVo;
import com.edu.cqupt.software7.vo.FeatureVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// TODO 公共模块新增类
@Slf4j
@RestController
@RequestMapping("/api/feature")
public class FeatureController {
    @Autowired
    FeatureManageService featureManageService;

    @Autowired
    FieldManagementService fieldManagementService;
    @GetMapping("/getFeatures")
    public Result<FeatureEntity> getFeture(@RequestParam("index") Integer belongType){ // belongType说明是属于诊断类型、检查类型、病理类型、生命特征类型
        String type = null;
        for (FeatureType value : FeatureType.values()) {
            if(value.getCode() == belongType){
                type = value.getName();
            }
        }
        List<FeatureVo> list = featureManageService.getFeatureList(type);
        return Result.success("200",list);
    }


    @GetMapping("/getFeaturesByTableName/{tableName}")
    public Result getFeaturesByTableName(@PathVariable("tableName") String tableName){
        List<FieldManagementEntity> res = fieldManagementService.getFeaturesByTableName(tableName);
        return Result.success(res);
    }


    // 根据表名查询出该表的表头（所有的列的列名）
    @GetMapping("/getAllFeaturesByTableName/{tableName}")
    public Result getAllFeaturesByTableName(@PathVariable("tableName") String tableName) {
        List<String> res = featureManageService.getAllFeaturesByTableNamea(tableName);
        log.info("查询{" + tableName + "}表的所有特征为：" + res);
        return Result.success(res);
    }






    // TODO 废弃方法
    @PostMapping("/insertFeature") // 上传特征分类结果
    public Result fieldInsert(@RequestBody FeatureListVo featureListVo){
        System.out.println("tableHeaders:"+ JSON.toJSONString(featureListVo));
        featureManageService.insertFeatures(featureListVo);
        return null;
    }
}
