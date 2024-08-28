package com.edu.cqupt.software7.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.cqupt.software7.entity.TableManager;
import com.edu.cqupt.software7.mapper.TableManagerMapper;
import com.edu.cqupt.software7.service.TableManagerService;
import com.edu.cqupt.software7.view.TableManagerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class TableManagerServiceImpl extends ServiceImpl<TableManagerMapper, TableManager>
        implements TableManagerService {


    @Autowired
    private TableManagerMapper tableManagerMapper;



    @Override
    public void insertTableManager(TableManagerDTO tableManagerDTO) {
        tableManagerMapper.insertTableManager(tableManagerDTO);
    }

    @Override
    public List<TableManager> getAllData() {


        List<TableManager> tableManager = tableManagerMapper.selectList(null);


        return tableManager;
    }

    @Override
    public List<String> getFiledByTableName(String tableName) {


        List<String> tableNames = tableManagerMapper.getFiledByTableName(tableName);

        return tableNames;
    }

    @Override
    public List<String> getCommentsByTableName(String tableName) {

        List<String> comments = tableManagerMapper.getCommentsByTableName(tableName);

        return comments;
    }

    @Override
    public List<Object> getInfoByTableName(String tableName) {



        List<Object> res = tableManagerMapper.getInfoByTableName(tableName);

        return res;
    }

    @Override
    public boolean[] getInfoByFiled(String param) {


        boolean r1 = false,r2 = false, r3 = false;
        TableManager tableManager = tableManagerMapper.getInfoByFiled(param);

        if (tableManager != null){

            r1 = Objects.equals(tableManager.getIsDemography(), "1");
            r2 = Objects.equals(tableManager.getIsPhysiological(), "1");
            r3 = Objects.equals(tableManager.getIsSociology(), "1");
        }

        return new boolean[]{r1,r2,r3};
    }

    @Override
    public List<TableManager> getAllTableManagersByFiledName(List<String> tableNames) {

        List<TableManager> res= new ArrayList<>();

        for (int i = 0; i< tableNames.size() ; i++) {
            QueryWrapper queryWrapper = new QueryWrapper();

            queryWrapper.eq("field_name", tableNames.get(i));
            queryWrapper.eq("table_name","Diabetes");
            TableManager tableManager = tableManagerMapper.selectOne(queryWrapper);
            res.add(tableManager);
        }

        return res;
    }
}
