package com.edu.cqupt.software7.controller;

import com.edu.cqupt.software7.common.R;
import com.edu.cqupt.software7.service.*;
import com.edu.cqupt.software7.view.task.FactorData;
import com.edu.cqupt.software7.vo.ExportVo;
import com.google.gson.Gson;
import org.json.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.edu.cqupt.software7.entity.Algorithm;
import com.edu.cqupt.software7.entity.Tables;
import com.edu.cqupt.software7.entity.Task;
import com.edu.cqupt.software7.util.PythonRun;
import com.edu.cqupt.software7.view.FactorDTO;
import com.edu.cqupt.software7.view.task.RuningResult;
import com.edu.cqupt.software7.view.task.TablesName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.*;
import java.util.*;

@RestController
@RequestMapping("/task")
public class TaskController {
    @Resource
    private TaskService taskService;
    @Resource
    private AlgorithmService algorithmService;
    @Resource
    private TablesService tablesService;
    @Resource
    private DataSourceProperties dataSourceProperties;
    @Resource
    private PythonRun pythonRun;
    @Value("${spring.datasource.url}")
    private String dbUrl;
    @Value("${spring.datasource.username}")
    private String dbUsername;
    @Value("${spring.datasource.password}")
    private String dbPassword;

    @Autowired
    CategoryService categoryService;

    @PostMapping("/update")
    public Boolean taskUpdate(@RequestBody Task task) {
        return taskService.updateById(task);
    }
    @PostMapping("/insert")
    public  Boolean taskInsert(@RequestBody Task task){

//        // 因为pg数据库在建表时会将大写全转为小写，所以对于上传的公共数据集，需将任务管理中选中的特征转为小写，不然在表中查不到
//        // 在category中查询该表的common字段，判断是不是公共数据集
//        if(categoryService.getById(task.getTableId()).getIsCommon() == 1){
//           return taskService.saveTaskFeatureByLowerCase(task);
//        }else{ // 否者直接插入
//            return taskService.save(task);
//        }
        return taskService.save(task);


    }

    @GetMapping("/getById/{taskId}")
    public R getById(@PathVariable int taskId){
        Task task = taskService.getById(taskId);
        return new R<>(200,"success", task);
    }

    @PostMapping("/export")
    public R export(@RequestBody ExportVo exportVo){
        List<Map<String, Object>> res = taskService.exportByTableNameAndFactors(exportVo);
        return new R<>(200,"success",res);
    }

    @GetMapping("/getByCreateUserName/{name}")
    public R getByCreateUserName(@PathVariable String name){
        List<Task> list = taskService.getByCreateUserName(name);
        return new R<>(200,"success", list);
    }

    @GetMapping("/getAll")
    public R getAll(){
        List<Task> list = taskService.list();
        return new R<>(200,"success", list, list.size());
    }

    @GetMapping("/getByName/{taskName}")
    public List<Task> getByName(@PathVariable String taskName){
        List<Task> task = taskService.lambdaQuery()
                .eq(Task::getTaskName,taskName)
                .list();
        return task;
    }
    @GetMapping("/getByDisease/{disease}")
    public List<Task> getByDisease(@PathVariable String disease){
        List<Task> task = taskService.lambdaQuery()
                .eq(Task::getDisease,disease)
                .list();
        return task;
    }
    @GetMapping("/delete/{taskId}")
    public boolean taskDelete(@PathVariable int taskId){
        QueryWrapper<Task> queryWrapper  =new QueryWrapper<>();
        queryWrapper.eq("task_id",taskId);
        return taskService.remove(queryWrapper);
    }


    @GetMapping("/getFactor/{tableName}")
    public List<FactorData> getFactor(@PathVariable String tableName){
        Connection connection = null;
        List<String> factors = new ArrayList<>();
        try{
            connection = DriverManager.getConnection(dbUrl,dbUsername,dbPassword);//url,user,password
        }catch(SQLException e){
            System.out.println("there is sql exception when connecting ...");
            e.printStackTrace();
        }

        try{
            DatabaseMetaData dbmd = connection.getMetaData();
            //表名列表
            ResultSet resultSet = dbmd.getColumns(null, null, tableName, null);
            while (resultSet.next()) {
                String name = resultSet.getString("COLUMN_NAME");
                factors.add(name);
//                System.out.println("Index Column name:" + name );
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(factors);
        factors.remove(0);
        factors.remove(factors.size()-1);
        factors.remove(factors.size()-1);

        List<FactorData> factorData = new ArrayList<>();
        for(int i =0;i<factors.size();i++){
            FactorData factor = new FactorData(factors.get(i),"others");
            factorData.add(factor);
        }

        return factorData;
    }

    @GetMapping("/getTables/{disease}")
    public List<Tables> getTables(@PathVariable String disease){
        List<Tables> tables = tablesService.lambdaQuery()
                .eq(Tables::getTableType,disease)
                .list();
        return tables;
    }

    @PostMapping("/runTask")
    public R runTask(@RequestBody Task task){
        R r = taskService.run(task);
        return r;
    }

    @PostMapping("/runing")
    public R runing(@RequestBody Task task) throws Exception{
        System.out.println(task);
        return pythonRun(task);
    }

    public R pythonRun(Task task) throws Exception{
        List<String> args = new LinkedList<>();
            args.add("--factor="+task.getFactor());
            args.add("--table-name="+task.getTableName());
            args.add("--index="+task.getIndexName());
            args.add("--interactionFactor="+task.getInteractionFactor());
//        args.add("--database-url="+dbUrl);
//        args.add("--database-user="+ dbUsername);
//        args.add("--database-pass="+ dbPassword);
        Algorithm algorithm = algorithmService.lambdaQuery()
                .eq(Algorithm::getAlgorithmName,task.getAlgorithmName())
                .list().get(0);
        String result = pythonRun.run(algorithm.getAlgorithmDeployPath(),args);
        // 创建 Gson 对象
        Gson gson = new Gson();

// 将字符串转换为 JSON 对象或数组
        Object json = gson.fromJson(result, Object.class);

// 打印 JSON 对象或数组（示例中直接打印）
        System.out.println(json);
//        Double[][] b = {{0.6, 0.75, 0.8, 1.02, 1.2},{0.4, 0.5, 0.9, 1.2, 1.4},{0.3, 0.6, 0.7, 1.1, 1.25}};
        return new R<>(200,"success", json);
    }
}
