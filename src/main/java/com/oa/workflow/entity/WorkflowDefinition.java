package com.oa.workflow.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("wf_definition")
public class WorkflowDefinition {

    @TableId(type = IdType.AUTO)
    private Long defId;

    private String defName;
}
