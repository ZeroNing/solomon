package com.steven.solomon.filter;

import com.steven.solomon.constant.code.BaseCode;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

public class GatewayAuthenticationFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {
        /**
         * 获取token
         */
        String token = resolveToken(serverWebExchange.getRequest());

        return null;
    }

    private String resolveToken(ServerHttpRequest request) {
        String bearerToken = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BaseCode.HEADER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
