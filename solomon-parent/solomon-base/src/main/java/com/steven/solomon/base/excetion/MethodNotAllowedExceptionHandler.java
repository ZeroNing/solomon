package com.steven.solomon.base.excetion;

import com.steven.solomon.base.code.error.BaseExceptionCode;
import com.steven.solomon.base.model.BaseExceptionVO;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component("MethodNotAllowedExceptionProcessor")
public class MethodNotAllowedExceptionHandler extends AbstractExceptionHandler{

  @Override
  public BaseExceptionVO handleBaseException(Exception ex) {
    return new BaseExceptionVO(BaseExceptionCode.REQUEST_METHOD_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
