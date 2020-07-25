package org.dev.framework.modules.sys.entity;

import org.dev.framework.core.entity.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 数据库字典组
 * </p>
 *
 * @author dean.x.liu
 * @since 2020-07-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysDic extends TenantEntity {

    private static final long serialVersionUID=1L;

    /**
     * ID
     */
    private Long id;

    /**
     * 编码
     */
    private String dicCode;

    /**
     * 名称
     */
    private String dicName;

    /**
     * 描述
     */
    private String description;


}
