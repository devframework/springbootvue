package org.dev.framework.modules.workflow.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.image.ProcessDiagramGenerator;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.apache.commons.io.FileUtils;
import org.dev.framework.common.PaginAtion;
import org.dev.framework.common.ResponseResult;
import org.dev.framework.modules.workflow.entity.WflowDefine;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("wflowDefine")
@Slf4j
public class WflowDefineController {
    /**
     * 流程定义查询
     *
     * @param wflowDefine
     * @param pagination
     * @return
     */
    @GetMapping("/list")
    public ResponseResult<IPage<ProcessDefinition>> list(WflowDefine wflowDefine, PaginAtion pagination) {
        IPage page = pagination.getPage();
        int firstResult = (pagination.getCurrent() - 1) * pagination.getSize();
        int maxResults = pagination.getCurrent() * pagination.getSize();
        //查询集合
        List<ProcessDefinition> list = ProcessEngines.getDefaultProcessEngine().getRepositoryService()//与流程定义和部署对象相关的Service
                .createProcessDefinitionQuery()//创建一个流程定义查询
                /*指定查询条件,where条件*/
                //.deploymentId(deploymentId)//使用部署对象ID查询
                //.processDefinitionId(processDefinitionId)//使用流程定义ID查询
                //.processDefinitionName("请假流程")//使用流程定义的KEY查询
                //.processDefinitionNameLike(processDefinitionNameLike)//使用流程定义的名称模糊查询
                /*排序*/
                .orderByProcessDefinitionVersion().asc()//按照版本的升序排列
                //.orderByProcessDefinitionName().desc()//按照流程定义的名称降序排列
                //.list();//返回一个集合列表，封装流程定义
                //.singleResult();//返回唯一结果集
                //.count();//返回结果集数量
                .latestVersion()//最新版本
                .listPage(firstResult, maxResults);//分页查询
        Long count = ProcessEngines.getDefaultProcessEngine().getRepositoryService()//与流程定义和部署对象相关的Service
                .createProcessDefinitionQuery().latestVersion()//创建一个流程定义查询
                /*指定查询条件,where条件*/
                //.deploymentId(deploymentId)//使用部署对象ID查询
                //.processDefinitionId(processDefinitionId)//使用流程定义ID查询
                //.processDefinitionKey(processDefinitionKey)//使用流程定义的KEY查询
                //.processDefinitionNameLike(processDefinitionNameLike)//使用流程定义的名称模糊查询
                .count();//返回结果集数量
        List<WflowDefine> wflowDefines = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (ProcessDefinition processDefinition1 : list) {
                wflowDefine = new WflowDefine();
                BeanUtils.copyProperties(processDefinition1, wflowDefine);
                wflowDefines.add(wflowDefine);
            }
        }
        page.setRecords(wflowDefines);
        page.setTotal(count);
        return ResponseResult.success(page);
    }

    /**
     * 删除流程定义
     *
     * @param deploymentIds
     * @return
     */
    @PostMapping("delete")
    @Transactional
    public ResponseResult delete(@RequestBody List<String> deploymentIds) {
        try {
            RepositoryService repositoryService = ProcessEngines.getDefaultProcessEngine().getRepositoryService();
            for (String deploymentId : deploymentIds) {
                repositoryService.deleteDeployment(deploymentId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.error();
        }
        return ResponseResult.success();
    }

    /**
     * 部署流程
     *
     * @param wflowDefine
     * @return
     */
    @PostMapping("deploy")
    @Transactional
    public ResponseResult deploy(@RequestBody WflowDefine wflowDefine) {
        try {
            //1.创建ProcessEngine对象
            ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

            //2.得到RepositoryService实例
            RepositoryService repositoryService = processEngine.getRepositoryService();

            //3.进行部署
            Deployment deployment = repositoryService.createDeployment()//创建Deployment对象
                    .name("订单审批")
                    .key("PURCHASEORDER.bpmn")
                    .category("purchase")
                    .tenantId("000000")
                    .addClasspathResource("process/PURCHASEORDER.bpmn")//添加bpmn文件
                    .addClasspathResource("process/PURCHASEORDER.bpmn.png")//添加png文件
                    .deploy();//部署
            log.info("name---" + deployment.getName());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.error();
        }
        return ResponseResult.success();
    }

    /**
     * 查看流程图
     *
     * @return
     */
    @GetMapping("view")
    public void view(@RequestParam("definitionId") String definitionId, HttpServletResponse response) throws IOException {
        //1.创建ProcessEngine对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        // 根据流程对象获取流程对象模型
        BpmnModel bpmnModel = processEngine.getRepositoryService().getBpmnModel(definitionId);
        ProcessDiagramGenerator processDiagramGenerator = new DefaultProcessDiagramGenerator();
        InputStream imageStream = null;
        try {
            imageStream = processDiagramGenerator.generateDiagram(bpmnModel, "宋体", "微软雅黑", "黑体");
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
}
