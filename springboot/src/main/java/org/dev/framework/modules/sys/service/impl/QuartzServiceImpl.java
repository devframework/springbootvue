package org.dev.framework.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.dev.framework.modules.sys.entity.QuartzJob;
import org.dev.framework.modules.sys.mapper.QuartzMapper;
import org.dev.framework.modules.sys.service.QuartzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuartzServiceImpl implements QuartzService {

    @Autowired
    QuartzMapper quartzMapper;

    /**
     * 分页查询数据
     *
     * @param page
     * @param jobName
     * @return
     */
    @Override
    public IPage<QuartzJob> page(Page<?> page, String jobName) {
        return quartzMapper.page(page, jobName);
    }
}
