package org.dev.framework.modules.workflow.service;

import org.activiti.engine.task.Task;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MyService {

    void startProcess();


    List<Task> getTasks(String assignee);
}
