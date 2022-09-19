package com.study.liu.utils.login;

import com.study.liu.utils.login.vo.MetaVo;
import com.study.liu.utils.login.vo.RouterVo;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class LoginUtils extends chlidLoginUtil {
    /**
     * 根据父节点的ID循环获取所有子节点
     *
     * @param list 分类表
     * @param parentId 传入的父节点ID,得到父节点
     * @return String
     */
    public static List<Map<String, Object>> getParentList(List<Map<String, Object>> list, int parentId)
    {
        List<Map<String, Object>> returnList = new ArrayList();
        for (Map<String, Object> map : list) {
            if(map.get("parent_id").toString().equals(parentId+"")){
                map.get("menu_id");
                returnList.add(map);
            } else continue;

        }
        //嵌套循环..........
        getChildList(returnList,list);
        return returnList;
    }

    public static void getChildList(List<Map<String, Object>> returnList,List<Map<String, Object>> list){
        for (Map<String, Object> map : returnList) {
            String parentId = map.get("menu_id") +"";
            List<Map<String, Object>> chlidList = new ArrayList();
            for (Map<String, Object> ListMap : list) {
                if(ListMap.get("parent_id").toString().equals(parentId)){
                    chlidList.add(ListMap);
                }
            }
            getChildList(chlidList,list);
            map.put("children",chlidList);
        }
    }


    public List<RouterVo> buildMenus(List<Map<String, Object>> menus)
    {
        List<RouterVo> routers = new LinkedList<RouterVo>();
        for (Map<String, Object> menu : menus)
        {
            RouterVo router = new RouterVo();
            router.setHidden("1".equals(menu.get("visible")));
            router.setName(getRouteName(menu));
            router.setPath(getRouterPath(menu));
            router.setComponent(getComponent(menu));
            router.setQuery(menu.get("query")+"");
            router.setMeta(new MetaVo(menu.get("menu_name")+"", menu.get("icon")+"", StringUtils.equals("1", menu.get("is_cache")+""), menu.get("path")+""));
            List<Map<String, Object>> cMenus = (List<Map<String, Object>>) menu.get("children");
            if (!cMenus.isEmpty() && cMenus.size() > 0 && "M".equals(menu.get("menu_type")))
            {
                router.setAlwaysShow(true);
                router.setRedirect("noRedirect");
                router.setChildren(buildMenus(cMenus));
            }
            else if (isMenuFrame(menu))
            {
                router.setMeta(null);
                List<RouterVo> childrenList = new ArrayList<RouterVo>();
                RouterVo children = new RouterVo();
                children.setPath(menu.get("path")+"");
                children.setComponent(menu.get("component")+"");
                children.setName(StringUtils.capitalize(menu.get("path")+""));
                children.setMeta(new MetaVo(menu.get("menu_name")+"", menu.get("icon")+"", StringUtils.equals("1", menu.get("is_cache")+""), menu.get("path")+""));
                children.setQuery(menu.get("query")+"");
                childrenList.add(children);
                router.setChildren(childrenList);
            }
/*            else if (menu.getParentId().intValue() == 0 && isInnerLink(menu))
            {
                router.setMeta(new MetaVo(menu.get("menu_name"), menu.get("icon")));
                router.setPath("/");
                List<RouterVo> childrenList = new ArrayList<RouterVo>();
                RouterVo children = new RouterVo();
                String routerPath = innerLinkReplaceEach(menu.get("path"));
                children.setPath(routerPath);
                children.setComponent(UserConstants.INNER_LINK);
                children.setName(StringUtils.capitalize(routerPath));
                children.setMeta(new MetaVo(menu.get("menu_name"), menu.get("icon"), menu.get("path")));
                childrenList.add(children);
                router.setChildren(childrenList);
            }*/
            routers.add(router);
        }
        return routers;
    }



}
