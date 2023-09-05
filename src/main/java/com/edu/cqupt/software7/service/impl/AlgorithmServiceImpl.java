package com.edu.cqupt.software7.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.cqupt.software7.entity.Algorithm;
import com.edu.cqupt.software7.mapper.AlgorithmMapper;
import com.edu.cqupt.software7.service.AlgorithmService;
import org.springframework.stereotype.Service;

@Service
public class AlgorithmServiceImpl extends ServiceImpl<AlgorithmMapper, Algorithm> implements AlgorithmService {
}
