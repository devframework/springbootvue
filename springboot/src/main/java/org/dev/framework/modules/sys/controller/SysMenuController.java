package org.dev.framework.modules.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.dev.framework.contant.PermissionsType;
import org.dev.framework.contant.PlatForm;
import org.dev.framework.modules.sys.entity.*;
import org.dev.framework.modules.sys.service.SysRoleService;
import org.dev.framework.common.ResponseResult;
import org.dev.framework.contant.PermissionsType;
import org.dev.framework.contant.PlatForm;
import org.dev.framework.contant.RoleTyle;
import org.dev.framework.contant.UserType;
import org.dev.framework.modules.sys.entity.*;
import org.dev.framework.modules.sys.service.*;
import org.dev.framework.security.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author hlt
 * @since 2020-06-24
 */
@RestController
@RequestMapping("/sysMenu")
public class SysMenuController {
    @Autowired
    SysMenuService sysMenuService;

    @Autowired
    SysRoleMenuService sysRoleMenuService;

    @Autowired
    SysRoleUserService sysRoleUserService;

    @Autowired
    SysFuncService sysFuncService;

    @Autowired
    SysRoleFuncService sysRoleFuncService;

    @Autowired
    SysUserService sysUserService;

    @Autowired
    SysRoleService sysRoleService;

    /****
     * 查询用户所有菜单信息
     * 返回树形结构书数据
     * PC端
     * @return
     */
    @RequestMapping("/user-tree-list")
    public ResponseResult<List<SysMenu>> userTreeList() {

        List<SysMenu> sysMenuList = new ArrayList<>();
        if (JwtUtil.CurrentUserName().equals(UserType.Administrator)) {
            //系统管理员 直接加载所有菜单
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("menu_platform", PlatForm.PC);
            sysMenuList = this.sysMenuService.list(queryWrapper);
        } else {
            //获取用户关联的角色信息
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("user_id", JwtUtil.CurrentUserId());
            List<SysRoleUser> sysRoleUsers = sysRoleUserService.list(queryWrapper);
            if (sysRoleUsers == null || sysRoleUsers.size() == 0) {
                return ResponseResult.error(403, "尚未找到您的权限,请联系管理员", null);
            }
            //获取角色关联的菜单信息
            List<Long> idArray = sysRoleUsers.stream().map(SysRoleUser::getRoleId).collect(Collectors.toList());
            queryWrapper = new QueryWrapper();
            queryWrapper.in("role_id", idArray);
            List<SysRoleMenu> sysRoleMenus = this.sysRoleMenuService.list(queryWrapper);
            if (sysRoleMenus == null || sysRoleMenus.size() == 0) {
                return ResponseResult.error(403, "尚未找到您的权限,请联系管理员", null);
            }
            //获取菜单信息
            idArray = sysRoleMenus.stream().map(SysRoleMenu::getMenuId).collect(Collectors.toList());
            queryWrapper = new QueryWrapper();
            queryWrapper.in("id", idArray);
            queryWrapper.eq("menu_platform", PlatForm.PC);
            sysMenuList = this.sysMenuService.list(queryWrapper);
            if (sysMenuList == null || sysMenuList.size() == 0) {
                return ResponseResult.error(403, "尚未找到您的权限,请联系管理员", null);
            }
        }

        List<SysMenu> rootMenus = sysMenuList.stream().filter(x -> x.getParentId() == 1).collect(Collectors.toList());
        List<SysMenu> menus = new ArrayList<>();
        for (SysMenu sysMenu : rootMenus) {
            sysMenu.setType(PermissionsType.menu);
            this.recursiveMenu(sysMenu, sysMenuList);
            menus.add(sysMenu);
        }
        return ResponseResult.success(menus);
    }


    /****
     * 查询用户所有菜单信息
     * 返回列表数据
     * PC端
     * @return
     */
    @RequestMapping("/user-list")
    public ResponseResult<List<SysMenu>> userList() {

        List<SysMenu> sysMenuList = new ArrayList<>();
        if (JwtUtil.CurrentUserName().equals(UserType.Administrator)) {
            //系统管理员 直接加载所有菜单
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("menu_platform", PlatForm.PC);
            sysMenuList = this.sysMenuService.list(queryWrapper);
        } else {
            //获取用户关联的角色信息
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("user_id", JwtUtil.CurrentUserId());
            List<SysRoleUser> sysRoleUsers = sysRoleUserService.list(queryWrapper);
            if (sysRoleUsers == null || sysRoleUsers.size() == 0) {
                return ResponseResult.success(null);
            }
            //获取角色关联的菜单信息
            List<Long> idArray = sysRoleUsers.stream().map(SysRoleUser::getRoleId).collect(Collectors.toList());
            queryWrapper = new QueryWrapper();
            queryWrapper.in("role_id", idArray);
            List<SysRoleMenu> sysRoleMenus = this.sysRoleMenuService.list(queryWrapper);
            if (sysRoleMenus == null || sysRoleMenus.size() == 0) {
                return ResponseResult.success(null);
            }
            //获取菜单信息
            idArray = sysRoleMenus.stream().map(SysRoleMenu::getMenuId).collect(Collectors.toList());
            queryWrapper = new QueryWrapper();
            queryWrapper.in("id", idArray);
            queryWrapper.eq("menu_platform", PlatForm.PC);
            sysMenuList = this.sysMenuService.list(queryWrapper);

        }
        return ResponseResult.success(sysMenuList);
    }


    /****
     * 查询所有菜单
     * 返回树形结构书数据
     * PC端
     * @return
     */
    @RequestMapping("/tree-list")
    public ResponseResult<List<SysMenu>> treeList() {

        //获取用户关联的角色信息
        List<SysMenu> sysMenuList = this.sysMenuService.list(new QueryWrapper<SysMenu>());
        List<SysMenu> rootMenus = sysMenuList.stream().filter(x -> x.getParentId() == 1).collect(Collectors.toList());
        List<SysMenu> menus = new ArrayList<>();
        for (SysMenu sysMenu : rootMenus) {
            sysMenu.setType(PermissionsType.menu);
            this.recursiveMenu(sysMenu, sysMenuList);
            menus.add(sysMenu);
        }
        return ResponseResult.success(rootMenus);
    }


    /****
     * 查询所有菜单
     * 返回树形结构书数据
     * PC端
     * @return
     */
    @RequestMapping("/tree-func-list")
    public ResponseResult<Map<String, Object>> treeFuncList(@RequestParam("roleId") Long roleId) {
        Map<String, Object> map = new HashMap<>();
        //查询所有菜单 方法信息 返回一个树形结构
        List<SysMenu> sysMenuList = this.sysMenuService.list(new QueryWrapper<SysMenu>());
        List<SysFunc> sysFuncList = this.sysFuncService.list(new QueryWrapper<SysFunc>());
        List<SysMenu> rootMenus = sysMenuList.stream().filter(x -> x.getParentId().equals(1L)).collect(Collectors.toList());
        List<SysMenu> menus = new ArrayList<>();
        for (SysMenu sysMenu : rootMenus) {
            sysMenu.setType(PermissionsType.menu);
            this.recursiveMenu(sysMenu, sysMenuList, sysFuncList);
            menus.add(sysMenu);
        }
        map.put("tree", rootMenus);
        //
        SysRole sysRole = sysRoleService.getById(roleId);
        if (sysRole.getRoleCode().equals(RoleTyle.Administrator)) {
            List<Long> idArray = sysMenuList.stream().map(SysMenu::getId).collect(Collectors.toList());
            List<Long> idArray2 = sysFuncList.stream().map(SysFunc::getId).collect(Collectors.toList());
            idArray.addAll(idArray2);
            //
            List<String> ids = idArray.stream().map(x -> String.valueOf(x)).collect(Collectors.toList());
            map.put("checkIds", ids);
        } else {
            // 获取角色有权限的菜单ID 方法ID
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setRoleId(roleId);
            QueryWrapper queryWrapper = new QueryWrapper(sysRoleMenu);
            List<SysRoleMenu> sysRoleMenus = this.sysRoleMenuService.list(queryWrapper);
            //获取菜单信息
            List<Long> meunIds = sysRoleMenus.stream().map(SysRoleMenu::getMenuId).collect(Collectors.toList());
            queryWrapper = new QueryWrapper();
            queryWrapper.in("id", meunIds);
            queryWrapper.eq("menu_platform", PlatForm.PC);
            sysMenuList = this.sysMenuService.list(queryWrapper);
            List<Long> cmenuids = new ArrayList<>();
            for (SysMenu sysMenu : rootMenus) {
                this.recursiveMenuId(sysMenu, sysMenuList, cmenuids);
            }
            //由于element api限制 先改为只传递选中的子叶节点id
            //List<Long> idArray = sysRoleMenus.stream().map(SysRoleMenu::getMenuId).collect(Collectors.toList());


            //方法授权信息
            SysRoleFunc sysRoleFunc = new SysRoleFunc();
            sysRoleFunc.setRoleId(roleId);
            QueryWrapper funcQueryWrapper = new QueryWrapper(sysRoleFunc);
            List<SysRoleFunc> sysRoleFuncs = this.sysRoleFuncService.list(funcQueryWrapper);

            List<Long> idArray2 = sysRoleFuncs.stream().map(SysRoleFunc::getFuncId).collect(Collectors.toList());
            //idArray.addAll(idArray2);
            cmenuids.addAll(idArray2);
            //
            //List<String> ids = idArray.stream().map(x -> String.valueOf(x)).collect(Collectors.toList());
            List<String> ids = cmenuids.stream().map(x -> String.valueOf(x)).collect(Collectors.toList());
            map.put("checkIds", ids);
        }

        return ResponseResult.success(map);
    }


    /****
     * 查询所有菜单
     * 返回树形结构书数据
     * 并返回用户有权限的菜单ID
     * PC端
     * @return
     */
    @RequestMapping("/tree-func-user-list")
    public ResponseResult<Map<String, Object>> treeFuncUserList(@RequestParam("userId") Long userId) {
        Map<String, Object> map = new HashMap<>();
        //查询所有菜单 方法信息 返回一个树形结构
        List<SysMenu> sysMenuList = this.sysMenuService.list(new QueryWrapper<SysMenu>());
        List<SysFunc> sysFuncList = this.sysFuncService.list(new QueryWrapper<SysFunc>());
        List<SysMenu> rootMenus = sysMenuList.stream().filter(x -> x.getParentId() == 1).collect(Collectors.toList());
        List<SysMenu> menus = new ArrayList<>();
        for (SysMenu sysMenu : rootMenus) {
            this.recursiveMenu(sysMenu, sysMenuList, sysFuncList);
            menus.add(sysMenu);
        }
        map.put("tree", rootMenus);
        //
        SysUser sysUser = this.sysUserService.getById(userId);
        if (sysUser.getLoginName().equals(UserType.Administrator)) {
            //获取菜单ID
            List<Long> idArray1 = sysMenuList.stream().map(SysMenu::getId).collect(Collectors.toList());
            // 获取函数ID
            List<Long> idArray2 = sysFuncList.stream().map(SysFunc::getId).collect(Collectors.toList());
            idArray1.addAll(idArray2);
            //
            List<String> ids = idArray1.stream().map(x -> String.valueOf(x)).distinct().collect(Collectors.toList());
            map.put("checkIds", ids);
        } else {
            //获取用户有的角色信息
            SysRoleUser sysRoleUser = new SysRoleUser();
            sysRoleUser.setUserId(userId);
            QueryWrapper queryWrapper = new QueryWrapper(sysRoleUser);
            List<SysRoleUser> sysRoleUsers = sysRoleUserService.list(queryWrapper);
            if (sysRoleUsers == null || sysRoleUsers.size() == 0) {
                return ResponseResult.success(map);
            }
            // 获取角色有权限的菜单ID 方法ID

            List<Long> idArray = sysRoleUsers.stream().map(SysRoleUser::getRoleId).collect(Collectors.toList());
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            queryWrapper = new QueryWrapper(sysRoleMenu);
            queryWrapper.in("role_id", idArray);
            List<SysRoleMenu> sysRoleMenus = this.sysRoleMenuService.list(queryWrapper);

            List<Long> meunIds = sysRoleMenus.stream().map(SysRoleMenu::getMenuId).collect(Collectors.toList());
            queryWrapper = new QueryWrapper();
            queryWrapper.in("id", meunIds);
            queryWrapper.eq("menu_platform", PlatForm.PC);
            sysMenuList = this.sysMenuService.list(queryWrapper);
            List<Long> cmenuids = new ArrayList<>();
            for (SysMenu sysMenu : rootMenus) {
                this.recursiveMenuId(sysMenu, sysMenuList, cmenuids);
            }
            //获取菜单ID 由于element api限制 先改为只传递选中的子叶节点id
            //List<Long> idArray = sysRoleMenus.stream().map(SysRoleMenu::getMenuId).collect(Collectors.toList());


            //方法授权信息
            SysRoleFunc sysRoleFunc = new SysRoleFunc();
            QueryWrapper funcQueryWrapper = new QueryWrapper(sysRoleFunc);
            funcQueryWrapper.in("role_id", idArray);
            List<SysRoleFunc> sysRoleFuncs = this.sysRoleFuncService.list(funcQueryWrapper);


            // 获取函数ID
            List<Long> idArray2 = sysRoleFuncs.stream().map(SysRoleFunc::getFuncId).collect(Collectors.toList());
            cmenuids.addAll(idArray2);
            //
            List<String> ids = cmenuids.stream().map(x -> String.valueOf(x)).collect(Collectors.toList());
            map.put("checkIds", ids);
        }

        return ResponseResult.success(map);
    }

    /**
     * 递归处理菜单
     *
     * @param sysMenu
     * @param sysMenus
     */
    private void recursiveMenu(SysMenu sysMenu, List<SysMenu> sysMenus) {
        List<SysMenu> sysMenuList = sysMenus.stream().filter(x -> x.getParentId().equals(sysMenu.getId())).collect(Collectors.toList());
        if (sysMenuList != null && sysMenuList.size() > 0) {
            for (SysMenu sysMenu1 : sysMenuList) {
                this.recursiveMenu(sysMenu1, sysMenus);
            }
            sysMenu.setChildrens(sysMenuList);
        }
    }


    /**
     * 递归处理菜单
     *
     * @param sysMenu
     * @param sysMenus
     */
    private void recursiveMenuId(SysMenu sysMenu, List<SysMenu> sysMenus, List<Long> idArray) {
        List<SysMenu> sysMenuList = sysMenus.stream().filter(x -> x.getParentId().equals(sysMenu.getId())).collect(Collectors.toList());
        if (sysMenuList != null && sysMenuList.size() > 0) {
            for (SysMenu sysMenu1 : sysMenuList) {
                this.recursiveMenuId(sysMenu1, sysMenus, idArray);
            }
        } else {
            idArray.add(sysMenu.getId());
        }
    }


    /**
     * 递归处理菜单
     * 并处理菜单菜单对应的FUNC
     *
     * @param sysMenu
     * @param sysMenus
     */
    private void recursiveMenu(SysMenu sysMenu, List<SysMenu> sysMenus, List<SysFunc> sysFuncs) {
        List<SysMenu> sysMenuList = sysMenus.stream().filter(x -> x.getParentId().equals(sysMenu.getId())).collect(Collectors.toList());
        List<SysFunc> sysFuncs1 = sysFuncs.stream().filter(x -> x.getMenuId().equals(sysMenu.getId())).collect(Collectors.toList());
        if (sysMenuList != null && sysMenuList.size() > 0) {
            for (SysMenu sysMenu1 : sysMenuList) {
                sysMenu1.setType(PermissionsType.menu);
                this.recursiveMenu(sysMenu1, sysMenus, sysFuncs);
            }
            sysMenu.setChildrens(sysMenuList);
        } else {
            if (sysFuncs1 != null && sysFuncs1.size() > 0) {
                for (SysFunc sysFunc : sysFuncs1) {
                    SysMenu sysMenu1 = new SysMenu();
                    sysMenu1.setId(sysFunc.getId());
                    sysMenu1.setType(PermissionsType.func);
                    sysMenu1.setMenuCode(sysFunc.getFuncCode());
                    sysMenu1.setMenuName(sysFunc.getFuncName());
                    sysMenuList.add(sysMenu1);
                }
            }
            sysMenu.setChildrens(sysMenuList);
        }
    }

    /**
     * 保存菜单信息
     *
     * @param sysMenu
     * @return
     */
    @PostMapping("/save")
    public ResponseResult<String> save(@RequestBody SysMenu sysMenu) {
        if (null == sysMenu.getId()) {
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("menu_code", sysMenu.getMenuCode());
            List<SysMenu> sysMenuList = this.sysMenuService.list(queryWrapper);
            if (sysMenuList != null && sysMenuList.size() > 0) {
                return ResponseResult.error("代码为:" + sysMenu.getMenuCode() + "的菜单已存在");
            }
            //
            queryWrapper = new QueryWrapper();
            queryWrapper.eq("menu_name", sysMenu.getMenuName());
            sysMenuList = this.sysMenuService.list(queryWrapper);
            if (sysMenuList != null && sysMenuList.size() > 0) {
                return ResponseResult.error("名称为:" + sysMenu.getMenuName() + "的菜单已存在");
            }
            if (null == sysMenu.getParentId()) {
                sysMenu.setParentId(1L);
            }
            this.sysMenuService.save(sysMenu);
        } else {
            this.sysMenuService.updateById(sysMenu);
        }
        return ResponseResult.success();
    }


    /**
     * 删除菜单
     *
     * @param sysMenu
     * @return
     */
    @PostMapping("/delete")
    public ResponseResult<String> delete(@RequestBody SysMenu sysMenu) {
        this.sysMenuService.removeById(sysMenu.getId());
        return ResponseResult.success();
    }
}

