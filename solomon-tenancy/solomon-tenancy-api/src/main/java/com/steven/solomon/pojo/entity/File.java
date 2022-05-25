package com.steven.solomon.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.steven.solomon.base.model.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@TableName("file")
@ApiModel("文件实体类")
public class File extends BaseEntity<String> {

  @ApiModelProperty(value="桶")
  String bucke;

  @ApiModelProperty(value="文件路径（包括文件名称）")
  String fileName;

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
