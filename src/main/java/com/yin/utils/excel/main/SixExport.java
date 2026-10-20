package com.yin.utils.excel.main;

import com.alibaba.fastjson2.JSONObject;
import com.yin.pojo.excel.Excel;
import com.yin.utils.FileUtils;
import com.yin.utils.excel.ExcelUtils;
import org.apache.commons.math3.analysis.function.Abs;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class SixExport {

    public static void main(String[] args) throws IOException {
        String path = "D:\\cs\\ssxjg\\myDemo\\src\\main\\resources\\xmwj.txt";
        String path1 = "D:\\cs\\ssxjg\\myDemo\\src\\main\\resources\\1.xlsx";
        BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
        String line = bufferedReader.readLine();
        ArrayList<Excel> excels = new ArrayList<>();
        while (line != null) {
            Excel excel = new Excel();
            excel.setOne(line.trim());
            excels.add(excel);
            line = bufferedReader.readLine();
        }
        bufferedReader.close();
        Excel excel = new Excel();
        ExcelUtils.getExcelUtils().writeExcel(path1,excel,excels);
    }
}
