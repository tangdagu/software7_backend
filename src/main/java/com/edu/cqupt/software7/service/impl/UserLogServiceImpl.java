package com.edu.cqupt.software7.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.cqupt.software7.mapper.UserLogMapper;
import com.edu.cqupt.software7.mapper.UserMapper;
import com.edu.cqupt.software7.entity.UserLog;
import com.edu.cqupt.software7.service.UserLogService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserLogServiceImpl extends ServiceImpl<UserLogMapper, UserLog>
        implements UserLogService {
    @Autowired
    private UserLogMapper userLogMapper;

    @Resource
    private UserMapper userMapper;
    @Override
    public int insertUserLog(UserLog userLog) {

        int insert = userLogMapper.insert(userLog);
        return insert;
    }


    @Override
    public PageInfo<UserLog> findByPageService(Integer pageNum, Integer pageSize, QueryWrapper<UserLog> queryWrapper) {
        PageHelper.startPage(pageNum,pageSize);
        List<UserLog> logInfos = userLogMapper.selectList(queryWrapper);
        return new PageInfo<>(logInfos);
    }
}
