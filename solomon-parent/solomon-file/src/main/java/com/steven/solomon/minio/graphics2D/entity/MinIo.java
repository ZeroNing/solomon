package com.steven.solomon.minio.graphics2D.entity;

import java.io.Serializable;

public class MinIo implements Serializable {

  String bucke;

  String fileName;

  public MinIo(){
    super();
  }

  public MinIo(String bucke, String fileName) {
    this.bucke = bucke;
    this.fileName = fileName;
  }

  public String getBucke() {
    return bucke;
  }

  public void setBucke(String bucke) {
    this.bucke = bucke;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }
}
