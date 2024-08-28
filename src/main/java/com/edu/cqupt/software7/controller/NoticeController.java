package com.edu.cqupt.software7.controller;


import com.edu.cqupt.software7.common.Result;
import com.edu.cqupt.software7.entity.Notification;
import com.edu.cqupt.software7.service.NoticeService;
import com.edu.cqupt.software7.vo.InsertNoticeVo;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @GetMapping("/allNotices")
    public PageInfo<Notification> allNotices(@RequestParam Integer pageNum , @RequestParam Integer pageSize){
        return noticeService.allNotices(pageNum, pageSize);
    }

    @GetMapping("/queryNotices")
    public List<Notification> queryNotices(){
        return noticeService.queryNotices();
    }




    @PostMapping("/updateNotice")
    public Result updateNotice(@RequestBody Notification notification){

        notification.setUpdateTime(new Date());
        noticeService.saveOrUpdate(notification);

        return Result.success(200 , "成功", null);
    }


    @PostMapping("delNotice")
    public Result delNotice(@RequestBody Notification notification){
        noticeService.removeById(notification.getInfoId());
        return Result.success(200 , "成功", null);
    }

    @PostMapping("insertNotice")
    public Result insertNotice(@RequestBody InsertNoticeVo notification){

        noticeService.saveNotification(notification);
        return Result.success(200 , "成功", null);
    }


}
