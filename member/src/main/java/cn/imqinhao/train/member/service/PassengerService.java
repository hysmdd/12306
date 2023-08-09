package cn.imqinhao.train.member.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import cn.imqinhao.train.common.context.LoginMemberContext;
import cn.imqinhao.train.common.resp.PageResp;
import cn.imqinhao.train.common.util.SnowUtil;
import cn.imqinhao.train.member.domain.Passenger;
import cn.imqinhao.train.member.domain.PassengerExample;
import cn.imqinhao.train.member.mapper.PassengerMapper;
import cn.imqinhao.train.member.req.PassengerQueryReq;
import cn.imqinhao.train.member.req.PassengerSaveReq;
import cn.imqinhao.train.member.resp.PassengerQueryResp;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Martis
 * @create 2023-08-09 15:19:47
 */
@Service
public class PassengerService {
    
    private static final Logger LOG = LoggerFactory.getLogger(PassengerService.class);

    @Resource
    private PassengerMapper passengerMapper;

    /**
     * 添加乘车人
     * @author :Martis
     * @create :2023-08-09 17:23:42
     * @param req 乘车人信息
     */
    public void save(PassengerSaveReq req) {
        Passenger passenger = BeanUtil.copyProperties(req, Passenger.class);
        passenger.setId(SnowUtil.getSnowflakeNextId());
        passenger.setMemberId(LoginMemberContext.getId());
        DateTime now = DateTime.now();
        passenger.setCreateTime(now);
        passenger.setUpdateTime(now);
        passengerMapper.insert(passenger);
    }

    /**
     * 查询乘车人
     * @author :Martis
     * @create :2023-08-09 17:25:44
     * @param req 查询条件
     * @return 乘车人
     */
    public PageResp<PassengerQueryResp> queryList(PassengerQueryReq req) {
        Long memberId = req.getMemberId();
        PassengerExample passengerExample = new PassengerExample();
        PassengerExample.Criteria criteria = passengerExample.createCriteria();
        if (ObjectUtil.isNotNull(memberId)) {
            criteria.andMemberIdEqualTo(memberId);
        }
        LOG.info("查询页码：{}", req.getPage());
        LOG.info("每页条数：{}", req.getSize());
        PageHelper.startPage(req.getPage(), req.getSize());
        List<Passenger> passengers = passengerMapper.selectByExample(passengerExample);
        PageInfo<Passenger> pageInfo = new PageInfo<>(passengers);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());
        List<PassengerQueryResp> list = BeanUtil.copyToList(passengers, PassengerQueryResp.class);
        PageResp<PassengerQueryResp> objectPageResp = new PageResp<>();
        objectPageResp.setTotal(pageInfo.getTotal());
        objectPageResp.setList(list);
        return objectPageResp;
    }

}
