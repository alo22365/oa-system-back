package com.oa.workflow.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 流程实例
 */
@Data
@TableName("wf_instance")
public class WorkflowInstance {

    @TableId(type = IdType.AUTO)
    private Long instanceId;

    private String instanceNo; // 流程编号

    private Long defId; // 流程定义ID

    private String defName; // 流程名称

    private String processType; // 流程类型：leave请假/expense报销/purchase采购

    private Long applicantId; // 申请人ID

    private String applicantName;

    private Long deptId;

    private String deptName;

    private String title; // 流程标题

    private String formData; // 表单数据JSON

    private Integer status; // 0草稿 1审批中 2已通过 3已驳回 4已撤销

    private Integer currentNodeId; // 当前节点ID

    private String currentNodeName;

    private BigDecimal amount; // 金额

    private Integer priority; // 优先级 0普通 1紧急

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    private LocalDateTime finishTime;

    private String remark;

    @TableLogic
    private Integer deleted;
}