package cn.imqinhao.train.business.feign;

import cn.imqinhao.train.common.req.MemberTicketReq;
import cn.imqinhao.train.common.resp.CommonResp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author Martis
 * @create 2023-08-16 21:18:29
 */
@FeignClient(name = "member", url = "http://127.0.0.1:17676")
public interface MemberFeign {

    @GetMapping("/member/feign/ticket/save")
    CommonResp<Object> save(@RequestBody MemberTicketReq req);

}
