package com.yin.utils.excel.main;

import cn.hutool.extra.pinyin.PinyinUtil;
import com.alibaba.fastjson2.JSONObject;
import com.yin.pojo.gzb1;
import com.yin.utils.excel.ExcelUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class FourExport {
    public static void main(String[] args) throws IOException {
        String path="D:\\公司\\合理用药药品上传(2).xlsx";
//        LinkedList excelTitle = ExcelUtils.getExcelUtils().getExcelTitle(path, 0, 0);
//        ExcelUtils.getExcelUtils().generateClassZ("gzb1", excelTitle,"D:\\cs\\ssxjg\\myDemo\\src\\main\\java\\com\\yin\\pojo\\");
        gzb1 fourExpo = new gzb1();
        List list = ExcelUtils.getExcelUtils().readExcel(path,fourExpo,0);
        ArrayList<gzb1> fourExports = new ArrayList<>();
        list.forEach(item->{
            gzb1 fourExport = JSONObject.parseObject(JSONObject.toJSONString(item), gzb1.class);
            if(fourExport.getDrugName()!=null){
                String firstLetter = PinyinUtil.getFirstLetter(fourExport.getDrugName(), "");
                fourExport.setDrugNameFirstSpell(firstLetter);
            }
            fourExports.add(fourExport);
        });
        ExcelUtils.getExcelUtils().writeExcel("D:\\公司\\1.xlsx",new FourExport(),fourExports);
    }
}
