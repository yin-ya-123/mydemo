package com.yin.utils.excel.NotPojoExcel;

import com.alibaba.fastjson2.JSONObject;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author yin
 * @date 2024-11-26
 * @Description
 */
@Data
public class SheetData {
    private List<String> header= new ArrayList<String>();

    private List<List<String>> datas=new ArrayList<>();


    public List<List<String>> toExcelHeader(){
        List<List<String>> headList =new ArrayList<>();
        for (String row : header) {
            ArrayList<String> h = new ArrayList<>();
            h.add(row);
            headList.add(h);
        }
        return headList;
    }

    public  List<JSONObject> toJsonData(){
        ArrayList<JSONObject> jsonObjects = new ArrayList<>();
        for (int i = 0; i < datas.size(); i++) {
            JSONObject jsonObject = new JSONObject();
            for (int i1 = 0; i1 < header.size(); i1++) {
                jsonObject.put(header.get(i1),datas.get(i).get(i1));
            }
            jsonObjects.add(jsonObject);
        }
        return jsonObjects;
    }
}
