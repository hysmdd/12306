package cn.imqinhao.train.member.req;

import jakarta.validation.constraints.NotBlank;

/**
 * @author Martis
 * @create 2023-08-08 15:38:09
 */
public class MemberRegisterReq {

    @NotBlank(message = "【手机号】不能为空")
    private String mobile;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public String toString() {
        return "MemberRegisterReq{" +
                "mobile='" + mobile + '\'' +
                '}';
    }
}
