package org.dev.framework.core.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.dev.framework.security.jwt.JwtUtil;
import org.dev.framework.security.jwt.JwtUtil;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class MetaHandler implements MetaObjectHandler {

    /**
     * 新增数据执行
     *
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("createBy", JwtUtil.CurrentUserId(), metaObject);
        this.setFieldValByName("createTime", new Date(), metaObject);
        this.setFieldValByName("updateBy", JwtUtil.CurrentUserId(), metaObject);
        this.setFieldValByName("updateTime", new Date(), metaObject);
    }

    /**
     * 更新数据执行
     *
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateBy", JwtUtil.CurrentUserId(), metaObject);
        this.setFieldValByName("updateTime", new Date(), metaObject);
    }

}