package org.dev.framework.modules.sys.entity;

import org.dev.framework.core.entity.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 上传文件
 * </p>
 *
 * @author dean.x.liu
 * @since 2020-07-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysUpload extends TenantEntity {

    private static final long serialVersionUID=1L;

    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 地址
     */
    private String url;

    /**
     * 排序
     */
    private Integer sort;


}
