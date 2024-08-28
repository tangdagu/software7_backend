package com.edu.cqupt.software7.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.edu.cqupt.software7.entity.UserLog;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserLogMapper extends BaseMapper<UserLog> {
}
