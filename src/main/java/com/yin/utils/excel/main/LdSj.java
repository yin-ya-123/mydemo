package com.yin.utils.excel.main;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.yin.common.Mark;
import com.yin.pojo.qt.ld.LdPtDzbl;
import com.yin.pojo.qt.ld.LdPtJsQd;
import com.yin.utils.AllUtils;
import com.yin.utils.FileUtils;
import com.yin.utils.excel.ExcelUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

public class LdSj {
    /*
    *
    * String string = jsonArray.get(0).toString();
            JSONObject entries1 = JSONUtil.parseObj(string);
            LinkedList jsonTitle = excelUtils.getJsonTitle(entries1);
            excelUtils.generateClass("LdPtDzbl",jsonTitle,"D:\\cs\\ssxjg\\myDemo\\src\\main\\java\\com\\yin\\pojo\\qt\\ld");
//            System.out.println(string);
            return;
    * */
    public static void main(String[] args) throws IOException {
        jsqd();
    }

    public void dzpl(){
        String path = "D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\2024-09-26";
        String path1 = "D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\2024-09-26\\数据.xlsx";
        File file = new File(path);
        ExcelUtils excelUtils = ExcelUtils.getExcelUtils();
        ArrayList<LdPtDzbl> ldPtDzbls = new ArrayList<>();
        for (File listFile : file.listFiles()) {
            JSONObject entries = JSONUtil.parseObj(FileUtils.readFile(listFile.getAbsolutePath()));
            AllUtils.setJsonData(entries);
            JSONArray jsonArray = AllUtils.getJsonArray(getArray());
            jsonArray.forEach(item->{
                LdPtDzbl bean = JSONUtil.toBean(item.toString(), LdPtDzbl.class);
                ldPtDzbls.add(bean);
            });
        }
        excelUtils.writeExcel(path1,new LdPtDzbl(),ldPtDzbls);
    }
    private static ArrayList<String> getArray() {
        ArrayList<String> stringArrayList = new ArrayList<>();
        stringArrayList.add("data");
        stringArrayList.add("pageBean");
        stringArrayList.add("data");
        return stringArrayList;
    }

    public static void jsqdScStl() throws IOException {
        String path="D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\2024-09-26\\结算清单";
        String path1="D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\2024-09-26\\结算清单\\1.xlsx";
        File file = new File(path);
        ExcelUtils excelUtils = ExcelUtils.getExcelUtils();
        ArrayList<LdPtDzbl> ldPtDzbls = new ArrayList<>();
        for (File listFile : file.listFiles()) {
            JSONObject entries = JSONUtil.parseObj(FileUtils.readFile(listFile.getAbsolutePath()));
            AllUtils.setJsonData(entries);
            JSONArray jsonArray = AllUtils.getJsonArray(getArray2());
            String string = jsonArray.get(0).toString();
            JSONObject entries1 = JSONUtil.parseObj(string);
            LinkedList jsonTitle = excelUtils.getJsonTitle(entries1);
            excelUtils.generateClass("LdPtJsQd",jsonTitle,"D:\\cs\\ssxjg\\myDemo\\src\\main\\java\\com\\yin\\pojo\\qt\\ld");
            return;
            //            System.out.println(string);
//            jsonArray.forEach(item->{
//                LdPtDzbl bean = JSONUtil.toBean(item.toString(), LdPtDzbl.class);
//                ldPtDzbls.add(bean);
//            });
        }
//        excelUtils.writeExcel(path1,new LdPtDzbl(),ldPtDzbls);
    }
    public static void jsqd() throws IOException {
        String path="D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\drgs上传问题数据导出\\结算清单下载\\";
        String path1="D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\drgs上传问题数据导出\\结算清单下载\\1.xlsx";
        File file = new File(path);
        ExcelUtils excelUtils = ExcelUtils.getExcelUtils();
        ArrayList<LdPtJsQd> ldPtJsQdArrayList = new ArrayList<>();
        for (File listFile : file.listFiles()) {
            JSONObject entries = JSONUtil.parseObj(FileUtils.readFile(listFile.getAbsolutePath()));
            AllUtils.setJsonData(entries);
            JSONArray jsonArray = AllUtils.getJsonArray(getArray2());
            jsonArray.forEach(item->{
                LdPtJsQd bean = JSONUtil.toBean(item.toString(), LdPtJsQd.class);
                ldPtJsQdArrayList.add(bean);
            });
        }
        excelUtils.writeExcel(path1,new LdPtJsQd(),ldPtJsQdArrayList);
    }
    private static ArrayList<String> getArray2() {
        ArrayList<String> stringArrayList = new ArrayList<>();
        stringArrayList.add("data");
        stringArrayList.add("data");
        return stringArrayList;
    }
}
