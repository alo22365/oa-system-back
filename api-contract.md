# OA 系统 API 契约（前后端唯一规范）

本文件为前后端对接的唯一接口契约，后续以此为准。

## 基础约定

- 基础地址：`http://localhost:8080`
- 接口前缀固定为：`/api/v1`
- 请求 Content-Type：`application/json`

## 认证与权限

- 使用 JWT Bearer Token
- 请求头：`Authorization: Bearer <token>`
- Token 来源：`POST /api/v1/auth/login`
- `permissions` 为按钮级权限来源（前端 `v-hasPermission` / 后端 `@PreAuthorize` 使用）
- `routers.hidden = true` 表示菜单不显示，仅用于路由存在

## 统一响应格式

```json
{
  "code": 200,
  "message": "操作成功",
  "data": {},
  "traceId": null
}
```

- `code`：业务状态码（200 成功，500 失败）
- `message`：提示信息
- `data`：业务数据（结构随接口变化）
- `traceId`：链路追踪 ID（可能为 null）

## 分页结构（MyBatis Plus IPage）

```json
{
  "records": [],
  "total": 0,
  "size": 10,
  "current": 1,
  "pages": 0
}
```

## 错误处理（统一约定）

### 错误响应示例

```json
{
  "code": 500,
  "message": "Internal server error",
  "data": null,
  "traceId": null
}
```

### 常见错误

- 401 Unauthorized：未登录或 token 失效
- 403 Forbidden：权限不足
- 500 Internal Server Error：服务端异常

### 前端推荐处理

- 401：清理本地登录态并跳转登录页
- 403：提示无权限（可保留当前页面）
- 500：提示“系统异常，请稍后重试”，必要时提示用户联系管理员

## 接口规范约束（必须遵守）

- 接口前缀固定为 `/api/v1`
- 不允许前端假造路由或权限
- 所有菜单必须来自 `/api/v1/auth/routers`
- 所有权限必须来自 `/api/v1/auth/info` 的 `permissions`

## 接口列表

### 认证 Auth

#### POST /api/v1/auth/login

请求体：

```json
{
  "username": "admin",
  "password": "123456"
}
```

响应 data（`LoginVO`）：

```json
{
  "token": "jwt-token",
  "tokenType": "Bearer",
  "expireTime": 1700000000000,
  "userId": 1,
  "username": "admin",
  "nickname": "系统管理员",
  "avatar": null,
  "roles": ["admin"],
  "permissions": ["system:user:list"]
}
```

#### POST /api/v1/auth/logout

- 需登录
- 响应 data：`null`

#### POST /api/v1/auth/refresh

- 需登录
- Header：`Authorization: Bearer <token>`
- 响应 data：`LoginVO`（含新 token 与用户信息）

#### GET /api/v1/auth/info

- 需登录
- 响应 data：`LoginVO`
- 注意：`token` / `tokenType` / `expireTime` 可能为 `null`（属正常设计）

#### GET /api/v1/auth/routers

- 需登录
- 响应 data：`RouterVO[]`

```json
[
  {
    "path": "/system",
    "component": "Layout",
    "name": "System",
    "hidden": false,
    "meta": { "title": "System", "icon": "setting" },
    "children": []
  }
]
```

### 用户 Users

#### GET /api/v1/system/users

- 需登录
- 权限：`system:user:list`
- 查询参数：`pageNum`, `pageSize`, `username`, `phone`, `status`
- 响应 data：`IPage<User>`

#### GET /api/v1/system/users/{id}

- 需登录
- 权限：`system:user:query`
- 响应 data：`User`

#### POST /api/v1/system/users

- 需登录
- 权限：`system:user:add`
- 请求体（`UserDTO`）

```json
{
  "username": "jane",
  "password": "secret",
  "nickname": "Jane",
  "email": "jane@example.com",
  "phone": "18800000000",
  "status": 1,
  "roleIds": [1]
}
```

#### PUT /api/v1/system/users/{id}

- 需登录
- 权限：`system:user:edit`
- 请求体：`UserDTO`（同创建）

#### DELETE /api/v1/system/users/{ids}

- 需登录
- 权限：`system:user:remove`
- 路径参数 `ids`：逗号分隔，如 `/api/v1/system/users/1,2,3`

#### PUT /api/v1/system/users/{id}/reset-password

- 需登录
- 权限：`system:user:resetPwd`
- 响应 data：`null`

#### PUT /api/v1/system/users/{id}/status

- 需登录
- 权限：`system:user:edit`
- 查询参数：`status`（整数）

#### PUT /api/v1/system/users/{id}/roles

- 需登录
- 权限：`system:user:edit`
- 请求体：角色 ID 数组（`Long[]`）

### 角色 Roles

#### GET /api/v1/system/roles

- 需登录
- 权限：`system:role:list`
- 响应 data：`Role[]`

#### GET /api/v1/system/roles/{id}

- 需登录
- 权限：`system:role:query`
- 响应 data：`Role`

#### POST /api/v1/system/roles

- 需登录
- 权限：`system:role:add`
- 请求体：`Role`

#### PUT /api/v1/system/roles/{id}

- 需登录
- 权限：`system:role:edit`
- 请求体：`Role`

#### DELETE /api/v1/system/roles/{ids}

- 需登录
- 权限：`system:role:remove`
- 路径参数 `ids`：逗号分隔

### 菜单 Menus

#### GET /api/v1/system/menus

- 需登录
- 权限：`system:menu:list`
- 响应 data：`Menu[]`（树形）

#### GET /api/v1/system/menus/{id}

- 需登录
- 权限：`system:menu:query`
- 响应 data：`Menu`

#### POST /api/v1/system/menus

- 需登录
- 权限：`system:menu:add`
- 请求体：`Menu`

#### PUT /api/v1/system/menus/{id}

- 需登录
- 权限：`system:menu:edit`
- 请求体：`Menu`

#### DELETE /api/v1/system/menus/{ids}

- 需登录
- 权限：`system:menu:remove`
- 路径参数 `ids`：逗号分隔

### 流程 Workflow

#### POST /api/v1/workflow/instance

- 需登录
- 请求体（`InstanceDTO`）

```json
{
  "defId": 1,
  "title": "请假申请",
  "formData": "{\"days\":3}"
}
```

- 响应 data：流程实例 ID（`Long`）

#### GET /api/v1/workflow/my-instances

- 需登录
- 查询参数：`pageNum`, `pageSize`
- 响应 data：`IPage<WorkflowInstance>`

#### GET /api/v1/workflow/instance/{id}

- 需登录
- 响应 data：`InstanceDetailVO`

#### GET /api/v1/workflow/tasks/todo

- 需登录
- 查询参数：`pageNum`, `pageSize`
- 响应 data：`IPage<WorkflowTask>`

#### GET /api/v1/workflow/tasks/done

- 需登录
- 查询参数：`pageNum`, `pageSize`
- 响应 data：`IPage<WorkflowTask>`

#### POST /api/v1/workflow/task/{taskId}/approve

- 需登录
- 请求体（`ApprovalDTO`）

```json
{
  "action": 1,
  "opinion": "同意"
}
```

#### POST /api/v1/workflow/instance/{id}/cancel

- 需登录

#### GET /api/v1/workflow/statistics

- 需登录
- 响应 data：统计对象（由服务端定义）
