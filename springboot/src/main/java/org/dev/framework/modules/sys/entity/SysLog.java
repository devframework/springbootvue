package org.dev.framework.modules.sys.entity;

import org.dev.framework.core.entity.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 操作记录
 * </p>
 *
 * @author dean.x.liu
 * @since 2020-07-06
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysLog extends TenantEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户ip
     */
    private String ip;

    /**
     * 请求url
     */
    private String url;

    /**
     * 操作目标
     */
    private String target;

    /**
     * 操作方法
     */
    private String method;

    /**
     * 操作方法描述
     */
    private String description;

    /**
     * 请求参数
     */
    private String params;

    /**
     * 返回结果
     */
    private String result;


}
