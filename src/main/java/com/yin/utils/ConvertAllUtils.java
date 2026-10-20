package com.yin.utils;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.yin.utils.enumM.TypeEnum.JSON;

/**
 * 类型转换帮助类
 */
public class ConvertAllUtils<T> {

    public static<T> ArrayList<T> convertListAll(List list,Class<T> clazz){
        ArrayList<T> ts = new ArrayList<>();
        for (Object o : list) {
            ts.add(JSONObject.parseObject(JSONObject.toJSONString(o),clazz));
        }
        return ts;
    }
   public static JSONObject convertJson(Object object){
       return JSONObject.parseObject(JSONObject.toJSONString(object));
   }
   public static JSONArray convertJsonArray(Object object){
        return JSONArray.parseArray(JSONObject.toJSONString(object));
   }
    public static cn.hutool.json.JSONObject convertJson2(Object object){
        return JSONUtil.parseObj(JSONUtil.toJsonStr(object));
    }
}
