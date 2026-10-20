package com.yin.utils.mytest;

import com.yin.pojo.gzb1;
import com.yin.utils.FileUtils;
import com.yin.utils.excel.ExcelUtils;
import com.yin.utils.excel.ExcelUtils3;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA
 *
 * @Auther: YIN
 * @DescriPtion:
 * @Date:2024-01-22/01/22/11:49
 */
@Slf4j
public class Test1 {
    //比对两个字段
    public static void main (String[] args) {
        String path1="E:\\公司\\项目文件\\康复医院\\票据\\总表1.txt";
        String path2="E:\\公司\\项目文件\\康复医院\\票据\\总表2.txt";
        String s = FileUtils.readFile(path1);
        String s1 = FileUtils.readFile(path2);
        String[] split = s.split(",");
        String[] split1 = s1.split(",");
        for (int i = 0; i < split.length; i++) {
            String one = split1[i].toUpperCase().trim();
            String two = split[i].toUpperCase().trim();
            boolean contains = one.contains(two);
            if(!contains){
                log.info("总表1:{},总表2:{},是否包含：{}",split[i].toUpperCase().trim(),split1[i].toUpperCase().trim(),contains);
            }

        }
    }
    @Test
    public void show(){
        String path="D:\\cs\\ssxjg\\myDemo\\src\\main\\java\\com\\yin\\pojo\\cw";
        String java = path.substring(path.indexOf("java")+5).replaceAll("\\\\",".");
        System.out.println(java);
    }

    @Test
    public void show2(){
        String path="D:\\公司\\1.xlsx";
        String path1="D:\\公司\\2.xlsx";
        gzb1 gzb1 = new gzb1();
        ExcelUtils3<com.yin.pojo.gzb1> gzb1ExcelUtils3 = new ExcelUtils3<>(gzb1)
                .setHeadRowNumber(1)
                .setPPath(path);
        List<com.yin.pojo.gzb1> gzb1s = gzb1ExcelUtils3.readExcel();
        gzb1ExcelUtils3.setWPath(path1)
                .setSheet("测试表格")
                .setExelTitle("测试写入表格")
                .setLastCol()
                .setExcelList(gzb1s).writeExcel(); //构建具体条件,并且写入excel
    }
    @Test
    public void show3(){
        String path="D:\\公司\\1.xlsx";
        String path1="D:\\公司\\2.xlsx";
        gzb1 gzb1 = new gzb1();
        boolean b = new ExcelUtils3<>(gzb1)
                .setHeadRowNumber(1)
                .setPPath(path)
                .setWPath(path1)
                .setSheet("测试表格")
                .setExelTitle("测试写入表格")
                .setLastCol()
                .readExcel(true).writeExcel();//构建具体条件,并且写入excel
    }
}
