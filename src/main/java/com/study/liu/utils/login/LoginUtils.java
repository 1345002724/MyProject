package com.study.liu.utils.login;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LoginUtils {
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

}
