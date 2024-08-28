package com.edu.cqupt.software7.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.edu.cqupt.software7.entity.tTable;

public interface tTableService extends IService<tTable> {
    void insertTable(tTable tTable);
}
