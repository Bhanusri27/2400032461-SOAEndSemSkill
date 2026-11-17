package com.klu.gateway;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

/** Runs AFTER the load balancer filter, so it logs the instance that round-robin picked. */
@Component
public class RoundRobinLogFilter implements GlobalFilter, Ordered {

    private static final Logger log = LoggerFactory.getLogger(RoundRobinLogFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        URI target = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR);
        log.info("GATEWAY: {} {} -> routed to {}", exchange.getRequest().getMethod(),
                exchange.getRequest().getURI().getPath(), target);
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 10151; // ReactiveLoadBalancerClientFilter is 10150
    }
}
