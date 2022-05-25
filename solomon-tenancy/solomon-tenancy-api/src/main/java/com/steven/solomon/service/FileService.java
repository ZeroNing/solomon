package com.steven.solomon.service;

import com.steven.solomon.exception.BaseException;
import com.steven.solomon.pojo.entity.File;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

  String insert(File file);

  String upload(String bucke,MultipartFile file) throws Exception;

  String upload(MultipartFile file) throws Exception;

  String checkDocumentCheckCode(MultipartFile file) throws BaseException;
}
