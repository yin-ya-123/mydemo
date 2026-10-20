package com.yin.utils.excel.main;

import cn.hutool.extra.pinyin.PinyinUtil;
import com.alibaba.fastjson2.JSONObject;
import com.yin.pojo.qt.Dyy;
import com.yin.pojo.qt.wjh.Wjh_1;
import com.yin.pojo.qt.wjh.wjh2;
import com.yin.utils.ConvertAllUtils;
import com.yin.utils.excel.ExcelUtils;
import com.yin.utils.excel.ExcelUtils2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Wjh_one {
    static String path1="D:\\公司\\项目文件\\康复医院\\其他数据导出\\其他\\工作簿1(1).xlsx";
    static String path2="D:\\公司\\项目文件\\康复医院\\其他数据导出\\其他\\工作簿1.xlsx";
    static String path3="D:\\公司\\项目文件\\康复医院\\其他数据导出\\其他\\11111.xlsx";
    static String path4="D:\\公司\\项目文件\\康复医院\\其他数据导出\\其他\\111111.xlsx";

    static String pojoPath = "D:\\cs\\ssxjg\\myDemo\\src\\main\\java\\com\\yin\\pojo\\qt\\wjh";

    public static void main(String[] args) throws IOException {
        ExcelUtils excelUtils = ExcelUtils.getExcelUtils();
//
//        LinkedList excelTitle = excelUtils.getExcelTitle(path3);
//        excelUtils.generateClassZ("wjh2",excelTitle,pojoPath);
        List list = excelUtils.readExcel(path3, new wjh2(),1);
        ArrayList<com.yin.pojo.qt.wjh.wjh2> wjh2s = ConvertAllUtils.convertListAll(list, com.yin.pojo.qt.wjh.wjh2.class);
        for (com.yin.pojo.qt.wjh.wjh2 wjh21 : wjh2s) {
            if(wjh21!=null){
                String pinyin = PinyinUtil.getPinyin(wjh21.getMc()).replace(" ","").trim();
                wjh21.setPym(pinyin);
            }
        }
        excelUtils.writeExcel(path4,new wjh2(),wjh2s);
    }

    public void text1(){
        ExcelUtils excelUtils = ExcelUtils.getExcelUtils();
        Wjh_1 wjhOne = new Wjh_1();
        List list = excelUtils.readExcel(path1,wjhOne,1);
        List<Wjh_1> wjh1s = ExcelUtils2.listToTList(list, Wjh_1.class);
        ArrayList<Wjh_1> wjh1s1 = new ArrayList<>();
        for (Wjh_1 wjh1 : wjh1s) {
            String firstLetter = PinyinUtil.getFirstLetter(wjh1.getYpmc(), "");
            wjh1.setPym(firstLetter);
            wjh1s1.add(wjh1);
        }
        excelUtils.writeExcel(path2,wjhOne,wjh1s1);
    }

    public void text2(){

    }


}
