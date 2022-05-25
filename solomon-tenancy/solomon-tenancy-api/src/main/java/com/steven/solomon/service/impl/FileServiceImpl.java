package com.steven.solomon.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.steven.solomon.mapper.FileMapper;
import com.steven.solomon.minio.graphics2D.entity.MinIo;
import com.steven.solomon.pojo.entity.File;
import com.steven.solomon.service.FileService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@DubboService
public class FileServiceImpl extends ServiceImpl<FileMapper, File> implements FileService {

  @Override
  @Transactional(rollbackFor = Exception.class, readOnly = false)
  public void save(String bucke, String fileName) {
    File file = new File();
    file.create("1");
    file.setBucke(bucke);
    file.setFileName(fileName);
    baseMapper.insert(file);
  }

  @Override
  @Transactional(rollbackFor = Exception.class, readOnly = false)
  public void save(MinIo minIo) {
    this.save(minIo.getBucke(),minIo.getFileName());
  }
}
