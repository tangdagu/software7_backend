package com.edu.cqupt.software7.view;

import com.edu.cqupt.software7.entity.Tables;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadResult {
    private String tableName;
    private List<String> tableHeaders;
    private List<Tables> res;
    private Integer code;
    private Exception e;

}