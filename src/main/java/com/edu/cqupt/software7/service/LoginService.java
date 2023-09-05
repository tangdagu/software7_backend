package com.edu.cqupt.software7.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.edu.cqupt.software7.common.Result;
import com.edu.cqupt.software7.entity.User;
import com.edu.cqupt.software7.view.LoginDTO;

public interface LoginService extends IService<User> {
    public Result login(LoginDTO loginDTO);
}