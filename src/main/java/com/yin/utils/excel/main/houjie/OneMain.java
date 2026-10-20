package com.yin.utils.excel.main.houjie;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.yin.common.ApplicationUtils;
import com.yin.pojo.qt.houjie.Tablefield;
import com.yin.utils.FileUtils;
import com.yin.utils.excel.ExcelUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * @author yin
 * @date 2025-07-18
 * @Description
 */
public class OneMain {
    public static void main(String[] args) throws IOException {
        String path ="E:\\文件信息\\表结构\\住院表结构";
        String savePath = "E:\\文件信息\\表结构\\住院表结构\\文本\\住院相关表.txt";
//        String path = "E:\\文件信息\\表结构\\门诊表结构";
//        String savePath = "E:\\文件信息\\表结构\\门诊表结构\\文本\\门诊相关表.txt";
        File file = new File(path);
        tableWrite(file, path, savePath);
    }

    private static void tableWrite(File file, String path, String savePath) throws IOException {
        // 添加对目录存在性的检查
        if (!file.exists() || !file.isDirectory()) {
            throw new IOException("指定路径不存在或不是一个目录: " + path);
        }

        StringBuilder stringBuilder = new StringBuilder();
        File[] files = file.listFiles();

        // 添加对listFiles()返回值的检查
        if (files == null) {
            throw new IOException("无法列出目录内容: " + path);
        }

        for (File file1 : files) {
            if (!file1.isFile()) {
                continue;
            }

            try {
                ExcelUtils<Tablefield> tablefieldExcelUtils = ExcelUtils.of(Tablefield.class);
                List<Tablefield> tablefields = tablefieldExcelUtils.readExcel(file1.getAbsolutePath(), 1);

                // 添加对tablefields的空值检查
                if (tablefields == null || tablefields.isEmpty()) {
                    continue;
                }

                JSONObject tableJsonS = new JSONObject();
                tableJsonS.putOpt("tableName",tablefields.get(0).getOWNER()+"."+tablefields.get(0).getTABLENAME());
                tableJsonS.putOpt("tableComment", FileUtils.getFileName(file1.getAbsolutePath()));
                JSONArray objects = new JSONArray();
                for (Tablefield tablefield : tablefields) {
                    tablefield.setTABLENAME(tablefield.getOWNER() + "." + tablefield.getTABLENAME());
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.putOpt("COLUMNNAME", tablefield.getCOLUMNNAME());
                    jsonObject.putOpt("COMMENTS", tablefield.getCOMMENTS());
                    objects.add(jsonObject);
                }
                tableJsonS.putOpt("fields", objects);
                stringBuilder.append(JSONUtil.toJsonStr(tableJsonS));
            } catch (Exception e) {
                System.err.println("处理文件时出错: " + file1.getAbsolutePath() + ", 错误: " + e.getMessage());
                // 可以选择继续处理其他文件或抛出异常
            }
        }
        FileUtils.writeFile(savePath, stringBuilder.toString(), false);
    }

    @Test
    public void generate() throws IOException {
        ExcelUtils excelUtils = ExcelUtils.getExcelUtils();
        String path = "D:\\公司\\项目文件\\东莞厚街\\数据导出\\2025-07-18\\DICT_DEPART.xlsx";
        excelUtils.generateClassEnhanceZ("Tablefield", ApplicationUtils.pathPojoHouJie, path);
    }
}
