package com.yin.utils.excel.main.pinghu;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.yin.pojo.BlSsxzPojo;
import com.yin.pojo.GlobalTempTableNameMb1;
import com.yin.utils.FileUtils;
import com.yin.utils.HttpUtils;
import com.yin.utils.NewHttpUtils;
import com.yin.utils.excel.ExcelUtils;
import com.yin.utils.excel.main.pinghu.entity.MainEntity;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        ExcelUtils<MainEntity> mainEntityExcelUtils =ExcelUtils.of(MainEntity.class);
        List<MainEntity> mainEntities = mainEntityExcelUtils.readExcel("E:\\文件信息\\2026-01-28\\未扫追溯码数据.xlsx", 1);
        for (MainEntity mainEntity : mainEntities) {
//            if (mainEntity.getCF_LSH_ID().equals("76949616663200006543")){
//                System.out.println(mainEntity);
//                getMainEntity(mainEntity);
//                System.out.println(mainEntity);
//            }
            System.out.println("起始:"+mainEntity);
            getMainEntity(mainEntity);
            System.out.println("结束:"+mainEntity);
        }
        String newPath ="E:\\文件信息\\2026-01-28\\追溯码导出.xlsx";
        mainEntityExcelUtils.writeExcel(newPath,mainEntities);
    }

    public static void getMainEntity(MainEntity entity) throws IOException, InterruptedException {
        Thread.sleep(100);
        String url ="http://10.173.96.132:31391/clientQuery?clientStartDate=1769443200000&clientEndDate=1769529540000&size=500&from=0 ";
        NewHttpUtils init = NewHttpUtils.init();
        String sendBody = "{\"query\":{\"bool\":{\"must\":[{\"query_string\":{\"query\":\"\\\""+entity.getCF_LSH_ID()+"\\\" AND \\\"DrugTraceCodeListWork\\\"\",\"default_field\":\"content\"}},{\"range\":{\"dtTime\":{\"gte\":1768955566000,\"lt\":1769560705000}}}]}},\"highlight\":{\"fields\":{\"content\":{\"fragment_size\":2147483647}}},\"sort\":[{\"dtTime\":\"desc\"},{\"seq\":\"desc\"}]}";
        JSONObject sendJson = JSONUtil.parseObj(sendBody);
        init.addHeaderChrome();
        init.addHeader("Content-Type","application/json");
        init.addHeader("host","10.173.96.132:31391");
        init.addHeader("Accept","*/*");
        init.addHeader("Accept-Encoding","gzip, deflate, br");
        init.addParamsStr(sendJson.toString());
        String responseStr = init.post(url).getResponseStr();
        System.out.println(responseStr);
        if (!responseStr.isEmpty()){
            ArrayList<String> zsm = getZsm(responseStr);
            // 去重并替换原列表
            ArrayList<String> newList = new ArrayList<>(new LinkedHashSet<>(zsm)); // 先清空，再添加去重后的元素
            entity.setZSM(String.join(",",newList));
        }
    }

    public  static ArrayList<String> getZsm(String info){
        if (!info.isEmpty()) {
            String hits = JSONUtil.parseObj(info).get("hits").toString();
            String hits1 = JSONUtil.parseObj(hits).get("hits").toString();
//        System.out.println(hits1);
            ArrayList<String> list = new ArrayList<>();
            JSONArray objects = JSONUtil.parseArray(hits1);
            for (Object object : objects) {
                JSONObject jsonObject = JSONUtil.parseObj(object.toString());
                String source = jsonObject.get("_source").toString();
                JSONObject jsonObject1 = JSONUtil.parseObj(source);
                String content = jsonObject1.get("content").toString();
                int i = content.indexOf(":");
                String content1 = content.substring(i + 1);
                if (JSONUtil.parseObj(content1).get("drugTracelist") != null) {
                    String drugTracelist = JSONUtil.parseObj(content1).get("drugTracelist").toString();
                    JSONArray objects1 = JSONUtil.parseArray(drugTracelist);
                    if (!objects1.isEmpty()) {
                        for (Object o : objects1) {
                            list.add(o.toString());
                        }
                    }
                }
            }
            return list;
        }
        return new ArrayList<>();
    }
}
