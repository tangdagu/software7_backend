package com.edu.cqupt.software7.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.edu.cqupt.software7.entity.Algorithm;
import com.edu.cqupt.software7.entity.Task;
import com.edu.cqupt.software7.service.AlgorithmService;
import com.edu.cqupt.software7.service.TaskService;
import com.edu.cqupt.software7.util.PythonRun;
import com.edu.cqupt.software7.view.FactorDTO;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/task")
public class TaskController {
    @Resource
    private TaskService taskService;
    @Resource
    private AlgorithmService algorithmService;
    @Resource
    private DataSourceProperties dataSourceProperties;
    @Resource
    private PythonRun pythonRun;

    @PostMapping("/update")
    public Boolean taskUpdate(@RequestBody Task task) {
        return taskService.updateById(task);
    }
    @PostMapping("/insert")
    public  Boolean taskInsert(@RequestBody Task task){
        return taskService.save(task);
    }

    @GetMapping("/getById/{taskId}")
    public Task getById(@PathVariable int taskId){
        return taskService.getById(taskId);
    }

    @PostMapping("/getAll")
    public List<Task> getAll(){
        return taskService.list();
    }

    @GetMapping("/getByName/{taskName}")
    public List<Task> getByName(@PathVariable String taskName){
        List<Task> task = taskService.lambdaQuery()
                .eq(Task::getTaskName,taskName)
                .list();
        return task;
    }
    @GetMapping("/delete/{taskId}")
    public boolean taskDelete(@PathVariable int taskId){
        QueryWrapper<Task> queryWrapper  =new QueryWrapper<>();
        queryWrapper.eq("id",taskId);
        return taskService.remove(queryWrapper);
    }

    @GetMapping("/getFactor/{tableName}")
    public List<FactorDTO> getFactor(@PathVariable String tableName){
        List<FactorDTO> factors = new ArrayList<>();
        return factors;
    }

//    @PostMapping("/runing")
//    public Graph runing(@RequestBody Task task) throws Exception{
//        System.out.println(task);
//        return pythonRun(task);
//    }
}
