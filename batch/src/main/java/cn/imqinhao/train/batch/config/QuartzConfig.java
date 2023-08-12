// package cn.imqinhao.train.batch.config;
//
// import cn.imqinhao.train.batch.job.TestJob;
// import org.quartz.*;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
//
// /**
//  * @author Martis
//  * @create 2023-08-12 11:54:29
//  */
// @Configuration
// public class QuartzConfig {
//
//     /**
//      * 声明一个任务
//      * @author :Martis
//      * @create :2023-08-12 11:57:09
//      */
//     @Bean
//     public JobDetail jobDetail() {
//         return JobBuilder.newJob(TestJob.class)
//                 .withIdentity("TestJob", "test")
//                 .storeDurably()
//                 .build();
//     }
//
//     /**
//      * 声明一个触发器，什么时候触发这个任务
//      * @author :Martis
//      * @create :2023-08-12 11:59:33
//      */
//     @Bean
//     public Trigger trigger() {
//         return TriggerBuilder.newTrigger()
//                 .forJob(jobDetail())
//                 .withIdentity("trigger", "trigger")
//                 .startNow()
//                 .withSchedule(CronScheduleBuilder.cronSchedule("*/2 * * * * ?"))
//                 .build();
//     }
//
// }
