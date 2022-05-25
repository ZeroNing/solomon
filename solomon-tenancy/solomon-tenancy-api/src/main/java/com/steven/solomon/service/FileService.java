package com.steven.solomon.service;

import com.steven.solomon.minio.graphics2D.entity.MinIo;

public interface FileService {

  void save(String bucke,String fileName);

  void save(MinIo minIo);
}
