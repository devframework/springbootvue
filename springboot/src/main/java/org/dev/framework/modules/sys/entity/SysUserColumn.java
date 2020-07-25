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
public class SysUserColumn extends TenantEntity {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long id;
    /**
     * 模型
     */
    private String modelCode;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 定义的值
     */
    private String tableColumns;
    /**
     * 默认
     */
    private Integer defalut;
    /**
     * 列表值
     */
    private String columnValue;



}
