package org.dev.framework.modules.workflow.controller;


import lombok.extern.slf4j.Slf4j;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowNode;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.image.ProcessDiagramGenerator;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.apache.commons.lang3.StringUtils;
import org.dev.framework.modules.workflow.util.ActivitiUtils;
import org.dev.framework.modules.workflow.util.ActivitiUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 工作流程图 前端控制器
 * </p>
 *
 * @author dean.x.liu
 * @since 2020-07-05
 */
@RestController
@RequestMapping("wflowChart")
@Slf4j
public class WflowChartController {
    @Autowired
    HistoryService historyService;
    @Autowired
    RepositoryService repositoryService;
    @Autowired
    ProcessEngineConfiguration processEngineConfiguration;
    @Autowired
    ProcessEngine processEngine;




    /**
     * 查看当前流程图
     * @param processInstanceId 流程实例
     * @param response 响应
     */
    @ResponseBody
    @RequestMapping(value = "/showImg",produces = MediaType.IMAGE_JPEG_VALUE)
    public void showImg(String processInstanceId, HttpServletResponse response) {
        /*
         * 参数校验
         */
        log.info("查看完整流程图！流程实例ID:{}", processInstanceId);
        if (StringUtils.isBlank(processInstanceId)) return;


        /*
         *  获取流程实例
         */
        HistoricProcessInstance processInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        if (processInstance == null) {
            log.error("流程实例ID:{}没查询到流程实例！", processInstanceId);
            return;
        }

        // 根据流程对象获取流程对象模型
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processInstance.getProcessDefinitionId());


        /*
         *  查看已执行的节点集合
         *  获取流程历史中已执行节点，并按照节点在流程中执行先后顺序排序
         */
        // 构造历史流程查询
        HistoricActivityInstanceQuery historyInstanceQuery = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId);
        // 查询历史节点
        List<HistoricActivityInstance> historicActivityInstanceList = historyInstanceQuery.orderByHistoricActivityInstanceStartTime().asc().list();
        if (historicActivityInstanceList == null || historicActivityInstanceList.size() == 0) {
            log.info("流程实例ID:{}没有历史节点信息！", processInstanceId);
            outputImg(response, bpmnModel, null, null);
            return;
        }
        // 已执行的节点ID集合(将historicActivityInstanceList中元素的activityId字段取出封装到executedActivityIdList)
        List<String> executedActivityIdList = historicActivityInstanceList.stream().map(item -> item.getActivityId()).collect(Collectors.toList());

        /*
         *  获取流程走过的线
         */
        // 获取流程定义
        ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService).getDeployedProcessDefinition(processInstance.getProcessDefinitionId());
        // 获取需要高亮显示
        List<String> flowIds = ActivitiUtils.getHighLightedFlows(bpmnModel, processDefinition, historicActivityInstanceList);


        /*
         * 输出图像，并设置高亮
         */
        outputImg(response, bpmnModel, flowIds, executedActivityIdList);
    }


    /**
     * <p>输出图像</p>
     *
     * @param response               响应实体
     * @param bpmnModel              图像对象
     * @param flowIds                已执行的线集合
     * @param executedActivityIdList void 已执行的节点ID集合
     * @author FRH
     * @time 2018年12月10日上午11:23:01
     * @version 1.0
     */
    private void outputImg(HttpServletResponse response, BpmnModel bpmnModel, List<String> flowIds, List<String> executedActivityIdList) {
        ProcessDiagramGenerator processDiagramGenerator = new DefaultProcessDiagramGenerator();
        InputStream imageStream = null;
        try {
            imageStream = processDiagramGenerator.generateDiagram(bpmnModel, executedActivityIdList, flowIds, "宋体", "微软雅黑", "黑体", true, "png");
            //response.setContentType("multipart/form-data");
            //response.setContentType("image/png;charset=utf-8");
            response.setContentType("image/svg+xml;charset=UTF-8");
            response.addHeader("Accept-Ranges","bytes");
            // 输出资源内容到相应对象
            byte[] b = new byte[1024];
            int len;
            while ((len = imageStream.read(b, 0, 1024)) != -1) {
                response.getOutputStream().write(b, 0, len);
            }
            response.getOutputStream().flush();
            imageStream.close();
        } catch (Exception e) {
            log.error("流程图输出异常！", e);
        } finally { // 流关闭
            //IOUtils.closeInputStream(imageStream);

        }
    }
    /**
     * 根据流程实例Id,获取实时流程图片
     *
     * @param  processInstanceId
     * @param response
     * @return
     */
    @RequestMapping(value = "/traceprocess", method = RequestMethod.GET)
    public void getFlowImgByInstanceId(@RequestParam("processInstanceId") String processInstanceId, HttpServletResponse response) {
        try {
            if (StringUtils.isEmpty(processInstanceId)) {
                log.error("processInstanceId is null");
                return;
            }
            ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
            // 获取历史流程实例
            HistoricProcessInstance historicProcessInstance = processEngine.getHistoryService()
                    .createHistoricProcessInstanceQuery()
                    .processInstanceId(processInstanceId).singleResult();
            // 获取流程中已经执行的节点，按照执行先后顺序排序
            List<HistoricActivityInstance> historicActivityInstances = processEngine.getHistoryService()
                    .createHistoricActivityInstanceQuery()
                    .processInstanceId(processInstanceId)
                    .orderByHistoricActivityInstanceId()
                    .asc().list();
            // 高亮已经执行流程节点ID集合
            List<String> highLightedActivitiIds = new ArrayList<>();
            int index = 1;
            for (HistoricActivityInstance historicActivityInstance : historicActivityInstances) {

                // 用默认颜色
                highLightedActivitiIds.add(historicActivityInstance.getActivityId());

                index++;
            }

            ProcessDiagramGenerator processDiagramGenerator = null;

            // 使用默认的程序图片生成器
            processDiagramGenerator = new DefaultProcessDiagramGenerator();


            BpmnModel bpmnModel = processEngine.getRepositoryService()
                    .getBpmnModel(historicProcessInstance.getProcessDefinitionId());
            // 高亮流程已发生流转的线id集合
            List<String> highLightedFlowIds = getHighLightedFlows(bpmnModel, historicActivityInstances);

            // 使用默认配置获得流程图表生成器，并生成追踪图片字符流
            InputStream imageStream = processDiagramGenerator.generateDiagram(bpmnModel,
                    highLightedActivitiIds, highLightedFlowIds, "宋体",
                    "微软雅黑", "黑体");

            OutputStream outputStream = response.getOutputStream();
            response.setContentType("image/svg+xml;charset=UTF-8");
            response.addHeader("Accept-Ranges","bytes");
            // 输出图片内容
            Integer byteSize = 1024;
            int len=0;
            byte[] b = new byte[byteSize];
            while ((len = imageStream.read(b, 0, byteSize)) != -1) {
                outputStream.write(b, 0, len);
            }
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            log.error("processInstanceId" + processInstanceId + "生成流程图失败，原因：" + e.getMessage(), e);
        }

    }


    /**
     * 获取已经流转的线
     *
     * @param bpmnModel
     * @param historicActivityInstances
     * @return
     */
    private static List<String> getHighLightedFlows(BpmnModel bpmnModel, List<HistoricActivityInstance> historicActivityInstances) {
        // 高亮流程已发生流转的线id集合
        List<String> highLightedFlowIds = new ArrayList<>();
        // 全部活动节点
        List<FlowNode> historicActivityNodes = new ArrayList<>();
        // 已完成的历史活动节点
        List<HistoricActivityInstance> finishedActivityInstances = new ArrayList<>();

        for (HistoricActivityInstance historicActivityInstance : historicActivityInstances) {
            FlowNode flowNode = (FlowNode) bpmnModel.getMainProcess().getFlowElement(historicActivityInstance.getActivityId(), true);
            historicActivityNodes.add(flowNode);
            if (historicActivityInstance.getEndTime() != null) {
                finishedActivityInstances.add(historicActivityInstance);
            }
        }

        FlowNode currentFlowNode = null;
        FlowNode targetFlowNode = null;
        // 遍历已完成的活动实例，从每个实例的outgoingFlows中找到已执行的
        for (HistoricActivityInstance currentActivityInstance : finishedActivityInstances) {
            // 获得当前活动对应的节点信息及outgoingFlows信息
            currentFlowNode = (FlowNode) bpmnModel.getMainProcess().getFlowElement(currentActivityInstance.getActivityId(), true);
            List<SequenceFlow> sequenceFlows = currentFlowNode.getOutgoingFlows();

            /**
             * 遍历outgoingFlows并找到已已流转的 满足如下条件认为已已流转： 1.当前节点是并行网关或兼容网关，则通过outgoingFlows能够在历史活动中找到的全部节点均为已流转 2.当前节点是以上两种类型之外的，通过outgoingFlows查找到的时间最早的流转节点视为有效流转
             */
            if ("parallelGateway".equals(currentActivityInstance.getActivityType()) || "inclusiveGateway".equals(currentActivityInstance.getActivityType())) {
                // 遍历历史活动节点，找到匹配流程目标节点的
                for (SequenceFlow sequenceFlow : sequenceFlows) {
                    targetFlowNode = (FlowNode) bpmnModel.getMainProcess().getFlowElement(sequenceFlow.getTargetRef(), true);
                    if (historicActivityNodes.contains(targetFlowNode)) {
                        highLightedFlowIds.add(targetFlowNode.getId());
                    }
                }
            } else {
                List<Map<String, Object>> tempMapList = new ArrayList<>();
                for (SequenceFlow sequenceFlow : sequenceFlows) {
                    for (HistoricActivityInstance historicActivityInstance : historicActivityInstances) {
                        if (historicActivityInstance.getActivityId().equals(sequenceFlow.getTargetRef())) {
                            Map<String, Object> map = new HashMap<>();
                            map.put("highLightedFlowId", sequenceFlow.getId());
                            map.put("highLightedFlowStartTime", historicActivityInstance.getStartTime().getTime());
                            tempMapList.add(map);
                        }
                    }
                }

                if (!CollectionUtils.isEmpty(tempMapList)) {
                    // 遍历匹配的集合，取得开始时间最早的一个
                    long earliestStamp = 0L;
                    String highLightedFlowId = null;
                    for (Map<String, Object> map : tempMapList) {
                        long highLightedFlowStartTime = Long.valueOf(map.get("highLightedFlowStartTime").toString());
                        if (earliestStamp == 0 || earliestStamp >= highLightedFlowStartTime) {
                            highLightedFlowId = map.get("highLightedFlowId").toString();
                            earliestStamp = highLightedFlowStartTime;
                        }
                    }

                    highLightedFlowIds.add(highLightedFlowId);
                }

            }

        }
        return highLightedFlowIds;
    }

}
