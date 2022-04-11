//package com.steven.solomon.handler;
//
//import com.steven.solomon.base.excetion.BaseException;
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
///**
// * 鉴权失败处理器
// */
//@Component
//public class SecurityAuthenticationFailureHandler implements ServerAccessDeniedHandler {
//
//  @Override
//  public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException denied) {
//    return null;
//  }
//}
