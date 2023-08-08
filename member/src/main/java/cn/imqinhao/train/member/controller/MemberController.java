package cn.imqinhao.train.member.controller;

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
    public Integer count() {
        return memberService.count();
    }

    @PostMapping("/register")
    public Long register(String mobile) {
        return memberService.register(mobile);
    }

}
