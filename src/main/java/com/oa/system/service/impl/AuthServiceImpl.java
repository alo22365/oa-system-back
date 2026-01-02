package com.oa.system.service.impl;

import com.oa.common.exception.BusinessException;
import com.oa.system.dto.LoginDTO;
import com.oa.system.entity.User;
import com.oa.system.mapper.MenuMapper;
import com.oa.system.mapper.RoleMapper;
import com.oa.system.mapper.UserMapper;
import com.oa.system.service.AuthService;
import com.oa.system.service.MenuService;
import com.oa.system.vo.LoginVO;
import com.oa.system.vo.RouterVO;
import com.oa.utils.JwtUtil;
import com.oa.utils.SecurityUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.util.Date;
import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserMapper userMapper;
    private final RoleMapper roleMapper;
    private final MenuMapper menuMapper;
    private final MenuService menuService;

    public AuthServiceImpl(AuthenticationManager authenticationManager,
                           JwtUtil jwtUtil,
                           UserMapper userMapper,
                           RoleMapper roleMapper,
                           MenuMapper menuMapper,
                           MenuService menuService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userMapper = userMapper;
        this.roleMapper = roleMapper;
        this.menuMapper = menuMapper;
        this.menuService = menuService;
    }

    @Override
    public LoginVO login(LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getUsername(), loginDTO.getPassword()));
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new BusinessException("Invalid credentials");
        }

        User user = userMapper.selectByUsername(loginDTO.getUsername());
        if (user == null) {
            throw new BusinessException("User not found");
        }

        String token = jwtUtil.generateToken(user.getUsername());
        return buildLoginVO(user, token);
    }

    @Override
    public void logout() {
        // Stateless JWT logout: no server-side token store.
    }

    @Override
    public LoginVO refreshToken(String token) {
        String rawToken = token;
        if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            rawToken = token.substring(7);
        }
        if (!StringUtils.hasText(rawToken) || !jwtUtil.validateToken(rawToken)) {
            throw new BusinessException("Invalid token");
        }
        String username = jwtUtil.getUsernameFromToken(rawToken);
        User user = userMapper.selectByUsername(username);
        if (user == null) {
            throw new BusinessException("User not found");
        }
        String newToken = jwtUtil.generateToken(username);
        return buildLoginVO(user, newToken);
    }

    @Override
    public LoginVO getCurrentUserInfo() {
        String username = SecurityUtils.getCurrentUsername();
        if (!StringUtils.hasText(username)) {
            throw new BusinessException("Unauthorized");
        }
        User user = userMapper.selectByUsername(username);
        if (user == null) {
            throw new BusinessException("User not found");
        }
        return buildLoginVO(user, null);
    }

    @Override
    public List<RouterVO> getRouters() {
        return menuService.getCurrentUserRouters();
    }

    private LoginVO buildLoginVO(User user, String token) {
        LoginVO loginVO = new LoginVO();
        loginVO.setUserId(user.getUserId());
        loginVO.setUsername(user.getUsername());
        loginVO.setNickname(user.getNickname());
        loginVO.setAvatar(user.getAvatar());

        List<String> roles = roleMapper.selectRoleKeysByUserId(user.getUserId());
        List<String> perms = menuMapper.selectPermsByUserId(user.getUserId());
        loginVO.setRoles(roles);
        loginVO.setPermissions(perms);

        if (token != null) {
            loginVO.setToken(token);
            loginVO.setTokenType("Bearer");
            Date expiration = jwtUtil.getExpirationFromToken(token);
            if (expiration != null) {
                loginVO.setExpireTime(expiration.getTime());
            }
        }
        return loginVO;
    }
}
