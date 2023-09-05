package com.edu.cqupt.software7.controller;

import com.edu.cqupt.software7.common.Result;
import com.edu.cqupt.software7.service.LoginService;
import com.edu.cqupt.software7.view.LoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping("/api")
public class LoginController {
    @Autowired
    LoginService loginService;

    @PostMapping(value = "/api/login")
    @CrossOrigin       //后端跨域
    public Result login(@RequestBody LoginDTO loginDTO){
        return loginService.login(loginDTO);
    }
}
