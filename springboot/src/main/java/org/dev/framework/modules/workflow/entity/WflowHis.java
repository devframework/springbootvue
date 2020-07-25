package org.dev.framework.modules.workflow.entity;

import lombok.Data;
import org.activiti.engine.task.DelegationState;

import java.util.Date;
import java.util.Map;

@Data
public class WflowHis {
    private String id;

    private String activityId;

    private String activityName;

    private String activityType;

    private String processDefinitionId;

    private String processInstanceId;

    private String executionId;

    private String taskId;

    private String calledProcessInstanceId;

    private String assignee;

    private Date startTime;

    private Date endTime;

    private Long turationInMillis;

    private String DeleteReason;

    private String tenantId;

    private Long durationInMillis;

    private Date time ;

    /**/

    private String name;

    private String description;

    private int priority;

    private String owner;

    private Date createTime;

    private String taskDefinitionKey;

    private Date dueDate;

    private String category;

    private String parentTaskId;

    private String formKey;

    private Map<String, Object> taskLocalVariables;

    private Map<String, Object> processVariables;

    private Date claimTime;

    private String BusinessKey;

    private String deleteReason ;
    private Long workTimeInMillis ;
}
