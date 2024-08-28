package com.edu.cqupt.software7.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.edu.cqupt.software7.common.R;
import com.edu.cqupt.software7.entity.Task;
import com.edu.cqupt.software7.vo.ExportVo;

import java.util.List;
import java.util.Map;

public interface TaskService extends IService<Task> {
    List<Task> getByCreateUserName(String name);

    Boolean saveTaskFeatureByLowerCase(Task task);

    List<Map<String, Object>> exportByTableNameAndFactors(ExportVo exportVo);

    R run(Task task);
}
