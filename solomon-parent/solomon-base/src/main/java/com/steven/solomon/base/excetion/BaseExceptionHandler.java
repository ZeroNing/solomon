package com.steven.solomon.base.excetion;

import com.steven.solomon.base.model.BaseExceptionVO;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component("BaseExceptionProcessor")
public class BaseExceptionHandler extends AbstractExceptionHandler {

  @Override
  public BaseExceptionVO handleBaseException(Exception ex) {
    BaseException e = (BaseException) ex;
    return new BaseExceptionVO(e.getCode(), e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
