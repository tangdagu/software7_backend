package com.edu.cqupt.software7.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.edu.cqupt.software7.common.FileResult;
import com.edu.cqupt.software7.entity.Algorithm;
import com.edu.cqupt.software7.service.AlgorithmService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

@RestController
@RequestMapping("/algorithm")
public class AlgorithmController {
    @Resource
    AlgorithmService algorithmService;
    @Value("${gorit.file.root.path2}")
    private String filePath;
    @PostMapping("/update")
    public Boolean algorithmUpdate(@RequestBody Algorithm algorithm) throws IOException {

        return algorithmService.updateById(algorithm);
    }
    @PostMapping("/insert")
    public  Boolean algorithmInsert(@RequestBody Algorithm algorithm){
        algorithm.setAlgorithmDeployPath(filePath+algorithm.getAlgorithmName());
        return algorithmService.save(algorithm);
    }
    @GetMapping("/getById/{algorithmId}")
    public Algorithm getById(@PathVariable int algorithmId){
        return algorithmService.getById(algorithmId);
    }

    @PostMapping("/getAll")
    public List<Algorithm> getAll(){
        return algorithmService.list();
    }
    @GetMapping("/getByName/{algorithmName}")
    public List<Algorithm> getByName(@PathVariable String algorithmName){
        List<Algorithm> algorithm = algorithmService.lambdaQuery()
                .eq(Algorithm::getAlgorithmName,algorithmName)
                .list();
        return algorithm;
    }
    @GetMapping("/delete/{algorithmId}")
    public Boolean algorithmDelete(@PathVariable int algorithmId){
        QueryWrapper<Algorithm> queryWrapper  =new QueryWrapper<>();
        queryWrapper.eq("algorithm_id",algorithmId);
        return algorithmService.remove(queryWrapper);
    }

    SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");

    // 日志打印
    private Logger log = LoggerFactory.getLogger("FileController");

    // 文件上传 （可以多文件上传）
    @PostMapping("/upload")
    public FileResult fileUploads(HttpServletRequest request, @RequestParam("file") MultipartFile file) throws IOException {
        // 得到格式化后的日期
//        String format = sdf.format(new Date());
        // 获取上传的文件名称
        String fileName = file.getOriginalFilename();
        // 时间 和 日期拼接
        String newFileName = fileName;
        // 得到文件保存的位置以及新文件名
        File dest = new File(filePath + newFileName);
        try {
            // 上传的文件被保存了
            file.transferTo(dest);
            // 打印日志
            log.info("上传成功，当前上传的文件保存在 {}",filePath + newFileName);
            // 自定义返回的统一的 JSON 格式的数据，可以直接返回这个字符串也是可以的。
            return FileResult.succ("上传成功");
        } catch (IOException e) {
            log.error(e.toString());
        }
        // 待完成 —— 文件类型校验工作
        return FileResult.fail("上传错误");
    }
}
