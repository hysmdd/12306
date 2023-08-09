package cn.imqinhao.train.member.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.imqinhao.train.common.util.SnowUtil;
import cn.imqinhao.train.member.domain.Passenger;
import cn.imqinhao.train.member.mapper.PassengerMapper;
import cn.imqinhao.train.member.req.PassengerSaveReq;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author Martis
 * @create 2023-08-09 15:19:47
 */
@Service
public class PassengerService {

    @Resource
    private PassengerMapper passengerMapper;

    public void save(PassengerSaveReq req) {
        Passenger passenger = BeanUtil.copyProperties(req, Passenger.class);
        passenger.setId(SnowUtil.getSnowflakeNextId());
        DateTime now = DateTime.now();
        passenger.setCreateTime(now);
        passenger.setUpdateTime(now);
        passengerMapper.insert(passenger);
    }

}
