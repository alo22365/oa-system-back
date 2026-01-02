package com.oa.system.controller;

import com.oa.common.core.Result;
import com.oa.system.entity.Role;
import com.oa.system.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "Role")
@RestController
@RequestMapping("/v1/system/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @Operation(summary = "Role list")
    @GetMapping
    @PreAuthorize("hasAuthority('system:role:list')")
    public Result<List<Role>> list() {
        return Result.success(roleService.listAll());
    }

    @Operation(summary = "Role detail")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('system:role:query')")
    public Result<Role> getById(@PathVariable Long id) {
        return Result.success(roleService.getRoleDetail(id));
    }

    @Operation(summary = "Create role")
    @PostMapping
    @PreAuthorize("hasAuthority('system:role:add')")
    public Result<Void> add(@Valid @RequestBody Role role) {
        roleService.addRole(role);
        return Result.success();
    }

    @Operation(summary = "Update role")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('system:role:edit')")
    public Result<Void> update(@PathVariable Long id, @Valid @RequestBody Role role) {
        roleService.updateRole(id, role);
        return Result.success();
    }

    @Operation(summary = "Delete roles")
    @DeleteMapping("/{ids}")
    @PreAuthorize("hasAuthority('system:role:remove')")
    public Result<Void> delete(@PathVariable List<Long> ids) {
        roleService.deleteRoles(ids);
        return Result.success();
    }
}
