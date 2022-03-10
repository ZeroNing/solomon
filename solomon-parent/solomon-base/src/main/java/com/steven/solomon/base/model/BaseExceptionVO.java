package com.steven.solomon.base.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.steven.solomon.utils.i18n.I18nUtils;
import com.steven.solomon.utils.verification.ValidateUtils;
import java.util.Locale;
import org.springframework.http.HttpStatus;

public class BaseExceptionVO extends BaseVO {

  /**
   * 异常编码
   */
  String code;
  /**
   * 异常语句
   */
  String message;

  /**
   * 状态编码
   */
  Integer statusCode;

  /**
   * 服务id
   */
  String serverId;
  /**
   * 国际化语言
   */
  @JsonIgnore
  Locale locale;

  /**
   * 国家化参数
   */
  @JsonIgnore
  String arg;

  public BaseExceptionVO(){
    super();
  }

  public BaseExceptionVO(String code, String message,HttpStatus statusCode){
    super();
    this.code = code;
    this.message = message;
    this.statusCode = statusCode.value();
  }

  public BaseExceptionVO(String code,HttpStatus statusCode){
    super();
    this.code = code;
    this.statusCode = statusCode.value();
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getMessage() {
    if(ValidateUtils.isEmpty(message)){
      message = ValidateUtils.isEmpty(locale) ? I18nUtils.getErrorMessage(code,arg) : I18nUtils.getErrorMessage(code,locale,arg);
    }
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Integer getStatusCode() {
//    return ValidateUtils.isEmpty(this.statusCode) ? HttpStatus.INTERNAL_SERVER_ERROR.value() : this.statusCode;
    return HttpStatus.INTERNAL_SERVER_ERROR.value();
  }

  public void setStatusCode(Integer statusCode) {
    this.statusCode = statusCode;
  }

  public String getServerId() {
    return serverId;
  }

  public void setServerId(String serverId) {
    this.serverId = serverId;
  }

  public void setLocale(Locale locale) {
    this.locale = locale;
  }

  public void setArg(String arg) {
    this.arg = arg;
  }

  public Locale getLocale() {
    return locale;
  }

  public String getArg() {
    return arg;
  }
}
