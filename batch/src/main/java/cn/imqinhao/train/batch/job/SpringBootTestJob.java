package cn.imqinhao.train.batch.job;

/**
 * 自带的定时任务只适合单体应用，不适合集群
 * 没法实时更改定时任务状态和策略
 * @author Martis
 * @create 2023-08-12 11:45:58
 */
// @EnableScheduling
// @Component
public class SpringBootTestJob {

    // @Scheduled(cron = "0/5 * * * * ?")
    private void test() {
        // 增加分布式锁，解决集群问题
        System.out.println("SpringBootTestJob.test");
    }

}
