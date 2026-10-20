import cn.hutool.extra.pinyin.PinyinUtil;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.yin.pojo.excel.yb.Shyjs_2;
import com.yin.pojo.excel.yb.Shyjs_3;
import com.yin.pojo.excel.yb.Shyjs_4;
import com.yin.utils.ConvertAllUtils;
import com.yin.utils.FileUtils;
import com.yin.utils.excel.ExcelUtils;
import org.junit.Test;
import org.springframework.util.StringUtils;

import java.io.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA
 *
 * @Auther: YIN
 * @DescriPtion:
 * @Date:2023-11-01/11/01/14:12
 */
public class Text1 {
    public static void main(String[] args) {
        four();
    }

    public static void two() {
//查询获取excel文件数据
        //excel路径
        //D:\公司\项目文件\康复医院\其他数据导出\医保\22-23年机审意见书\模板1-省本级-市异地-审核意见书
        File file = new File("D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\22-23年机审意见书\\模板2-市医保");
        File file2 = new File("D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\22-23年机审意见书\\模板2-市医保-2\\1.xlsx");
        Shyjs_2 shyjs2 = new Shyjs_2();//模板1实体类
        ArrayList<Shyjs_2> shyjs2s = new ArrayList<>();
        for (File listFile : file.listFiles()) {
            System.out.println(listFile.getAbsolutePath());
            String newPath = listFile.getAbsolutePath();
            List list = ExcelUtils.getExcelUtils().readExcel(newPath, new Shyjs_2(), 3);
            System.out.println(list.size());
//            //把查询出来的数据，存放到集合中
            show(list);
        }
//        String newPath = FileUtils.createFile(file2);
//        ExcelUtils.getExcelUtils().writeExcel(newPath,shyjs2,shyjs2s);
    }

    public static void show(List objects) {
        for (Object o : objects) {
            JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(o));
            if (jsonObject.get("cbrxm") != null) {
                if (jsonObject.get("cbrxm").equals("程亨明") && jsonObject.get("ynbmc").equals("梅毒螺旋体特异抗体测定")) {
                    System.out.println(jsonObject);
                }
            }
        }
    }

    public static void three() {
//查询获取excel文件数据
        //excel路径
        //D:\公司\项目文件\康复医院\其他数据导出\医保\22-23年机审意见书\模板1-省本级-市异地-审核意见书
        File file = new File("D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\22-23年机审意见书\\模板3-预剔除费用表");
        File file2 = new File("D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\22-23年机审意见书\\模板3-预剔除费用表-2\\1.xlsx");
        Shyjs_3 shyjs3 = new Shyjs_3();//模板1实体类
        ArrayList<Shyjs_3> shyjs3s = new ArrayList<>();
        for (File listFile : file.listFiles()) {
            System.out.println(listFile.getAbsolutePath());
            List list = ExcelUtils.getExcelUtils().readExcel(listFile.getAbsolutePath(), shyjs3, 3);
            //把查询出来的数据，存放到集合中
            for (Object o : list) {
                Shyjs_3 shyjs11 = JSONObject.parseObject(JSONObject.toJSONString(o).toString(), Shyjs_3.class);
                if (StringUtils.hasText(shyjs11.getCbrxm())) {
                    if (shyjs11.getCbrxm().equals("程亨明") && shyjs11.getYnbmc().equals("梅毒螺旋体特异抗体测定")) {
                        System.out.println(shyjs11);
                    }
                }
            }
        }
    }

    /**
     * 模板4-预剔除费用表
     */
    public static void four() {
//查询获取excel文件数据
        //excel路径
        //D:\公司\项目文件\康复医院\其他数据导出\医保\22-23年机审意见书\模板1-省本级-市异地-审核意见书
        File file = new File("D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\22-23年机审意见书\\模板4-版审核意见书");
        File file2 = new File("D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\22-23年机审意见书\\模板4-版审核意见书-2\\1.xlsx");
        Shyjs_4 shyjs4 = new Shyjs_4();//模板1实体类
        ArrayList<Shyjs_4> shyjs4s = new ArrayList<>();
        for (File listFile : file.listFiles()) {
            System.out.println(listFile.getAbsolutePath());
            List list = ExcelUtils.getExcelUtils().readExcel(listFile.getAbsolutePath(), shyjs4, 3);
            System.out.println(list.size());
            if (listFile.getAbsolutePath().equals("D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\22-23年机审意见书\\模板4-版审核意见书\\杭州富春康复医院_省本级本地_2023年11月审核意见书（第1版）.xlsx")) {
                System.out.println(list);
            }
            //把查询出来的数据，存放到集合中
//            for (Object o : list) {
//                Shyjs_4 shyjs11 = JSONObject.parseObject(JSONObject.toJSONString(o).toString(),Shyjs_4.class);
//                shyjs4s.add(shyjs11);
//            }
        }
        System.out.println(shyjs4s.size());
//        String newPath = FileUtils.createFile(file2);
//        ExcelUtils.getExcelUtils().writeExcel(newPath,shyjs4,shyjs4s);
    }

    @Test
    public void test() throws IOException {
        String path = "D:\\公司\\笔记\\新建 XLSX 工作表.xlsx";
        String classPath="D:\\cs\\ssxjg\\myDemo\\src\\test\\java";
//        LinkedList<String> excelTitle = ExcelUtils.getExcelUtils().getExcelTitle();
//        ExcelUtils.getExcelUtils().generateClassZ("Text",excelTitle, classPath);
    }
    @Test
    public void show(){
        String pinyin = PinyinUtil.getFirstLetter("你好", "");
        System.out.println(pinyin);
    }


    @Test
    public void test2() throws IOException {
        String path = "E:\\文件信息保存\\2024-11-15\\dx\\new 3.txt";
        ArrayList<String> strings = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                if(line.contains("RequestData：")){
                    line=line.replace("RequestData：","");
                    // 处理每一行数据
//                    System.out.println(line);
                    JSONObject jsonObject = JSONObject.parseObject(line);
                    strings.add(line);
                }
            }
            System.out.println(strings.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
