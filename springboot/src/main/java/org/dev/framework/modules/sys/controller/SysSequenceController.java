package org.dev.framework.modules.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.dev.framework.exception.CustomException;
import org.dev.framework.modules.sys.entity.SysSequence;
import org.dev.framework.modules.sys.service.SysSequenceService;
import org.dev.framework.common.PaginAtion;
import org.dev.framework.common.ResponseResult;
import org.dev.framework.core.aop.OperLog;
import org.dev.framework.modules.sys.entity.SysSequence;
import org.dev.framework.modules.sys.service.SysSequenceService;
import org.dev.framework.modules.sys.util.SequenceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * sys_sequence 前端控制器
 * </p>
 *
 * @author dean.x.liu
 * @since 2020-07-08
 */
@RestController
@RequestMapping("/sysSequence")
public class SysSequenceController {
    @Autowired
    SysSequenceService sysSequenceService;

    @Autowired
    SequenceUtil sequenceUtil;

    /**
     * 流水码集合
     *
     * @return
     */
    @GetMapping("/list")
    @OperLog(description = "流水码集合")
    public ResponseResult<IPage<SysSequence>> list(SysSequence sysSequence, PaginAtion pagination) {
        return ResponseResult.success(sysSequenceService.page(pagination.getPage(),
                new QueryWrapper<>(sysSequence)));
    }

    /**
     * 保存流水码信息
     *
     * @param sysSequence
     * @return
     */
    @PostMapping("/save")
    @OperLog(description = "新增/修改流水码")
    public ResponseResult<String> save(@RequestBody SysSequence sysSequence) {
        if (null == sysSequence.getId()) {
            SysSequence sysSequence1 = new SysSequence();
            sysSequence1.setCode(sysSequence.getCode());
            QueryWrapper queryWrapper = new QueryWrapper(sysSequence1);
            List<SysSequence> sysDicList = this.sysSequenceService.list(queryWrapper);
            if (sysDicList != null && sysDicList.size() > 0) {
                return ResponseResult.error("代码为:" + sysSequence.getCode() + "的流水码已存在");
            }
            sysSequence1 = new SysSequence();
            sysSequence1.setName(sysSequence.getName());
            queryWrapper = new QueryWrapper(sysSequence1);
            sysDicList = this.sysSequenceService.list(queryWrapper);
            if (sysDicList != null && sysDicList.size() > 0) {
                return ResponseResult.error("名称为:" + sysSequence.getName() + "的流水码已存在");
            }
            this.sysSequenceService.save(sysSequence);
        } else {
            this.sysSequenceService.updateById(sysSequence);
        }
        return ResponseResult.success();
    }

    /**
     * 批量删除方法
     *
     * @param ids
     * @return
     */
    @PostMapping("/delete")
    @OperLog(description = "批量删除流水码")
    @Transactional
    public ResponseResult<String> delete(@RequestBody List<Long> ids) {
        this.sysSequenceService.removeByIds(ids);
        return ResponseResult.success();
    }

    /**
     * 测试
     */

    @PostMapping("/test")
    public void test() throws CustomException {
        for (int i = 0; i < 100; i++) {
            sequenceUtil.GeneratorCode("FLOW_CODE");
            sequenceUtil.GeneratorCode("FLOW_CODE", "P");
        }
    }
}

