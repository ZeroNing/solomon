package com.steven.solomon.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.steven.solomon.base.model.BaseEntity;
import com.steven.solomon.minio.graphics2D.entity.MinIo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.UUID;

@TableName("file")
@ApiModel("文件实体类")
public class File extends BaseEntity<String> {

  @ApiModelProperty(value="桶")
  String bucke;

  @ApiModelProperty(value="文件路径（包括文件名称）")
  String fileName;

  @ApiModelProperty(value="文件校验码")
  String md5;

  public File() {
    super();
    setId(UUID.randomUUID().toString());
    this.create();
  }

  public File(MinIo minIo){
    super();
    setId(UUID.randomUUID().toString());
    this.create();
    this.fileName = minIo.getFileName();
    this.bucke = minIo.getBucke();
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

  public String getMd5() {
    return md5;
  }

  public void setMd5(String md5) {
    this.md5 = md5;
  }
}
