package com.yin.utils.excel.main;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.yin.MyDemoApplication;
import com.yin.pojo.qt.Ml;
import com.yin.service.impl.OracleServiceImpl;
import com.yin.utils.excel.ExcelUtils;
import com.yin.utils.excel.ExcelUtils2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;
@SpringBootTest(classes = MyDemoApplication.class)
@RunWith(SpringRunner.class)
public class NineExport {
    @Autowired
    private OracleServiceImpl oracleService;

    public static void main(String[] args) throws IOException {
        String path = "D:\\公司\\项目文件\\康复医院\\药房数据导出\\2024-05-22\\工作簿1.xlsx";
        ExcelUtils excelUtils = ExcelUtils.getExcelUtils();
        Ml ml = new Ml();
        List list = excelUtils.readExcel(path, ml, 1);
        List<Ml> mls = ExcelUtils2.listToTList(list, Ml.class);
        System.out.println(mls);
    }

    @Test
    public void show(){
        String path = "D:\\公司\\项目文件\\康复医院\\药房数据导出\\2024-05-22\\工作簿1.xlsx";
        ExcelUtils excelUtils = ExcelUtils.getExcelUtils();
        Ml ml = new Ml();
        List list = excelUtils.readExcel(path, ml, 1);
        List<Ml> mls = ExcelUtils2.listToTList(list, Ml.class);
        for (Ml ml1 : mls) {
            List list2 = oracleService.selectAll(getsql1(ml1.getYptym()));
            if(list2.size()>0){
                for (Object o : list2) {
                    JSONObject jsonObject = JSONUtil.parseObj(JSONUtil.toJsonStr(o));
                    String s = getsql2(jsonObject.get("YP_ID").toString(), jsonObject.get("YP_MC").toString());
                    System.out.println(s);
                    oracleService.insertAll(s);
                }
            }

        }

    }
    public String getsql1(String name){
        return "SELECT * FROM DICTMANAGE.drug_info c where  c.yp_mc like '%"+name+"%'";
    }
    public String getsql2(String  ypid,String yp_mc){
        return "INSERT INTO dypgjzjypml values('"+ypid+"','"+yp_mc+"')";
    }

}
