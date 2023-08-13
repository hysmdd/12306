package cn.imqinhao.train.batch.controller;

import cn.imqinhao.train.batch.feign.BusinessFeign;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Martis
 * @create 2023-08-07 19:17:13
 */
@RestController
public class TestController {
    
    private static final Logger LOG = LoggerFactory.getLogger(TestController.class);

    @Resource
    private BusinessFeign businessFeign;

    @RequestMapping("/hello")
    public String hello() {
        String businessHello = businessFeign.hello();
        LOG.info(businessHello);
        return "Hello World, Batch";
    }

}
