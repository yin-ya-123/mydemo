package com.yin.utils.excel.main.yb;

import com.alibaba.fastjson2.JSONObject;
import com.yin.pojo.qt.yb.Bzph;
import com.yin.pojo.qt.yb.Cyxs;
import com.yin.pojo.qt.yb.Jzds22;
import com.yin.utils.ConvertAllUtils;
import com.yin.utils.excel.ExcelUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * @author yin
 * @date 2024-11-06
 * @Description
 */
public class Jzds {
   static String pathJzds = "D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\2024-11-06\\基准点数-22年与23年对比.xlsx";
   static String pathcyxs = "D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\2024-11-06\\差异系数-22年与23年对比.xlsx";
    static String pathbz = "D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\2024-11-06\\主要病组平衡点测算模型(9月4日更新）.xlsx";
    static String pathbz_pp = "D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\2024-11-06\\主要病组平衡点测算模型(9月4日更新）-匹配.xlsx";
    public static void main(String[] args) throws IOException {
//        generate();
        show();
    }


    public static void show() {
        ExcelUtils excelUtils = ExcelUtils.getExcelUtils();
        HashMap<String,Jzds22> mapJzds22 = new HashMap<>();
        HashMap<String, Cyxs> mapCyxs = new HashMap<>();
        List jzds22s = excelUtils.readExcel(pathJzds, new Jzds22(),1);
        ArrayList<Jzds22> jzds22s1 = ConvertAllUtils.convertListAll(jzds22s, Jzds22.class);
        List cyxs = excelUtils.readExcel(pathcyxs, new Cyxs(),1);
        ArrayList<Cyxs> cyxs1 = ConvertAllUtils.convertListAll(cyxs, Cyxs.class);
        for (Jzds22 jzds22 : jzds22s1) {
//            System.out.println(jzds22);
            if(null!=jzds22.getDRGbm()){
                mapJzds22.put(jzds22.getDRGbm(),jzds22);
            }
        }
        for (Cyxs cyx : cyxs1) {
//            System.out.println(cyx);
            if (null!=cyx.getDRGbm()){
                mapCyxs.put(cyx.getDRGbm(),cyx);
            }
        }
        List list = excelUtils.readExcel(pathbz, new Bzph(),1);
        ArrayList<Bzph> bzphs = ConvertAllUtils.convertListAll(list, Bzph.class);
        for (Bzph bzph : bzphs) {
            bzph.setLjfy(mapJzds22.get(bzph.getFzbm()).getNljfy());
            bzph.setJzds(mapJzds22.get(bzph.getFzbm()).getNjzds());
            bzph.setCyxs(mapCyxs.get(bzph.getFzbm()).getNcyxs());
        }
        excelUtils.writeExcel(pathbz_pp,new Bzph(),bzphs);

    }

    public static void generate() throws IOException {

        String pathPojo = "D:\\cs\\ssxjg\\myDemo\\src\\main\\java\\com\\yin\\pojo\\qt\\yb";
        ExcelUtils excelUtils = ExcelUtils.getExcelUtils();
//        LinkedList<String> excelTitle = excelUtils.getExcelTitle(pathJzds);
//        LinkedList<String> excelTitle2 = excelUtils.getExcelTitle(pathbz);
        LinkedList<String> excelTitle3 = excelUtils.getExcelTitle(pathcyxs);
//        excelUtils.generateClassZ("Jzds22", excelTitle, pathPojo);
//        excelUtils.generateClassZ("Bzph", excelTitle2, pathPojo);
        excelUtils.generateClassZ("Cyxs", excelTitle3, pathPojo);

    }
}
