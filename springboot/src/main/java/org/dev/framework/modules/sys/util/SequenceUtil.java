package org.dev.framework.modules.sys.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.activiti.engine.impl.event.logger.DatabaseEventFlusher;
import org.dev.framework.modules.sys.entity.SysSequence;
import org.dev.framework.modules.sys.entity.SysSequenceLog;
import org.dev.framework.modules.sys.entity.SysSequenceQueue;
import org.dev.framework.modules.sys.service.SysSequenceService;
import org.dev.framework.exception.CustomException;
import org.dev.framework.modules.sys.entity.SysSequence;
import org.dev.framework.modules.sys.mapper.QuartzMapper;
import org.dev.framework.modules.sys.service.SysSequenceLogService;
import org.dev.framework.modules.sys.service.SysSequenceQueueService;
import org.dev.framework.modules.sys.service.SysSequenceService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class SequenceUtil {

    @Autowired
    SysSequenceService sysSequenceService;

    @Autowired
    SysSequenceLogService sysSequenceLogService;

    @Autowired
    SysSequenceQueueService sysSequenceQueueService;

    private final static String defalutPrefix = "defalut";

    /**
     * @param SequenceCode
     * @return
     * @throws CustomException
     */
    public synchronized String GeneratorCode(String SequenceCode) throws CustomException {
        return this.GeneratorCode(SequenceCode, "defalut");
    }

    /**
     * 生成流水码
     *
     * @param SequenceCode
     * @param prefix
     * @return
     * @throws CustomException
     */
    public synchronized String GeneratorCode(String SequenceCode, String prefix) throws CustomException {
        String flowCode = "";
        SysSequence sysSequence = new SysSequence();
        sysSequence.setCode(SequenceCode);
        QueryWrapper queryWrapper = new QueryWrapper(sysSequence);
        sysSequence = this.sysSequenceService.getOne(queryWrapper);
        if (sysSequence == null) {
            throw new CustomException("编码不存在");
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(sysSequence.getDateFormat());
        Date now = new Date();
        String currentDate = dateFormat.format(now);
        //查看队列中是否有数据
        SysSequenceQueue sysSequenceQueue = new SysSequenceQueue();
        sysSequenceQueue.setSequenceId(sysSequence.getId());
        sysSequenceQueue.setPrefix(prefix);
        sysSequenceQueue.setCurrentDate(currentDate);
        QueryWrapper queryWrapper1 = new QueryWrapper(sysSequenceQueue);
        int currentValue = sysSequence.getInitValue();
        SysSequenceQueue sysSequenceQueue1 = this.sysSequenceQueueService.getOne(queryWrapper1);
        if (prefix.equals(defalutPrefix)) {
            prefix = "";
        }
        if (sysSequenceQueue1 == null) {
            //不存在数据记录
            flowCode = prefix + sysSequence.getPrefix() + sysSequence.getSeparator() + dateFormat.format(now) + this.flowCode(sysSequence.getNumLength(), currentValue);
            //保存队列记录
            sysSequenceQueue.setCurrentCode(flowCode);
            sysSequenceQueue.setCurrentValue(sysSequence.getInitValue());
            this.sysSequenceQueueService.save(sysSequenceQueue);
            this.insertLog(sysSequenceQueue);
        } else {
            //存在数据记录
            //获取当前值
            int currentIndex = sysSequenceQueue1.getCurrentValue();
            currentIndex = currentIndex + 1;
            flowCode = prefix + sysSequence.getPrefix() + sysSequence.getSeparator() + dateFormat.format(now) + this.flowCode(sysSequence.getNumLength(), currentIndex);
            sysSequenceQueue1.setCurrentValue(currentIndex);
            sysSequenceQueue1.setCurrentCode(flowCode);
            this.sysSequenceQueueService.updateById(sysSequenceQueue1);
            this.insertLog(sysSequenceQueue1);
        }
        return flowCode;
    }

    /**
     * 左边补0
     *
     * @param length
     * @param index
     * @return
     * @throws CustomException
     */
    private String flowCode(int length, int index) throws CustomException {
        //得到一个NumberFormat的实例
        NumberFormat nf = NumberFormat.getInstance();
        //设置是否使用分组
        nf.setGroupingUsed(false);
        //设置最大整数位数
        nf.setMaximumIntegerDigits(length);
        //设置最小整数位数
        nf.setMinimumIntegerDigits(length);
        //获取最大值
        int maxValue = 0;
        String intValueStr = "";
        for (int i = 0; i < length; i++) {
            intValueStr += "9";
        }
        maxValue = Integer.valueOf(intValueStr);
        if (maxValue < index) {
            throw new CustomException("流水码已到达最大值,无法生成新的流水码！");
        }
        return nf.format(index);
    }

    /**
     * 保存流水日志
     *
     * @param sysSequenceQueue
     */
    private void insertLog(SysSequenceQueue sysSequenceQueue) {
        SysSequenceLog sysSequenceLog = new SysSequenceLog();
        BeanUtils.copyProperties(sysSequenceQueue, sysSequenceLog);
        sysSequenceLog.setId(null);
        sysSequenceLog.setFlowCode(sysSequenceQueue.getCurrentCode());
        this.sysSequenceLogService.save(sysSequenceLog);
    }
}
