package com.yin.utils.excel.main.image;

import com.alibaba.fastjson2.JSONObject;
import com.yin.MyDemoApplication;
import com.yin.pojo.Text1;
import com.yin.service.OracleService;
import com.yin.service.impl.HtOracleServiceImpl;
import com.yin.utils.ConvertAllUtils;
import com.yin.utils.Sql.SqlAssemble;
import com.yin.utils.bese64.Base64Utils;
import com.yin.utils.excel.ExcelUtils;
import com.yin.utils.mytest.Test1;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Or;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * @author yin
 * @date 2024-11-15
 * @Description
 */
@SpringBootTest(classes = MyDemoApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
public class One {
    @Resource
    private OracleService htOracleServiceImpl;
    @Test
    public void test() {
        String sql = SqlAssemble.getSqlAssemble()
                .select("USERMGMT.USER_X_SIGNATURE")
                .pageSel(1,10)
                .getSql();
        System.out.println(sql);
        List list = htOracleServiceImpl.selectAll(sql);
        ExcelUtils objectExcelUtils = new ExcelUtils<>();
        ArrayList<Text1> text1s = new ArrayList<>();
        for (Object o : list) {
            Text1 text1 = new Text1();
            JSONObject jsonObject = ConvertAllUtils.convertJson(o);
            String string = jsonObject.getString("SIGNATURE_PHOTO");
            try {
                text1.setByteArray(Base64Utils.base64ToByte(string));
                text1s.add(text1);
            }catch (Exception e){
                System.out.println("解析失败"+jsonObject.getString("USER_ID"));
            }
        }
        objectExcelUtils.writeExcel("E:\\文件信息保存\\2024-11-15\\1.xlsx",new Text1(),text1s);
    }
    public static void main(String[] args) {
//        ExcelUtils objectExcelUtils = new ExcelUtils<>();
//        ArrayList<Text1> text1s = new ArrayList<>();
//        Text1 text1 = new Text1();
//        byte[] imageBytes = Base64.getDecoder().decode(Base64Utils.s);
//        text1.setByteArray(imageBytes);
//        text1s.add(text1);
//        objectExcelUtils.writeExcel("E:\\文件信息保存\\2024-11-15\\1.xlsx",new Text1(),text1s);
    }
}
