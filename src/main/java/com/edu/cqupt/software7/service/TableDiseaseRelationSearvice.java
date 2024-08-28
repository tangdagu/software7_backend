package com.edu.cqupt.software7.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.edu.cqupt.software7.entity.TableDescribeEntity;
import com.edu.cqupt.software7.entity.TableDiseaseRelationEntity;

public interface TableDiseaseRelationSearvice extends IService<TableDiseaseRelationEntity> {
    TableDiseaseRelationEntity getDeiseaseByTableNameAndTableId(TableDiseaseRelationEntity tableDiseaseRelationEntity);
}
