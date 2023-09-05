package com.edu.cqupt.software7;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.edu.cqupt.software7.mapper")
public class Software7Application {

	public static void main(String[] args) {
		SpringApplication.run(Software7Application.class, args);
	}

}
