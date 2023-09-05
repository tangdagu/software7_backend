package com.edu.cqupt.software7.service;

import com.edu.cqupt.software7.view.UploadResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService{
    public UploadResult fileUpload(MultipartFile file, String newName, String disease) throws IOException;
}
