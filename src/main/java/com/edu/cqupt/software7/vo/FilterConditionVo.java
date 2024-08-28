package com.edu.cqupt.software7.vo;

import com.edu.cqupt.software7.entity.FilterDataCol;
import com.edu.cqupt.software7.entity.FilterDataInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilterConditionVo {
    private FilterDataInfo filterDataInfo;
    private List<FilterDataCol> filterDataCols;
}
