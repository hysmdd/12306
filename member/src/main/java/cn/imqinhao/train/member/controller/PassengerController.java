package cn.imqinhao.train.member.controller;

import cn.imqinhao.train.common.context.LoginMemberContext;
import cn.imqinhao.train.common.resp.CommonResp;
import cn.imqinhao.train.common.resp.PageResp;
import cn.imqinhao.train.member.req.PassengerQueryReq;
import cn.imqinhao.train.member.req.PassengerSaveReq;
import cn.imqinhao.train.member.resp.PassengerQueryResp;
import cn.imqinhao.train.member.service.PassengerService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * @author Martis
 * @create 2023-08-09 15:23:04
 */
@RestController
@RequestMapping("/passenger")
public class PassengerController {

    @Resource
    private PassengerService passengerService;

    /**
     * 添加乘车人
     * @author :Martis
     * @create :2023-08-09 17:30:33
     * @param req 乘车人信息
     */
    @PostMapping("/save")
    public CommonResp<Object> save(@Valid @RequestBody PassengerSaveReq req) {
        passengerService.save(req);
        return new CommonResp<>();
    }

    /**
     * 查询乘车人
     * @author :Martis
     * @create :2023-08-09 17:33:48
     * @param req 查询条件
     * @return 乘车人
     */
    @GetMapping("/query-list")
    public CommonResp<PageResp<PassengerQueryResp>> queryList(@Valid PassengerQueryReq req) {
        req.setMemberId(LoginMemberContext.getId());
        PageResp<PassengerQueryResp> list = passengerService.queryList(req);
        return new CommonResp<>(list);
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp<Object> delete(@PathVariable Long id) {
        passengerService.delete(id);
        return new CommonResp<>();
    }

}
