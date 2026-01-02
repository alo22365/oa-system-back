package com.oa.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oa.common.exception.BusinessException;
import com.oa.system.entity.Role;
import com.oa.system.mapper.RoleMapper;
import com.oa.system.service.RoleService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    private final RoleMapper roleMapper;

    public RoleServiceImpl(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    @Override
    public List<Role> listAll() {
        return roleMapper.selectList(new LambdaQueryWrapper<Role>()
                .orderByDesc(Role::getCreateTime));
    }

    @Override
    public Role getRoleDetail(Long roleId) {
        Role role = roleMapper.selectById(roleId);
        if (role == null) {
            return null;
        }
        role.setMenuIds(roleMapper.selectMenuIdsByRoleId(roleId));
        return role;
    }

    @Override
    public void addRole(Role role) {
        if (role.getStatus() == null) {
            role.setStatus(1);
        }
        roleMapper.insert(role);
        if (role.getMenuIds() != null && !role.getMenuIds().isEmpty()) {
            roleMapper.insertRoleMenus(role.getRoleId(), role.getMenuIds());
        }
    }

    @Override
    public void updateRole(Long roleId, Role role) {
        Role existing = roleMapper.selectById(roleId);
        if (existing == null) {
            throw new BusinessException("Role not found");
        }
        role.setRoleId(roleId);
        roleMapper.updateById(role);

        roleMapper.deleteRoleMenusByRoleId(roleId);
        if (role.getMenuIds() != null && !role.getMenuIds().isEmpty()) {
            roleMapper.insertRoleMenus(roleId, role.getMenuIds());
        }
    }

    @Override
    public void deleteRoles(List<Long> roleIds) {
        if (roleIds == null || roleIds.isEmpty()) {
            return;
        }
        roleMapper.deleteBatchIds(roleIds);
        for (Long roleId : roleIds) {
            roleMapper.deleteRoleMenusByRoleId(roleId);
        }
    }
}
