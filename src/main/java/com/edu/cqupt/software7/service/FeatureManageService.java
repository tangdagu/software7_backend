package com.edu.cqupt.software7.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.edu.cqupt.software7.entity.FeatureEntity;
import com.edu.cqupt.software7.vo.FeatureListVo;
import com.edu.cqupt.software7.vo.FeatureVo;

import java.util.List;

// TODO 公共模块新增类
public interface FeatureManageService extends IService<FeatureEntity> {
    List<FeatureVo> getFeatureList(String belongType);

    void insertFeatures(FeatureListVo featureListVo);

    List<String> getAllFeaturesByTableNamea(String tableName);
}
