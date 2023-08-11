package cn.imqinhao.train.business.controller.admin;

import cn.imqinhao.train.business.req.TrainCarriageQueryReq;
import cn.imqinhao.train.business.req.TrainCarriageSaveReq;
import cn.imqinhao.train.business.resp.TrainCarriageQueryResp;
import cn.imqinhao.train.business.service.TrainCarriageService;
import cn.imqinhao.train.common.resp.CommonResp;
import cn.imqinhao.train.common.resp.PageResp;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/train-carriage")
public class TrainCarriageAdminController {

    @Resource
    private TrainCarriageService trainCarriageService;

    @PostMapping("/save")
    public CommonResp<Object> save(@Valid @RequestBody TrainCarriageSaveReq req) {
        trainCarriageService.save(req);
        return new CommonResp<>();
    }

    @GetMapping("/query-list")
    public CommonResp<PageResp<TrainCarriageQueryResp>> queryList(@Valid TrainCarriageQueryReq req) {
        PageResp<TrainCarriageQueryResp> list = trainCarriageService.queryList(req);
        return new CommonResp<>(list);
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp<Object> delete(@PathVariable Long id) {
        trainCarriageService.delete(id);
        return new CommonResp<>();
    }

}
