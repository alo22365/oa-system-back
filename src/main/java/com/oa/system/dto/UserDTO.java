package com.oa.system.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.util.List;

@Data
public class UserDTO {

    @NotBlank(message = "username is required")
    private String username;

    private String password;

    private String nickname;

    private String email;

    private String phone;

    private Integer status;

    private List<Long> roleIds;
}
