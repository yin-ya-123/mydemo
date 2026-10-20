package com.yin.utils.excel.main.kangfu;

import cn.hutool.json.JSONUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.analysis.ExcelReadExecutor;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.read.builder.ExcelReaderSheetBuilder;
import com.alibaba.excel.read.listener.PageReadListener;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.util.ListUtils;
import com.yin.pojo.qt.kangfu.JnkfPojo;
import com.yin.pojo.qt.kangfu.ZcZj2025YBSCPojo;
import com.yin.pojo.qt.kangfu.ZcZj2025YBSCPojoTwo;
import com.yin.utils.ConvertAllUtils;
import com.yin.utils.excel.ExcelListener.DataListener;
import com.yin.utils.excel.ExcelUtils;
import org.apache.poi.ss.formula.functions.T;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yin
 * @date 2025-02-18
 * @Description
 */
public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        String path ="D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\2025-03-14\\江南康复\\江南康复自查自纠汇总版.xlsx";
        String pat2 ="D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\2025-03-14\\江南康复\\工作簿1.xlsx";
        String savePath ="D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\2025-03-14\\江南康复\\江南康复自查自纠汇总版-汇总.xlsx";
        ExcelUtils<JnkfPojo> jnkfPojoExcelUtils = ExcelUtils.of(JnkfPojo.class);
        HashMap<String, List<JnkfPojo>> stringListHashMap = jnkfPojoExcelUtils.readeExcelSheetAllIsNotNull(path, 1);
        for (Map.Entry<String, List<JnkfPojo>> stringListEntry : stringListHashMap.entrySet()) {
            System.out.println(stringListEntry.getKey());
            List<JnkfPojo> value = stringListEntry.getValue();
            double num = 0;
            for (JnkfPojo jnkfPojo : value) {
//                System.out.println(jnkfPojo.toString());
                double v = Double.parseDouble(jnkfPojo.getJe());
                num += v;

            }
            System.out.println(num);
        }
    }



    @Test
    public void test() {
        String path = "D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\2025-03-09\\最终数据汇总\\ces";
        ExcelUtils<ZcZj2025YBSCPojo> zcZj2025YBSCPojoExcelUtils = ExcelUtils.of(ZcZj2025YBSCPojo.class);

    }

}
