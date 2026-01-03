package com.oa.dashboard.controller;

import com.oa.common.core.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Collections;
import java.util.Map;

@Tag(name = "Dashboard")
@RestController
@RequestMapping("/v1/dashboard")
public class DashboardController {

    @Operation(summary = "Dashboard overview (placeholder)")
    @GetMapping("/overview")
    public Result<Map<String, Object>> overview() {
        return Result.success(Collections.emptyMap());
    }
}
