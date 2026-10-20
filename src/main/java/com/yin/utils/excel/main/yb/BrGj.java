package com.yin.utils.excel.main.yb;

import com.yin.common.ApplicationUtils;
import com.yin.pojo.qt.yb.BrgjBqAll;
import com.yin.pojo.qt.yb.BrgjYbq;
import com.yin.pojo.qt.yb.BrgjYsj;
import com.yin.utils.excel.ExcelUtils;
import com.yin.utils.excel.params.ExcelParams;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * @author yin
 * @date 2024-11-29
 * @Description
 */
public class BrGj {
    static String onePath="D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\2024-11-30\\杭州富春康复医院20241119-按科室原.xlsx";
    static String bqPath="D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\2024-11-30\\病区";
    static String threePath="D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\2024-11-30\\杭州富春康复医院20241119-按科室原-匹配.xlsx";
    static String fourPath="D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\2024-11-30\\杭州富春康复医院20241119-自查自纠匹配.xlsx";

    static ExcelUtils excelUtils=ExcelUtils.getExcelUtils();
    public static void main(String[] args) throws IOException {
        dataHandle();
    }
    public static void dataHandle(){
        ExcelUtils<BrgjYsj> brgjYsjExcelUtils = new ExcelUtils<>();
        ExcelUtils<BrgjBqAll> brgjBqAllExcelUtils = new ExcelUtils<>();
        List<BrgjYsj> brgjYsjs = brgjYsjExcelUtils.readExcel(threePath, new BrgjYsj(),1);
        ArrayList<BrgjBqAll> brgjBqAlls1 = new ArrayList<>();
        HashMap<String, BrgjBqAll> stringBrgjBqAllHashMap = new HashMap<>();
        //获取所有病区数据
        File file = new File(bqPath);
        for (File listFile : file.listFiles()) {
//            System.out.println(listFile.getAbsolutePath());
            List<BrgjBqAll> brgjBqAlls = brgjBqAllExcelUtils.readExcel(listFile.getAbsolutePath(), new BrgjBqAll(), 1);
            brgjBqAlls1.addAll(brgjBqAlls);
        }
        for (BrgjBqAll brgjBqAll : brgjBqAlls1) {
            stringBrgjBqAllHashMap.put(brgjBqAll.getJsID(),brgjBqAll);
        }
        for (BrgjYsj brgjYsj : brgjYsjs) {
            BrgjBqAll brgjBqAll = stringBrgjBqAllHashMap.get(brgjYsj.getJsID());
            if(null!=brgjBqAll && null!=brgjBqAll.getSjjcyy() && null!=brgjYsj.getJjzcze()){
                if(brgjYsj.getJjzcze().equals(brgjBqAll.getJjzcze())){
                    brgjYsj.setZczjqk(brgjBqAll.getZczjqk());
                }
            }
        }
        brgjYsjExcelUtils.writeExcel(fourPath,new BrgjYsj(),brgjYsjs);

    }


    public static void YsjCl(){
        ExcelUtils<BrgjYsj> brgjYsjExcelUtils = new ExcelUtils<>();
        List<BrgjYsj> brgjYsjs = brgjYsjExcelUtils.readExcel(onePath, new BrgjYsj(),1);
        BrgjYsj brgjYsjData = null;
        for (BrgjYsj brgjYsj : brgjYsjs) {
            if(null!=brgjYsj.getKs()){
                brgjYsjData=brgjYsj;
            }else {
                brgjYsj.setKs(brgjYsjData.getKs());
                brgjYsj.setXm(brgjYsjData.getXm());
                brgjYsj.setSfzh(brgjYsjData.getSfzh());
                brgjYsj.setJsID(brgjYsjData.getJsID());
                brgjYsj.setJssj(brgjYsjData.getJssj());
                brgjYsj.setRysj(brgjYsjData.getRysj());
                brgjYsj.setCysj(brgjYsj.getCysj());
                brgjYsj.setYlfze(brgjYsjData.getYlfze());
                brgjYsj.setJjzcze(brgjYsjData.getJjzcze());
            }
        }
        brgjYsjExcelUtils.writeExcel(threePath,new BrgjYsj(),brgjYsjs);
    }

    public static void generateClass() throws IOException {
        LinkedList<String> excelTitle = excelUtils.getExcelTitle(onePath);
        ExcelParams.headRowNumber=1;
//        LinkedList excelTitle1 = excelUtils.getExcelTitle(twoPath);
//        excelUtils.generateClassZ("BrgjYsj",excelTitle,ApplicationUtils.pathPojo);
//        excelUtils.generateClassZ("BrgjYbq",excelTitle1,ApplicationUtils.pathPojo);
//        excelUtils.generateClassZ("Hmc2024", excelTitle2, ApplicationUtils.pathPojo);
    }
}
