package com.steven.solomon.base.model;

import com.steven.solomon.base.code.BaseCode;
import org.springframework.http.HttpStatus;

public class BaseResponseVO extends BaseVO {

  private String message;

  private Integer status;

  private Object data;

  public BaseResponseVO(Object data){
    super();
    this.message = BaseCode.DEFAULT_SUCCESS_PHRASE;
    this.data = data;
    this.status = HttpStatus.OK.value();
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public Object getData() {
    return data;
  }

  public void setData(Object data) {
    this.data = data;
  }
}
