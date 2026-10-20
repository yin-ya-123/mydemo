package com.yin.utils.excel.main.yb;

import com.yin.common.ApplicationUtils;
import com.yin.common.err.YException;
import com.yin.pojo.qt.yb.HtBlPojo;
import com.yin.utils.excel.ExcelUtils;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * @author yin
 * @date 2025-02-10
 * @Description
 */
public class Main {
    public static void main(String[] args) throws IOException, YException {
        String path="D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\2025-02-11";
        String path1="D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\2025-02-11\\在院低倍率.xlsx";
        String savePath=path+"\\在院DRG分组报表.XLSX";
//        ExcelUtils excelUtils = ExcelUtils.getExcelUtils();
//        LinkedList excelTitle = excelUtils.getExcelTitle(path1);

//        excelUtils.generateClassEnhance("HtBlPojo", ApplicationUtils.pathPojo,path);
        ExcelUtils<HtBlPojo> htBlPojoExcelUtils = ExcelUtils.of(HtBlPojo.class);
        htBlPojoExcelUtils.excelToAll(path,savePath,new HtBlPojo());
//        List<HtBlPojo> htBlPojos = htBlPojoExcelUtils.readExcel(path1, new HtBlPojo(), 1);
//        for (HtBlPojo htBlPojo : htBlPojos) {
//            System.out.println(htBlPojo);
//        }
    }
}
