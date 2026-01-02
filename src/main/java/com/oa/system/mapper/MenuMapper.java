package com.oa.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oa.system.entity.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

    @Select({
            "select distinct m.* from sys_menu m",
            "inner join sys_role_menu rm on m.menu_id = rm.menu_id",
            "inner join sys_user_role ur on ur.role_id = rm.role_id",
            "where ur.user_id = #{userId} and m.status = 1 and m.deleted = 0",
            "order by m.parent_id asc, m.sort_order asc"
    })
    List<Menu> selectMenusByUserId(@Param("userId") Long userId);

    @Select({
            "select distinct m.permission from sys_menu m",
            "inner join sys_role_menu rm on m.menu_id = rm.menu_id",
            "inner join sys_user_role ur on ur.role_id = rm.role_id",
            "where ur.user_id = #{userId} and m.permission is not null and m.permission <> ''",
            "and m.deleted = 0"
    })
    List<String> selectPermsByUserId(@Param("userId") Long userId);
}
