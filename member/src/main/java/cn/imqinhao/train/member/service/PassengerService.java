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
        DateTime now = DateTime.now();
        Passenger passenger = BeanUtil.copyProperties(req, Passenger.class);
        // id为空，表示保存
        if (ObjectUtil.isNull(passenger.getId())) {
            passenger.setId(SnowUtil.getSnowflakeNextId());
            passenger.setMemberId(LoginMemberContext.getId());
            passenger.setCreateTime(now);
            passenger.setUpdateTime(now);
            passengerMapper.insert(passenger);
        } else {
            // id不为空，表示修改
            passenger.setUpdateTime(now);
            passengerMapper.updateByPrimaryKey(passenger);
        }
    }

    /**
     * 查询乘车人
     * @author :Martis
     * @create :2023-08-09 17:25:44
     * @param req 查询条件
     * @return 乘车人
     */
    public PageResp<PassengerQueryResp> queryList(PassengerQueryReq req) {
        // 获取会员ID
        Long memberId = req.getMemberId();
        // 构造条件
        PassengerExample passengerExample = new PassengerExample();
        PassengerExample.Criteria criteria = passengerExample.createCriteria();
        // 如果会员ID不为空
        if (ObjectUtil.isNotNull(memberId)) {
            // 添加条件
            criteria.andMemberIdEqualTo(memberId);
        }
        // 打印日志
        LOG.info("查询页码：{}", req.getPage());
        LOG.info("每页条数：{}", req.getSize());
        // 分页查询
        PageHelper.startPage(req.getPage(), req.getSize());
        // 根据条件查询会员列表
        List<Passenger> passengers = passengerMapper.selectByExample(passengerExample);
        // 构造分页对象
        PageInfo<Passenger> pageInfo = new PageInfo<>(passengers);
        // 打印日志
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());
        // 拷贝对象
        List<PassengerQueryResp> list = BeanUtil.copyToList(passengers, PassengerQueryResp.class);
        // 构造返回数据
        PageResp<PassengerQueryResp> objectPageResp = new PageResp<>();
        // 设置匹配的会员总数量
        objectPageResp.setTotal(pageInfo.getTotal());
        // 封装数据
        objectPageResp.setList(list);
        // 返回数据
        return objectPageResp;
    }

}
