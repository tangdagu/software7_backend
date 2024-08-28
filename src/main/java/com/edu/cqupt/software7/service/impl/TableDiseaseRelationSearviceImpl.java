package com.edu.cqupt.software7.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.cqupt.software7.entity.TableDescribeEntity;
import com.edu.cqupt.software7.entity.TableDiseaseRelationEntity;
import com.edu.cqupt.software7.mapper.TableDescribeMapper;
import com.edu.cqupt.software7.mapper.TableDiseaseRelationMapper;
import com.edu.cqupt.software7.service.TableDataService;
import com.edu.cqupt.software7.service.TableDescribeService;
import com.edu.cqupt.software7.service.TableDiseaseRelationSearvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TableDiseaseRelationSearviceImpl extends ServiceImpl<TableDiseaseRelationMapper, TableDiseaseRelationEntity> implements TableDiseaseRelationSearvice {

    @Autowired
    TableDiseaseRelationMapper tableDiseaseRelationMapper;

    @Override
    public TableDiseaseRelationEntity getDeiseaseByTableNameAndTableId(TableDiseaseRelationEntity tableDiseaseRelationEntity) {
        QueryWrapper<TableDiseaseRelationEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("table_name", tableDiseaseRelationEntity.getTableName()).eq("table_id", tableDiseaseRelationEntity.getTableId());
        TableDiseaseRelationEntity entity = tableDiseaseRelationMapper.selectOne(queryWrapper);

        return entity;
    }
}
