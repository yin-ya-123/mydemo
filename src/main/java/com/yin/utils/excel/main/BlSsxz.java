package com.yin.utils.excel.main;

import cn.hutool.extra.pinyin.PinyinUtil;
import com.yin.pojo.BlSsxzPojo;
import com.yin.utils.FileUtils;
import com.yin.utils.excel.ExcelUtils;
import com.yin.utils.excel.ExcelUtils2;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class BlSsxz {
    public static void main(String[] args) throws IOException {
        String path ="D:\\公司\\项目文件\\康复医院\\病历手术新增\\工作簿1.xlsx";
        String path2 ="D:\\公司\\项目文件\\康复医院\\病历手术新增\\工作簿2.xlsx";
        ExcelUtils excelUtils = ExcelUtils.getExcelUtils();
        BlSsxzPojo blSsxzPojo = new BlSsxzPojo();
        List list = excelUtils.readExcel(path,blSsxzPojo,1);
        List<BlSsxzPojo> blSsxzPojos = ExcelUtils2.listToTList(list, BlSsxzPojo.class);
        for (BlSsxzPojo ssxzPojo : blSsxzPojos) {
            if(ssxzPojo.getMc()!=null){
                String firstLetter = PinyinUtil.getFirstLetter(ssxzPojo.getMc(), "");
                ssxzPojo.setPym(firstLetter);
                System.out.println(ssxzPojo);
            }
        }
        excelUtils.writeExcel(path2,blSsxzPojo,blSsxzPojos);
//        excelUtils.generateClass("BlSsxzPojo",excelTitle, "D:\\cs\\ssxjg\\myDemo\\src\\main\\java\\com\\yin\\pojo");
    }
}
