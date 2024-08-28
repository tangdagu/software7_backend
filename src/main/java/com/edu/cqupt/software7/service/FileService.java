package com.edu.cqupt.software7.service;

import com.edu.cqupt.software7.common.Result;
import com.edu.cqupt.software7.view.UploadResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService{
    public Result fileUpload(MultipartFile file) throws IOException;
}
