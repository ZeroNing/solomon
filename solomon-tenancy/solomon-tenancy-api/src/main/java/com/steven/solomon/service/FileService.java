package com.steven.solomon.service;

import com.steven.solomon.minio.graphics2D.entity.MinIo;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

  String save(String bucke,String fileName);

  String save(MinIo minIo);

  String upload(String bucke,MultipartFile file) throws Exception;

  String upload(MultipartFile file) throws Exception;
}
