package com.steven.solomon.param;

import cn.hutool.core.util.StrUtil;
import com.steven.solomon.enums.OrderByEnum;
import com.steven.solomon.verification.ValidateUtils;
import java.io.Serializable;

public class BasePageParam implements Serializable {

  Integer pageNo = 0;

  Integer pageSize = 10;

  String orderByField;

  OrderByEnum orderByMethod;

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

  public String getOrderByField() {
    return orderByField;
  }

  public void setOrderByField(String orderByField) {
    this.orderByField = orderByField;
  }

  public OrderByEnum getOrderByMethod() {
    return orderByMethod;
  }

  public void setOrderByMethod(OrderByEnum orderByMethod) {
    this.orderByMethod = orderByMethod;
  }

  public String orderBy(){
    if(ValidateUtils.isEmpty(this.orderByField)){
      return null;
    }
    if(ValidateUtils.isEmpty(this.orderByMethod)){
      this.orderByMethod = OrderByEnum.DESC;
    }
    return this.orderByField + StrUtil.SPACE + this.orderByMethod.getValue();
  }
}
