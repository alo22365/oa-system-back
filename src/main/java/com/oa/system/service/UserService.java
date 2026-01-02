package com.oa.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.oa.system.dto.UserDTO;
import com.oa.system.dto.UserQueryDTO;
import com.oa.system.entity.User;
import java.util.List;

public interface UserService extends IService<User> {

    IPage<User> selectUserPage(UserQueryDTO queryDTO);

    User getUserDetail(Long userId);

    void addUser(UserDTO userDTO);

    void updateUser(Long userId, UserDTO userDTO);

    void resetPassword(Long userId);

    void changeStatus(Long userId, Integer status);

    void updateUserRoles(Long userId, List<Long> roleIds);
}
