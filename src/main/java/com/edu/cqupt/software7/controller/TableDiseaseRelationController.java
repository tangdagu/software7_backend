package com.edu.cqupt.software7.controller;

import com.edu.cqupt.software7.common.Result;
import com.edu.cqupt.software7.entity.TableDiseaseRelationEntity;
import com.edu.cqupt.software7.service.TableDiseaseRelationSearvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TableDiseaseRelationController {
    @Autowired
    TableDiseaseRelationSearvice tableDiseaseRelationSearvice;

    @PostMapping("/getDiseaseByTableNameAndTableId")
    public Result getDeiseaseByTableNameAndTableId(@RequestBody TableDiseaseRelationEntity tableDiseaseRelationEntity){

        TableDiseaseRelationEntity entity = tableDiseaseRelationSearvice.getDeiseaseByTableNameAndTableId(tableDiseaseRelationEntity);
        return Result.success(entity);
    }
}
