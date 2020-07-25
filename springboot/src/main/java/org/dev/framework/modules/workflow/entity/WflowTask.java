package org.dev.framework.modules.workflow.entity;

import lombok.Data;
import org.activiti.engine.task.DelegationState;

import java.util.Date;
import java.util.Map;

@Data
public class WflowTask {
    private String id;

    private String name;

    private String description;

    private int priority;

    private String owner;

    private String assignee;

    private String processInstanceId;

    private String executionId;

    private String processDefinitionId;

    private Date createTime;

    private String taskDefinitionKey;

    private Date dueDate;

    private String category;

    private String parentTaskId;

    private String tenantId;

    private String formKey;

    private Map<String, Object> taskLocalVariables;

    private Map<String, Object> processVariables;

    private Date claimTime;

    private String BusinessKey;

}
