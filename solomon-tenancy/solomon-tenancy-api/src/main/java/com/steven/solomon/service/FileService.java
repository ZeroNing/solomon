package com.steven.solomon.service;

import com.steven.solomon.minio.graphics2D.entity.MinIo;

public interface FileService {

  String save(String bucke,String fileName);

  String save(MinIo minIo);
}
