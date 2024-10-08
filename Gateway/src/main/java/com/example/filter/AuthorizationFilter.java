package com.example.filter;

import com.example.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import static util.HttpConstants.CUSTOM_USER_HEADER;
import static util.HttpConstants.CUSTOM_USER_ROLE_HEADER;

@Component
public class AuthorizationFilter implements GatewayFilterFactory<AuthorizationFilter.Config> {
    private static final String JWT_TOKEN_PREFIX = "Bearer ";

    @Autowired
    private TokenService tokenService;

    public static class Config {
    }

    @Override
    public Class<Config> getConfigClass() {
        return Config.class;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            if (token != null) {
                token = token.substring(JWT_TOKEN_PREFIX.length());
            }
            if (!tokenService.checkToken(token)) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            return chain.filter(replaceCustomHeadersWithValuesFromJwt(exchange, token));
        };
    }

    private ServerWebExchange replaceCustomHeadersWithValuesFromJwt(ServerWebExchange exchange, String token) {
        String role = tokenService.getRoleFromToken(token);
        String user = tokenService.getClientFromToken(token);
        ServerHttpRequest request = exchange.getRequest()
                .mutate()
                .headers(httpHeaders -> {
                    httpHeaders.set(CUSTOM_USER_HEADER, user);
                    httpHeaders.set(CUSTOM_USER_ROLE_HEADER, role);
                })
                .build();
        return exchange.mutate().request(request).build();
    }
}
