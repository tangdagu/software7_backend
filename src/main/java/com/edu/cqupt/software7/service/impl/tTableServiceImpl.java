package com.edu.cqupt.software7.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.cqupt.software7.mapper.tTableMapper;
import com.edu.cqupt.software7.entity.tTable;
import com.edu.cqupt.software7.service.tTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class tTableServiceImpl extends ServiceImpl<tTableMapper, tTable>
        implements tTableService {
    @Autowired
    private tTableMapper tTableMapper;
    @Override
    public void insertTable(tTable tTable) {
        tTableMapper.insertTable(tTable);
    }
}
