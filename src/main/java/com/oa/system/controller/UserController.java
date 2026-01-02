package com.oa.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.oa.common.core.Result;
import com.oa.system.dto.UserDTO;
import com.oa.system.dto.UserQueryDTO;
import com.oa.system.entity.User;
import com.oa.system.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 用户管理控制器
 */
@Tag(name = "用户管理")
@RestController
@RequestMapping("/api/v1/system/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "用户列表")
    @GetMapping
    @PreAuthorize("hasAuthority('system:user:list')")
    public Result<IPage<User>> list(UserQueryDTO queryDTO) {
        IPage<User> page = userService.selectUserPage(queryDTO);
        return Result.success(page);
    }

    @Operation(summary = "用户详情")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('system:user:query')")
    public Result<User> getById(@PathVariable Long id) {
        User user = userService.getById(id);
        return Result.success(user);
    }

    @Operation(summary = "新增用户")
    @PostMapping
    @PreAuthorize("hasAuthority('system:user:add')")
    public Result<Void> add(@Valid @RequestBody UserDTO userDTO) {
        userService.addUser(userDTO);
        return Result.success();
    }

    @Operation(summary = "修改用户")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('system:user:edit')")
    public Result<Void> update(@PathVariable Long id,
                               @Valid @RequestBody UserDTO userDTO) {
        userService.updateUser(id, userDTO);
        return Result.success();
    }

    @Operation(summary = "删除用户")
    @DeleteMapping("/{ids}")
    @PreAuthorize("hasAuthority('system:user:remove')")
    public Result<Void> delete(@PathVariable List<Long> ids) {
        userService.removeByIds(ids);
        return Result.success();
    }

    @Operation(summary = "重置密码")
    @PutMapping("/{id}/reset-password")
    @PreAuthorize("hasAuthority('system:user:resetPwd')")
    public Result<Void> resetPassword(@PathVariable Long id) {
        userService.resetPassword(id);
        return Result.success();
    }

    @Operation(summary = "修改用户状态")
    @PutMapping("/{id}/status")
    @PreAuthorize("hasAuthority('system:user:edit')")
    public Result<Void> changeStatus(@PathVariable Long id,
                                     @RequestParam Integer status) {
        userService.changeStatus(id, status);
        return Result.success();
    }
}