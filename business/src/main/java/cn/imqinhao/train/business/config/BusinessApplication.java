package cn.imqinhao.train.business.config;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @author Martis
 * @create 2023-08-10 17:55:41
 */
@SpringBootApplication
@ComponentScan("cn.imqinhao")
@MapperScan("cn.imqinhao.train.*.mapper")
public class BusinessApplication {
    
    private static final Logger LOG = LoggerFactory.getLogger(BusinessApplication.class);

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(BusinessApplication.class);
        ConfigurableEnvironment environment = app.run(args).getEnvironment();
        LOG.info("启动成功！！");
        LOG.info("测试地址：http://127.0.0.1:{}{}", environment.getProperty("server.port"), environment.getProperty("server.servlet.context-path"));
    }
    
}
