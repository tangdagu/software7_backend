package com.edu.cqupt.software7.view;

import com.edu.cqupt.software7.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginVO implements Serializable {
    private Integer id;
    private String token;
    private User user;
    //省略getter、setter
}