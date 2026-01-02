package com.oa.workflow.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oa.workflow.dto.ApprovalDTO;
import com.oa.workflow.dto.InstanceDTO;
import com.oa.workflow.dto.TaskQueryDTO;
import com.oa.workflow.entity.WorkflowInstance;
import com.oa.workflow.entity.WorkflowTask;
import com.oa.workflow.service.WorkflowService;
import com.oa.workflow.vo.InstanceDetailVO;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class WorkflowServiceImpl implements WorkflowService {

    @Override
    public Long startInstance(InstanceDTO instanceDTO) {
        return 0L;
    }

    @Override
    public IPage<WorkflowInstance> selectMyInstances(TaskQueryDTO queryDTO) {
        return new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize());
    }

    @Override
    public InstanceDetailVO getInstanceDetail(Long instanceId) {
        InstanceDetailVO detail = new InstanceDetailVO();
        detail.setInstance(null);
        detail.setTasks(Collections.emptyList());
        return detail;
    }

    @Override
    public IPage<WorkflowTask> selectTodoTasks(TaskQueryDTO queryDTO) {
        return new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize());
    }

    @Override
    public IPage<WorkflowTask> selectDoneTasks(TaskQueryDTO queryDTO) {
        return new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize());
    }

    @Override
    public void approveTask(Long taskId, ApprovalDTO approvalDTO) {
    }

    @Override
    public void cancelInstance(Long instanceId) {
    }

    @Override
    public Map<String, Object> getStatistics() {
        return new HashMap<>();
    }
}
