package cn.imqinhao.train.member.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Martis
 * @create 2023-08-07 19:17:13
 */
@RestController
@RefreshScope
public class TestController {

    @Value("${test.nacos}")
    private String testNacos;

    @RequestMapping("/hello")
    public String hello() {
        // return "Hello Member!";
        return String.format("Hello %s!", testNacos);
    }

}
