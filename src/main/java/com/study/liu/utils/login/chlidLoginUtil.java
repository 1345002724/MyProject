package com.study.liu.utils.login;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;

public class chlidLoginUtil {
    /**
     * 获取路由名称
     *
     * @param menu 菜单信息
     * @return 路由名称
     */
    public String getRouteName(Map<String, Object> menu)
    {
        String routerName = StringUtils.capitalize(menu.get("path")+"");
        // 非外链并且是一级目录（类型为目录）
        if (isMenuFrame(menu))
        {
            routerName = StringUtils.EMPTY;
        }
        return routerName;
    }
    /**
     * 是否为菜单内部跳转
     *
     * @param menu 菜单信息
     * @return 结果
     */
    public boolean isMenuFrame(Map<String, Object> menu)
    {
        return Integer.parseInt(menu.get("parent_id")+"") == 0 && "C".equals(menu.get("menu_type"))
                && menu.get("is_frame").equals("1");
    }
    /**
     * 是否为内链组件
     *
     * @param menu 菜单信息
     * @return 结果
     */
    public boolean isInnerLink(Map<String, Object> menu)
    {
        return menu.get("is_frame").equals("1") ;
    }
    /**
     * 获取路由地址
     *
     * @param menu 菜单信息
     * @return 路由地址
     */
    public String getRouterPath(Map<String, Object> menu)
    {
        String routerPath = menu.get("path")+"";
        // 内链打开外网方式
        if (Integer.parseInt(menu.get("parent_id")+"") != 0 && isInnerLink(menu))
        {
            //routerPath = innerLinkReplaceEach(routerPath);
        }
        // 非外链并且是一级目录（类型为目录）
        if (0 == Integer.parseInt(menu.get("parent_id")+"") && "M".equals(menu.get("menu_type"))
                && "1".equals(menu.get("is_frame")+""))
        {
            routerPath = "/" + menu.get("path");
        }
        // 非外链并且是一级目录（类型为菜单）
        else if (isMenuFrame(menu))
        {
            routerPath = "/";
        }
        return routerPath;
    }

    /**
     * 获取组件信息
     *
     * @param menu 菜单信息
     * @return 组件信息
     */
    public String getComponent(Map<String, Object> menu)
    {
        String component = "Layout";
        if (menu.get("component")!=null && !isMenuFrame(menu))
        {
            component = menu.get("component")+"";
        }
        else if (menu.get("component")!=null && Integer.parseInt(menu.get("parent_id")+"") != 0 && isInnerLink(menu))
        {
            component = "InnerLink";
        }
        else if (menu.get("component")!=null && isParentView(menu))
        {
            component = "ParentView";
        }
        return component;
    }
    /**
     * 是否为parent_view组件
     *
     * @param menu 菜单信息
     * @return 结果
     */
    public boolean isParentView(Map<String, Object> menu)
    {
        return Integer.parseInt(menu.get("parent_id")+"") != 0 && "M".equals(menu.get("menu_type"));
    }
}
