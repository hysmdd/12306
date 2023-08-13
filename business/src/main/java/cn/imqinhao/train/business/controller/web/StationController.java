package cn.imqinhao.train.business.controller.web;


import cn.imqinhao.train.business.resp.StationQueryResp;
import cn.imqinhao.train.business.service.StationService;
import cn.imqinhao.train.common.resp.CommonResp;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/station")
public class StationController {

    @Resource
    private StationService stationService;

    @GetMapping("/query-all")
    public CommonResp<List<StationQueryResp>> queryAll() {
        return new CommonResp<>(stationService.queryAll());
    }

}
