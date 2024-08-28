package com.edu.cqupt.software7.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.edu.cqupt.software7.entity.FieldManagementEntity;

import java.util.List;

// TODO 公共模块新增类
public interface FieldManagementService extends IService<FieldManagementEntity> {
    List<FieldManagementEntity> getFiledByDiseaseName(String diseaseName);

    void updateFieldsByDiseaseName(String diseaseName, List<String> fields);

    List<FieldManagementEntity> getFeaturesByTableName(String tableName);

    void deleteFeatureByTableName(String label);
}
