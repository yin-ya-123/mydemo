package com.yin.utils.excel.main;

import com.alibaba.fastjson2.JSONObject;
import com.yin.MyDemoApplication;
import com.yin.pojo.excel.yb.Shyjs_1;
import com.yin.pojo.excel.yb.Shyjs_2;
import com.yin.pojo.excel.yb.Shyjs_3;
import com.yin.pojo.excel.yb.Shyjs_4;
import com.yin.service.OracleService;
import com.yin.service.impl.OracleServiceImpl;
import com.yin.utils.FileUtils;
import com.yin.utils.excel.ExcelUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 汇总机审数据
 */
public class ThreeExport {
    @Autowired
    private OracleService oracleService;
    public static void main(String[] args) {
        //去除excel标题，然后从新把数据导出
        one();
        two();
        three();
        four();
    }
    /**
     * 模板1-省本级-市异地-审核意见书
     */
    public static void one(){
        //查询获取excel文件数据
        //excel路径
        //D:\公司\项目文件\康复医院\其他数据导出\医保\22-23年机审意见书\模板1-省本级-市异地-审核意见书
        File file = new File("D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\22-23年机审意见书\\模板1-省本级-市异地-审核意见书");
//        File file2 = new File("D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\22-23年机审意见书\\模板1-省本级-市异地-审核意见书-2\\1.xlsx");
        ArrayList<Shyjs_1> shyjs1s = new ArrayList<>();
        Shyjs_1 shyjs1 = new Shyjs_1();//模板1实体类
        for (File listFile : file.listFiles()) {
            System.out.println(listFile.getAbsolutePath());
            List list = ExcelUtils.getExcelUtils().readExcel(listFile.getAbsolutePath(), shyjs1,3);
            //把查询出来的数据，存放到集合中
            for (Object o : list) {
                Shyjs_1 shyjs11 = JSONObject.parseObject(JSONObject.toJSONString(o).toString(), Shyjs_1.class);
                shyjs1s.add(shyjs11);
            }
        }
        String newPath="D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\22-23年机审意见书\\模板1-省本级-市异地-审核意见书-2\\1.xlsx";
        FileUtils.createFile(newPath);
        ExcelUtils.getExcelUtils().writeExcel(newPath,shyjs1,shyjs1s);
    }

    /**
     * 模板2-市医保
     */
    public static void two(){
//查询获取excel文件数据
        //excel路径
        //D:\公司\项目文件\康复医院\其他数据导出\医保\22-23年机审意见书\模板1-省本级-市异地-审核意见书
        File file = new File("D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\22-23年机审意见书\\模板2-市医保");
        File file2 = new File("D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\22-23年机审意见书\\模板2-市医保-2\\1.xlsx");
        Shyjs_2 shyjs2 = new Shyjs_2();//模板1实体类
        ArrayList<Shyjs_2> shyjs2s = new ArrayList<>();
        for (File listFile : file.listFiles()) {
            System.out.println(listFile.getAbsolutePath());
            List list = ExcelUtils.getExcelUtils().readExcel(listFile.getAbsolutePath(), shyjs2,3);
            //把查询出来的数据，存放到集合中
            for (Object o : list) {
                Shyjs_2 shyjs11 = JSONObject.parseObject(JSONObject.toJSONString(o).toString(),Shyjs_2.class);
                shyjs2s.add(shyjs11);
            }
        }
        FileUtils.createFile(file2);
        ExcelUtils.getExcelUtils().writeExcel(file2.getAbsolutePath(),shyjs2,shyjs2s);
    }
    /**
     * 模板3-版审核意见书
     */
    public static void three(){
//查询获取excel文件数据
        //excel路径
        //D:\公司\项目文件\康复医院\其他数据导出\医保\22-23年机审意见书\模板1-省本级-市异地-审核意见书
        File file = new File("D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\22-23年机审意见书\\模板3-预剔除费用表");
        File file2 = new File("D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\22-23年机审意见书\\模板3-预剔除费用表-2\\1.xlsx");
        Shyjs_3 shyjs3 = new Shyjs_3();//模板1实体类
        ArrayList<Shyjs_3> shyjs3s = new ArrayList<>();
        for (File listFile : file.listFiles()) {
            System.out.println(listFile.getAbsolutePath());
            List list = ExcelUtils.getExcelUtils().readExcel(listFile.getAbsolutePath(), shyjs3,3);
            //把查询出来的数据，存放到集合中
            for (Object o : list) {
                Shyjs_3 shyjs11 = JSONObject.parseObject(JSONObject.toJSONString(o).toString(),Shyjs_3.class);
                shyjs3s.add(shyjs11);
            }
        }
        FileUtils.createFile(file2);
        ExcelUtils.getExcelUtils().writeExcel(file2.getAbsolutePath(),shyjs3,shyjs3s);
    }
    /**
     * 模板4-预剔除费用表
     */
    public static void four(){
//查询获取excel文件数据
        //excel路径
        //D:\公司\项目文件\康复医院\其他数据导出\医保\22-23年机审意见书\模板1-省本级-市异地-审核意见书
        File file = new File("D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\22-23年机审意见书\\模板4-版审核意见书");
        File file2 = new File("D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\22-23年机审意见书\\模板4-版审核意见书-2\\1.xlsx");
        Shyjs_4 shyjs4 = new Shyjs_4();//模板1实体类
        ArrayList<Shyjs_4> shyjs4s = new ArrayList<>();
        for (File listFile : file.listFiles()) {
            System.out.println(listFile.getAbsolutePath());
            List list = ExcelUtils.getExcelUtils().readExcel(listFile.getAbsolutePath(), shyjs4,3);
            //把查询出来的数据，存放到集合中
            for (Object o : list) {
                Shyjs_4 shyjs11 = JSONObject.parseObject(JSONObject.toJSONString(o).toString(),Shyjs_4.class);
                shyjs4s.add(shyjs11);
            }
        }
        FileUtils.createFile(file2);
        ExcelUtils.getExcelUtils().writeExcel(file2.getAbsolutePath(),shyjs4,shyjs4s);
    }
}
