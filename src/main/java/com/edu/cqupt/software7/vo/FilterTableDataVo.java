package com.edu.cqupt.software7.vo;

import com.edu.cqupt.software7.entity.CategoryEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO 公共模块新增类

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilterTableDataVo {
    private AddDataFormVo addDataForm;
    private CategoryEntity nodeData;
    private String nodeid;
    private String status;
}
