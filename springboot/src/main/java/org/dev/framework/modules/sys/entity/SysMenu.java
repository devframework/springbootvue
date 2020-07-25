package org.dev.framework.modules.sys.entity;


import java.util.List;


import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.experimental.Accessors;
import org.dev.framework.core.entity.TenantEntity;

/**
 * <p>
 *
 * </p>
 *
 * @author hlt
 * @since 2020-06-24
 */
@Data
@Accessors(chain = true)
public class SysMenu extends TenantEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 菜单ID
     */
    private Long id;
    /**
     * 父级节点
     */
    private Long parentId;
    /**
     * 菜单类型
     */
    private String menuType;
    /**
     * 菜单平台 PC pc端 webapp移动端网页 app移动端app
     */
    private String menuPlatform;
    /**
     * 菜单名称
     */
    private String menuName;
    /**
     * 菜单代码
     */
    private String menuCode;
    /**
     * 菜单URL
     */
    private String menuUrl;
    /**
     * 菜单备注
     */
    private String menuDesc;
    /**
     * 菜单图标
     */
    private String menuIcon;
    /**
     * 菜单排序
     */
    private Integer sortNo;


    /**
     * 子菜单
     */
    @TableField(exist = false)
    private List<SysMenu> childrens;

    /**
     * 类型 menu 菜单 func 方法
     */
    @TableField(exist = false)
    private String type;

    /**
     * 角色ID
     */
    @TableField(exist = false)
    private String roleId;
}
