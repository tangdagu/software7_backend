package com.edu.cqupt.software7.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.filter.CorsFilter;

//@Configuration
//public class CrosConfig {
//
//    @Bean
//    public CorsFilter corsFilter() {
//        //1. 添加 CORS配置信息
//        CorsConfiguration config = new CorsConfiguration();
//        //放行哪些原始域
//        config.addAllowedOrigin("*");
//        //是否发送 Cookie
//        config.setAllowCredentials(false);
//        //放行哪些请求方式
//        config.addAllowedMethod("*");
//        //放行哪些原始请求头部信息
//        config.addAllowedHeader("*");
//        //暴露哪些头部信息
//        config.addExposedHeader("*");
//        //2. 添加映射路径
//        UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource();
//        corsConfigurationSource.registerCorsConfiguration("/**", config);
//        //3. 返回新的CorsFilter
//        return new CorsFilter(corsConfigurationSource);
//    }
//}
@Configuration
public class CrosConfig {

    @Bean
    public CorsFilter corsFilter() {
        //1. 添加 CORS配置信息
        CorsConfiguration config = new CorsConfiguration();
        //放行哪些原始域
//        config.addAllowedOriginPattern("*");

        config.addAllowedOriginPattern("*"); //允许所有的请求类型

        config.setMaxAge(3600L);
        //是否发送 Cookie
        config.setAllowCredentials(true);
        //放行哪些请求方式
        config.addAllowedMethod("*");
        //放行哪些原始请求头部信息
        config.addAllowedHeader("*");
        //暴露哪些头部信息
        config.addExposedHeader("*");
        //2. 添加映射路径
        UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource();
        corsConfigurationSource.registerCorsConfiguration("/**", config);
        //3. 返回新的CorsFilter
        return new CorsFilter(corsConfigurationSource);
    }
//    @Bean
//    public CorsWebFilter corsConfigurationSource() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration corsConfiguration = new CorsConfiguration();
//
//        // 1.配置跨域
//        corsConfiguration.addAllowedHeader("*");
//        corsConfiguration.addAllowedMethod("*");
//        corsConfiguration.addAllowedOrigin("*");
//        corsConfiguration.setAllowCredentials(true);
//
//        source.registerCorsConfiguration("/**", corsConfiguration);
//        return new CorsWebFilter(source);
//    }


}