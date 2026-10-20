package com.yin.utils.mytest;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.yin.MyDemoApplication;
import com.yin.pojo.qt.Ml;
import com.yin.service.OracleService;
import com.yin.utils.FileUtils;
import com.yin.utils.HttpUtils;
import com.yin.utils.excel.ExcelUtils;
import com.yin.utils.excel.sql.Sql;
import okhttp3.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 *
 * @Auther: YIN
 * @DescriPtion:
 * @Date:2024-02-01/02/01/13:23
 */
@SpringBootTest(classes = MyDemoApplication.class)
@RunWith(SpringRunner.class)
public class Test2 {
    @Autowired
    private OracleService oracleService;

    @Test
    public void show() {
        List list = oracleService.selectAll(Sql.getmlsql());
        List<Ml> mls = new ArrayList<>();
        for (Object o : list) {
            String jsonString = JSONObject.toJSONString(o);
            JSONObject jsonObject = JSONObject.parseObject(jsonString);
            String[] split = jsonObject.get("对应项目编码").toString().split("、");
            for (String s : split) {
                Ml ml = new Ml();
//                ml.setXmbm(jsonObject.get("项目编码").toString());
//                ml.setXmmc(jsonObject.get("项目名称").toString());
//                ml.setDyxmbm(s);
//                ml.setDyxmmc("");
//                ml.setLy(jsonObject.get("来源").toString());
                mls.add(ml);
            }
            Ml ml = new Ml();
            ExcelUtils.getExcelUtils().writeExcel("F:\\1.xlsx", ml, mls);
        }
    }


    @Test
    public void show1() throws IOException {
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("type","1");
        try (Response response = HttpUtils.getHttpUtils().httpPost("https://cnapi.ilive.cn/cool/search/topsearch", null, paramsMap)) {
            if (response.body() != null) {
                System.out.println(response.body().string());
            }
        }
    }

}
