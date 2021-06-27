package com.XJobSchedulerPart;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.concurrent.TimeUnit;

public class MyScheduler {

    public static void main(String[] args) throws SchedulerException, InterruptedException {
        // standard Scheduler Factory;
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();

        Scheduler scheduler = schedulerFactory.getScheduler();
        // Job 实例
        // 使用 jobDetail可以解决并发访问的问题。
        JobDetail jobDetail = JobBuilder.newJob(Jobs.class).
                withIdentity("job1", "group1").build();

//        Trigger trigger = TriggerBuilder.newTrigger()
//                .withIdentity("trigger1", "triggerGroup1")
//                .startNow().withSchedule(SimpleScheduleBuilder.simpleSchedule()
//                        .withIntervalInSeconds(2).withRepeatCount(5)).build();

        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1","triggerGroup1")
                .usingJobData("trigger1", "这是JobDetail1的trigger").startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(1)
                        // 注意，这个repeatCount 是从 0 开始计算的，写2实际上是执行了3次。
                .withRepeatCount(2)).build();

        CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "triggerGroup1")
                .usingJobData("trigger1", "这是jobDetail1的trigger")
                .startNow()
                .withSchedule(CronScheduleBuilder
                        .cronSchedule("0 0/1 13 * * ?")).build();
//        cronTrigger.

        scheduler.scheduleJob(jobDetail ,cronTrigger);
        scheduler.start();

//        TimeUnit.MINUTES.sleep(1);
//        scheduler.shutdown();
//        System.out.println("--------scheduler shutdown ! ------------");

    }

}
