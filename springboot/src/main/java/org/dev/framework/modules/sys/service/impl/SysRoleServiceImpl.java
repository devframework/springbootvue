package org.dev.framework.modules.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.dev.framework.modules.sys.entity.SysRole;
import org.dev.framework.modules.sys.mapper.SysRoleMapper;
import org.dev.framework.modules.sys.service.SysRoleService;
import org.dev.framework.modules.sys.entity.SysRole;
import org.dev.framework.modules.sys.mapper.SysRoleMapper;
import org.dev.framework.modules.sys.service.SysRoleService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 权限管理-角色管理 服务实现类
 * </p>
 *
 * @author hlt
 * @since 2020-06-24
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

}
