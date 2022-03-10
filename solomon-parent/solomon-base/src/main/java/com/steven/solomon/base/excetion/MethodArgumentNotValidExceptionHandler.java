package com.steven.solomon.base.excetion;

import com.steven.solomon.base.model.BaseExceptionVO;
import com.steven.solomon.utils.logger.LoggerUtils;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;

@Component("MethodArgumentNotValidExceptionProcessor")
public class MethodArgumentNotValidExceptionHandler extends AbstractExceptionHandler {

  private Logger logger = LoggerUtils.logger(getClass());

  @Override
  public BaseExceptionVO handleBaseException(Exception ex) {
    MethodArgumentNotValidException e = (MethodArgumentNotValidException) ex;
    return new BaseExceptionVO(e.getBindingResult().getFieldError().getDefaultMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
