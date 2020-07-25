package org.dev.framework.modules.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.dev.framework.modules.sys.entity.SysRole;
import org.dev.framework.modules.sys.entity.SysRoleUser;
import org.dev.framework.modules.sys.service.SysRoleService;
import org.dev.framework.modules.sys.service.SysRoleUserService;
import org.dev.framework.common.ResponseResult;
import org.dev.framework.modules.sys.entity.SysRole;
import org.dev.framework.modules.sys.entity.SysRoleUser;
import org.dev.framework.modules.sys.service.SysRoleService;
import org.dev.framework.modules.sys.service.SysRoleUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色用户关系表 前端控制器
 * </p>
 *
 * @author hlt
 * @since 2020-06-24
 */
@RestController
@RequestMapping("/sysRoleUser")
public class SysRoleUserController {

    @Autowired
    SysRoleService sysRoleService;

    @Autowired
    SysRoleUserService sysRoleUserService;

    /***
     * 角色查询 返回所有角色并将已有角色
     * @param userId
     * @return
     */
    @GetMapping("/role-list-set")
    public ResponseResult<Map<String, Object>> listForSet(@RequestParam("userId") Long userId) {
        Map<String, Object> map = new HashMap<>();
        //查询所有角色
        QueryWrapper queryWrapper = new QueryWrapper();
        List<SysRole> sysRoleList = sysRoleService.list(queryWrapper);
        map.put("tree", sysRoleList);
        //获取用户已有的角色信息
        SysRoleUser sysRoleUser = new SysRoleUser();
        sysRoleUser.setUserId(userId);
        queryWrapper = new QueryWrapper(sysRoleUser);
        List<SysRoleUser> sysRoleUsers = sysRoleUserService.list(queryWrapper);
        List<Long> idArray = sysRoleUsers.stream().map(SysRoleUser::getRoleId).collect(Collectors.toList());

        List<String> ids = idArray.stream().map(x -> String.valueOf(x)).collect(Collectors.toList());
        map.put("checkIds", ids);
        return ResponseResult.success(map);
    }

    /**
     * 保存用户角色分配
     *
     * @param userId
     * @param sysRoles
     */
    @PostMapping("save")
    @Transactional
    public ResponseResult save(@RequestParam("userId") Long userId, @RequestBody List<SysRole> sysRoles) {
        //查询原先角色分配信息
        SysRoleUser sysRoleUser = new SysRoleUser();
        sysRoleUser.setUserId(userId);
        QueryWrapper queryWrapper = new QueryWrapper(sysRoleUser);
        List<SysRoleUser> sysRoleUsers = sysRoleUserService.list(queryWrapper);
        List<SysRoleUser> sysRoleUsersNew = new ArrayList<>();
        for (SysRole sysRole : sysRoles) {
            sysRoleUser = new SysRoleUser();
            sysRoleUser.setUserId(userId);
            sysRoleUser.setRoleId(sysRole.getId());
            sysRoleUsersNew.add(sysRoleUser);
        }
        //原先用户无角色信息 直接新增
        if (sysRoleUsers == null || sysRoleUsers.size() == 0) {
            this.sysRoleUserService.saveBatch(sysRoleUsersNew);
            return ResponseResult.success();
        }
        //
        //查找删除的角色
        List<Long> delIdArray = new ArrayList<>();
        List<SysRoleUser> sysRoleMenusAdd = new ArrayList<>();

        for (SysRoleUser sysRoleUser1 : sysRoleUsers) {
            if (!sysRoleUsersNew.contains(sysRoleUser1)) {
                delIdArray.add(sysRoleUser1.getId());
            }
        }

        //查找新增的角色
        for (SysRoleUser sysRoleUser1 : sysRoleUsersNew) {
            if (!sysRoleUsers.contains(sysRoleUser1)) {
                sysRoleMenusAdd.add(sysRoleUser1);
            }
        }
        this.sysRoleUserService.removeByIds(delIdArray);
        if (sysRoleMenusAdd != null && sysRoleMenusAdd.size() > 0) {
            this.sysRoleUserService.saveBatch(sysRoleMenusAdd);
        }
        return ResponseResult.success();
    }
}

