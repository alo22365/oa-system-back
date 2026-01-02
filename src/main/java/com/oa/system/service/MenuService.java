package com.oa.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.oa.system.entity.Menu;
import com.oa.system.vo.RouterVO;
import java.util.List;

public interface MenuService extends IService<Menu> {

    List<Menu> listMenuTree();

    List<RouterVO> getCurrentUserRouters();

    Menu getMenuDetail(Long menuId);

    void addMenu(Menu menu);

    void updateMenu(Long menuId, Menu menu);

    void deleteMenus(List<Long> menuIds);
}
