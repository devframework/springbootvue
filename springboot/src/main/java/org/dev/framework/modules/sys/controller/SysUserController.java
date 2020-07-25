package org.dev.framework.modules.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.dev.framework.modules.sys.entity.SysUser;
import org.dev.framework.common.PaginAtion;
import org.dev.framework.common.ResponseResult;
import org.dev.framework.modules.sys.entity.SysUser;
import org.dev.framework.modules.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.print.DocFlavor;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 用户基本信息 前端控制器
 * </p>
 *
 * @author hlt
 * @since 2020-06-24
 */
@RestController
@RequestMapping("/sysUser")
public class SysUserController {

    @Autowired
    SysUserService sysUserService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 系统用户列表
     *
     * @param sysUser
     * @param pagination
     * @return
     */
    @RequestMapping("/list")
    public ResponseResult<IPage<SysUser>> list(SysUser sysUser, PaginAtion pagination) {
        Page page = pagination.getPage();
        return ResponseResult.success(sysUserService.page(page, new QueryWrapper<SysUser>(sysUser)));
    }


    /**
     * 禁用启用用户
     *
     * @param enable
     * @param idArray
     * @return
     */
    @PostMapping("/enable")
    public ResponseResult enable(@RequestParam("enable") boolean enable, @RequestBody List<Long> idArray) {
        boolean result = false;
        List<SysUser> sysUsers = new ArrayList<>();
        for (Long userId : idArray) {
            SysUser sysUser = this.sysUserService.getById(userId);
            if (sysUser != null) {
                if (enable) {
                    sysUser.setIsEnable(1);
                } else {
                    sysUser.setIsEnable(0);
                }
                sysUsers.add(sysUser);
            }
        }
        result = this.sysUserService.updateBatchById(sysUsers);
        return result ? ResponseResult.success() : ResponseResult.error();
    }

    /**
     * 用户编辑
     *
     * @param sysUser
     * @return
     */
    @PostMapping("/save")
    public ResponseResult<String> save(@RequestBody SysUser sysUser) {
        boolean result = false;
        if (null == sysUser.getId()) {

            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("login_name", sysUser.getLoginName());
            List<SysUser> sysUsers = this.sysUserService.list(queryWrapper);
            if (sysUsers != null && sysUsers.size() > 0) {
                return ResponseResult.error("用户账号已存在");
            }
            queryWrapper = new QueryWrapper();
            queryWrapper.eq("email", sysUser.getEmail());
            sysUsers = this.sysUserService.list(queryWrapper);
            if (sysUsers != null && sysUsers.size() > 0) {
                return ResponseResult.error("邮箱已存在");
            }
            queryWrapper = new QueryWrapper();
            queryWrapper.eq("mobile_phone", sysUser.getMobilePhone());
            sysUsers = this.sysUserService.list(queryWrapper);
            if (sysUsers != null && sysUsers.size() > 0) {
                return ResponseResult.error("手机号已经存在");
            }

            sysUser.setPassword(passwordEncoder.encode(sysUser.getPassword()));
            result = this.sysUserService.save(sysUser);
        } else {
            sysUser.setPassword(passwordEncoder.encode(sysUser.getPassword()));
            result = this.sysUserService.updateById(sysUser);
        }
        if (result)
            return ResponseResult.success();

        return ResponseResult.error();
    }

    /**
     * 获取用户信息
     *
     * @param id
     * @return
     */
    @GetMapping("/get")
    public ResponseResult<SysUser> get(@RequestParam("id") Long id) {
        SysUser sysUser = this.sysUserService.getById(id);
        sysUser.setPassword("");
        return ResponseResult.success(sysUser);
    }


    /**
     * 获取用户信息
     *
     * @param ids
     * @return
     */
    @PostMapping("/delete")
    public ResponseResult<String> delete(@RequestBody List<Long> ids) {
        SysUser sysUser = new SysUser();
        sysUser.setLoginName("admin");
        QueryWrapper entityWrapper = new QueryWrapper(sysUser);
        entityWrapper.in("id", ids);
        List<SysUser> sysUsers = this.sysUserService.list(entityWrapper);
        if (sysUsers != null && sysUsers.size() > 0) {
            return ResponseResult.error("系统管理员账号不能删除");
        }
        this.sysUserService.removeByIds(ids);
        return ResponseResult.success();
    }


    /**
     * 重置密码
     *
     * @param ids
     * @return
     */
    @PostMapping("/rest-password")
    public ResponseResult<String> restPassword(@RequestBody List<Long> ids) {
        for (Long id : ids) {
            SysUser sysUser = new SysUser();
            sysUser.setId(id);
            sysUser.setPassword(passwordEncoder.encode("000000"));
            this.sysUserService.updateById(sysUser);
        }
        return ResponseResult.success();
    }
}

