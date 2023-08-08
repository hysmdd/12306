package cn.imqinhao.train.member.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

/**
 * @author Martis
 * @create 2023-08-07 20:56:15
 */
@SpringBootApplication
@ComponentScan("cn.imqinhao")
public class MemberApplication {

    private static final Logger LOG = LoggerFactory.getLogger(MemberApplication.class);

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(MemberApplication.class);
        Environment env = app.run(args).getEnvironment();
        LOG.info("启动成功！！");
        LOG.info("测试地址：\thttp://127.0.0.1:{}{}", env.getProperty("server.port"), env.getProperty("server.servlet.context-path"));

    }
}
