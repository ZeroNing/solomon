package com.steven.solomon.exception;

import com.steven.solomon.base.code.error.BaseExceptionCode;
import com.steven.solomon.base.excetion.AbstractExceptionHandler;
import com.steven.solomon.base.model.BaseExceptionVO;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component("FlowExceptionProcessor")
public class FlowExceptionHandler extends AbstractExceptionHandler {

  @Override
  public BaseExceptionVO handleBaseException(Exception ex) {
    return new BaseExceptionVO(BaseExceptionCode.SYSTEM_LIMITING,HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
