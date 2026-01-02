package com.oa.system.controller;

import com.oa.common.core.Result;
import com.oa.system.entity.Menu;
import com.oa.system.service.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "Menu")
@RestController
@RequestMapping("/v1/system/menus")
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @Operation(summary = "Menu tree")
    @GetMapping
    @PreAuthorize("hasAuthority('system:menu:list')")
    public Result<List<Menu>> list() {
        return Result.success(menuService.listMenuTree());
    }

    @Operation(summary = "Menu detail")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('system:menu:query')")
    public Result<Menu> getById(@PathVariable Long id) {
        return Result.success(menuService.getMenuDetail(id));
    }

    @Operation(summary = "Create menu")
    @PostMapping
    @PreAuthorize("hasAuthority('system:menu:add')")
    public Result<Void> add(@Valid @RequestBody Menu menu) {
        menuService.addMenu(menu);
        return Result.success();
    }

    @Operation(summary = "Update menu")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('system:menu:edit')")
    public Result<Void> update(@PathVariable Long id, @Valid @RequestBody Menu menu) {
        menuService.updateMenu(id, menu);
        return Result.success();
    }

    @Operation(summary = "Delete menus")
    @DeleteMapping("/{ids}")
    @PreAuthorize("hasAuthority('system:menu:remove')")
    public Result<Void> delete(@PathVariable List<Long> ids) {
        menuService.deleteMenus(ids);
        return Result.success();
    }
}
