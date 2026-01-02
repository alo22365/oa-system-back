package com.oa.workflow.dto;

import lombok.Data;

@Data
public class TaskQueryDTO {

    private Integer pageNum = 1;

    private Integer pageSize = 10;
}
