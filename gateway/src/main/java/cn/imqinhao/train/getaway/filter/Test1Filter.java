// package cn.imqinhao.train.getaway.config;
//
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.cloud.gateway.filter.GatewayFilterChain;
// import org.springframework.cloud.gateway.filter.GlobalFilter;
// import org.springframework.core.Ordered;
// import org.springframework.stereotype.Component;
// import org.springframework.web.server.ServerWebExchange;
// import reactor.core.publisher.Mono;
//
// /**
//  * @author Martis
//  * @create 2023-08-09 13:30:12
//  */
// @Component
// public class Test1Filter implements GlobalFilter, Ordered {
//
//     private static final Logger LOG = LoggerFactory.getLogger(Test1Filter.class);
//
//     @Override
//     public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//         LOG.info("Test1Filter");
//         return chain.filter(exchange);
//     }
//
//     @Override
//     public int getOrder() {
//         return 0;
//     }
// }