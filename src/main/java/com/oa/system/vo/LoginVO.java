package com.oa.system.vo;

import lombok.Data;
import java.util.List;

@Data
public class LoginVO {

    private String token;

    private String tokenType;

    private Long expireTime;

    private Long userId;

    private String username;

    private String nickname;

    private String avatar;

    private List<String> roles;

    private List<String> permissions;
}
