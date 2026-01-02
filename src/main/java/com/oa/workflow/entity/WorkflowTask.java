package com.oa.workflow.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 流程任务
 */
@Data
@TableName("wf_task")
public class WorkflowTask {

    @TableId(type = IdType.AUTO)
    private Long taskId;

    private Long instanceId;

    private Integer nodeId;

    private String nodeName;

    private Long assigneeId; // 审批人ID

    private String assigneeName;

    private Integer status; // 0待处理 1已同意 2已驳回 3已转办

    private String opinion; // 审批意见

    private String attachments; // 附件JSON

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    private LocalDateTime finishTime;

    private Long transferTo; // 转办给谁

    @TableLogic
    private Integer deleted;
}