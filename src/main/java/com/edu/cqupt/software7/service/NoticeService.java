package com.edu.cqupt.software7.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.edu.cqupt.software7.entity.Notification;
import com.edu.cqupt.software7.vo.InsertNoticeVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface NoticeService extends IService<Notification> {
    PageInfo<Notification> allNotices(Integer pageNum, Integer pageSize);

    void saveNotification(InsertNoticeVo notification);

    List<Notification> queryNotices();

}
