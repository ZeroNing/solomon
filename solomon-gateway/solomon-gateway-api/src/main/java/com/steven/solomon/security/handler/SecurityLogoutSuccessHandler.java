package com.steven.solomon.security.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.logout.ServerLogoutHandler;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class SecurityLogoutSuccessHandler implements ServerLogoutSuccessHandler {

  @Override
  public Mono<Void> onLogoutSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
    return null;
  }
}
