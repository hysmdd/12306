package cn.imqinhao.train.member.controller;

import cn.imqinhao.train.common.resp.CommonResp;
import cn.imqinhao.train.member.req.MemberRegisterReq;
import cn.imqinhao.train.member.service.MemberService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * @author Martis
 * @create 2023-08-08 14:37:18
 */
@RestController
@RequestMapping("/member")
public class MemberController {

    @Resource
    private MemberService memberService;

    @GetMapping("/count")
    public CommonResp<Integer> count() {
        int count = memberService.count();
        return new CommonResp(count);
    }

    @PostMapping("/register")
    public CommonResp<Long> register(MemberRegisterReq req) {
        Long registerId = memberService.register(req);
        return new CommonResp<>(registerId);
    }

}
