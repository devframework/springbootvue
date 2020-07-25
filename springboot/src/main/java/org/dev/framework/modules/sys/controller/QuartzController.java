package org.dev.framework.modules.sys.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.dev.framework.modules.sys.entity.QuartzJob;
import org.dev.framework.modules.sys.service.QuartzService;
import org.dev.framework.common.PaginAtion;
import org.dev.framework.common.ResponseResult;
import org.dev.framework.modules.sys.entity.QuartzJob;
import org.dev.framework.modules.sys.service.QuartzService;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("quartz")
public class QuartzController {

    private static final String TRIGGER_IDENTITY = "trigger";
    private static final String PARAM_NAME = "paramName";
    private static final String PARAM_VALUE = "paramValue";
    private static final String SCHEDULER_INSTANCE_NAME = "schedulerInstanceName";

    @Autowired
    Scheduler scheduler;

    @Autowired
    QuartzService quartzService;


    /**
     * 定时JOB查询
     *
     * @param quartz
     * @return
     */
    @GetMapping("list")
    public ResponseResult<IPage<QuartzJob>> list(QuartzJob quartz, PaginAtion pagination) {
        Page<QuartzJob> page = pagination.getPage();
        return ResponseResult.success(this.quartzService.page(page, quartz.getJobName()));
    }


    /**
     * 保存定时任务
     * 存在修改 不存在新增
     *
     * @param quartz
     * @return
     */
    @PostMapping("save")
    public ResponseResult save(@RequestBody QuartzJob quartz) {
        try {
            //如果是修改  展示旧的 任务
            if (quartz.getOldJobGroup() != null && !"".equals(quartz.getOldJobGroup())) {
                JobKey key = new JobKey(quartz.getOldJobName(), quartz.getOldJobGroup());
                scheduler.deleteJob(key);
            }

            //构建job信息
            Class cls = Class.forName(quartz.getJobClassName());
            cls.newInstance();
            JobDetail job = JobBuilder.newJob(cls).withIdentity(quartz.getJobName(),
                    quartz.getJobGroup())
                    .withDescription(quartz.getDescription()).build();
            putDataMap(job, quartz);

            // 触发时间点
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(quartz.getCronExpression().trim());
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity(TRIGGER_IDENTITY + quartz.getJobName(), quartz.getJobGroup())
                    .startNow().withSchedule(cronScheduleBuilder).build();
            //交由Scheduler安排触发
            scheduler.scheduleJob(job, trigger);
            return ResponseResult.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.error();
        }
    }


    /**
     * 删除定时JOb
     *
     * @param quartzs
     * @return
     */
    @PostMapping("delete")
    @Transactional
    public ResponseResult delete(@RequestBody  List<QuartzJob> quartzs) {
        try {
            for (QuartzJob quartz : quartzs) {
                TriggerKey triggerKey = TriggerKey.triggerKey(TRIGGER_IDENTITY + quartz.getJobName(), quartz.getJobGroup());
                // 停止触发器
                scheduler.pauseTrigger(triggerKey);
                // 移除触发器
                scheduler.unscheduleJob(triggerKey);
                // 删除任务
                scheduler.deleteJob(JobKey.jobKey(quartz.getJobName(), quartz.getJobGroup()));
                System.out.println("removeJob:" + JobKey.jobKey(quartz.getJobName()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.error();
        }
        return ResponseResult.success();
    }

    /**
     * 手工调用
     *
     * @param quartzs
     * @return
     */
    @PostMapping("start")
    public ResponseResult start(@RequestBody List<QuartzJob> quartzs) {
        try {
            for (QuartzJob quartz : quartzs) {
                // 启动JOB
                scheduler.triggerJob(JobKey.jobKey(quartz.getJobName(), quartz.getJobGroup()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.error();
        }
        return ResponseResult.success();
    }

    /**
     * 暂停JOB
     *
     * @param quartzs
     * @return
     */
    @PostMapping("pause")
    public ResponseResult pause(@RequestBody List<QuartzJob> quartzs) {
        try {
            for (QuartzJob quartz : quartzs) {
                // 启动JOB
                scheduler.pauseJob(JobKey.jobKey(quartz.getJobName(), quartz.getJobGroup()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.error();
        }
        return ResponseResult.success();
    }


    /**
     * 暂停所有JOB
     *
     * @param quartz
     * @return
     */
    @GetMapping("pause-all")
    public ResponseResult pauseAll( ) {
        try {
            // 启动JOB
            scheduler.pauseAll();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.error();
        }
        return ResponseResult.success();
    }


    /**
     * 恢复JOB
     *
     * @param quartzs
     * @return
     */
    @PostMapping("resume")
    public ResponseResult resume( @RequestBody List<QuartzJob> quartzs) {
        try {
            for (QuartzJob quartz : quartzs) {
                // 启动JOB
                scheduler.resumeJob(JobKey.jobKey(quartz.getJobName(), quartz.getJobGroup()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.error();
        }
        return ResponseResult.success();
    }

    /**
     * 恢复JOB
     *
     * @param quartz
     * @return
     */
    @GetMapping("resume-all")
    public ResponseResult resumeAll() {
        try {
            // 启动JOB
            scheduler.resumeAll();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.error();
        }
        return ResponseResult.success();
    }

    /**
     * 参数处理
     *
     * @param job
     * @param quartz
     */
    private void putDataMap(JobDetail job, QuartzJob quartz) {

        // 将scheduler instanceName存入jobDataMap，方便业务job中进行数据库操作
        JobDataMap jobDataMap = job.getJobDataMap();
        jobDataMap.put(SCHEDULER_INSTANCE_NAME, "quartzScheduler");

        List<Map<String, Object>> jobDataParam = quartz.getJobDataParam();
        if (jobDataParam == null || jobDataParam.isEmpty()) {
            return;
        }
        jobDataParam.forEach(jobData -> jobDataMap.put(String.valueOf(jobData.get(PARAM_NAME)), jobData.get(PARAM_VALUE)));
    }
}
