package com.yin.utils.excel.main.kangfu;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.yin.common.ApplicationUtils;
import com.yin.pojo.qt.kangfu.HzfzkfyyS;
import com.yin.pojo.qt.kangfu.Ndqsbl24;
import com.yin.utils.AllUtils;
import com.yin.utils.FileUtils;
import com.yin.utils.excel.ExcelUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author yin
 * @date 2025-03-05
 * @Description 床日申请退出
 */
public class ChuangRiShenQingTuiChu {
    static String path2 = "D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\2025-03-05\\床日数据";
    public static void main(String[] args) {
        String path2 = "D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\2025-03-05\\24年度清算病例-匹配.xlsx";
        String save = "D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\2025-03-05\\24年度清算病例-匹配-1.xlsx";
        ExcelUtils<Ndqsbl24> ndqsbl24ExcelUtils = ExcelUtils.of(Ndqsbl24.class);
        List<Ndqsbl24> ndqsbl24s = ndqsbl24ExcelUtils.readExcel(path2, 1);
        HashMap<String, String> info = getInfo();
//        System.out.println(info);
        for (Ndqsbl24 ndqsbl24 : ndqsbl24s) {
            String crlx = info.get(ndqsbl24.getJsID());
            if (crlx != null){
                System.out.println(crlx);
                ndqsbl24.setCrlx(crlx);
            }
        }
        ndqsbl24ExcelUtils.writeExcel(save, ndqsbl24s);
        System.out.println("导出完毕");
    }

    public static HashMap<String,String> getInfo(){
        File file = new File(path2);
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        for (File listFile : file.listFiles()) {
            String s = FileUtils.readFile(listFile.getAbsolutePath(), "utf-8");
            String replace = s.replace("tbody", "table");
            Document parse = Jsoup.parse(replace);
            Elements elementsByClass = parse.getElementsByClass("el-table__row");
            for (Element byClass : elementsByClass) {
//                床日
                Elements cr = byClass.getElementsByClass("el-table_2_column_27 is-center ").select("div");
//                结算id
                Elements jsid = byClass.getElementsByClass("el-table_2_column_26 is-center ").select("div");
//                System.out.println(byClass);
//                System.out.println(cr.html());
//                System.out.println(jsid.html());
                stringStringHashMap.put(jsid.html(),cr.html());
            }
        }
        return stringStringHashMap;
    }


    @Test
    public void two(){
        String path1 = "D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\2025-03-05\\杭州富春康复医院（省）.xlsx";
        ExcelUtils<HzfzkfyyS> hzfzkfyySExcelUtils = ExcelUtils.of(HzfzkfyyS.class);
        List<HzfzkfyyS> hzfzkfyyS = hzfzkfyySExcelUtils.readExcel(path1, 1);
        String save = "D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\2025-03-05\\24年度清算病例-匹配-1.xlsx";
    }

    @Test
    public void generate() throws IOException {
        ExcelUtils excelUtils = ExcelUtils.getExcelUtils();
        String path = "D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\2025-03-05\\24年度清算病例-匹配.xlsx";
        String path1 = "D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\2025-03-05\\杭州富春康复医院（省）.xlsx";
//        excelUtils.generateClassEnhanceZ("Ndqsbl24", ApplicationUtils.pathPojoKangFu, path);
        excelUtils.generateClassEnhanceZ("HzfzkfyyS", ApplicationUtils.pathPojoKangFu, path1);
    }



}
