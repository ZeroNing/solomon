package com.steven.solomon.base.excetion;

import com.steven.solomon.base.model.BaseExceptionVO;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;

@Component("BindExceptionProcessor")
public class BindExceptionHandler extends AbstractExceptionHandler {

  @Override
  public BaseExceptionVO handleBaseException(Exception ex) {
    BindException e = (BindException) ex;
    return new BaseExceptionVO(e.getFieldError().getDefaultMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
