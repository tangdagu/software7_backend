package com.edu.cqupt.software7.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.edu.cqupt.software7.entity.User;
import com.edu.cqupt.software7.view.QueryDTO;

import java.util.List;

public interface UserService extends IService<User> {
    public IPage<User> selectUserPage(QueryDTO queryDTO);
    public Integer addUser(User user);
    public Integer updateUser(User user);
    public Integer deleteUser(Integer id);
    public void batchDelete(List<Integer> ids);
}
