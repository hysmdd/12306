package cn.imqinhao.train.batch.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Martis
 * @create 2023-08-13 08:48:30
 */
@FeignClient(name = "business", url = "http://127.0.0.1:37676/business")
public interface BusinessFeign {

    @GetMapping("/hello")
    String hello();

}
