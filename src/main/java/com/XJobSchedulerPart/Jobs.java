package com.XJobSchedulerPart;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 定时任务框架Demo QuartZ
 */
public class Jobs implements Job {

    /**
     * 需要执行的任务
     * @param jobExecutionContext
     * @throws JobExecutionException
     */
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String printTime = new SimpleDateFormat("yy-MM-dd HH-mm-ss").format(new Date());
        System.out.println(" PrintWordsJob start at: " +
                printTime + ", prints: Hello Job-" + new Random().nextInt(100));

    }



}
