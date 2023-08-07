package cn.imqinhao.train.member.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Martis
 * @create 2023-08-07 19:17:13
 */
@RestController
public class TestController {

    @RequestMapping("/hello")
    public String hello() {
        return "Hello World";
    }

}
