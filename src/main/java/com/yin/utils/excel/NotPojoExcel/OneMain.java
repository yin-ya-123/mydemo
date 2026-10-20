package com.yin.utils.excel.NotPojoExcel;

import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

/**
 * @author yin
 * @date 2024-11-26
 * @Description
 */
@Slf4j
public class OneMain {
    static String pathOne="D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\2024-11-25\\医保医师20241125.xlsx";
    static String pathThree1="E:\\文件信息保存\\2024-11-25\\新建 XLSX 工作表1.xlsx";

    public static void main(String[] args) throws IOException {
        SheetData read = ExcelTool.read(pathOne);
        List<JSONObject> jsonData = read.toJsonData();
        System.out.println(jsonData);
        ExcelTool.write(pathThree1,read);
    }
}
