package com.steven.solomon.utils.ajax;

import com.steven.solomon.base.model.BaseExceptionVO;
import com.steven.solomon.constant.code.BaseCode;
import com.steven.solomon.utils.json.JackJsonUtils;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;

public class AjaxResultUtils {

  public static String responseErrorJson(String code, String message,String serverId) throws IOException {
    Map<String, Object> result = new HashMap<>(3);
    result.put(BaseCode.HTTP_STATUS, HttpStatus.INTERNAL_SERVER_ERROR.value());
    result.put(BaseCode.ERROR_CODE, code);
    result.put(BaseCode.MESSAGE, message);
    result.put(BaseCode.SERVER_ID, serverId);
    return JackJsonUtils.formatJsonByFilter(result);
  }

  public static String responseErrorJson(BaseExceptionVO baseExceptionVO) throws IOException {
    return responseErrorJson(baseExceptionVO.getCode(), baseExceptionVO.getMessage(),baseExceptionVO.getServerId());
  }
}
