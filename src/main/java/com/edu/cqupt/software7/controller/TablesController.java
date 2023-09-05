package com.edu.cqupt.software7.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.edu.cqupt.software7.entity.Tables;
import com.edu.cqupt.software7.service.FileService;
import com.edu.cqupt.software7.service.TablesService;
import com.edu.cqupt.software7.view.UploadResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/tables")
public class TablesController {
    @Resource
    TablesService tablesService;
    @Resource
    FileService fileService;

    @PostMapping("/update")
    public Boolean tablesUpdate(@RequestBody Tables tables) throws IOException {
        return tablesService.updateById(tables);
    }
    @PostMapping("/insert")
    public  Boolean tableInsert(@RequestBody Tables tables){
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
    public boolean tablesDelete(@PathVariable int tablesId){
        QueryWrapper<Tables> queryWrapper  =new QueryWrapper<>();
        queryWrapper.eq("id",tablesId);
        return tablesService.remove(queryWrapper);
    }
    @PostMapping("/upload")
    public UploadResult uploadFile(@RequestPart("file") MultipartFile file,@RequestParam("newName") String newName,@RequestParam("disease") String disease){
        try {
            return fileService.fileUpload(file, newName,disease);
        } catch (Exception e) {
            UploadResult res =new UploadResult();
            res.setCode(500);
            System.out.println(e);
            res.setE(e);
            return res;
        }
    }
}
