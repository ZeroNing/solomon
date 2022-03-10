package com.steven.solomon.service;

import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GatewayService {


  @Transactional(readOnly = false)
  @GlobalTransactional
  public String hello(String str) throws Exception {
    for(long i = 1; i <= 10;i++){
//      testServiceInterface.hello(i * 200L);
    }
    throw new Exception();
//    return str;
  }
}
