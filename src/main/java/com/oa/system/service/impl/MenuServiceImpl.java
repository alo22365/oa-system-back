package com.oa.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oa.common.exception.BusinessException;
import com.oa.system.entity.Menu;
import com.oa.system.entity.User;
import com.oa.system.mapper.MenuMapper;
import com.oa.system.mapper.UserMapper;
import com.oa.system.service.MenuService;
import com.oa.system.vo.MetaVO;
import com.oa.system.vo.RouterVO;
import com.oa.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    private final MenuMapper menuMapper;
    private final UserMapper userMapper;

    public MenuServiceImpl(MenuMapper menuMapper, UserMapper userMapper) {
        this.menuMapper = menuMapper;
        this.userMapper = userMapper;
    }

    @Override
    public List<Menu> listMenuTree() {
        List<Menu> menus = menuMapper.selectList(new LambdaQueryWrapper<Menu>()
                .orderByAsc(Menu::getParentId, Menu::getOrderNum));
        return buildMenuTree(menus, 0L);
    }

    @Override
    public List<RouterVO> getCurrentUserRouters() {
        String username = SecurityUtils.getCurrentUsername();
        if (username == null) {
            throw new BusinessException("Unauthorized");
        }
        User user = userMapper.selectByUsername(username);
        if (user == null) {
            throw new BusinessException("User not found");
        }
        List<Menu> menus = menuMapper.selectMenusByUserId(user.getUserId()).stream()
                .filter(m -> !"F".equalsIgnoreCase(m.getMenuType()))
                .collect(Collectors.toList());
        List<Menu> tree = buildMenuTree(menus, 0L);
        return buildRouters(tree);
    }

    @Override
    public Menu getMenuDetail(Long menuId) {
        return menuMapper.selectById(menuId);
    }

    @Override
    public void addMenu(Menu menu) {
        if (menu.getParentId() == null) {
            menu.setParentId(0L);
        }
        if (menu.getStatus() == null) {
            menu.setStatus(1);
        }
        if (menu.getVisible() == null) {
            menu.setVisible(0);
        }
        menuMapper.insert(menu);
    }

    @Override
    public void updateMenu(Long menuId, Menu menu) {
        Menu existing = menuMapper.selectById(menuId);
        if (existing == null) {
            throw new BusinessException("Menu not found");
        }
        menu.setMenuId(menuId);
        menuMapper.updateById(menu);
    }

    @Override
    public void deleteMenus(List<Long> menuIds) {
        if (menuIds == null || menuIds.isEmpty()) {
            return;
        }
        menuMapper.deleteBatchIds(menuIds);
    }

    private List<Menu> buildMenuTree(List<Menu> menus, Long parentId) {
        List<Menu> tree = new ArrayList<>();
        for (Menu menu : menus) {
            if (Objects.equals(menu.getParentId(), parentId)) {
                menu.setChildren(buildMenuTree(menus, menu.getMenuId()));
                tree.add(menu);
            }
        }
        return tree;
    }

    private List<RouterVO> buildRouters(List<Menu> menus) {
        List<RouterVO> routers = new ArrayList<>();
        for (Menu menu : menus) {
            RouterVO router = new RouterVO();
            router.setName(menu.getMenuName());
            router.setPath(menu.getPath());
            router.setHidden(menu.getVisible() != null && menu.getVisible() == 1);

            String component = menu.getComponent();
            if ("M".equalsIgnoreCase(menu.getMenuType())) {
                component = component == null || component.isBlank() ? "Layout" : component;
            }
            router.setComponent(component);

            MetaVO meta = new MetaVO();
            meta.setTitle(menu.getMenuName());
            meta.setIcon(menu.getIcon());
            router.setMeta(meta);

            if (menu.getChildren() != null && !menu.getChildren().isEmpty()) {
                router.setChildren(buildRouters(menu.getChildren()));
            }
            routers.add(router);
        }
        return routers;
    }
}
