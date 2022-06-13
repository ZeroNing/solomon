package com.steven.solomon.controller;

import com.steven.solomon.base.model.BaseResponseVO;
import com.steven.solomon.service.FileService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
@Api(tags  = "文件接口")
public class FileController {

  @Resource
  private FileService fileService;

  @PostMapping("/upload")
  public BaseResponseVO<String> upload(@RequestBody MultipartFile file) throws Exception {
    return new BaseResponseVO(fileService.upload(file));
  }
}
