package org.dev.framework.modules.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.dev.framework.modules.sys.entity.SysRole;
import org.dev.framework.modules.sys.service.SysRoleService;
import org.dev.framework.common.PaginAtion;
import org.dev.framework.common.ResponseResult;
import org.dev.framework.modules.sys.entity.SysRole;
import org.dev.framework.modules.sys.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 权限管理-角色管理 前端控制器
 * </p>
 *
 * @author hlt
 * @since 2020-06-24
 */
@RestController
@RequestMapping("/sysRole")
public class SysRoleController {

    @Autowired
    SysRoleService sysRoleService;

    /**
     * 查询角色列表
     *
     * @param sysRole
     * @param pagination
     * @return
     */
    @RequestMapping("/list")
    public ResponseResult<IPage<SysRole>> list(SysRole sysRole, PaginAtion pagination) {
        return ResponseResult.success(sysRoleService.page(pagination.getPage(),
                new QueryWrapper<>(sysRole)));
    }

    /**
     * 保存角色信息
     *
     * @param sysRole
     * @return
     */
    @PostMapping("/save")
    public ResponseResult<String> save(@RequestBody SysRole sysRole) {
        if (null == sysRole.getId()) {
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("role_code", sysRole.getRoleCode());
            List<SysRole> sysRoleLis = this.sysRoleService.list(queryWrapper);
            if (sysRoleLis != null && sysRoleLis.size() > 0) {
                return ResponseResult.error("代码为:" + sysRole.getRoleCode() + "的角色已存在");
            }
            //
            queryWrapper = new QueryWrapper();
            queryWrapper.eq("role_name", sysRole.getRoleCode());
            sysRoleLis = this.sysRoleService.list(queryWrapper);
            if (sysRoleLis != null && sysRoleLis.size() > 0) {
                return ResponseResult.error("名称为:" + sysRole.getRoleCode() + "的角色已存在");
            }
            this.sysRoleService.save(sysRole);
        } else {
            this.sysRoleService.updateById(sysRole);
        }
        return ResponseResult.success();
    }


    /**
     * 获取角色信息
     *
     * @param id
     * @return
     */
    @GetMapping("/get")
    public ResponseResult<SysRole> get(@RequestParam("id") Long id) {

        return ResponseResult.success(this.sysRoleService.getById(id));
    }


    /**
     * 删除角色信息
     *
     * @param ids
     * @return
     */
    @PostMapping("/delete")
    public ResponseResult<String> delete(@RequestBody List<Long> ids) {
        SysRole sysRole = new SysRole();
        sysRole.setRoleCode("administrator");
        QueryWrapper queryWrapper = new QueryWrapper(sysRole);
        queryWrapper.in("id", ids);
        List<SysRole> sysRoles = this.sysRoleService.list(queryWrapper);
        if (sysRoles != null && sysRoles.size() > 0) {
            return ResponseResult.error("系统管理员角色不能删除");
        }
        this.sysRoleService.removeByIds(ids);
        return ResponseResult.success();
    }
}

