package com.steven.solomon.base.excetion;

import com.steven.solomon.base.model.BaseExceptionVO;
import javax.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component("ConstraintViolationExceptionProcessor")
public class ConstraintViolationExceptionHandler extends AbstractExceptionHandler {

  @Override
  public BaseExceptionVO handleBaseException(Exception ex) {
    ConstraintViolationException e = (ConstraintViolationException) ex;
    return new BaseExceptionVO(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
