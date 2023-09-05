package com.edu.cqupt.software7.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.edu.cqupt.software7.entity.Algorithm;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface AlgorithmMapper extends BaseMapper<Algorithm> {
    IPage<Algorithm> selectAlgorithmPage(Page<Algorithm> page);
}
