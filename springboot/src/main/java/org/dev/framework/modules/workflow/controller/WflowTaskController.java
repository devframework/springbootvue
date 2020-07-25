package org.dev.framework.modules.workflow.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Task;
import org.dev.framework.modules.workflow.entity.WflowTask;
import org.dev.framework.common.PaginAtion;
import org.dev.framework.common.ResponseResult;
import org.dev.framework.modules.workflow.entity.WflowDefine;
import org.dev.framework.modules.workflow.entity.WflowTask;
import org.dev.framework.security.jwt.JwtUtil;
import org.dev.framework.utils.SpringSecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("wflowTask")
public class WflowTaskController {

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    TaskService taskService;

    /**
     * 待签核流程
     *
     * @param wflowTask
     * @param pagination
     * @return
     */
    @GetMapping("/list")
    public ResponseResult<IPage<WflowTask>> List(WflowTask wflowTask, PaginAtion pagination) {
        IPage page = pagination.getPage();
        int firstResult = (pagination.getCurrent() - 1) * pagination.getSize();
        int maxResults = pagination.getCurrent() * pagination.getSize();
        //获取ProcessEngine对象 默认配置文件名称：activiti.cfg.xml 并且configuration的Bean实例ID为processEngineConfiguration
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //获取一个TaskService对象
        TaskService taskService = processEngine.getTaskService();
        //查询代办业务 createTaskQuery 查询任务 taskCandidateOrAssigned 查询任务执行者 processDefinitionKey：查询流程
        /**
         * taskCandidateOrAssigned匹配规则:1.Assigned 2.配置bpmn文件中定义的值
         * taskAssignee匹配规则:1.Assigned
         */
        List<WflowTask> wflowTasks = new ArrayList<>();
        List<Task> list = taskService.createTaskQuery().taskCandidateOrAssigned(SpringSecurityUtils.CurrentUser().getLoginName())
                .orderByTaskDueDate().desc().listPage(firstResult, maxResults);
        long count = taskService.createTaskQuery().taskCandidateOrAssigned(SpringSecurityUtils.CurrentUser().getLoginName()).count();
        for (Task task : list) {
            WflowTask wflowTask1 = new WflowTask();
            BeanUtils.copyProperties(task, wflowTask1);
            wflowTasks.add(wflowTask1);
        }
        page.setRecords(wflowTasks);
        page.setTotal(count);
        return ResponseResult.success(page);
    }


    /**
     * 所有待签核流程
     *
     * @param wflowTask
     * @param pagination
     * @return
     */
    @GetMapping("/all-list")
    public ResponseResult<IPage<WflowTask>> allList(WflowTask wflowTask, PaginAtion pagination) {
        IPage page = pagination.getPage();
        int firstResult = (pagination.getCurrent() - 1) * pagination.getSize();
        int maxResults = pagination.getCurrent() * pagination.getSize();
        //获取ProcessEngine对象 默认配置文件名称：activiti.cfg.xml 并且configuration的Bean实例ID为processEngineConfiguration
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //获取一个TaskService对象
        TaskService taskService = processEngine.getTaskService();
        //查询代办业务 createTaskQuery 查询任务 taskCandidateOrAssigned 查询任务执行者 processDefinitionKey：查询流程
        /**
         * taskCandidateOrAssigned匹配规则:1.Assigned 2.配置bpmn文件中定义的值
         * taskAssignee匹配规则:1.Assigned
         */
        List<WflowTask> wflowTasks = new ArrayList<>();
        List<Task> list = taskService.createTaskQuery()
                .orderByTaskDueDate().desc().listPage(firstResult, maxResults);
        long count = taskService.createTaskQuery().count();
        for (Task task : list) {

            WflowTask wflowTask1 = new WflowTask();
            BeanUtils.copyProperties(task, wflowTask1);
            wflowTasks.add(wflowTask1);
        }
        page.setRecords(wflowTasks);
        page.setTotal(count);
        return ResponseResult.success(page);
    }

    /**
     * 类型转换
     *
     * @param task
     * @return
     */
    private WflowTask ConvertWflowTask(Task task) {
        WflowTask wflowTask = new WflowTask();
        wflowTask.setId(task.getId());
        wflowTask.setParentTaskId(task.getParentTaskId());
        wflowTask.setAssignee(task.getAssignee());
        wflowTask.setName(task.getName());
        wflowTask.setCategory(task.getCategory());
        wflowTask.setDescription(task.getDescription());
        wflowTask.setDueDate(task.getDueDate());
        wflowTask.setFormKey(task.getFormKey());
        wflowTask.setBusinessKey(task.getBusinessKey());
        wflowTask.setOwner(task.getOwner());
        wflowTask.setPriority(task.getPriority());
        wflowTask.setTenantId(task.getTenantId());
        wflowTask.setCreateTime(task.getCreateTime());
        wflowTask.setClaimTime(task.getClaimTime());
        wflowTask.setProcessInstanceId(task.getProcessInstanceId());
        wflowTask.setProcessDefinitionId(task.getProcessDefinitionId());
        wflowTask.setProcessVariables(task.getProcessVariables());
        wflowTask.setTaskDefinitionKey(task.getTaskDefinitionKey());
        wflowTask.setTaskLocalVariables(task.getTaskLocalVariables());
        return wflowTask;
    }

    /**
     * 启动流程
     *
     * @return
     */
    @GetMapping("/start")
    public ResponseResult startProcess() {
        //得到processEngine对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //得到RuntimeService方法
        RuntimeService runtimeService = processEngine.getRuntimeService();
        Authentication.setAuthenticatedUserId(JwtUtil.CurrentUserName());
        ProcessInstance processInstance = runtimeService.startProcessInstanceById("purchaseorder:4:0caf690d-c96a-11ea-a628-fa94c28bc5f4", "xxx");
        System.out.println(processInstance.getId());
        System.out.println(processInstance.getDeploymentId());
        System.out.println(processInstance.getActivityId());
        return ResponseResult.success();
    }

    /**
     * 完成流程
     *
     * @param idArray
     * @return
     */
    @PostMapping("/complete")
    public ResponseResult completeProcess(@RequestBody List<String> idArray) {
        for (String taskid : idArray) {
            taskService.complete(taskid);
        }
        return ResponseResult.success();
    }


    /**
     * 拒绝流程
     *
     * @param idArray
     * @return
     */
    @PostMapping("/reject")
    public ResponseResult reject(@RequestBody List<String> idArray) {
        for (String taskid : idArray) {
            taskService.deleteTask(taskid, " 测试拒绝愿意i你");
        }
        return ResponseResult.success();
    }


}
