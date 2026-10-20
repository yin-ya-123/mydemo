package com.yin.utils.mytest;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.yin.MyDemoApplication;
import com.yin.common.ApplicationSql;
import com.yin.pojo.excel.Excel;
import com.yin.service.impl.OracleServiceImpl;
import com.yin.utils.excel.ExcelUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 财务报表对账
 */
@SpringBootTest(classes = MyDemoApplication.class)
@RunWith(SpringRunner.class)
public class CwDz {
    @Autowired
    private OracleServiceImpl oracleService;

    @Test
    public void main(){
        //传入日期，03-01就是查询2月份的数据
        String wjsSql = ApplicationSql.getWjsSql("2024-03-01");
        List list = oracleService.selectAll(wjsSql);
    }
    public static void main(String[] args) throws IOException {
//        String path="D:\\公司\\项目文件\\康复医院\\药房数据导出\\2024-03-05\\1.xlsx";
//        LinkedList excelTitle = ExcelUtils.getExcelUtils().getExcelTitle(path);
//        ExcelUtils.getExcelUtils().generateClass("WjsSj",excelTitle,"D:\\cs\\ssxjg\\myDemo\\src\\main\\java\\com\\yin\\pojo\\cw");
    }
    @Test
    public void aVoid(){
        String lsb = ApplicationSql.getLsb();
        List list = oracleService.selectAll(lsb);
        JSONArray objects = JSONArray.parseArray(JSONObject.toJSONString(list));
        ArrayList<Excel> excels = new ArrayList<>();
        objects.forEach(item->{
            System.out.println(item);
            String string = JSONObject.parseObject(JSONObject.toJSONString(item)).getString("ONE");
            String xmForBrxx = ApplicationSql.getXmForBrxx(string);
            List list1 = oracleService.selectAll(xmForBrxx);
            for (Object o : list1) {
                JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(o));
                System.out.println(jsonObject.toString());
                Excel excel = new Excel();
                excel.setOne(jsonObject.getString("PAT_NAME"));
                excel.setTwo(jsonObject.getString("ZYKSMC"));
                excel.setThree(jsonObject.getString("ZYCWH"));
                excel.setFoure(jsonObject.getString("YZ_MC"));
                excels.add(excel);
            }
        });
        System.out.println(JSONObject.toJSONString(excels));
        String path="D:\\公司\\项目文件\\康复医院\\其他数据导出\\2024-03-25\\1.xlsx";
        List<Excel> collect = excels.stream().distinct().collect(Collectors.toList()); //再次去重
        ExcelUtils.getExcelUtils().writeExcel(path,new Excel(),collect);
    }

}
