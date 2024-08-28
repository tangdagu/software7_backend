package com.edu.cqupt.software7.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.edu.cqupt.software7.entity.FeatureEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

// TODO 公共模块新增类

@Mapper
public interface FeatureManageMapper extends BaseMapper<FeatureEntity> {
    List<FeatureEntity> selectFeatures(@Param("belongType") String belongType);

    void batchInsertFeatures(ArrayList<FeatureEntity> featureEntities);

    List<String> getAllFeaturesByTableName(String tableName);
}
