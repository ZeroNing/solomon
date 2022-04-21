package com.steven.solomon.base.excetion;

import com.steven.solomon.base.model.BaseExceptionVO;
import com.steven.solomon.constant.code.BaseCode;
import com.steven.solomon.constant.code.BaseExceptionCode;
import com.steven.solomon.utils.logger.LoggerUtils;
import com.steven.solomon.utils.spring.SpringUtil;
import com.steven.solomon.utils.verification.ValidateUtils;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;

public class BaseGlobalExceptionHandler {

    private static final Logger logger = LoggerUtils.logger(BaseGlobalExceptionHandler.class);

    public static BaseExceptionVO handler(Exception ex, Integer httpStatus, String serverId, Locale locale) {
        String exceptionSimpleName = ex.getClass().getSimpleName();
        BaseExceptionVO baseExceptionVO = new BaseExceptionVO(BaseExceptionCode.BASE_EXCEPTION_CODE, ValidateUtils.isNotEmpty(httpStatus) ? HttpStatus.valueOf(httpStatus) : HttpStatus.INTERNAL_SERVER_ERROR);

        if (ValidateUtils.isEmpty(AbstractExceptionHandler.exceptionHandlerMap)) {
            AbstractExceptionHandler.exceptionHandlerMap = SpringUtil.getBeansOfType(AbstractExceptionHandler.class);
        }

        String exceptionHandlerName = exceptionSimpleName + AbstractExceptionHandler.HANDLER_NAME;
        AbstractExceptionHandler abstractExceptionHandler = AbstractExceptionHandler.exceptionHandlerMap.get(exceptionHandlerName);

        if (ValidateUtils.isEmpty(abstractExceptionHandler)) {
            logger.info("BaseGlobalExceptionHandler 处理gateWay异常遇到未知异常抛出默认的系统异常 S9999,未知异常为:{}", exceptionSimpleName);
        } else {
            baseExceptionVO = abstractExceptionHandler.handleBaseException(ex);
        }
        baseExceptionVO.setServerId(serverId);
        baseExceptionVO.setLocale(locale);
        return baseExceptionVO;
    }

    public static Map<String,Object> handlerMap(Exception ex, Integer httpStatus, String serverId, Locale locale) {
        Map<String, Object> result = new HashMap<>(4);
        BaseExceptionVO baseExceptionVO = handler(ex, httpStatus, serverId, locale);
        result.put(BaseCode.HTTP_STATUS, HttpStatus.INTERNAL_SERVER_ERROR.value());
        result.put(BaseCode.ERROR_CODE, baseExceptionVO.getCode());
        result.put(BaseCode.MESSAGE, baseExceptionVO.getMessage());
        result.put(BaseCode.SERVER_ID, serverId);
        return result;
    }
}
