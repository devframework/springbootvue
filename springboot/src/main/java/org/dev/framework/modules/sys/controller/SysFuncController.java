package org.dev.framework.modules.sys.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.dev.framework.modules.sys.entity.SysFunc;
import org.dev.framework.common.PaginAtion;
import org.dev.framework.common.ResponseResult;

import org.dev.framework.modules.sys.entity.SysFunc;

import org.dev.framework.modules.sys.service.SysFuncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author hlt
 * @since 2020-06-24
 */
@RestController
@RequestMapping("/sysFunc")
public class SysFuncController {

    @Autowired
    SysFuncService sysFuncService;


    /**
     * 返回菜单保存的方法列表
     *
     * @param menuId
     * @param pagination
     * @return
     */
    @RequestMapping("/menu-func-list")
    public ResponseResult<IPage<SysFunc>> menuFuncList(@RequestParam("menuId") Long menuId, PaginAtion pagination) {
        SysFunc sysFunc = new SysFunc();
        sysFunc.setMenuId(menuId);
        QueryWrapper entityWrapper = new QueryWrapper(sysFunc);
        return ResponseResult.success(this.sysFuncService.page(pagination.getPage(), entityWrapper));
    }

    /**
     * 保存方法信息
     *
     * @param sysFunc
     * @return
     */
    @PostMapping("/save")
    public ResponseResult<String> save(@RequestBody SysFunc sysFunc) {
        if (null == sysFunc.getId()) {
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("func_code", sysFunc.getFuncCode());
            List<SysFunc> sysFuncList = this.sysFuncService.list(queryWrapper);
            if (sysFuncList != null && sysFuncList.size() > 0) {
                return ResponseResult.error("代码为:" + sysFunc.getFuncCode() + "的方法已存在");
            }
            //
            queryWrapper = new QueryWrapper();
            queryWrapper.eq("func_name", sysFunc.getFuncName());
            sysFuncList = this.sysFuncService.list(queryWrapper);
            if (sysFuncList != null && sysFuncList.size() > 0) {
                return ResponseResult.error("名称为:" + sysFunc.getFuncName() + "的方法已存在");
            }
            this.sysFuncService.save(sysFunc);
        } else {
            this.sysFuncService.updateById(sysFunc);
        }
        return ResponseResult.success();
    }

    /**
     * 批量删除方法
     * @param ids
     * @return
     */
    @PostMapping("/delete")
    public ResponseResult<String> delete(@RequestBody List<Long> ids) {
        this.sysFuncService.removeByIds(ids);
        return ResponseResult.success();
    }

}

