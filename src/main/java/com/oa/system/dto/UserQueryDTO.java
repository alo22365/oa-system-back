package com.oa.system.dto;

import lombok.Data;

@Data
public class UserQueryDTO {

    private Integer pageNum = 1;

    private Integer pageSize = 10;

    private String username;

    private String phone;

    private Integer status;
}
