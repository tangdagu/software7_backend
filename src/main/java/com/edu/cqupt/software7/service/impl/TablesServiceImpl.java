package com.edu.cqupt.software7.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.cqupt.software7.entity.Tables;
import com.edu.cqupt.software7.mapper.TablesMapper;
import com.edu.cqupt.software7.service.TablesService;
import org.springframework.stereotype.Service;

@Service
public class TablesServiceImpl extends ServiceImpl<TablesMapper, Tables> implements TablesService {

    public void updateDataTable(String table_name, String disease){

    }
}
