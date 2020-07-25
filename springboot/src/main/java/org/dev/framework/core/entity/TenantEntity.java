package org.dev.framework.core.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;


public class TenantEntity  extends BaseEntity{
    /**
     * 项目编码
     */
    @TableField(value = "tenant_id",fill = FieldFill.INSERT)
    private Long tenantId;

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }
}
