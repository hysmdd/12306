package cn.imqinhao.train.member.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.imqinhao.train.common.exception.BusinessException;
import cn.imqinhao.train.common.exception.BusinessExceptionEnum;
import cn.imqinhao.train.common.util.JwtUtil;
import cn.imqinhao.train.common.util.SnowUtil;
import cn.imqinhao.train.member.domain.Member;
import cn.imqinhao.train.member.domain.MemberExample;
import cn.imqinhao.train.member.mapper.MemberMapper;
import cn.imqinhao.train.member.req.MemberLoginReq;
import cn.imqinhao.train.member.req.MemberRegisterReq;
import cn.imqinhao.train.member.req.MemberSendCodeReq;
import cn.imqinhao.train.member.resp.MemberLoginResp;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Martis
 * @create 2023-08-08 14:36:03
 */
@Service
public class MemberService {
    
    private static final Logger LOG = LoggerFactory.getLogger(MemberService.class);

    @Resource
    MemberMapper memberMapper;

    /**
     * 统计有多少个用户
     * @author :Martis
     * @create :2023-08-09 09:02:21
     * @return 用户数量
     */
    public int count() {
        return Math.toIntExact(memberMapper.countByExample(null));
    }

    /**
     * 用户注册
     * @author :Martis
     * @create :2023-08-09 08:24:48
     * @param req 手机号
     * @return 注册id
     */
    public Long register(MemberRegisterReq req) {
        String mobile = req.getMobile();
        Member memberDB = selectMemberByMobile(mobile);
        if (ObjectUtil.isNotNull(memberDB)) {
            // return members.get(0).getId();
            throw new BusinessException(BusinessExceptionEnum.MEMBER_MOBILE_EXIST);
        }
        Member member = new Member();
        member.setId(SnowUtil.getSnowflakeNextId());
        member.setMobile(mobile);
        memberMapper.insert(member);
        return member.getId();
    }

    /**
     * 获取短信验证码
     * @author :Martis
     * @create :2023-08-09 08:38:50
     * @param req 手机号
     */
    public void sendCode(MemberSendCodeReq req) {
        String mobile = req.getMobile();
        Member memberDB = selectMemberByMobile(mobile);
        // 如果手机号不存在，则插入记录
        if (ObjectUtil.isNull(memberDB)) {
            LOG.info("手机号不存在，插入一条记录");
            Member member = new Member();
            member.setId(SnowUtil.getSnowflakeNextId());
            member.setMobile(mobile);
            memberMapper.insert(member);
        } else {
            LOG.info("手机号已存在，不插入记录");
        }
        // 生成验证码
        // String code = RandomUtil.randomString(4);
        String code = "1314";
        LOG.info("生成短信验证码：{}", code);
        // 保存短信记录表：手机号，短信验证码，有效期，是否已使用，业务类型，发送时间，使用时间
        LOG.info("保存短信记录表");
        // 对接短信通道，发送短信
        LOG.info("对接短信通道");
        // 保存验证码到redis
    }

    /**
     * 登录
     * @author :Martis
     * @create :2023-08-09 09:01:20
     * @param req 手机号、验证码
     */
    public MemberLoginResp login(@Valid MemberLoginReq req) {
        String mobile = req.getMobile();
        String code = req.getCode();
        Member memberDB = selectMemberByMobile(mobile);
        if (ObjectUtil.isNull(memberDB)) {
            throw new BusinessException(BusinessExceptionEnum.MEMBER_MOBILE_NOT_EXIST);
        }
        // 校验短信验证码
        if (!"1314".equals(code)) {
            throw new BusinessException(BusinessExceptionEnum.MEMBER_MOBILE_CODE_ERROR);
        }
        MemberLoginResp memberLoginResp = BeanUtil.copyProperties(memberDB, MemberLoginResp.class);
        String token = JwtUtil.createToken(memberLoginResp.getId(), memberLoginResp.getMobile());
        memberLoginResp.setToken(token);
        return memberLoginResp;
    }

    /**
     * 根据手机号查询用户
     * @author :Martis
     * @create :2023-08-09 09:01:38
     * @param mobile 手机号
     * @return 用户集合
     */
    public Member selectMemberByMobile(String mobile) {
        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria().andMobileEqualTo(mobile);
        List<Member> list = memberMapper.selectByExample(memberExample);
        if (CollUtil.isEmpty(list)) {
            return null;
        } else {
            return list.get(0);
        }
    }

}
