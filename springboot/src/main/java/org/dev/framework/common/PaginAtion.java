package org.dev.framework.common;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.util.StringUtils;

import java.io.Serializable;

public class PaginAtion implements Serializable {
    private Integer current = 1;

    private Integer size = 10;

    private String asc;

    private String desc;

    public Integer getCurrent() {
        return current == null ? 1 : current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public Integer getSize() {
        return size == null ? 10 : size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getAsc() {
        return asc;
    }

    public void setAsc(String asc) {
        this.asc = asc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    /***
     *
     * @param <T>
     * @return
     */
    public <T> Page<T> getPage() {
        if (StringUtils.isEmpty(asc) && StringUtils.isEmpty(desc)) {
            Page<T> page = new Page<>(getCurrent(), getSize());
            return page;
        } else {
            if (StringUtils.hasText(asc)) {
                Page<T> page = new Page<>(getCurrent(), getSize());
                return page;
            } else {
                Page<T> page = new Page<>(getCurrent(), getSize());
                return page;
            }

        }
    }
}
