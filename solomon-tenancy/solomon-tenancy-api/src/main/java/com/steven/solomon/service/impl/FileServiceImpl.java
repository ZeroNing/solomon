package com.steven.solomon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.steven.solomon.code.TenancyErrorCode;
import com.steven.solomon.date.DateTimeUtils;
import com.steven.solomon.exception.BaseException;
import com.steven.solomon.mapper.FileMapper;
import com.steven.solomon.minio.graphics2D.entity.MinIo;
import com.steven.solomon.minio.utils.MinioUtils;
import com.steven.solomon.pojo.entity.File;
import com.steven.solomon.rsa.Md5Utils;
import com.steven.solomon.service.FileService;
import java.util.UUID;
import javax.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@DubboService
public class FileServiceImpl extends ServiceImpl<FileMapper, File> implements FileService {

  @Resource
  private MinioUtils minioUtils;

  @Override
  @Transactional(rollbackFor = Exception.class, readOnly = false)
  public String insert(File file) {
    file.create("1");
    file.setBucke(file.getBucke().trim());
    file.setFileName(file.getFileName().trim());
    baseMapper.insert(file);
    return file.getId();
  }

  @Override
  @Transactional(rollbackFor = Exception.class, readOnly = false)
  public String upload(String bucke, MultipartFile multipartFile) throws Exception {
    String documentCheckCode = checkDocumentCheckCode(multipartFile);
    String type = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf(".")+1);
    MinIo minIo = minioUtils.putObject(bucke, DateTimeUtils.getLocalYearString()+"/"+DateTimeUtils.getLocalMonthString()+"/"+ UUID
        .randomUUID().toString()+"."+type,multipartFile);
    File file= new File(minIo);
    file.setMd5(documentCheckCode);
    return this.insert(file);
  }

  @Override
  @Transactional(rollbackFor = Exception.class, readOnly = false)
  public String upload(MultipartFile file) throws Exception {
    return this.upload("default",file);
  }

  @Override
  public String checkDocumentCheckCode(MultipartFile file) throws BaseException {
    String documentCheckCode = Md5Utils.getFileMd5Code(file);
    LambdaQueryWrapper<File> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(File::getMd5,documentCheckCode);
    long count = baseMapper.selectCount(queryWrapper);
    if(count >= 1){
      throw new BaseException(TenancyErrorCode.FILE_IS_EXIT);
    }
    return documentCheckCode;
  }
}
