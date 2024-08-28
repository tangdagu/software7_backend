package com.edu.cqupt.software7.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.edu.cqupt.software7.entity.TableManager;
import com.edu.cqupt.software7.view.TableManagerDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TableManagerMapper extends BaseMapper<TableManager> {




    List<String> getFiledByTableName(String tableName);

    List<String> getCommentsByTableName(String tableName);

    List<Object> getInfoByTableName(String tableName);

    TableManager getInfoByFiled(String param);

    void insertTableManager(TableManagerDTO tableManagerDTO);
}