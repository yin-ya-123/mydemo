package com.yin.utils.excel.main;

import com.alibaba.fastjson.JSONObject;
import com.yin.MyDemoApplication;
import com.yin.pojo.excel.KjywSyqd;
import com.yin.pojo.excel.KjywSyqdKsMx;
import com.yin.service.OracleService;
import com.yin.utils.FileUtils;
import com.yin.utils.excel.ExcelUtils;
import com.yin.utils.excel.sql.Sql;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 *
 * @Auther: YIN
 * @DescriPtion: sql查询导出
 * @Date:2023-11-30/11/30/10:55
 */
@SpringBootTest(classes = MyDemoApplication.class)
@RunWith(SpringRunner.class)
public class TwoExport {
    @Autowired
    private OracleService oracleService;

    @Test
    public void show1(){
        List list = oracleService.selectAll(Sql.getTestSql());
        String jsonString = JSONObject.toJSONString(list);
        System.out.println(jsonString);
    }

    @Test
    public void kjyw(){
        String path="E:\\公司\\项目文件\\康复医院\\药房数据导出\\2024-01-17";
        String one[]={"2023-01-01 00:00:00","2023-02-01 00:00:00","2023-03-01 00:00:00","2023-04-01 00:00:00","2023-05-01 00:00:00","2023-06-01 00:00:00","2023-07-01 00:00:00","2023-08-01 00:00:00","2023-09-01 00:00:00","2023-10-01 00:00:00","2023-11-01 00:00:00","2023-12-01 00:00:00"};
        String two[]={"2023-02-01 00:00:00","2023-03-01 00:00:00","2023-04-01 00:00:00","2023-05-01 00:00:00","2023-06-01 00:00:00","2023-07-01 00:00:00","2023-08-01 00:00:00","2023-09-01 00:00:00","2023-10-01 00:00:00","2023-11-01 00:00:00","2023-12-01 00:00:00","2024-01-01 00:00:00"};
        KjywSyqd kjywSyqdSt = new KjywSyqd();
        KjywSyqdKsMx kjywSyqdKsMxSt = new KjywSyqdKsMx();
        for (int i = 0; i < one.length; i++) {
            String data=one[i].substring(0,7);
            //创建路径
            String filePath=path+"\\"+data+"\\";
            FileUtils.createFile(filePath);
            List list = oracleService.selectAll(Sql.getKjywQdSql(one[i], two[i]));
            ArrayList<KjywSyqd> kjywSyqds = new ArrayList<>();
            for (Object o : list) {
                KjywSyqd kjywSyqd = JSONObject.parseObject(JSONObject.toJSONString(o), KjywSyqd.class);
                //明细数据导出
                mxdc(one[i], two[i],kjywSyqd.getKs(),filePath+kjywSyqd.getBm()+".xlsx", kjywSyqdKsMxSt);
                kjywSyqds.add(kjywSyqd);
            }
            //实体化对象
            ExcelUtils.getExcelUtils().writeExcel(filePath+"抗菌药物使用强度汇总.xlsx",kjywSyqdSt,kjywSyqds);
            System.out.println("汇总写入成功:"+filePath+"抗菌药物使用强度汇总.xlsx");
        }
    }

    public void mxdc(String kssj,String jssj,String ks,String path,KjywSyqdKsMx kjywSyqdKsMx){
        List list = oracleService.selectAll(Sql.getKjywQdKsMxSql(kssj,jssj,ks));
        ArrayList<KjywSyqdKsMx> kjywSyqdKsMxes = new ArrayList<>();
        for (Object o : list) {
            KjywSyqdKsMx kjywSyqdKsMx1 = JSONObject.parseObject(JSONObject.toJSONString(o), KjywSyqdKsMx.class);
            kjywSyqdKsMxes.add(kjywSyqdKsMx1);
        }
        //实体化对象
        ExcelUtils.getExcelUtils().writeExcel(path,kjywSyqdKsMx,kjywSyqdKsMxes);
        System.out.println("明细写入成功:"+path);
    }

    @Test
    public void dzpj(){
        List list = oracleService.selectAll(Sql.getDzpjzb());
        System.out.println(JSONObject.toJSONString(list));
    }

}
