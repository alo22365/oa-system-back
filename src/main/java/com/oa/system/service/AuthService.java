package com.oa.system.service;

import com.oa.system.dto.LoginDTO;
import com.oa.system.vo.LoginVO;
import com.oa.system.vo.RouterVO;
import java.util.List;

public interface AuthService {

    LoginVO login(LoginDTO loginDTO);

    void logout();

    LoginVO refreshToken(String token);

    LoginVO getCurrentUserInfo();

    List<RouterVO> getRouters();
}
