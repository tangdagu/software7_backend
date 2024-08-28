package com.edu.cqupt.software7.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.edu.cqupt.software7.entity.TableManager;
import com.edu.cqupt.software7.view.TableManagerDTO;

import java.util.List;

public interface TableManagerService extends IService<TableManager> {

    List<TableManager> getAllData();

    List<String> getFiledByTableName(String tableName);

    List<String> getCommentsByTableName(String tableName);

    List<Object> getInfoByTableName(String tableName);

    boolean[] getInfoByFiled(String param);

    List<TableManager> getAllTableManagersByFiledName(List<String> tableNames);

    void insertTableManager(TableManagerDTO tableManagerDTO);


}