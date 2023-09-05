package com.edu.cqupt.software7.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.cqupt.software7.entity.Task;
import com.edu.cqupt.software7.mapper.TaskMapper;
import com.edu.cqupt.software7.service.TaskService;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements TaskService {
}
