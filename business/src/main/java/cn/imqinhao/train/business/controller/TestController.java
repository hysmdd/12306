package cn.imqinhao.train.business.controller;

import cn.imqinhao.train.business.req.StationQueryReq;
import cn.imqinhao.train.business.req.StationSaveReq;
import cn.imqinhao.train.business.resp.StationQueryResp;
import cn.imqinhao.train.business.service.StationService;
import cn.imqinhao.train.common.resp.CommonResp;
import cn.imqinhao.train.common.resp.PageResp;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

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

    @RestController
    @RequestMapping("/admin/station")
    public static class AdminStationController {

        @Resource
        private StationService stationService;

        @PostMapping("/save")
        public CommonResp<Object> save(@Valid @RequestBody StationSaveReq req) {
            stationService.save(req);
            return new CommonResp<>();
        }

        @GetMapping("/query-list")
        public CommonResp<PageResp<StationQueryResp>> queryList(@Valid StationQueryReq req) {
            PageResp<StationQueryResp> list = stationService.queryList(req);
            return new CommonResp<>(list);
        }

        @DeleteMapping("/delete/{id}")
        public CommonResp<Object> delete(@PathVariable Long id) {
            stationService.delete(id);
            return new CommonResp<>();
        }

    }
}
