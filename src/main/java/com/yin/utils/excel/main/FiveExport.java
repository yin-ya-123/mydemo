package com.yin.utils.excel.main;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.yin.pojo.excel.yb.Shyjs_3;
import com.yin.pojo.qt.Dyy;
import com.yin.utils.FileUtils;
import com.yin.utils.excel.ExcelUtils;
import com.yin.utils.excel.ExcelUtils2;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class FiveExport {

    public static void main(String[] args) throws IOException {
        String path="D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\2024-03-21\\第二页.txt";
        String dyy = FileUtils.readFile(path);
        String string = JSONObject.parseObject(JSONObject.parseObject(dyy).get("data").toString()).get("data").toString();
        JSONArray objects = JSONArray.parseArray(string);
        ArrayList<Dyy> dyys = new ArrayList<>();
        for (Object object : objects) {
            Dyy dyy1 = JSONObject.parseObject(object.toString(), Dyy.class);
            dyys.add(dyy1);
        }
          ExcelUtils.getExcelUtils().writeExcel("D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\2024-03-21\\2.xlsx",new Dyy(),dyys);
    }

    @Test
    public void one(){
        String[] paths=new String[]{"D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\2024-03-21\\1.xlsx","D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\2024-03-21\\2.xlsx"};
        List<Dyy> dyys = ExcelUtils2.mergeExcel(paths, new Dyy(), Dyy.class, 1);
        System.out.println(dyys.size());
//        ExcelUtils.getExcelUtils().writeExcel("D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\2024-03-21\\3.xlsx",dyy,dyys);
    }
}
