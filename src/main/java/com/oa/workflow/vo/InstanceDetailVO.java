package com.oa.workflow.vo;

import com.oa.workflow.entity.WorkflowInstance;
import com.oa.workflow.entity.WorkflowTask;
import lombok.Data;
import java.util.List;

@Data
public class InstanceDetailVO {

    private WorkflowInstance instance;

    private List<WorkflowTask> tasks;
}
