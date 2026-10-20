package com.yin.utils.excel.poi;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 *@author yin
 *@date 2025-04-08
 *@Description
 */
@Slf4j
public class MillionRowHandler extends DefaultHandler {
    private SharedStringsTable sst;
    private String currentCellValue;
    private boolean isString;
    private int rowNum = 0;
    private int cellNum = 0;
    @Getter
    private List<List<String>> data;
    private List<String> reusableRowData = new ArrayList<>();
    private final StringBuilder currentCellValueBuilder = new StringBuilder();

    public MillionRowHandler(SharedStringsTable sst) {
//        log.info("初始化-MillionRowHandler");
        this.sst = sst;
        data = new ArrayList<>(1000);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
//        log.info("开始解析-MillionRowHandler:qName={},uri={},localName={}", qName, uri, localName);
//        log.info("开始解析-MillionRowHandler");
        switch (qName) {
            case "c": // 单元格开始
                String cellType = attributes.getValue("t");
                isString = cellType != null && cellType.equals("s");
                break;
            case "row":
                rowNum++;
                reusableRowData.clear();
//                log.info("行:{}", rowNum);
                break;
            case "v": // 单元格值开始
                currentCellValueBuilder.delete(0, currentCellValueBuilder.length());
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
//        log.info("解析到数据-MillionRowHandler");
        currentCellValueBuilder.append(ch, start, length);
        currentCellValue = currentCellValueBuilder.toString();
//            log.info("解析到数据-MillionRowHandler:{}", currentCellValue);
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        if ("v".equals(qName)) {
            cellNum++;
            if (isString) {
                int idx = Integer.parseInt(currentCellValue);
                currentCellValue = sst.getItemAt(idx).getString();
            }
            if (currentCellValue != null) {
                reusableRowData.add(currentCellValue);
            }
        } else if ("row".equals(qName)) {
            cellNum = 0;
            if (!reusableRowData.isEmpty()) { // 只有当行数据不为空时才添加到data中
                data.add(reusableRowData);
            }
            reusableRowData = new ArrayList<>(); // 清空可复用的行数据
        }
    }

    public void close() {
        data.clear();
        data = null;
        reusableRowData.clear();
        reusableRowData = null;
        currentCellValueBuilder.delete(0, currentCellValueBuilder.length());
    }
}
