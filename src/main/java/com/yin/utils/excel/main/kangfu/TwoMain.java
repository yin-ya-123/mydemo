package com.yin.utils.excel.main.kangfu;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.yin.utils.FileUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

/**
 * 床日申请退出数据处理
 */
public class TwoMain {

    public static void main(String[] args) throws IOException {
        export();
    }
    public static void export() throws IOException {
        String path = "E:\\文件信息\\2026-01-26\\33.xlsx";

        Files.deleteIfExists(new File(path).toPath());
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        //创建第一行标题
        Row row = sheet.createRow(0);
        String ingo = FileUtils.readFile("E:\\文件信息\\2026-01-26\\新建 文本文档.txt","UTF-8");
        Document parse = Jsoup.parse(ingo);
        //获取标题
        Element tr = parse.selectFirst("tr");
        Elements th = tr.select("th");
        for (int i = 0; i < th.size(); i++) {
            row.createCell(i).setCellValue(th.get(i).text());
        }
        //获取数据
        Elements trs = parse.getElementsByClass("el-table__row");
        for (int i = 0; i < trs.size() && i<134; i++) {
            //创建数据行
            Row row1 = sheet.createRow(i + 1);
            Elements td = trs.get(i).select("td");
            for (int i1 = 0; i1 < td.size(); i1++) {
                //创建数据列
                row1.createCell(i1).setCellValue(td.get(i1).text());
            }
        }
        //导出
        FileOutputStream fileOutputStream = new FileOutputStream(path);
        workbook.write(fileOutputStream);
        fileOutputStream.close();
        workbook.close();
    }
}
