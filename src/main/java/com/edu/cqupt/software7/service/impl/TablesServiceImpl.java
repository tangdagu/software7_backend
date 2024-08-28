package com.edu.cqupt.software7.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.cqupt.software7.entity.Tables;
import com.edu.cqupt.software7.mapper.TablesMapper;
import com.edu.cqupt.software7.service.TablesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class TablesServiceImpl extends ServiceImpl<TablesMapper, Tables> implements TablesService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void updateDataTable(String table_name, String disease){

    }

    @Override
    public Integer countAllcase() {

            // 构造查询语句,
            String query = "SELECT SUM(reltuples) \n" +  // reltuples在pg_class表中表示某个relname表的大概行数，注意不是准确行数，可能有误差，但是这里估计个大概就差不多了
                    "FROM pg_class \n" +
                    "WHERE relkind = 'r' \n" +  // relkind = 'r'在pg_class表中表示某个relname表为普通表
                    "AND relnamespace = '2200' \n" +  // relnamespace = '2200'在pg_class表中表示某个relname表在public里
                    "AND relname NOT IN ('category2', 'factor', 't_table_manager', 'tables', 'user_log'," +
                                        " 'category2', 'field_management', 'table_describe', 'user_management', 'table_disease_relation');";
                                        //  排除系统表，不计算系统表的行数

            // 执行查询
            Integer rowCount = jdbcTemplate.queryForObject(query, Integer.class);

            return rowCount != null ? rowCount.intValue() : 0;
    }
}
