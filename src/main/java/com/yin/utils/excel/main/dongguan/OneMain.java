package com.yin.utils.excel.main.dongguan;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson2.JSONObject;
import com.yin.MyDemoApplication;
import com.yin.service.impl.DgHisOracleServiceImpl;
import com.yin.utils.ConvertAllUtils;
import com.yin.utils.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 *@author yin
 *@date 2025-07-06
 *@Description
 */
@SpringBootTest(classes = MyDemoApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
public class OneMain {
    @Resource
    private DgHisOracleServiceImpl dgHisOracleServiceImpl;

    @Test
    public void test() throws Exception {
        JSONObject jsonObject2 = JSONObject.parseObject("{\"TMPL_NAME\":\"上午\",\"AM_LIMIT_NUM\":\"7\",\"PM_END_TIME\":\"17:00\",\"AM_START_TIME\":\"08:00\",\"UPDATE_BY\":\"\",\"DEL_FLAG\":\"\",\"PM_START_TIME\":\"14:30\",\"UPDATE_TIME\":\"\",\"PM_LIMIT_NUM\":\"0\",\"AM_END_TIME\":\"11:30\",\"CLINIC_ID\":\"1201\",\"CREATE_BY\":\"8888\",\"DOCTOR_ID\":\"\",\"CREATE_TIME\":\"\",\"TMPL_ID\":\"\",\"SXW\":\"\",\"FY_ID\":\"1001\",\"FB_ID\":\"00\",\"opter\":\"8888\",\"oper\":\"8888\",\"operId\":\"8888\",\"operName\":\"超级管理员\",\"client_uid\":\"eyJjbGllbnRfaWQiOiIwZmZkejkzdXNsNGY1YzB0cmJyazhkNzg4M3NidXpicXFmbnh1cG93eTk4Mmw4N2t2byIsImNsaWVudF9zb2NrZXQiOiIxMC4zNS4xLjI3OjgwODgiLCJzZXJ2ZXJfc29ja2V0IjoiMTAuMzUuMS4yNzo4MDg4In0=\"}");
//        System.out.println(jsonObject2);
        String ks = "种植门诊";
        String zsid = "120103";
        List list = dgHisOracleServiceImpl.selectAll(sql1(ks));
        for (Object o : list) {
            JSONObject jsonObject = ConvertAllUtils.convertJson(o);
//            System.out.println(jsonObject);
            String bc = jsonObject.get("班次").toString();
            String sl = jsonObject.get("A").toString();
            String s = sql2(ks, bc);
            List list1 = dgHisOracleServiceImpl.selectAll(s);
            int i = 0;
            for (Object o1 : list1) {
                i++;
                JSONObject jsonObject1 = ConvertAllUtils.convertJson(o1);
//                System.out.println(jsonObject1);
                String sd = jsonObject1.get("时段").toString();
//                System.out.println(sd);
                if (Integer.parseInt(sl) > 1) {
                    if (sd.equals("上午")) {
                        jsonObject2.put("TMPL_NAME", jsonObject1.get("班次"));
                        jsonObject2.put("AM_LIMIT_NUM", jsonObject1.get("CS"));
                        jsonObject2.put("AM_START_TIME", jsonObject1.get("KSSJ"));
                        jsonObject2.put("AM_END_TIME", jsonObject1.get("JSSJ"));
                    } else if (sd.equals("下午")) {
                        jsonObject2.put("PM_LIMIT_NUM", jsonObject1.get("CS"));
                        jsonObject2.put("PM_START_TIME", jsonObject1.get("KSSJ"));
                        jsonObject2.put("PM_END_TIME", jsonObject1.get("JSSJ"));
                    }
                } else {
                    if(sd.equals("上午")){
                        jsonObject2.put("AM_LIMIT_NUM", jsonObject1.get("CS"));
                        jsonObject2.put("AM_START_TIME", jsonObject1.get("KSSJ"));
                        jsonObject2.put("AM_END_TIME", jsonObject1.get("JSSJ"));
                        jsonObject2.put("PM_LIMIT_NUM", "0");
                        jsonObject2.put("PM_START_TIME", "12:00");
                        jsonObject2.put("PM_END_TIME", "17:30");
                    }else if(sd.equals("下午")){
                        jsonObject2.put("AM_LIMIT_NUM", "0");
                        jsonObject2.put("AM_START_TIME","08:00");
                        jsonObject2.put("AM_END_TIME","12:00");
                        jsonObject2.put("PM_LIMIT_NUM", jsonObject1.get("CS"));
                        jsonObject2.put("PM_START_TIME", jsonObject1.get("KSSJ"));
                        jsonObject2.put("PM_END_TIME",  jsonObject1.get("JSSJ"));
                    }
                }
                jsonObject2.put("CLINIC_ID", zsid);
                jsonObject2.put("TMPL_NAME", jsonObject1.get("班次"));
                if (i == Integer.parseInt(sl)) {
                    System.out.println(jsonObject2);
                    i = 0;
                }
            }
        }
    }

    @Test
    public void test2(){
        System.out.printf("123456");
    }

    public String sql1(String ks) {
        String sql = "SELECT \n" +
                "班次,COUNT(1) A\n" +
                "FROM (\n" +
                "SELECT a.班次,a.时段,MIN(TO_CHAR(a.开始时间,'HH24:MI')) KSSJ,MAX(TO_CHAR(a.结束时间,'HH24:MI')) JSSJ FROM \n" +
                "global_temp_table_nameY a\n" +
                "where a.科室 ='" + ks + "'\n" +
                "GROUP BY a.班次,a.时段\n" +
                ")\n" +
                "GROUP BY 班次\n" +
                "ORDER BY 班次";
        return sql;
    }

    public String sql2(String ks, String pc) {
        String sql = "SELECT a.班次,a.时段,MIN(TO_CHAR(a.开始时间,'HH24:MI')) KSSJ,MAX(TO_CHAR(a.结束时间,'HH24:MI')) JSSJ,COUNT(1) CS FROM \n" +
                "global_temp_table_nameY a\n" +
                "where a.科室 ='" + ks + "'\n" +
                " AND a.班次 ='" + pc + "'" +
                "GROUP BY a.班次,a.时段\n" +
                "ORDER BY A.班次";
        return sql;
    }
}
