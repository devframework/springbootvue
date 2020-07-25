package org.dev.framework.modules.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.dev.framework.modules.sys.entity.SysLog;
import org.dev.framework.modules.sys.service.SysLogService;
import org.dev.framework.common.PaginAtion;
import org.dev.framework.common.ResponseResult;
import org.dev.framework.modules.sys.entity.SysLog;
import org.dev.framework.modules.sys.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 操作记录 前端控制器
 * </p>
 *
 * @author dean.x.liu
 * @since 2020-07-06
 */
@RestController
@RequestMapping("/sysLog")
public class SysLogController {

    @Autowired
    SysLogService sysLogService;

    /**
     * 查询日志列表
     *
     * @param sysLog
     * @param pagination
     * @return
     */
    @RequestMapping("/list")
    public ResponseResult<IPage<SysLog>> list(SysLog sysLog, PaginAtion pagination) {
        return ResponseResult.success(sysLogService.page(pagination.getPage(),
                new QueryWrapper<>(sysLog)));
    }
}

