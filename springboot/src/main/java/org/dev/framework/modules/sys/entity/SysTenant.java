package org.dev.framework.modules.sys.entity;

import java.time.LocalDateTime;
import org.dev.framework.core.entity.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 租户信息
 * </p>
 *
 * @author dean.x.liu
 * @since 2020-07-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysTenant extends TenantEntity {

    private static final long serialVersionUID=1L;

    private Long id;

    /**
     * 租户编码
     */
    private String tenantCode;

    /**
     * 租户名称
     */
    private String tenantName;

    /**
     * 租户描述
     */
    private String description;

    /**
     * 租户url地址
     */
    private String tenantUrl;

    /**
     * 租户数据库
     */
    private String tenantDb;

    /**
     * 服务到期时间
     */
    private LocalDateTime expirationDate;

    /**
     * 开始时间
     */
    private LocalDateTime startDate;


}
