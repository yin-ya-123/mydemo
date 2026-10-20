package com.yin.utils.excel.main;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.yin.pojo.qt.cyjd.Cyxs22;
import com.yin.pojo.qt.cyjd.Cyxs23;
import com.yin.pojo.qt.cyjd.Jzds22;
import com.yin.pojo.qt.cyjd.Jzds23;
import com.yin.utils.FileUtils;
import com.yin.utils.excel.ExcelUtils;
import com.yin.utils.excel.ExcelUtils2;
import com.yin.utils.excel.ExcelUtils3;

import java.io.IOException;
import java.util.*;

public class SevenExport {
    static String path1 = "D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\2024-03-30\\差异系数-22年.xlsx";
    static String path2 = "D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\2024-03-30\\差异系数-23年.xlsx";
    static String path3 = "D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\2024-03-30\\基准点数-22年.xlsx";
    static String path4 = "D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\2024-03-30\\基准点数-23年.xlsx";
    static String path5 = "D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\2024-03-30\\新\\差异系数-23年-组合.xlsx";
    static String path6 = "D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\2024-03-30\\新\\基准点数-23年-组合.xlsx";
    static ExcelUtils excelUtils = ExcelUtils.getExcelUtils();
    public static void main(String[] args) throws IOException {
        
    }
    public void show3(){
        Jzds22 jzds22 = new Jzds22();
        Jzds23 jzds23 = new Jzds23();
        List list = excelUtils.readExcel(path3, jzds22, 1);
        List list1 = excelUtils.readExcel(path4, jzds23, 1);
        List<Jzds22> jzds22s = ExcelUtils2.listToTList(list, Jzds22.class);
        List<Jzds23> jzds23s = ExcelUtils2.listToTList(list1, Jzds23.class);
        Map<String, String> map1 = getMap1(jzds22s);
        Map<String, String> map2 = getMap2(jzds22s);
        for (Jzds23 jzds231 : jzds23s) {
            String jzds = map1.get(jzds231.getDRGbm());
            String ljfy = map2.get(jzds231.getDRGbm());
            jzds231.setNjzds(jzds);
            jzds231.setNljfy(ljfy);
        }
        excelUtils.writeExcel(path6,jzds23,jzds23s);
    }
    public void show2(){
        Cyxs22 cyxs22 = new Cyxs22();
        Cyxs23 cyxs23 = new Cyxs23();
        ArrayList<Cyxs23> cyxs23s = new ArrayList<>();
        List list = excelUtils.readExcel(path1, cyxs22,1);
        List list1 = excelUtils.readExcel(path2, cyxs23,1);
        List<Cyxs23> cyxs23s1 = ExcelUtils2.listToTList(list1, Cyxs23.class);
        List<Cyxs22> cyxs22s = ExcelUtils2.listToTList(list, Cyxs22.class);
        Map<String, String> map = getMap(cyxs22s);
        for (Cyxs23 cyxs231 : cyxs23s1) {
            String cyxs = map.get(cyxs231.getDRGbm());
            cyxs231.setEencyxs(cyxs);
//            System.out.println(cyxs);
        }
        FileUtils.createFile(path5);
        excelUtils.writeExcel(path5,cyxs23,cyxs23s1);
    }
    public static Map<String,String> getMap(List<Cyxs22> cyxs22s){
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        for (Cyxs22 cyxs22 : cyxs22s) {
            stringStringHashMap.put(cyxs22.getFzbm(),cyxs22.getCyxs());
        }
        return stringStringHashMap;
    }
    public static Map<String,String> getMap1(List<Jzds22> jzds22s){
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        for (Jzds22 jzds22 : jzds22s) {
            stringStringHashMap.put(jzds22.getFzbm(),jzds22.getJzds());
        }
        return stringStringHashMap;
    }
    public static Map<String,String> getMap2(List<Jzds22> jzds22s){
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        for (Jzds22 jzds22 : jzds22s) {
            stringStringHashMap.put(jzds22.getFzbm(),jzds22.getLjfy());
        }
        return stringStringHashMap;
    }
    public void show() throws IOException {
        String path1 = "D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\2024-03-30\\差异系数-22年.xlsx";
        String path2 = "D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\2024-03-30\\差异系数-23年.xlsx";
        String path3 = "D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\2024-03-30\\基准点数-22年.xlsx";
        String path4 = "D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\2024-03-30\\基准点数-23年.xlsx";
        String path5 = "D:\\cs\\ssxjg\\myDemo\\src\\main\\java\\com\\yin\\pojo\\qt\\cyjd";
        ExcelUtils excelUtils = ExcelUtils.getExcelUtils();
        LinkedList excelTitle1 = excelUtils.getExcelTitle(path1);
        LinkedList excelTitle2 = excelUtils.getExcelTitle(path2);
        LinkedList excelTitle3 = excelUtils.getExcelTitle(path3);
        LinkedList excelTitle4 = excelUtils.getExcelTitle(path4);
        excelUtils.generateClassZ("Cyxs22",excelTitle1,path5);
        excelUtils.generateClassZ("Cyxs23",excelTitle2,path5);
        excelUtils.generateClassZ("Jzds22",excelTitle3,path5);
        excelUtils.generateClassZ("Jzds23",excelTitle4,path5);
    }
}
