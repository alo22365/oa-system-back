package com.oa.workflow.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.oa.workflow.dto.ApprovalDTO;
import com.oa.workflow.dto.InstanceDTO;
import com.oa.workflow.dto.TaskQueryDTO;
import com.oa.workflow.entity.WorkflowInstance;
import com.oa.workflow.entity.WorkflowTask;
import com.oa.workflow.vo.InstanceDetailVO;
import java.util.Map;

public interface WorkflowService {

    Long startInstance(InstanceDTO instanceDTO);

    IPage<WorkflowInstance> selectMyInstances(TaskQueryDTO queryDTO);

    InstanceDetailVO getInstanceDetail(Long instanceId);

    IPage<WorkflowTask> selectTodoTasks(TaskQueryDTO queryDTO);

    IPage<WorkflowTask> selectDoneTasks(TaskQueryDTO queryDTO);

    void approveTask(Long taskId, ApprovalDTO approvalDTO);

    void cancelInstance(Long instanceId);

    Map<String, Object> getStatistics();
}
