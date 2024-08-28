package com.edu.cqupt.software7.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.edu.cqupt.software7.entity.UserLog;
import com.github.pagehelper.PageInfo;

public interface UserLogService extends IService<UserLog> {
    int insertUserLog(UserLog userLog);


    PageInfo<UserLog> findByPageService(Integer pageNum, Integer pageSize, QueryWrapper<UserLog> queryWrapper);
}
