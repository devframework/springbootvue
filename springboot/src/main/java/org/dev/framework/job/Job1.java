package org.dev.framework.job;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

//可以防止并发
@DisallowConcurrentExecution
@Slf4j
public class Job1 extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("**--开始执行任务---**");
        log.info("-------------------这是定时JOB-----------------");
        log.info("**--任务执行完成---**");
    }
}
