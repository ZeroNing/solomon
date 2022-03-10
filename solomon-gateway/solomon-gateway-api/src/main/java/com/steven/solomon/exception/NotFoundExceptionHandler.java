package com.steven.solomon.exception;

import com.steven.solomon.base.code.error.BaseExceptionCode;
import com.steven.solomon.base.excetion.AbstractExceptionHandler;
import com.steven.solomon.base.model.BaseExceptionVO;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component("NotFoundExceptionProcessor")
public class NotFoundExceptionHandler extends AbstractExceptionHandler {

  @Override
  public BaseExceptionVO handleBaseException(Exception ex) {
    BaseExceptionVO baseExceptionVO = new BaseExceptionVO(BaseExceptionCode.BASE_EXCEPTION_CODE, HttpStatus.INTERNAL_SERVER_ERROR);
    if(ex instanceof NotFoundException){
      String reason = ((NotFoundException) ex).getReason();
      reason = reason.substring(reason.lastIndexOf("for ")+4);
      baseExceptionVO = new BaseExceptionVO(BaseExceptionCode.SERVICE_CALL_ERROR,((NotFoundException) ex).getStatus());
      baseExceptionVO.setArg(reason);
    } else {
      return baseExceptionVO;
    }
    return baseExceptionVO;
  }

}
