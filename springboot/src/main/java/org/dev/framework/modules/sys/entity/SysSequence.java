package org.dev.framework.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import org.dev.framework.core.entity.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * sys_sequence
 * </p>
 *
 * @author dean.x.liu
 * @since 2020-07-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysSequence extends TenantEntity {

    private static final long serialVersionUID=1L;

    private Long id;

    /**
     * 序列号代码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 前缀
     */
    private String prefix;

    /**
     * 分隔符
     */
    @TableField("`separator`")
    private String separator;

    /**
     * 日期格式
     */
    private String dateFormat;

    /**
     * 数字部分长度
     */
    private Integer numLength;

    /**
     * 初始值
     */
    private Integer initValue;


}
