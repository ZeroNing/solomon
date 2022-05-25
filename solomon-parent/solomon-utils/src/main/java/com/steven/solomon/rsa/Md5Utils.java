package com.steven.solomon.rsa;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.springframework.web.multipart.MultipartFile;

public class Md5Utils {

  private final static String MD5 ="MD5";

  /**
   * 文件md5加密
   * @param file
   * @return 返回文件校验码
   */
  public static String getFileMd5Code(MultipartFile file) {
    byte[] fileBytes =new byte[0];
    try {
      fileBytes = file.getBytes();
    }catch (IOException e) {
      e.printStackTrace();
    }
    MessageDigest md5 =null;
    try {
      md5 = MessageDigest.getInstance(MD5);
    }catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    byte[] digest = md5.digest(fileBytes);
    return new BigInteger(1, digest).toString(16);
  }
}
