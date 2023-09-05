package com.edu.cqupt.software7.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Result {
    //相应码
    private Integer code;
    //信息
    private String message;
    //返回数据
    private Object data;
    //省略getter、setter、构造方法
}
