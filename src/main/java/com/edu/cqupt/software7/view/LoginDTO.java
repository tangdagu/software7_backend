package com.edu.cqupt.software7.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginDTO {
    private String loginName;
    private String password;
    //省略getter、setter
}