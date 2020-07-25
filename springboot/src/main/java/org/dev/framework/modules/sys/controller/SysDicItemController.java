package org.dev.framework.modules.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.dev.framework.modules.sys.entity.SysDicItem;
import org.dev.framework.common.PaginAtion;
import org.dev.framework.common.ResponseResult;
import org.dev.framework.core.aop.OperLog;
import org.dev.framework.modules.sys.entity.SysDicItem;
import org.dev.framework.modules.sys.service.SysDicItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 数据库字典组-子级 前端控制器
 * </p>
 *
 * @author dean.x.liu
 * @since 2020-07-05
 */
@RestController
@RequestMapping("/sysDicItem")
public class SysDicItemController {


    @Autowired
    SysDicItemService sysDicItemService;

    /**
     * 数据字典子项集合
     *
     * @return
     */
    @GetMapping("/list")
    @OperLog(description = "数据字典集合")
    public ResponseResult<IPage<SysDicItem>> list(SysDicItem sysDicItem, PaginAtion pagination) {
        return ResponseResult.success(sysDicItemService.page(pagination.getPage(),
                new QueryWrapper<>(sysDicItem)));
    }

    /**
     * 保存数据字典子项信息
     *
     * @param sysDicItem
     * @return
     */
    @PostMapping("/save")
    public ResponseResult<String> save(@RequestBody SysDicItem sysDicItem) {
        if (null == sysDicItem.getId()) {
            SysDicItem sysDicItem1 = new SysDicItem();
            sysDicItem1.setDicItemCode(sysDicItem.getDicItemCode());
            sysDicItem1.setDicId(sysDicItem.getDicId());
            QueryWrapper queryWrapper = new QueryWrapper(sysDicItem1);
            List<SysDicItem> sysDicList = this.sysDicItemService.list(queryWrapper);
            if (sysDicList != null && sysDicList.size() > 0) {
                return ResponseResult.error("代码为:" + sysDicItem.getDicItemCode() + "的子项已存在");
            }
            this.sysDicItemService.save(sysDicItem);
        } else {
            this.sysDicItemService.updateById(sysDicItem);
        }
        return ResponseResult.success();
    }

    /**
     * 批量删除方法
     *
     * @param ids
     * @return
     */
    @PostMapping("/delete")
    public ResponseResult<String> delete(@RequestBody List<Long> ids) {
        this.sysDicItemService.removeByIds(ids);
        return ResponseResult.success();
    }


}

