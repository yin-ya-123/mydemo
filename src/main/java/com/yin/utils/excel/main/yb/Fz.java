package com.yin.utils.excel.main.yb;

import com.alibaba.fastjson2.JSONObject;
import com.yin.MyDemoApplication;
import com.yin.pojo.qt.yb.Fz2024;
import com.yin.pojo.qt.yb.FzOne;
import com.yin.pojo.qt.yb.FzTwo;
import com.yin.pojo.qt.yb.YcFz2024;
import com.yin.service.OracleService;
import com.yin.utils.ConvertAllUtils;
import com.yin.utils.excel.ExcelUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * @author yin
 * @date 2025-01-16
 * @Description 分组处理
 */
@SpringBootTest(classes = MyDemoApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
@Component
public class Fz {
    static String path1 = "E:\\文件信息保存\\2025-01-16\\新匹配\\one.xlsx";
    static String path3 = "E:\\文件信息保存\\2025-01-16\\新匹配\\one-新.xlsx";
    static String path2 = "E:\\文件信息保存\\2025-01-16\\新匹配\\two.xlsx";
    static String path4 = "E:\\文件信息保存\\2025-01-16\\新匹配\\two-新.xlsx";
    @Resource
    private OracleService oracleServiceImpl;

    String sql = "SELECT * FROM his.v_inpat_info_my where zylsh='in_zylsh'";

    public static void main(String[] args) throws IOException {
        generate();
    }

    @Test
    public void one() {
        ExcelUtils<FzOne> fzOneExcelUtils = new ExcelUtils<>();
        List<FzOne> fzOnes = fzOneExcelUtils.readExcel(path1, new FzOne(), 1);
        for (FzOne fzOne : fzOnes) {
            if (fzOne.getRyxm() != null && fzOne.getRysj() != null && fzOne.getCysj() != null) {
                String sql = "SELECT br.zylsh FROM his.v_inpat_info br where br.pat_name ='" + fzOne.getRyxm() + "' " +
                        "and to_char(br.ry_rq,'yyyy-mm-dd') = '" + fzOne.getRysj().substring(0, 10) + "'" +
                        " and to_char(br.cy_rq,'yyyy-mm-dd')='" + fzOne.getCysj().substring(0, 10) + "'";
                List list = oracleServiceImpl.selectAll(sql);
//            System.out.println(fzOne);
                if (!list.isEmpty()) {
                    JSONObject jsonObject = ConvertAllUtils.convertJson(list.get(0));
                    String zylsh = jsonObject.get("ZYLSH") != null ? jsonObject.get("ZYLSH").toString() : "";
                    String sql2 = "SELECT * FROM his.v_inpat_info_my where 住院流水号='" + zylsh + "'";
                    List list2 = oracleServiceImpl.selectAll(sql2);
                    JSONObject jsonObject2 = ConvertAllUtils.convertJson(list2.get(0));
                    String ndlj = jsonObject2.get("本年度住院累计") != null ? jsonObject2.get("本年度住院累计").toString() : "";
                    String hxj = jsonObject2.get("本次呼吸机辅助天数") != null ? jsonObject2.get("本次呼吸机辅助天数").toString() : "";
                    String sfzy = jsonObject2.get("患者是否在院") != null ? jsonObject2.get("患者是否在院").toString() : "";
                    String ks = jsonObject2.get("出院病区") != null ? jsonObject2.get("出院病区").toString() : "";
                    fzOne.setNdljts(ndlj);
                    fzOne.setBchxjts(hxj);
                    fzOne.setSfzy(sfzy);
                }
            }
        }
        fzOneExcelUtils.writeExcel(path3, new FzOne(), fzOnes);
    }

    @Test
    public void two() {
        ExcelUtils<FzTwo> fzOneExcelUtils = new ExcelUtils<>();
        List<FzTwo> fzTwos = fzOneExcelUtils.readExcel(path2, new FzTwo(), 1);
        for (FzTwo fzTwo : fzTwos) {
            if (fzTwo.getRyxm() != null && fzTwo.getRysj() != null && fzTwo.getCysj() != null) {
                String sql = "SELECT br.zylsh FROM his.v_inpat_info br where br.pat_name ='" + fzTwo.getRyxm() + "' " +
                        "and to_char(br.ry_rq,'yyyy-mm-dd') = '" + fzTwo.getRysj().substring(0, 10) + "'" +
                        " and to_char(br.cy_rq,'yyyy-mm-dd')='" + fzTwo.getCysj().substring(0, 10) + "'";
                List list = oracleServiceImpl.selectAll(sql);
                if (!list.isEmpty()) {
                    JSONObject jsonObject = ConvertAllUtils.convertJson(list.get(0));
                    String zylsh = jsonObject.get("ZYLSH") != null ? jsonObject.get("ZYLSH").toString() : "";
                    String sql2 = "SELECT * FROM his.v_inpat_info_my where 住院流水号='" + zylsh + "'";
                    List list2 = oracleServiceImpl.selectAll(sql2);
                    JSONObject jsonObject2 = ConvertAllUtils.convertJson(list2.get(0));
                    String ndlj = jsonObject2.get("本年度住院累计") != null ? jsonObject2.get("本年度住院累计").toString() : "";
                    String hxj = jsonObject2.get("本次呼吸机辅助天数") != null ? jsonObject2.get("本次呼吸机辅助天数").toString() : "";
                    String sfzy = jsonObject2.get("患者是否在院") != null ? jsonObject2.get("患者是否在院").toString() : "";
                    String ks = jsonObject2.get("出院病区") != null ? jsonObject2.get("出院病区").toString() : "";
                    fzTwo.setNdljts(ndlj);
                    fzTwo.setBchxjts(hxj);
                    fzTwo.setSfzy(sfzy);
                    fzTwo.setKs(ks);
                }
            }
        }
        fzOneExcelUtils.writeExcel(path4, new FzTwo(), fzTwos);
    }

    public static void generate() throws IOException {
        String pathPojo = "D:\\cs\\ssxjg\\myDemo\\src\\main\\java\\com\\yin\\pojo\\qt\\yb";
        ExcelUtils excelUtils = ExcelUtils.getExcelUtils();
        LinkedList<String> excelTitle = excelUtils.getExcelTitle(path1);
        LinkedList<String> excelTitle2 = excelUtils.getExcelTitle(path2);
        excelUtils.generateClassZ("FzOne", excelTitle, pathPojo);
        excelUtils.generateClassZ("FzTwo", excelTitle2, pathPojo);

    }
}
