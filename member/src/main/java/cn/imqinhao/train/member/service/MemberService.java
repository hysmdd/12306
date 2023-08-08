package cn.imqinhao.train.member.service;

import cn.hutool.core.collection.CollUtil;
import cn.imqinhao.train.member.domain.Member;
import cn.imqinhao.train.member.domain.MemberExample;
import cn.imqinhao.train.member.mapper.MemberMapper;
import cn.imqinhao.train.member.req.MemberRegisterReq;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Martis
 * @create 2023-08-08 14:36:03
 */
@Service
public class MemberService {

    @Resource
    MemberMapper memberMapper;

    public int count() {
        return Math.toIntExact(memberMapper.countByExample(null));
    }

    public Long register(MemberRegisterReq req) {
        String mobile = req.getMobile();
        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria().andMobileEqualTo(mobile);
        List<Member> members = memberMapper.selectByExample(memberExample);
        if (CollUtil.isNotEmpty(members)) {
            // return members.get(0).getId();
            throw new RuntimeException("手机号已注册");
        }
        Member member = new Member();
        member.setId(System.currentTimeMillis());
        member.setMobile(mobile);
        memberMapper.insert(member);
        return member.getId();
    }

}
