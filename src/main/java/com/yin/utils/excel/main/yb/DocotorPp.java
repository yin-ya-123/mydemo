package com.yin.utils.excel.main.yb;

import com.yin.common.ApplicationUtils;
import com.yin.pojo.qt.yb.Hmc2024;
import com.yin.pojo.qt.yb.YbHs2024;
import com.yin.pojo.qt.yb.YbYs2024;
import com.yin.utils.excel.ExcelUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * @author yin
 * @date 2024-11-25
 * @Description 医生护士数据匹配
 */
public class DocotorPp {
    static String pathOne="D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\2024-11-25\\医保医师20241125.xlsx";
    static String pathTwo="D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\2024-11-25\\医保护士20241125.xlsx";
    static String pathThree = "D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\2024-11-25\\花名册.xlsx";
    static String pathFour="D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\2024-11-25\\花名册-匹配.xlsx";
    static ExcelUtils excelUtils=ExcelUtils.getExcelUtils();
    public static void main(String[] args) throws IOException {
        dataHandle();
    }


    public static void dataHandle(){
        ExcelUtils<YbYs2024> ybYs2024ExcelUtils = new ExcelUtils<>();
        ExcelUtils<YbHs2024> ybHs2024ExcelUtils = new ExcelUtils<>();
        ExcelUtils<Hmc2024> hmc2024ExcelUtils = new ExcelUtils<>();
        List<YbYs2024> ybYs2024s = ybYs2024ExcelUtils.readExcel(pathOne, new YbYs2024(), 1);
        List<YbHs2024> ybHs2024s = ybHs2024ExcelUtils.readExcel(pathTwo, new YbHs2024(), 1);
        List<Hmc2024> hmc2024s = hmc2024ExcelUtils.readExcel(pathThree, new Hmc2024(), 1);
        HashMap<String, YbYs2024> stringYbYs2024HashMap = new HashMap<>();
        HashMap<String, YbHs2024> stringYbHs2024HashMap = new HashMap<>();
        for (YbYs2024 ybYs2024 : ybYs2024s) {
            stringYbYs2024HashMap.put(ybYs2024.getZjhm(),ybYs2024);
        }
        for (YbHs2024 ybHs2024 : ybHs2024s) {
            stringYbHs2024HashMap.put(ybHs2024.getZjhm(),ybHs2024);
        }
        for (Hmc2024 hmc2024 : hmc2024s) {
            YbYs2024 ybYs2024 = stringYbYs2024HashMap.get(hmc2024.getSfzhm());
            YbHs2024 ybHs2024 = stringYbHs2024HashMap.get(hmc2024.getSfzhm());
            if(null!=ybYs2024){
                hmc2024.setZyjszc(ybYs2024.getZyjszw());
                hmc2024.setZyzsh(ybYs2024.getYszyzsbm());
            }else if (null!=ybHs2024){
                hmc2024.setZyjszc(ybHs2024.getZyjszw());
                hmc2024.setZyzsh(ybHs2024.getZyzsbh());
            }
        }
        hmc2024ExcelUtils.writeExcel(pathFour,new Hmc2024(),hmc2024s);
    }

    public static void generateClass() throws IOException {
        LinkedList<String> excelTitle = excelUtils.getExcelTitle(pathOne);
        LinkedList excelTitle1 = excelUtils.getExcelTitle(pathTwo);
        LinkedList excelTitle2 = excelUtils.getExcelTitle(pathThree);
//        excelUtils.generateClassZ("YbYs2024",excelTitle,ApplicationUtils.pathPojo);
//        excelUtils.generateClassZ("YbHs2024",excelTitle1,ApplicationUtils.pathPojo);
        excelUtils.generateClassZ("Hmc2024", excelTitle2, ApplicationUtils.pathPojo);
    }
}
