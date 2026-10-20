package com.yin.utils.excel.main.liangshan;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.text.csv.CsvData;
import cn.hutool.core.text.csv.CsvReader;
import cn.hutool.core.text.csv.CsvRow;
import cn.hutool.core.text.csv.CsvUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.read.builder.ExcelReaderSheetBuilder;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.read.metadata.holder.ReadWorkbookHolder;
import com.alibaba.excel.read.metadata.holder.csv.CsvReadWorkbookHolder;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import com.yin.common.ApplicationUtils;
import com.yin.common.err.YException;
import com.yin.pojo.qt.liangshan.LsBasyPojo;
import com.yin.pojo.qt.liangshan.LsCyCfMxPojo;
import com.yin.pojo.qt.liangshan.LsOnePojo;
import com.yin.utils.FileUtils;
import com.yin.utils.excel.ExcelUtils;
import com.yin.utils.excel.params.ExcelParams;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author yin
 * @date 2025-02-05
 * @Description 梁山 绩效考核数据处理
 */
@Slf4j
public class OneExport {
    public static ExcelUtils excelUtils = ExcelUtils.getExcelUtils();
    public static String onePath = "E:\\文件信息保存\\2025-02-05\\草药\\2024-01.xlsx";
    public static String onePath2 = "E:\\文件信息保存\\2025-02-05\\草药\\2024-01处方详细.xlsx";

    public static void main(String[] args) throws IOException {
        ExcelUtils<LsOnePojo> lsOnePojoExcelUtils = ExcelUtils.of(LsOnePojo.class);
        String path ="E:\\文件信息保存\\2025-07-04";
        String one = path+"\\医嘱.xlsx";
        String saveFile = path+"\\医嘱-匹配.xlsx";
        String two = path+"\\草药处方明细.xlsx";
        orderNormal(two,one,saveFile); //给梁山草药医嘱匹配处方详情
        lsOnePojoExcelUtils.writeCsv(saveFile,1,10000);
//        csvToExcel(); //csv转换为execl
//        generateClass(); //创建实体类
//        excel(); //汇总excel数据
    }

    public static void excel() throws YException {
        String path = "E:\\文件信息保存\\2025-02-09";
        String savePath=path+"\\汇总.xlsx";
        ExcelUtils<LsBasyPojo> lsBasyPojoExcelUtils = ExcelUtils.of(LsBasyPojo.class);
        lsBasyPojoExcelUtils.excelToAll(path,savePath,new LsBasyPojo());
    }

    /**
     * csv转换excel
     */
    public static void csvToExcel() {
        String path = "E:\\文件信息保存\\2025-02-09";
        File file = new File(path);
        File[] files = file.listFiles();
        for (File file1 : files) {
            if (file1.isFile() && FileUtils.getFileSuffix(file1.getAbsolutePath()).equals("csv")) {
                System.out.println(file1.getAbsolutePath());
                String excelPath = "E:\\文件信息保存\\2025-02-09\\" + FileUtils.getFileName(file1.getAbsolutePath()) + ".xlsx";
                try (CSVReader csvReader = new CSVReader(new InputStreamReader(new FileInputStream(file1.getAbsolutePath()), "GB2312")); Workbook workbook = new XSSFWorkbook()) {
                    Sheet sheet = workbook.createSheet("data");
                    String[] nextLine;
                    int rowNum = 0;
                    while ((nextLine = csvReader.readNext()) != null) {
                        Row row = sheet.createRow(rowNum++);
                        for (int i = 0; i < nextLine.length; i++) {
                            Cell cell = row.createCell(i);
                            String value = nextLine[i];
                            cell.setCellValue(value);
                        }
                    }
                    try (FileOutputStream fos = new FileOutputStream(excelPath)) {
                        workbook.write(fos);
                    }
                    System.out.println("转换成功:"+excelPath);
                } catch (IOException | CsvValidationException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * 处理梁山中医病案首页医嘱数据匹配处方详情
     */
    public static void orderNormal(String cfmxPath,String orderPath,String saveFile) {
        ExcelUtils<LsOnePojo> lsOnePojoExcelUtils = ExcelUtils.of(LsOnePojo.class);
        ExcelUtils<LsCyCfMxPojo> lsCyCfMxPojoExcelUtils = ExcelUtils.of(LsCyCfMxPojo.class);
        List<LsOnePojo> lsOnePojos = lsOnePojoExcelUtils.readExcel(orderPath, new LsOnePojo(), 1);
        List<LsCyCfMxPojo> lsCyCfMxPojos = lsCyCfMxPojoExcelUtils.readExcel(cfmxPath, new LsCyCfMxPojo(), 1);
//        转换lsCyCfMxPojos数据导map
        Map<Integer, LsCyCfMxPojo> lsCyCfMxPojoMap = lsCyCfMxPojos.stream().collect(Collectors.toMap(
                LsCyCfMxPojo::getYZJL_ID, Function.identity(), (existing, replacement) -> existing
        ));
//        System.out.println(lsCyCfMxPojoMap);
//        Optional.ofNullable(lsCyCfMxPojoMap.get(lsOnePojo.getYZBH())) lsCyCfMxPojoMap非空判断，.ifPresent(mxpojo->{ 获取匹配的对象
        lsOnePojos.forEach(lsOnePojo -> Optional.ofNullable(lsCyCfMxPojoMap.get(lsOnePojo.getYZBH())).ifPresent(mxpojo -> {
                    lsOnePojo.setCFXQ(mxpojo.getCFXX());
                    if (lsOnePojo.getYZFL() == 2) {
                        lsOnePojo.setJIXING("饮片");
                    }
                })
        );
//        HashMap<String, LsCyCfMxPojo> stringLsCyCfMxPojoHashMap = new HashMap<>();
//        for (LsCyCfMxPojo lsCyCfMxPojo : lsCyCfMxPojos) {
//            stringLsCyCfMxPojoHashMap.put(lsCyCfMxPojo.getYZJL_ID(), lsCyCfMxPojo);
//        }
//        for (LsOnePojo lsOnePojo : lsOnePojos) {
//            LsCyCfMxPojo lsCyCfMxPojo = stringLsCyCfMxPojoHashMap.get(lsOnePojo.getYZBH());
//            if(lsCyCfMxPojo!=null){
//                lsOnePojo.setCFXQ(lsCyCfMxPojo.getCFXX());
//            }
//        }
        lsOnePojoExcelUtils.writeExcel(saveFile, new LsOnePojo(), lsOnePojos);
    }


    public void convert() {
        String saveFile = "E:\\文件信息保存\\2025-02-05\\草药\\草药.xlsx";
        String saveFile2 = "E:\\文件信息保存\\2025-02-05\\草药\\草药详细.xlsx";
        File file = new File("E:\\文件信息保存\\2025-02-05\\草药");
        ArrayList<String> cy = new ArrayList<>();
        ArrayList<String> cyxx = new ArrayList<>();
        File[] files = file.listFiles();
        for (File file1 : files) {
            if (file1.getAbsolutePath().contains("处方详细")) {
                cyxx.add(file1.getAbsolutePath());
            } else {
                cy.add(file1.getAbsolutePath());
            }
        }
        ExcelUtils<LsOnePojo> lsOnePojoExcelUtils = new ExcelUtils<>();
        ExcelUtils<LsCyCfMxPojo> lsCyCfMxPojoExcelUtils = new ExcelUtils<>();
        ArrayList<LsOnePojo> lsOnePojos1 = new ArrayList<>();
        for (String s : cy) {
            List<LsOnePojo> lsOnePojos = lsOnePojoExcelUtils.readExcel(s, new LsOnePojo(), 1);
            lsOnePojos1.addAll(lsOnePojos);
        }
        lsOnePojoExcelUtils.writeExcel(saveFile, new LsOnePojo(), lsOnePojos1);
        ArrayList<LsCyCfMxPojo> lsCyCfMxPojos = new ArrayList<>();
        for (String s : cyxx) {
            List<LsCyCfMxPojo> lsCyCfMxPojos1 = lsCyCfMxPojoExcelUtils.readExcel(s, new LsCyCfMxPojo(), 1);
            lsCyCfMxPojos.addAll(lsCyCfMxPojos1);
        }
        lsCyCfMxPojoExcelUtils.writeExcel(saveFile2, new LsCyCfMxPojo(), lsCyCfMxPojos);
    }

    public static void generateClass() throws IOException {
        String shouye="E:\\文件信息保存\\2025-02-09\\2024年01月-国家平台-病案首页 (2).xlsx";
//        LinkedList<String> excelTitle = excelUtils.getExcelTitle(onePath2);
        LinkedList<String> excelTitle2 = excelUtils.getExcelTitle(shouye);
        ExcelParams.headRowNumber = 1;
//        System.out.println(excelTitle2);
//        LinkedList excelTitle1 = excelUtils.getExcelTitle(twoPath);
//        excelUtils.generateClassZ("LsCyCfMxPojo", excelTitle, ApplicationUtils.pathPojoLS);
        excelUtils.generateClassZ("LsBasyPojo", excelTitle2, ApplicationUtils.pathPojoLS);
//        excelUtils.generateClassZ("BrgjYbq",excelTitle1,ApplicationUtils.pathPojo);
//        excelUtils.generateClassZ("Hmc2024", excelTitle2, ApplicationUtils.pathPojo);
    }
}
