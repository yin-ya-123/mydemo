package com.yin.utils.excel.main;

import com.alibaba.fastjson2.JSONObject;
import com.yin.pojo.qt.Zy1;
import com.yin.pojo.qt.mz1;
import com.yin.utils.ConvertAllUtils;
import com.yin.utils.excel.ExcelUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TenMainExport {

    public static void main(String[] args) throws IOException {
        String path1="D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\2024-10-17\\主演.xlsx";
        String path12="D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\2024-10-17\\住院.xlsx";
        String pojoPath = "D:\\cs\\ssxjg\\myDemo\\src\\main\\java\\com\\yin\\pojo\\qt";
        ExcelUtils excelUtils = ExcelUtils.getExcelUtils();
//        LinkedList excelTitle = excelUtils.getExcelTitle(path1);
//        excelUtils.generateClass("mz1",excelTitle,pojoPath);
        List list = excelUtils.readExcel(path1, new mz1(),1);
        ArrayList<mz1> mz1s = ConvertAllUtils.convertListAll(list, mz1.class);
        mz1s.forEach(item->{
            if(item.getRET_JSXX()!=null){
                JSONObject jsonObject = JSONObject.parseObject(item.getRET_JSXX());
                String string = jsonObject.get("setlinfo").toString();
                String string1 = JSONObject.parseObject(string).get("inscp_scp_amt").toString();
                item.set符合政策范围金额(string1);
                item.setRET_JSXX("");
            }
        });
        excelUtils.writeExcel(path12,new mz1(), mz1s);
    }

    @Test
    public void show() throws IOException {
        String path1="D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\2024-10-17\\主演.xlsx";
        String path12="D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\2024-10-17\\住院.xlsx";
        String pojoPath = "D:\\cs\\ssxjg\\myDemo\\src\\main\\java\\com\\yin\\pojo\\qt";
        ExcelUtils excelUtils = ExcelUtils.getExcelUtils();
//        LinkedList excelTitle = excelUtils.getExcelTitle(path1);
//        excelUtils.generateClass("Zy1",excelTitle,pojoPath);
        List list = excelUtils.readExcel(path1, new Zy1(),1);
        ArrayList<Zy1> zy1s = ConvertAllUtils.convertListAll(list, Zy1.class);
        zy1s.forEach(item->{
            if(item.getRET_JSXX()!=null){
                JSONObject jsonObject = JSONObject.parseObject(item.getRET_JSXX());
                String string = jsonObject.get("setlinfo").toString();
                String string1 = JSONObject.parseObject(string).get("inscp_scp_amt").toString();
                item.set符合政策范围金额(string1);
                item.setRET_JSXX("");
            }
        });
        excelUtils.writeExcel(path12,new Zy1(), zy1s);
    }
}
