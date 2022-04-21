package com.steven.solomon.exception;

import com.steven.solomon.base.excetion.AbstractExceptionHandler;
import com.steven.solomon.base.model.BaseExceptionVO;
import com.steven.solomon.constant.code.BaseExceptionCode;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component("ResponseStatusExceptionProcessor")
public class ResponseStatusExceptionHandler extends AbstractExceptionHandler {

  @Override
  public BaseExceptionVO handleBaseException(Exception ex) {
    return new BaseExceptionVO(BaseExceptionCode.BAD_REQUEST,HttpStatus.INTERNAL_SERVER_ERROR);
  }

}
