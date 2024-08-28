package com.edu.cqupt.software7.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.edu.cqupt.software7.entity.tTable;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface tTableMapper extends BaseMapper<tTable> {
    void insertTable(tTable tTable);

    List<Map<String, Object>> getMutipleList(String tablename);
}
