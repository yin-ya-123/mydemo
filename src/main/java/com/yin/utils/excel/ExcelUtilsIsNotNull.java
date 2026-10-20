package com.yin.utils.excel;

import cn.hutool.json.JSONUtil;
import com.yin.utils.excel.poi.ExcelRead;
import com.yin.utils.excel.poi.MillionRowHandler;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.Iterator;
import java.util.List;

/**
 *
 *@author yin
 *@date 2025-03-14
 *@Description 只读取不为空行的数据
 */
public class ExcelUtilsIsNotNull {
    public static void main(String[] args) throws OpenXML4JException, IOException {
        long startTime = Instant.now().toEpochMilli();
        ExcelRead excelRead = new ExcelRead();
        ExcelRead read1 = excelRead.read("E:\\文件信息保存\\2025-03-24\\mx1万.xlsx");
//        ExcelRead read1 = excelRead.read("E:\\文件信息保存\\2025-03-24\\测试.xlsx");
        List<List<String>> data1 = read1.getData();
        System.out.println(data1.get(0));
        System.out.println(data1.get(1));
        System.out.println("用时:" + (Instant.now().toEpochMilli() - startTime) + "毫秒" + (Instant.now().toEpochMilli() - startTime) / 1000 + "秒");
    }
}
