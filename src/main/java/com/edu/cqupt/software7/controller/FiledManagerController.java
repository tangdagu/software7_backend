package com.edu.cqupt.software7.controller;

import com.edu.cqupt.software7.common.R;
import com.edu.cqupt.software7.entity.TableManager;
import com.edu.cqupt.software7.entity.UserLog;
import com.edu.cqupt.software7.service.TableManagerService;
import com.edu.cqupt.software7.service.UserLogService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.github.pagehelper.PageHelper;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/filedManager")
public class FiledManagerController {

    @Autowired
    private TableManagerService tableManagerService;


    @Autowired
    private UserLogService userLogService;


//    @Resource
//    private DataTableManagerMapper dataTableManagerMapper;

    /**
     * 查询所有的字段
     *
     * @return
     */
    @GetMapping("/queryTableManager")
    public R<List<TableManager>> queryTableManger(int pageNum) {


        PageHelper.startPage(pageNum, 15);

        List<TableManager> allData = tableManagerService.getAllData();


        PageInfo pageInfo = new PageInfo<>(allData);

        return new R<>(200, "成功", allData, pageInfo.getPages());

    }


    // 编辑字段
    @PostMapping("/updateFiled")
    public R updateFiled(@RequestBody TableManager tableManager, HttpServletRequest request) {

        tableManagerService.updateById(tableManager);

        Integer userId = (Integer) request.getSession().getAttribute("userId");

        // 操作日志
        UserLog userLog = new UserLog();
        userLog.setUid(String.valueOf(userId));
        userLog.setTime(new Date());


        userLog.setOperation("更新字段: " + tableManager.getFieldName());


        userLogService.save(userLog);

        return new R<>(200, "成功", null);

    }


    // 删除字段
    @PostMapping("/delFiled")
    public R delFiled(@RequestBody TableManager tableManager, HttpServletRequest request) {

        Integer userId = (Integer) request.getSession().getAttribute("userId");

        // 操作日志
        UserLog userLog = new UserLog();
        userLog.setUid(String.valueOf(userId));
        userLog.setTime(new Date());


        userLog.setOperation("删除字段: " + tableManager.getFieldName());


        userLogService.save(userLog);


        boolean b = tableManagerService.removeById(tableManager.getId());
        if (b == true) {

            return new R<>(200, "删除成功", null);
        }

        return new R<>(500, "删除失败", null);
    }
}