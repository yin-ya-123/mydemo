package com.yin.utils;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.yin.common.Mark;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Map;

public class AllUtils {

    @Getter
    @Setter
    private static JSONObject jsonData;

    /**
     * 查找最后数据为str
     * @param list
     * @return
     */
    public static String getJsonStr(ArrayList<String> list){
        String str = "";
        for (int i = 0; i < list.size(); i++) {
            str = getJson(list.get(i), (list.size() - i));
        }
        return str;
    }

    /**
     * 查找最后数据为str
     * @param map
     * @return
     */

    public static String getJsonStr(Map<ArrayList<String>,String> map,int arrayIndex){
        final String[] str = {""};
        int count=0;
        for (Map.Entry<ArrayList<String>,String> info : map.entrySet()) {
            switch (info.getValue()){
                case Mark.KEYStr:
                    String jsonStr = getJsonStr(info.getKey());
                    str[0] = jsonStr;
                    setJsonData(JSONUtil.parseObj(jsonStr));
                    break;
                case Mark.KEYLIST:
                    JSONArray jsonArray = getJsonArray(info.getKey());
                    str[0] = jsonArray.get(arrayIndex).toString();
//                    System.out.println("值为:"+str[0]);
                    count++;
                    if(count<map.size()){
                        setJsonData(JSONUtil.parseObj(str[0]));
                    }
                    break;
            }
        }
        return str[0];
    }

    private static String getJson(String list, int size) {
        if (size > 1) {
            setJsonData(JSONUtil.parseObj(getJsonData().get(list).toString()));
            return getJsonData().toString();
        }
        return getJsonData().get(list).toString();
    }

    /**
     * 查找最后节点数据，为array
     * @param list
     * @return
     */
    public static JSONArray getJsonArray(ArrayList<String> list){
        String str = "";
        for (int i = 0; i < list.size(); i++) {
            str = getJson(list.get(i), (list.size() - i));
        }
        return  JSONUtil.parseArray(str);
    }

}
