package com.yin.utils.excel.main.yb;

import com.alibaba.fastjson2.JSONObject;
import com.yin.MyDemoApplication;
import com.yin.common.ApplicationUtils;
import com.yin.pojo.qt.yb.All2024;
import com.yin.pojo.qt.yb.AllData;
import com.yin.pojo.qt.yb.AllFcData;
import com.yin.service.OracleService;
import com.yin.utils.ConvertAllUtils;
import com.yin.utils.excel.ExcelUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author yin
 * @date 2024-11-20
 * @Description 数据撇皮
 */
@SpringBootTest(classes = MyDemoApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
public class SjppOne {
    @Autowired
    private OracleService hisOracleServiceImpl;

    static ExcelUtils excelUtils = ExcelUtils.getExcelUtils();
    static String pathOne = "D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\2024-11-20\\杭州富春康复医院20241119(1).xlsx";
    static String pathTwo = "D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\2024-11-20\\2023年全部数据.xlsx";
    static String pathThree = "D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\2024-11-20\\杭州富春康复医院20241119-数据导出.xlsx";
    static String pathFour = "D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\2024-11-20\\202401-09(1).xlsx";
    static String pathFive = "D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\2024-11-20\\杭州富春康复医院20241119（1）.xlsx";
    static String pathSix = "D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\2024-11-20\\杭州富春康复医院20241119（1）-数据导出.xlsx";

    static String pathSeven = "D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\2024-11-20\\new\\杭州富春康复医院20241119.xlsx";
    static String pathEight = "D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\2024-11-20\\new\\杭州富春康复医院20241119-科室匹配.xlsx";

    public static void main(String[] args) throws IOException {
//        generateClass();
        dataHandle();
    }


    public static void dataHandle() {
        ExcelUtils<AllFcData> allFcDataExcelUtils = new ExcelUtils<>();
        ExcelUtils<AllData> allDataExcelUtils = new ExcelUtils<>();
        ExcelUtils<All2024> all2024ExcelUtils = new ExcelUtils<>();
        List<AllFcData> allFcData = allFcDataExcelUtils.readExcel(pathOne, new AllFcData(), 1);
        List<AllData> allData = allDataExcelUtils.readExcel(pathTwo, new AllData(), 1);
        List<All2024> all2024s = all2024ExcelUtils.readExcel(pathFour, new All2024(), 1);
        HashMap<String, AllData> twoMap = new HashMap<>();
        HashMap<String, All2024> twoMap2024 = new HashMap<>();
        for (AllData allDatum : allData) {
            twoMap.put(allDatum.getJsID(), allDatum);
        }
        for (All2024 all2024 : all2024s) {
            twoMap2024.put(all2024.getJsID(), all2024);
        }
        for (AllFcData allFcDatum : allFcData) {
            if (allFcDatum.getJsid() == null || allFcDatum.getJsid().isEmpty()) {
                continue;
            }
            AllData allData1 = twoMap.get(allFcDatum.getJsid());
            if (allData1 != null) {
                allFcDatum.setKs(allData1.getCyks());
                allFcDatum.setXm(allData1.getXm());
                allFcDatum.setZffs(allData1.getBllx());
                allFcDatum.setZyts(ApplicationUtils.dateDifference(allFcDatum.getRysj(), allFcDatum.getCysj()));
                allFcDatum.setYbjtzfje(allData1.getNdDRGzfje());
            } else {
                All2024 all2024 = twoMap2024.get(allFcDatum.getJsid());
//                System.out.println(all2024);
                if (all2024 != null) {
                    allFcDatum.setKs(all2024.getCyks());
                    allFcDatum.setXm(all2024.getRyxm());
                    allFcDatum.setZyts(ApplicationUtils.dateDifference(allFcDatum.getRysj(), allFcDatum.getCysj()));
                    allFcDatum.setYbjtzfje(all2024.getADRGyjzje());
                }
            }
        }
//        System.out.println(allFcData);
        //导出数据
        allFcDataExcelUtils.writeExcel(pathThree, new AllFcData(), allFcData);

    }

    @Test
    public void lastHandle() {
        ExcelUtils<AllFcData> allFcDataExcelUtils = new ExcelUtils<>();
        List<AllFcData> allFcData = allFcDataExcelUtils.readExcel(pathFive, new AllFcData(), 1);
        for (AllFcData allFcDatum : allFcData) {
//            System.out.println(allFcDatum);
            if (allFcDatum.getJsid() != null && allFcDatum.getYbjtzfje() == null && allFcDatum.getKs() == null) {
//                System.out.println(allFcDatum);
                String sql = "\n" +
                        "SELECT br.cybqmc,br.pat_name,br.zyts FROM HIS.V_INPAT_INFO BR\n" +
                        "where TO_CHAR(BR.ry_rq,'YYYY-MM-DD')='" + allFcDatum.getRysj().substring(0, 10) + "'" +
                        " and TO_CHAR(BR.cy_rq,'YYYY-MM-DD')='" + allFcDatum.getCysj().substring(0, 10) + "' and br.sfz_hm='" + allFcDatum.getSfzh() + "'";
                List list = hisOracleServiceImpl.selectAll(sql);
                if (!list.isEmpty()) {
                    JSONObject jsonObject = ConvertAllUtils.convertJson(list.get(0));
                    allFcDatum.setKs(jsonObject.getString("CYBQMC"));
                    allFcDatum.setXm(jsonObject.getString("PAT_NAME"));
                    allFcDatum.setZyts(jsonObject.getString("ZYTS"));
                }
            }
        }
        allFcDataExcelUtils.writeExcel(pathSix, new AllFcData(), allFcData);
    }

    @Test
    public void lastHandleTwo() {
        ExcelUtils<AllFcData> allFcDataExcelUtils = new ExcelUtils<>();
        List<AllFcData> allFcData = allFcDataExcelUtils.readExcel(pathSeven, new AllFcData(), 1);
        for (AllFcData allFcDatum : allFcData) {
            String sql = "\n" +
                    "SELECT br.cybqmc,br.pat_name,br.zyts,br.ry_rq,br.cy_rq \n" +
                    "FROM \n" +
                    "HIS.V_INPAT_INFO BR\n" +
                    "JOIN HIS.MI_TRANS_INFO MI ON BR.zylsh=MI.JZ_LSH\n" +
                    "where MI.YBJY_LSH ='" + allFcDatum.getJsid() + "'";
            List list = hisOracleServiceImpl.selectAll(sql);
            if (!list.isEmpty()) {
                JSONObject jsonObject = ConvertAllUtils.convertJson(list.get(0));
                allFcDatum.setKs(jsonObject.getString("CYBQMC"));
            }
//            log.info(allFcDatum.toString());
        }
        allFcDataExcelUtils.writeExcel(pathEight, new AllFcData(), allFcData);
    }

    public static void generateClass() throws IOException {
        LinkedList<String> excelTitle = excelUtils.getExcelTitle(pathTwo);
        LinkedList excelTitle1 = excelUtils.getExcelTitle(pathOne);
        LinkedList excelTitle2 = excelUtils.getExcelTitle(pathFour);
//        excelUtils.generateClassZ("2023AllData",excelTitle,ApplicationUtils.pathPojo);
//        excelUtils.generateClassZ("2023AllFcData",excelTitle1,ApplicationUtils.pathPojo);
        excelUtils.generateClassZ("All2024", excelTitle2, ApplicationUtils.pathPojo);
    }


}
