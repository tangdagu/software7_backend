package com.edu.cqupt.software7.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.edu.cqupt.software7.common.R;
import com.edu.cqupt.software7.common.Result;
import com.edu.cqupt.software7.entity.Tables;
import com.edu.cqupt.software7.entity.UserLog;
import com.edu.cqupt.software7.service.FileService;
import com.edu.cqupt.software7.service.TablesService;
import com.edu.cqupt.software7.service.UserLogService;
import com.edu.cqupt.software7.view.UploadResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/tables")
public class TablesController {
    @Resource
    TablesService tablesService;
    @Resource
    FileService fileService;
    @Resource
    UserLogService userLogService;


    // 获取数据库里病例数量总数
    @GetMapping("/getAllcase")
    public R countAllcase(){
        Integer total = tablesService.countAllcase();
        return new R<>(200, "success", total);
    }


    @PostMapping("/update")
    public Boolean tablesUpdate(@RequestBody Tables tables, HttpServletRequest request) throws IOException {
        Integer userId = (Integer) request.getSession().getAttribute("userId");

        // 操作日志
        UserLog userLog = new UserLog();
        userLog.setUid(String.valueOf(userId));
        userLog.setTime(new Date());


        userLog.setOperation("更新表信息: " + tables.getTableName());


        userLogService.save(userLog);
        return tablesService.updateById(tables);
    }
    @PostMapping("/insert")
    public  Boolean tableInsert(@RequestBody Tables tables, HttpServletRequest request){
        Integer userId = (Integer) request.getSession().getAttribute("userId");

        // 操作日志
        UserLog userLog = new UserLog();
        userLog.setUid(String.valueOf(userId));
        userLog.setTime(new Date());


        userLog.setOperation("上传数据表: " + tables.getTableName());


        userLogService.save(userLog);
        return tablesService.save(tables);
    }
    @GetMapping("/getById/{tablesId}")
    public Tables getById(@PathVariable int tablesId){
        return tablesService.getById(tablesId);
    }
    @PostMapping("/getAll")
    public List<Tables> getAll(){
        return tablesService.list();
    }
    @GetMapping("/getByName/{tablesName}")
    public List<Tables> getByName(@PathVariable String tablesName){
        List<Tables> tables = tablesService.lambdaQuery()
                .eq(Tables::getTableName,tablesName)
                .list();
        return tables;
    }
    @GetMapping("/delete/{tablesId}")
    public boolean tablesDelete(@PathVariable int tablesId, HttpServletRequest request){
        Integer userId = (Integer) request.getSession().getAttribute("userId");

        // 操作日志
        UserLog userLog = new UserLog();
        userLog.setUid(String.valueOf(userId));
        userLog.setTime(new Date());


        userLog.setOperation("删除数据表: " + tablesService.getById(tablesId).getTableName());


        userLogService.save(userLog);
        QueryWrapper<Tables> queryWrapper  =new QueryWrapper<>();
        queryWrapper.eq("table_id",tablesId);
        return tablesService.remove(queryWrapper);
    }
    @PostMapping("/upload")
    public Result uploadFile(@RequestPart("file") MultipartFile file){
        try {
            return fileService.fileUpload(file);
        } catch (Exception e) {
            Result res =new Result<>(400, "文件上传失败"); // TODO 第三个参数是一个对象
            System.out.println(e);
            return res;
        }
    }
}
