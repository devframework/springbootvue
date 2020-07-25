package org.dev.framework.modules.sys.entity;


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
public class SysFunc extends TenantEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 菜单ID
     */
    private Long id;
    /**
     * 菜单id
     */
    private Long menuId;
    /**
     * 方法代码
     */
    private String funcCode;
    /**
     * 方法名称
     */
    private String funcName;
    /**
     * 方法URL
     */
    private String funcUrl;
    /**
     * 方法描述
     */
    private String funcDesc;
    /**
     * 方法排序
     */
    private Integer sortNo;


}
