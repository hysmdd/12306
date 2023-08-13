package cn.imqinhao.train.business.controller.web;


import cn.imqinhao.train.business.resp.TrainQueryResp;
import cn.imqinhao.train.business.service.TrainService;
import cn.imqinhao.train.common.resp.CommonResp;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/train")
public class TrainController {

    @Resource
    private TrainService trainService;
    @GetMapping("/query-all")
    public CommonResp<List<TrainQueryResp>> queryAll() {
        return new CommonResp<>(trainService.queryAll());
    }

}
