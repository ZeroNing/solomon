package com.steven.solomon.base.excetion;

import com.steven.solomon.exception.AbstractExceptionHandler;
import com.steven.solomon.logger.LoggerUtils;
import com.steven.solomon.vo.BaseExceptionVO;
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
    return new BaseExceptionVO(e.getBindingResult().getFieldError().getDefaultMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
  }
}
