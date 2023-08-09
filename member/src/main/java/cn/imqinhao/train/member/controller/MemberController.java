package cn.imqinhao.train.member.controller;

import cn.imqinhao.train.common.resp.CommonResp;
import cn.imqinhao.train.member.req.MemberRegisterReq;
import cn.imqinhao.train.member.req.MemberSendCodeReq;
import cn.imqinhao.train.member.service.MemberService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
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

    /**
     * 注册/登录
     * @author :Martis
     * @create :2023-08-09 08:32:59
     * @param req 手机号
     * @return 注册id
     */
    @PostMapping("/register")
    public CommonResp<Long> register(@Valid MemberRegisterReq req) {
        Long registerId = memberService.register(req);
        return new CommonResp<>(registerId);
    }

    /**
     * 获取短信验证码
     * @author :Martis
     * @create :2023-08-09 08:32:40
     * @param req 手机号
     * @return 短信验证码
     */
    @PostMapping("/send-code")
    public CommonResp<Long> sendCode(@Valid MemberSendCodeReq req) {
        memberService.sendCode(req);
        return new CommonResp<>();
    }

}
