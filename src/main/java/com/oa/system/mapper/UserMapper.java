package com.oa.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oa.system.entity.User;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("select * from sys_user where username = #{username} and deleted = 0")
    User selectByUsername(@Param("username") String username);

    @Select("select role_id from sys_user_role where user_id = #{userId}")
    List<Long> selectRoleIdsByUserId(@Param("userId") Long userId);

    @Delete("delete from sys_user_role where user_id = #{userId}")
    int deleteUserRolesByUserId(@Param("userId") Long userId);

    @Insert({
            "<script>",
            "insert into sys_user_role(user_id, role_id) values",
            "<foreach collection='roleIds' item='roleId' separator=','>",
            "(#{userId}, #{roleId})",
            "</foreach>",
            "</script>"
    })
    int insertUserRoles(@Param("userId") Long userId,
                        @Param("roleIds") List<Long> roleIds);
}
