package com.yin.utils.excel.main.pinghueryuan;


import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.yin.MyDemoApplication;
import com.yin.service.impl.EyOracleServiceImpl;
import com.yin.service.impl.HjOracleServiceImpl;
import com.yin.service.impl.PhOracleServiceImpl;
import com.yin.service.impl.WhOracleServiceImpl;
import com.yin.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.*;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

@SpringBootTest(classes = MyDemoApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
public class One {

    @Resource
//    private EyOracleServiceImpl eyOracleServiceImpl;
//    @Resource
    private PhOracleServiceImpl phOracleServiceImpl;
//    private WhOracleServiceImpl whOracleService;
//    private HjOracleServiceImpl hjOracleService;

    @Test
    public void main() throws Exception {
//        List list = phOracleServiceImpl.selectAll(Sql.sql1);
        String sql = FileUtils.readFile("D:\\cs\\ssxjg\\myDemo\\src\\main\\java\\com\\yin\\utils\\excel\\main\\pinghueryuan\\Sql.txt", "utf-8");
        List list = phOracleServiceImpl.selectAll(sql.replace(";", ""));
        log.info("{}", list);
        String path = "E:\\文件信息\\2026-09-24\\";
        String fileName = "药品-核对-中间表 ";
        String newPath = path + "\\" + fileName + ".xlsx";
        FileUtils.createFile(newPath);
        Files.deleteIfExists(new File(newPath).toPath());
        export(list, "ONE", newPath, fileName, true);
        //sql语句写入本地
        FileUtils.createFile(path);
        FileUtils.writeFile(path + "\\" + fileName + ".sql", sql);
    }

    public void export(List data, String name, String path, String titleName, boolean isYymc) throws IOException {

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        //标题大小18
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 18);
        CellStyle cellStyle18 = workbook.createCellStyle();
        cellStyle18.setAlignment(HorizontalAlignment.CENTER);
        cellStyle18.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle18.setFont(font);
        //设置样式居中
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        //设置样式背景色灰色
        CellStyle cellStyle1 = workbook.createCellStyle();
        cellStyle1.setAlignment(HorizontalAlignment.CENTER);
        cellStyle1.setVerticalAlignment(VerticalAlignment.CENTER);
        // 下边框
        cellStyle1.setBorderBottom(BorderStyle.THIN);
        // 左边框
        cellStyle1.setBorderLeft(BorderStyle.THIN);
        // 上边框
        cellStyle1.setBorderTop(BorderStyle.THIN);
        // 右边框
        cellStyle1.setBorderRight(BorderStyle.THIN);
        //设置第一行进行合并
        JSONObject entries1 = JSONUtil.parseObj(data.get(0));
        int lastCol = 0;
        for (Map.Entry<String, Object> entry : entries1) {
            lastCol++;
        }
        CellRangeAddress titleRegion1 = new CellRangeAddress(0, 0, 0, lastCol - 1);
        //设置第二行
        CellRangeAddress titleRegion2 = new CellRangeAddress(1, 1, 0, 2);
        CellRangeAddress titleRegion5 = new CellRangeAddress(4, 4, 0, 1);
        //设置第五行时间合并
        CellRangeAddress titleRegion5_1 = new CellRangeAddress(4, 4, 3, 4);
        sheet.addMergedRegion(titleRegion1);
        sheet.addMergedRegion(titleRegion2);
        sheet.addMergedRegion(titleRegion5);
        sheet.addMergedRegion(titleRegion5_1);
        //构建excel数据
        Row row1 = sheet.createRow(0);
        Cell cell = row1.createCell(0);
        titleName = isYymc ? "${param.YYMC}" + titleName : titleName;
        cell.setCellValue(titleName);
        cell.setCellStyle(cellStyle18);
        //第二行
        Row row2 = sheet.createRow(1);
        Cell cell1 = row2.createCell(0);
        cell1.setCellValue("统计时间:${param.KSSJ} - ${param.JSSJ}");
        //第三行
        Row row3 = sheet.createRow(2);
        //第四行
        Row row4 = sheet.createRow(3);
        for (Object o : data) {
            JSONObject entries = JSONUtil.parseObj(o);
            int i = 0;
            for (Map.Entry<String, Object> entry : entries) {
                String title = entry.getKey();
                String value = "#{" + name + "." + entry.getKey().toLowerCase() + "}";
                //第三行为标题
                Cell cell2 = row3.createCell(i);
                cell2.setCellValue(title);
                cell2.setCellStyle(cellStyle1);
                //第四行为组合数据
                Cell cell3 = row4.createCell(i);
                cell3.setCellValue(value);
                cell3.setCellStyle(cellStyle);
                cell3.setCellStyle(cellStyle1);
                i++;
            }
            break;
        }
        //第五行
        Row row5 = sheet.createRow(4);
        Cell cell2 = row5.createCell(0);
        cell2.setCellValue("制表人:${param.userName}");
        Cell cell4 = row5.createCell(2);
        cell4.setCellValue("制表日期:");
        Cell cell5 = row5.createCell(3);
        cell5.setCellValue("=dateStr()");
        FileOutputStream fileOutputStream = new FileOutputStream(path);
        workbook.write(fileOutputStream);
        fileOutputStream.close();
        workbook.close();
    }
}
