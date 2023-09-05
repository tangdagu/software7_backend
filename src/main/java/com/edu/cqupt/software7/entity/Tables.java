package com.edu.cqupt.software7.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)

@TableName("tables")
public class Tables {
    @TableId(type = IdType.AUTO)
    private Integer tableId;
    private String tableName;
    private String tableType;
    private String tableDescription;
    private Integer tableCreateId;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Timestamp tableCreateDate;
}