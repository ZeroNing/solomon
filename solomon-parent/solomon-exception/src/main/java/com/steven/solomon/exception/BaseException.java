package com.steven.solomon.exception;

public class BaseException extends Exception {

  private static final long serialVersionUID = -5121152313724499190L;

  protected String code;

  protected String[] args;

  public BaseException(String code,String message){
    super(message);
    this.code = code;
  }

  public BaseException(String code) {
    this.code = code;
  }

  public BaseException(String code, String... args) {
    this.code = code;
    this.args = args;
  }

  public BaseException(String code, Throwable e, String... args) {
    super(e);
    this.code = code;
    this.args = args;
  }

  public String getCode() {
    return code;
  }

  public String[] getArgs() {
    return args;
  }

}
