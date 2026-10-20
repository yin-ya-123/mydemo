package com.yin.utils.excel;

import com.alibaba.fastjson2.JSONObject;
import com.yin.pojo.excel.yb.Shyjs_3;
import com.yin.pojo.qt.Dyy;

import java.util.*;

/**
 * 第二个使用exel的帮助类
 */
public class ExcelUtils2<T> {

    /**
     * 把读取excel出来的list转换为对应的实体类集合
     * @param list excel数据
     * @param objectClass 实体类对象
     * @return 返回dataList
     * @param <T>
     */
    public static<T> List<T> listToTList(List list,Class<T> objectClass) {
        List<T> dataList=new ArrayList<>();
        for (Object o : list) {
            T data = JSONObject.parseObject(JSONObject.toJSONString(o).toString(), objectClass);
            dataList.add(data);
        }
        return dataList;
    }
    /**
     * 合并excel到一个lis中
     * @param paths 路径
     * @param object 实体类对象 new
     * @param objectClass 实体类对象.clss
     * @param headRowNumber 表头行数,从第几行开始读取
     * @return 返回集合
     * @param <T>
     */
    public static<T> List<T> mergeExcel(String[] paths,Object object,Class<T> objectClass,Integer headRowNumber){
        List<T> dataList=new ArrayList<>();
        for (String path : paths) {
            List list = ExcelUtils.getExcelUtils().readExcel(path,object,headRowNumber);
            List<T> ts = ExcelUtils2.listToTList(list, objectClass);
            dataList.addAll(ts);
        }
        return dataList;
    }

}
