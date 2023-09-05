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

@TableName("task")
public class Task {
    @TableId(type = IdType.AUTO)
    private Integer taskId;
    private String taskName;
    private String taskType;
    private String taskDescription;
    private String tableName;
    private String algorithmName;
    private String disease;
    private String factor;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Timestamp taskCreateDate;

}