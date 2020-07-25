package org.dev.framework.modules.workflow.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowNode;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.image.ProcessDiagramGenerator;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.dev.framework.common.PaginAtion;
import org.dev.framework.common.ResponseResult;
import org.dev.framework.modules.workflow.entity.WflowHis;
import org.dev.framework.modules.workflow.util.ActivitiUtils;
import org.dev.framework.utils.SpringSecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("wflowHistory")
@Slf4j
public class WflowHistoryController {

    /**
     * 签核历史查询
     *
     * @param wflowHis
     * @param pagination
     * @return
     */
    @RequestMapping("/list")
    public ResponseResult<IPage<WflowHis>> list(WflowHis wflowHis, PaginAtion pagination) {
        IPage page = pagination.getPage();
        int firstResult = (pagination.getCurrent() - 1) * pagination.getSize();
        int maxResults = pagination.getCurrent() * pagination.getSize();
        //获取ProcessEngine对象 默认配置文件名称：activiti.cfg.xml 并且configuration的Bean实例ID为processEngineConfiguration
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //获取HistoryService接口
        HistoryService historyService = processEngine.getHistoryService();
        //获取历史任务
        HistoricActivityInstanceQuery historicActivityInstanceQuery = historyService.createHistoricActivityInstanceQuery().taskAssignee(SpringSecurityUtils.CurrentUser().getLoginName());
        //获取指定流程实例的任务
        //historicActivityInstanceQuery.processInstanceId("2501");
        //获取任务列表
        List<HistoricActivityInstance> list = historicActivityInstanceQuery.orderByHistoricActivityInstanceEndTime().desc().listPage(firstResult, maxResults);
        //查询总数
        Long count = historicActivityInstanceQuery.taskAssignee(SpringSecurityUtils.CurrentUser().getLoginName()).count();
        //
        List<WflowHis> wflowHisList = new ArrayList<>();
        for (HistoricActivityInstance ai : list) {
            WflowHis wflowHis1 = new WflowHis();
            BeanUtils.copyProperties(ai, wflowHis1);
            wflowHisList.add(wflowHis1);
        }
        page.setTotal(count);
        page.setRecords(list);
        return ResponseResult.success(page);
    }

    /**
     * 流程历史查询
     *
     * @param wflowHis
     * @param pagination
     * @return
     */
    @RequestMapping("/all-list")
    public ResponseResult<IPage<WflowHis>> allList(WflowHis wflowHis, PaginAtion pagination) {
        IPage page = pagination.getPage();
        int firstResult = (pagination.getCurrent() - 1) * pagination.getSize();
        int maxResults = pagination.getCurrent() * pagination.getSize();
        //获取ProcessEngine对象 默认配置文件名称：activiti.cfg.xml 并且configuration的Bean实例ID为processEngineConfiguration
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //获取HistoryService接口
        HistoryService historyService = processEngine.getHistoryService();
        //获取历史任务
        HistoricActivityInstanceQuery historicActivityInstanceQuery = historyService.createHistoricActivityInstanceQuery();
        //获取指定流程实例的任务
        //historicActivityInstanceQuery.processInstanceId("2501");
        //获取任务列表
        List<HistoricActivityInstance> list = historicActivityInstanceQuery.listPage(firstResult, maxResults);
        //
        Long count = historicActivityInstanceQuery.count();
        //
        List<WflowHis> wflowHisList = new ArrayList<>();
        for (HistoricActivityInstance ai : list) {
            WflowHis wflowHis1 = new WflowHis();
            BeanUtils.copyProperties(ai, wflowHis1);
            wflowHisList.add(wflowHis1);
        }
        page.setTotal(count);
        page.setRecords(list);
        return ResponseResult.success(page);
    }
}
