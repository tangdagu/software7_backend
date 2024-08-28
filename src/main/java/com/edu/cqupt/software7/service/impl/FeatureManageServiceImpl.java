package com.edu.cqupt.software7.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.cqupt.software7.mapper.FeatureManageMapper;
import com.edu.cqupt.software7.entity.FeatureEntity;
import com.edu.cqupt.software7.service.FeatureManageService;
import com.edu.cqupt.software7.vo.FeatureClassVo;
import com.edu.cqupt.software7.vo.FeatureListVo;
import com.edu.cqupt.software7.vo.FeatureVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// TODO 公共模块新增类

@Service
public class FeatureManageServiceImpl extends ServiceImpl<FeatureManageMapper,FeatureEntity> implements FeatureManageService {
    @Autowired
    FeatureManageMapper featureManageMapper;
    @Override
    public List<FeatureVo> getFeatureList(String belongType) {
        List<FeatureEntity> featureEntities = featureManageMapper.selectFeatures(belongType);
        //封装vo
        ArrayList<FeatureVo> featureVos = new ArrayList<>();
        for (FeatureEntity featureEntity : featureEntities) {
            FeatureVo featurevVo = new FeatureVo();
            featurevVo.setCharacterId(featureEntity.getCharacterId());
            featurevVo.setChName(featureEntity.getChName());
            featurevVo.setFeatureName(featureEntity.getFeatureName());
            featurevVo.setUnit(featureEntity.getUnit());
            //TODO 封装字段是否离散 离散就是discrete  连续就是continuous

            //TODO 如果离散就封装取值 连续就是封装范围
            featureVos.add(featurevVo);
        }
        return featureVos;
    }

    @Override
    public void insertFeatures(FeatureListVo featureListVo) {
        // 封装对象信息
        List<FeatureClassVo> tableHeaders = featureListVo.getTableHeaders();
        ArrayList<FeatureEntity> featureEntities = new ArrayList<>();
        for (FeatureClassVo tableHeader : tableHeaders) {
            FeatureEntity featureEntity = new FeatureEntity();
            featureEntity.setChName(null);// TODO 特征中文解释
            featureEntity.setFeatureName(tableHeader.getFieldName());
            featureEntity.setUnit("character varying"); // TODO  特征类型
            featureEntity.setDiseaseStandard(false);
            featureEntity.setTableName(featureListVo.getTableName());
            if(tableHeader.getIsDiagnosis()=="1") featureEntity.setDiagnosis(true);
            if(tableHeader.getIsPathology()=="1") featureEntity.setPathology(true);
            if(tableHeader.getIsExamine()=="1") featureEntity.setExamine(true);
            if(tableHeader.getIsLabel()=="1") featureEntity.setLabel(true);
            if(tableHeader.getIsVitalSigns()=="1") featureEntity.setVitalSigns(true);
            featureEntities.add(featureEntity);
        }
        // 将featureEntities插入到field_management表中
        try {
            featureManageMapper.batchInsertFeatures(featureEntities);
            System.out.println("插入特征到字段表成功！");
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("插入特征到字段表失败！");
        }

    }

    @Override
    public List<String> getAllFeaturesByTableNamea(String tableName) {
        List<String> res =featureManageMapper.getAllFeaturesByTableName(tableName);
        return res;
    }
}
