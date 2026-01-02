package com.oa.system.controller;

import com.oa.common.core.Result;
import com.oa.system.dto.LoginDTO;
import com.oa.system.service.AuthService;
import com.oa.system.vo.LoginVO;
import com.oa.system.vo.RouterVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "Auth")
@RestController
@RequestMapping("/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "Login")
    @PostMapping("/login")
    public Result<LoginVO> login(@Valid @RequestBody LoginDTO loginDTO) {
        LoginVO loginVO = authService.login(loginDTO);
        return Result.success(loginVO);
    }

    @Operation(summary = "Logout")
    @PostMapping("/logout")
    public Result<Void> logout() {
        authService.logout();
        return Result.success();
    }

    @Operation(summary = "Refresh token")
    @PostMapping("/refresh")
    public Result<LoginVO> refresh(@RequestHeader("Authorization") String token) {
        LoginVO loginVO = authService.refreshToken(token);
        return Result.success(loginVO);
    }

    @Operation(summary = "Get current user info")
    @GetMapping("/info")
    public Result<LoginVO> getUserInfo() {
        LoginVO userInfo = authService.getCurrentUserInfo();
        return Result.success(userInfo);
    }

    @Operation(summary = "Get routers")
    @GetMapping("/routers")
    public Result<List<RouterVO>> getRouters() {
        return Result.success(authService.getRouters());
    }
}
