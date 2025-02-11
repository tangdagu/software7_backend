package com.edu.cqupt.software7.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.edu.cqupt.software7.entity.CategoryEntity;
import com.edu.cqupt.software7.entity.Category2Entity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

// TODO 公共模块新增类

@Mapper
public interface Category2Mapper extends BaseMapper<Category2Entity> {
    void removeNode(@Param("id") String id);
}
