package com.edu.cqupt.software7.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.cqupt.software7.mapper.FieldManagementMapper;
import com.edu.cqupt.software7.entity.FieldManagementEntity;
import com.edu.cqupt.software7.service.FieldManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// TODO 公共模块新增类

@Service
public class FieldManagementServiceImpl extends ServiceImpl<FieldManagementMapper, FieldManagementEntity> implements FieldManagementService {

    @Autowired
    private FieldManagementMapper fieldManagementMapper;

    @Override
    public List<FieldManagementEntity> getFiledByDiseaseName(String diseaseName) {

        List<FieldManagementEntity> res = fieldManagementMapper.getFiledByDiseaseName(diseaseName);

        return res;
    }

    @Override
    public void updateFieldsByDiseaseName(String diseaseName, List<String> fields) {

        fieldManagementMapper.updateFieldsByDiseaseName(diseaseName, fields);
    }

    @Override
    public List<FieldManagementEntity> getFeaturesByTableName(String tableName) {

        QueryWrapper<FieldManagementEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("table_name", tableName).ne("feature_name", "ID");
        List<FieldManagementEntity> res = fieldManagementMapper.selectList(queryWrapper);
        return res;
    }

    @Override
    public void deleteFeatureByTableName(String label) {
        QueryWrapper<FieldManagementEntity> Wrapper = new QueryWrapper<>();
        Wrapper.eq("table_name", label);
        fieldManagementMapper.delete(Wrapper);
    }
}
