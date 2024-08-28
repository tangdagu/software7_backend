package com.edu.cqupt.software7.common;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 用于存储用户登录时临时的key和验证码的键值对，放在common里用于全局访问，加Component注解让spring管理
 */
@Component
public class CaptureConfig {
    public static Map<String, String> CAPTURE_MAP = new HashMap<>();
}
