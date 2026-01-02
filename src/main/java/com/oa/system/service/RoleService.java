package com.oa.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.oa.system.entity.Role;
import java.util.List;

public interface RoleService extends IService<Role> {

    List<Role> listAll();

    Role getRoleDetail(Long roleId);

    void addRole(Role role);

    void updateRole(Long roleId, Role role);

    void deleteRoles(List<Long> roleIds);
}
