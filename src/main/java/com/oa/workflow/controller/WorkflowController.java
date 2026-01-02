package com.oa.workflow.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.oa.common.core.Result;
import com.oa.workflow.dto.ApprovalDTO;
import com.oa.workflow.dto.InstanceDTO;
import com.oa.workflow.dto.TaskQueryDTO;
import com.oa.workflow.entity.WorkflowInstance;
import com.oa.workflow.entity.WorkflowTask;
import com.oa.workflow.service.WorkflowService;
import com.oa.workflow.vo.InstanceDetailVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * 工作流控制器
 */
@Tag(name = "工作流管理")
@RestController
@RequestMapping("/api/v1/workflow")
public class WorkflowController {

    private final WorkflowService workflowService;

    public WorkflowController(WorkflowService workflowService) {
        this.workflowService = workflowService;
    }

    @Operation(summary = "发起流程")
    @PostMapping("/instance")
    public Result<Long> startInstance(@Valid @RequestBody InstanceDTO instanceDTO) {
        Long instanceId = workflowService.startInstance(instanceDTO);
        return Result.success(instanceId);
    }

    @Operation(summary = "我的申请")
    @GetMapping("/my-instances")
    public Result<IPage<WorkflowInstance>> myInstances(TaskQueryDTO queryDTO) {
        IPage<WorkflowInstance> page = workflowService.selectMyInstances(queryDTO);
        return Result.success(page);
    }

    @Operation(summary = "流程详情")
    @GetMapping("/instance/{id}")
    public Result<InstanceDetailVO> getInstanceDetail(@PathVariable Long id) {
        InstanceDetailVO detail = workflowService.getInstanceDetail(id);
        return Result.success(detail);
    }

    @Operation(summary = "待办任务")
    @GetMapping("/tasks/todo")
    public Result<IPage<WorkflowTask>> todoTasks(TaskQueryDTO queryDTO) {
        IPage<WorkflowTask> page = workflowService.selectTodoTasks(queryDTO);
        return Result.success(page);
    }

    @Operation(summary = "已办任务")
    @GetMapping("/tasks/done")
    public Result<IPage<WorkflowTask>> doneTasks(TaskQueryDTO queryDTO) {
        IPage<WorkflowTask> page = workflowService.selectDoneTasks(queryDTO);
        return Result.success(page);
    }

    @Operation(summary = "审批任务")
    @PostMapping("/task/{taskId}/approve")
    public Result<Void> approve(@PathVariable Long taskId,
                                @Valid @RequestBody ApprovalDTO approvalDTO) {
        workflowService.approveTask(taskId, approvalDTO);
        return Result.success();
    }

    @Operation(summary = "撤销流程")
    @PostMapping("/instance/{id}/cancel")
    public Result<Void> cancelInstance(@PathVariable Long id) {
        workflowService.cancelInstance(id);
        return Result.success();
    }

    @Operation(summary = "流程统计")
    @GetMapping("/statistics")
    public Result<?> statistics() {
        return Result.success(workflowService.getStatistics());
    }
}