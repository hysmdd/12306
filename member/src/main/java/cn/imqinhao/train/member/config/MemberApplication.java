package cn.imqinhao.train.member.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Martis
 * @create 2023-08-07 20:56:15
 */
@SpringBootApplication
@ComponentScan("cn.imqinhao.train.member")
public class MemberApplication {
    public static void main(String[] args) {
        SpringApplication.run(MemberApplication.class, args);
    }
}
