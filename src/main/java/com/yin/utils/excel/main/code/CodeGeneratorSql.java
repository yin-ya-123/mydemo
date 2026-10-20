package com.yin.utils.excel.main.code;

import cn.hutool.extra.pinyin.PinyinUtil;
import com.yin.utils.excel.ExcelUtils;

import java.io.IOException;
import java.util.LinkedList;

/**
 * @author yin
 * @date 2025-01-07
 * @Description 根据excel标题生成建表语句
 */
public class CodeGeneratorSql {
    public static void main(String[] args) throws IOException {
        show();
    }

    public static void show() throws IOException {
        String path = "E:\\文件信息保存\\2025-01-08\\目录库.xlsx";
        ExcelUtils excelUtils = ExcelUtils.getExcelUtils();
        LinkedList excelTitle = excelUtils.getExcelTitle(path);
        StringBuilder str=new StringBuilder();
        String tableName = "YBGZ_MLK";
        str.append("CREATE TABLE ").append(tableName).append(" (\n");
        for (Object o : excelTitle) {
            //获取汉字首拼
            String firstLetter = PinyinUtil.getFirstLetter((String) o, "").toUpperCase();
            str.append(firstLetter).append(" varchar2(20),--").append(o).append("\n");
        }
        str.append(")\n");
        for (Object o : excelTitle) {
            //获取汉字首拼
            String firstLetter = PinyinUtil.getFirstLetter((String) o, "").toUpperCase();
            str.append("comment on column BDHIS.").append(tableName).append(".").append(firstLetter).append(" is ").append("'").append(o).append("';").append("\n");
        }
        //comment on column BDHIS.YBGZ_PAT_JSD.SORT is '排序'
        System.out.println(str.toString());
    }
}
