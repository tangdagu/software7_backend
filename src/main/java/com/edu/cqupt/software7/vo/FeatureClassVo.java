package com.edu.cqupt.software7.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

// TODO 公共模块新增类
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeatureClassVo {
    private String fieldName;
    private String isDiagnosis;
    private String isExamine;
    private String isPathology;
    private String isVitalSigns;
    private String isLabel;
}
