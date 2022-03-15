package com.steven.solomon.base.model;

import java.io.Serializable;

public class BasePageParam implements Serializable {

  Integer pageNo = 0;

  Integer pageSize = 10;

  public Integer getPageNo() {
    return pageNo;
  }

  public void setPageNo(Integer pageNo) {
    this.pageNo = pageNo;
  }

  public Integer getPageSize() {
    return pageSize;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }
}
