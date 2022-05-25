package com.steven.solomon.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.steven.solomon.date.DateTimeUtils;
import com.steven.solomon.mapper.FileMapper;
import com.steven.solomon.minio.graphics2D.entity.MinIo;
import com.steven.solomon.minio.utils.MinioUtils;
import com.steven.solomon.pojo.entity.File;
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
  public String save(String bucke, String fileName) {
    File file = new File();
    file.create("1");
    file.setBucke(bucke.trim());
    file.setFileName(fileName.trim());
    baseMapper.insert(file);
    return file.getId();
  }

  @Override
  @Transactional(rollbackFor = Exception.class, readOnly = false)
  public String save(MinIo minIo) {
    return this.save(minIo.getBucke(),minIo.getFileName());
  }

  @Override
  @Transactional(rollbackFor = Exception.class, readOnly = false)
  public String upload(String bucke, MultipartFile file) throws Exception {
    String type = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
    MinIo minIo = minioUtils.putObject(bucke, DateTimeUtils.getLocalYearString()+"/"+DateTimeUtils.getLocalMonthString()+"/"+ UUID
        .randomUUID().toString()+"."+type,file);
    return this.save(minIo);
  }

  @Override
  @Transactional(rollbackFor = Exception.class, readOnly = false)
  public String upload(MultipartFile file) throws Exception {
    return this.upload("default",file);
  }
}
