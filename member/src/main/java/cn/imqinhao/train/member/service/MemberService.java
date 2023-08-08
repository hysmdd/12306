package cn.imqinhao.train.member.service;

import cn.imqinhao.train.member.mapper.MemberMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

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

}
