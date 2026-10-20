package com.yin.utils.excel.main.kangfu;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.yin.MyDemoApplication;
import com.yin.pojo.qt.kangfu.WzPojo;
import com.yin.service.OracleService;
import com.yin.utils.ConvertAllUtils;
import com.yin.utils.FileUtils;
import com.yin.utils.excel.ExcelUtils;
import com.yin.utils.excel.main.kangfu.sql.WlSql;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yin
 * @date 2025-03-04
 * @Description 物资信息查询
 */
@SpringBootTest(classes = MyDemoApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
public class WzApp {

    @Autowired
    private OracleService hisOracleServiceImpl;
    @Test
    public void main() {
        log.info("物资信息查询");
        String wlSql = WlSql.getWlSql("WL41112");
        List list = hisOracleServiceImpl.selectAll(wlSql);
        String saveFile = "E:\\文件信息保存\\2025-03-06\\WL41112.xlsx";
        FileUtils.createFile(saveFile);
        ExcelUtils<WzPojo> wzPojoExcelUtils = ExcelUtils.of(WzPojo.class);
        ArrayList<WzPojo> wzPojos = new ArrayList<>();
        for (Object o : list) {
            JSONObject jsonObject = JSONUtil.parseObj(o);
            System.out.println(jsonObject);
            String zylsh = jsonObject.get("ZYLSH").toString();
            String brxm = jsonObject.get("病人姓名").toString();
            String rysj = jsonObject.get("入院日期").toString();
            String cysj = jsonObject.get("出院日期").toString();
            String jssj = jsonObject.get("结算日期").toString();
            String zyh = jsonObject.get("住院号").toString();
            String dqbq = jsonObject.get("当前病区").toString();
            String wzmc = jsonObject.get("物资名称").toString();
            String gg = jsonObject.get("规格").toString();
            String wzBh = jsonObject.get("WZ_BH").toString();
            String wzId = jsonObject.get("WZ_ID").toString();
            String wzCdId = jsonObject.get("WZ_CD_ID").toString();
            String medcodg = jsonObject.get("MEDCODG").toString();
            String jzrq = jsonObject.get("记账日期").toString();
            String zyfymxid = jsonObject.get("ZYFYMXID").toString();
            String sl = jsonObject.get("SL").toString();
            String wlSqlForMiInfo = WlSql.getWlSqlForMiInfo(zylsh, medcodg,zyfymxid);
            List list1 = hisOracleServiceImpl.selectAll(wlSqlForMiInfo);
            for (Object o1 : list1) {
                Object o4 = JSONUtil.parseObj(o1).get("SENDINFO");
                String intime = JSONUtil.parseObj(o4).get("inf_time").toString();
                Object o2 = JSONUtil.parseObj(o4).get("input");
                Object o3 = JSONUtil.parseObj(o2).get("feedetail");
                JSONArray objects = JSONUtil.parseArray(JSONUtil.toJsonStr(o3));
                for (Object object : objects) {
                    JSONObject jsonObject1 = JSONUtil.parseObj(object);
                    String medListCodg = jsonObject1.get("med_list_codg").toString();
                    String feedetlSn = jsonObject1.get("feedetl_sn").toString();
                    String memo = jsonObject1.get("memo").toString();
                    if(medListCodg.equals(medcodg)){
                        WzPojo wzPojo = new WzPojo();
                        wzPojo.setName(brxm);
                        wzPojo.setKs(dqbq);
                        wzPojo.setWzmc(wzmc);
                        wzPojo.setGg(gg);
                        wzPojo.setBm(wzBh);
                        wzPojo.setOne1(medListCodg);
                        wzPojo.setOne2(memo);
                        wzPojo.setScsj(intime);
                        wzPojo.setFyid(feedetlSn);
                        wzPojo.setZyh(zyh);
                        wzPojo.setJssj(jssj);
                        wzPojo.setRysj(rysj);
                        wzPojo.setCysj(cysj);
                        wzPojo.setZylsh(zylsh);
                        wzPojo.setJzrq(jzrq);
                        wzPojo.setFyid1(zyfymxid);
                        wzPojos.add(wzPojo);
                    }
                }
            }
            wzPojoExcelUtils.writeExcel(saveFile,wzPojos);
        }
    }
}
