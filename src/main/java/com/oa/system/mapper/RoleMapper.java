package com.oa.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oa.system.entity.Role;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    @Select({
            "select r.role_key from sys_role r",
            "inner join sys_user_role ur on r.role_id = ur.role_id",
            "where ur.user_id = #{userId} and r.deleted = 0"
    })
    List<String> selectRoleKeysByUserId(@Param("userId") Long userId);

    @Select("select menu_id from sys_role_menu where role_id = #{roleId}")
    List<Long> selectMenuIdsByRoleId(@Param("roleId") Long roleId);

    @Delete("delete from sys_role_menu where role_id = #{roleId}")
    int deleteRoleMenusByRoleId(@Param("roleId") Long roleId);

    @Insert({
            "<script>",
            "insert into sys_role_menu(role_id, menu_id) values",
            "<foreach collection='menuIds' item='menuId' separator=','>",
            "(#{roleId}, #{menuId})",
            "</foreach>",
            "</script>"
    })
    int insertRoleMenus(@Param("roleId") Long roleId,
                        @Param("menuIds") List<Long> menuIds);
}
