package org.dev.framework.modules.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.dev.framework.contant.PermissionsType;
import org.dev.framework.modules.sys.entity.SysMenu;
import org.dev.framework.modules.sys.entity.SysRoleFunc;
import org.dev.framework.modules.sys.entity.SysRoleMenu;
import org.dev.framework.common.ResponseResult;
import org.dev.framework.contant.PermissionsType;
import org.dev.framework.modules.sys.entity.SysMenu;
import org.dev.framework.modules.sys.entity.SysRoleFunc;
import org.dev.framework.modules.sys.entity.SysRoleMenu;
import org.dev.framework.modules.sys.service.SysRoleFuncService;
import org.dev.framework.modules.sys.service.SysRoleMenuService;
import org.dev.framework.security.MySecurityMetadataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.Date;
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
@RequestMapping("/sysRoleFunc")
public class SysRoleFuncController {

    @Autowired
    SysRoleMenuService sysRoleMenuService;

    @Autowired
    SysRoleFuncService sysRoleFuncService;

    @Autowired
    MySecurityMetadataSource securityMetadataSource;
    /**
     * 保存角色授权信息
     *
     * @param sysMenuList
     */
    @PostMapping("save")
    @Transactional
    public ResponseResult save(@RequestParam("roleId") Long roleId, @RequestBody List<SysMenu> sysMenuList) {
        // 新的权限没有 直接清空原先权限授权
        // 菜单授权信息
        SysRoleMenu sysRoleMenu = new SysRoleMenu();
        sysRoleMenu.setRoleId(roleId);
        QueryWrapper menuEntityWrapper = new QueryWrapper(sysRoleMenu);
        //方法授权信息
        SysRoleFunc sysRoleFunc = new SysRoleFunc();
        sysRoleFunc.setRoleId(roleId);
        QueryWrapper funcEntityWrapper = new QueryWrapper(sysRoleFunc);

        //首选获取所选角色的原先权限
        List<SysRoleMenu> sysRoleMenus = new ArrayList<>();//菜单授权信息
        List<SysRoleFunc> sysRoleFuncs = new ArrayList<>();//方法授权信息
        List<SysRoleMenu> sysRoleMenusOld = this.sysRoleMenuService.list(menuEntityWrapper);
        List<SysRoleFunc> sysRoleFuncsOld = this.sysRoleFuncService.list(funcEntityWrapper);
        // 新权限处理
        for (SysMenu sysMenu : sysMenuList) {
            //菜单授权
            if (PermissionsType.menu.equals(sysMenu.getType())) {

                SysRoleMenu sysRoleMenu1 = new SysRoleMenu();
                sysRoleMenu1.setRoleId(roleId);
                sysRoleMenu1.setMenuId(sysMenu.getId());
                sysRoleMenu1.setCreateTime(new Date());
                sysRoleMenus.add(sysRoleMenu1);
            } else {
                //方法授权
                SysRoleFunc sysRoleFunc1 = new SysRoleFunc();
                sysRoleFunc1.setRoleId(roleId);
                sysRoleFunc1.setFuncId(sysMenu.getId());
                sysRoleFunc1.setCreateTime(new Date());
                sysRoleFuncs.add(sysRoleFunc1);
            }

        }
        //菜单权限处理
        if (sysRoleMenusOld == null || sysRoleMenusOld.size() == 0) {
            //原先不存在菜单
            if (sysRoleMenus != null && sysRoleMenus.size() > 0) {
                this.sysRoleMenuService.saveBatch(sysRoleMenus);
            }
            return ResponseResult.success();
        } else {
            //查找删除的菜单权限
            List<Long> delIdArray = new ArrayList<>();
            List<SysRoleMenu> sysRoleMenusAdd = new ArrayList<>();

            for (SysRoleMenu sysRoleMenu1 : sysRoleMenusOld) {
                if (sysRoleMenus.stream().filter(x -> x.getMenuId().equals(sysRoleMenu1.getMenuId())).count() <= 0) {
                    delIdArray.add(sysRoleMenu1.getId());
                }
            }

            //查找新增的菜单权限
            for (SysRoleMenu sysRoleMenu1 : sysRoleMenus) {
                if (sysRoleMenusOld.stream().filter(x -> x.getMenuId().equals(sysRoleMenu1.getMenuId())).count() <= 0) {
                    sysRoleMenusAdd.add(sysRoleMenu1);
                }
            }
            if (delIdArray != null && delIdArray.size() > 0) {
                this.sysRoleMenuService.removeByIds(delIdArray);
            }
            if (sysRoleMenusAdd != null && sysRoleMenusAdd.size() > 0) {
                this.sysRoleMenuService.saveBatch(sysRoleMenusAdd);
            }
        }

        //方法权限处理
        if (sysRoleFuncsOld == null || sysRoleFuncsOld.size() == 0) {
            //原先不存在方法权限
            if (sysRoleFuncs != null && sysRoleFuncs.size() > 0) {
                this.sysRoleFuncService.saveBatch(sysRoleFuncs);
            }
        } else {
            //查找删除的权限
            List<Long> delIdArray = new ArrayList<>();
            List<SysRoleFunc> sysRoleFuncsAdd = new ArrayList<>();

            for (SysRoleFunc sysRoleFunc1 : sysRoleFuncsOld) {
                if (sysRoleFuncs.stream().filter(x -> x.getFuncId().equals(sysRoleFunc1.getFuncId())).count() <= 0) {
                    delIdArray.add(sysRoleFunc1.getId());
                }
            }

            //查找新增的权限
            for (SysRoleFunc sysRoleFunc1 : sysRoleFuncs) {
                if (sysRoleFuncsOld.stream().filter(x -> x.getFuncId().equals(sysRoleFunc1.getFuncId())).count() <= 0) {
                    sysRoleFuncsAdd.add(sysRoleFunc1);
                }
            }
            if (delIdArray != null && delIdArray.size() > 0) {
                this.sysRoleFuncService.removeByIds(delIdArray);
            }
            if (sysRoleFuncsAdd != null && sysRoleFuncsAdd.size() > 0) {
                this.sysRoleFuncService.saveBatch(sysRoleFuncsAdd);
            }
        }
        securityMetadataSource.clearResourceDefine();
        return ResponseResult.success();
    }
}

