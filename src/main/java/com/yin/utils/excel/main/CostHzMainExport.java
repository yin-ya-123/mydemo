package com.yin.utils.excel.main;

import com.alibaba.fastjson2.JSONObject;
import com.yin.MyDemoApplication;
import com.yin.common.ApplicationUtils;
import com.yin.pojo.qt.CostHzMainExportPojo;
import com.yin.pojo.qt.yb.DrgYcFzPojo;
import com.yin.service.OracleService;
import com.yin.utils.ConvertAllUtils;
import com.yin.utils.excel.ExcelUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 汇总明细费用汇总匹配导出
 */
@SpringBootTest(classes = MyDemoApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
public class CostHzMainExport {
    @Resource
    private OracleService hisOracleServiceImpl;

    @Test
    public void exportDrg() throws IOException {
        String path = "D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\2025-02-11\\202501预测分组0211（2.0）(1).xlsx";
        String savePath = "D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\2025-02-11\\202501预测分组0211（2.0）-匹配.xlsx";
        ExcelUtils<DrgYcFzPojo> drgYcFzPojoExcelUtils = ExcelUtils.of(DrgYcFzPojo.class);
        List<DrgYcFzPojo> drgYcFzPojos = drgYcFzPojoExcelUtils.readExcel(path, 1);
        for (DrgYcFzPojo drgYcFzPojo : drgYcFzPojos) {
            String sqlPatInfo = getSqlPatInfo(drgYcFzPojo.getXm(), drgYcFzPojo.getRysj(), drgYcFzPojo.getCysj());
            List list = hisOracleServiceImpl.selectAll(sqlPatInfo);
            if (list.size() > 0) {
                ArrayList<CostHzMainExportPojo> costHzMainExportPojos = ConvertAllUtils.convertListAll(list, CostHzMainExportPojo.class);
                CostHzMainExportPojo costHzMainExportPojo = costHzMainExportPojos.get(0);
                System.out.println(drgYcFzPojo.getXm()+costHzMainExportPojo.get住院流水号());
                //费用信息
                String sql2 = getSql2(costHzMainExportPojo.get住院流水号());
                //本年度之类的信息
                String sql3 = getsql11(costHzMainExportPojo.get住院流水号());
                List list1 = hisOracleServiceImpl.selectAll(sql2);
                List list2 = hisOracleServiceImpl.selectAll(sql3);
                JSONObject jsonObject = ConvertAllUtils.convertJson(list1.get(0));
                JSONObject jsonObject2 = ConvertAllUtils.convertJson(list2.get(0));
                String bndzylj = jsonObject2.get("本年度住院累计").toString();
                String hzsfzy = "";
                String bchxjfz = "";
                if (jsonObject2.get("患者是否在院") != null) {
                    hzsfzy = jsonObject2.get("患者是否在院").toString();
                }
                if (jsonObject2.get("本次呼吸机辅助天数") != null) {
                    bchxjfz = jsonObject2.get("本次呼吸机辅助天数").toString();
                }
                String jyf = jsonObject.get("JYF").toString();
                String YFHJ = jsonObject.get("YFHJ").toString();
                String qzgyy = jsonObject.get("QZGYY").toString();
                String qzkfzl = jsonObject.get("QZKFZL").toString();
                drgYcFzPojo.setSfzy(hzsfzy);
                drgYcFzPojo.setHxjts(bchxjfz);
                drgYcFzPojo.setNdljts(bndzylj);
                drgYcFzPojo.setGyy(qzgyy);
                drgYcFzPojo.setKffy(qzkfzl);
            }
        }
        drgYcFzPojoExcelUtils.writeExcel(savePath, drgYcFzPojos);
    }

    @Test
    public void mainEx() {
        ExcelUtils excelUtils = ExcelUtils.getExcelUtils();
        String path = "D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\2025-02-11\\202501预测分组0211（2.0）(1).xlsx";
        List list = hisOracleServiceImpl.selectAll(getSql1());
        ArrayList<CostHzMainExportPojo> costHzMainExportPojos = ConvertAllUtils.convertListAll(list, CostHzMainExportPojo.class);
        for (CostHzMainExportPojo costHzMainExportPojo : costHzMainExportPojos) {
            if (costHzMainExportPojo.get住院流水号() != null && costHzMainExportPojo.get住院流水号() != "") {
                String sql2 = getSql2(costHzMainExportPojo.get住院流水号());
                String sql3 = getsql11(costHzMainExportPojo.get住院流水号());
                List list1 = hisOracleServiceImpl.selectAll(sql2);
                List list2 = hisOracleServiceImpl.selectAll(sql3);
                JSONObject jsonObject = ConvertAllUtils.convertJson(list1.get(0));
                JSONObject jsonObject2 = ConvertAllUtils.convertJson(list2.get(0));
                String string = jsonObject2.get("本年度住院累计").toString();
                String string1 = "";
                String string2 = "";
                if (jsonObject2.get("患者是否在院") != null) {
                    string1 = jsonObject2.get("患者是否在院").toString();
                }
                if (jsonObject2.get("本次呼吸机辅助天数") != null) {
                    string2 = jsonObject2.get("本次呼吸机辅助天数").toString();
                }
                String jyf = jsonObject.get("JYF").toString();
                String YFHJ = jsonObject.get("YFHJ").toString();
                String qzgyy = jsonObject.get("QZGYY").toString();
                String qzkfzl = jsonObject.get("QZKFZL").toString();
                costHzMainExportPojo.set本年度住院累计(string);
                costHzMainExportPojo.set患者是否在院(string1);
                costHzMainExportPojo.set本次呼吸机辅助天数(string2);
                costHzMainExportPojo.set药品费(YFHJ);
                costHzMainExportPojo.set检验(jyf);
                costHzMainExportPojo.set其中高压氧(qzgyy);
                costHzMainExportPojo.set其中康复治疗(qzkfzl);
                log.info("{}已匹配", costHzMainExportPojo.get姓名());
            }
        }
        String str = "edt_operationonevolume";
        String str2 = "edt_operationonecases";
        String str3 = "edt_operationonevolumeavg";
        excelUtils.writeExcel(path, new CostHzMainExportPojo(), costHzMainExportPojos);

    }

    public String getSqlPatInfo(String xm, String kssj, String jssj) {
        String sql = "SELECT b.zylsh 住院流水号 FROM his.v_inpat_info b " +
                "where b.pat_name = '" + xm + "' " +
                "and substr(to_char(b.ry_rq,'yyyy-mm-dd hh24:mi:ss'),0,10) = substr('" + kssj + "',0,10) " +
                "and substr('" + jssj + "',0,10)=substr(to_char(b.cy_rq,'yyyy-mm-dd hh24:mi:ss'),0,10)";
        return sql;
    }

    public String getSql1() {
        String sql = "SELECT A.*,B.住院流水号 FROM \n" +
                "global_temp_table_name a \n" +
                "LEFT JOIN  his.v_inpat_info_my B ON a.姓名=b.病人姓名 and substr(a.入院时间,0,10)=substr(b.入院日期,0,10) and substr(a.出院时间,0,10)=substr(b.出院日期,0,10)\n" +
                "where a.序号 IS NOT NULL\n";
        return sql;
    }

    public String getsql11(String zylsh) {
        String sql = "SELECT * FROM his.v_inpat_info_my b where b.住院流水号='" + zylsh + "' ";
        return sql;
    }

    public String getSql2(String zylsh) {
        String sql = "select c.zylsh_id\n" +
                ",sum(c.ssje) zfy                                                                                             \n" +
                ",sum(c.ret2-(case when c.sfxm_id in (select zl.zlxm_id from dictmanage.dict_zl_pro zl where zl.zlxm_mc like '%床位费%') then c.ret8 else '0' end)) ybnje                                                                                          --总费用\n" +
                ",sum(case when c.zysf_lx in ('01','02','03') then c.ssje else 0 end) yfhj                                   \n" +
                ",sum(case when c.zysf_lx in ('01') then c.ssje else 0 end) qzxy                                              \n" +
                ",sum(case when c.zysf_lx in ('02') then c.ssje else 0 end) qzzcy                                             \n" +
                ",sum(case when c.zysf_lx in ('03') then c.ssje else 0 end) qzcy                                              \n" +
                ",sum(case when c.zysf_lx in ('01','02','03') and d.jky_flag='1' then c.ssje else 0 end) qzjky                \n" +
                ",sum(case when c.zysf_lx in ('110') then c.ssje else 0 end) jyf                                              \n" +
                ",sum(case when c.zysf_lx in ('36') then c.ssje else 0 end) ssf                                               \n" +
                ",sum(case when c.zysf_lx in ('44') then c.ssje else 0 end) syf                                               \n" +
                ",sum(case when c.zysf_lx in ('30') then c.ssje else 0 end) clf                                               \n" +
                ",sum(case when c.zysf_lx in ('43') then c.ssje else 0 end) sxf                                               \n" +
                ",sum(case when c.zysf_lx in ('05','31','32','33') then c.ssje else 0 end) jcf                                \n" +
                ",sum(case when c.zysf_lx in ('04') then c.ssje else 0 end) cwf                                               \n" +
                ",sum(case when c.zysf_lx in ('109','112','114','17','20') then c.ssje else 0 end) zlf                        \n" +
                ",sum(case when c.zysf_lx in ('109','112','114','17','20') and c.zx_ksid in ('KS32') then c.ssje else 0 end) qzgyy             \n" +
                ",sum(case when c.zysf_lx in ('109','112','114','17','20') and c.zx_ksid in ('KS09') then c.ssje else 0 end) qzkfzl            \n" +
                ",sum(case when c.zysf_lx in ('20')   then c.ssje else 0 end) qzzyzl                           \n" +
                ",sum(case when c.zysf_lx in ('39') then c.ssje else 0 end) mzf                                               \n" +
                ",sum(case when c.zysf_lx in ('40') then c.ssje else 0 end) jhf                                               \n" +
                ",sum(case when c.zysf_lx in ('46') then c.ssje else 0 end) hlf                                              \n" +
                ",sum(case when c.zysf_lx in ('49') then c.ssje else 0 end) zlf2                                              \n" +
                ",sum(case when c.zysf_lx in ('47') then c.ssje else 0 end) qtf                                               \n" +
                ",sum(case when c.zysf_lx in ('47') and c.zx_ksid in ('KS') then c.ssje else 0 end) qzyyk                     \n" +
                ",sum(case when c.zysf_lx in ('111') then c.ssje else 0 end) zcf                                              \n" +
                ",sum(case when c.zysf_lx in ('90')  then c.ssje else 0 end) hsbzf                                           \n" +
                "from pat_inpat_order_cost c \n" +
                "left join pat_inpat_info i on i.zylsh=c.zylsh_id\n" +
                "left join dictmanage.drug_info d on d.bz_bm=c.sfxm_id and c.jl_ly='3'\n" +
                "where 1=1 AND C.ZYLSH_ID='" + zylsh + "'\n" +
                "and c.fy_status in ('2', '7')\n" +
                "group by c.zylsh_id";
        return sql;
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
//        ExcelUtils excelUtils = ExcelUtils.getExcelUtils();
//        String path = "D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\2024-09-19\\202401-08DRGs预测分组0912-分析.xlsx";
//        String pojoPath = "D:\\cs\\ssxjg\\myDemo\\src\\main\\java\\com\\yin\\pojo\\qt";
//        LinkedList excelTitle = excelUtils.getExcelTitle(path);
//        excelUtils.generateClass("CostHzMainExportPojo2", excelTitle, pojoPath);
        String path = "D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\2025-02-11\\202501预测分组0211（2.0）(1).xlsx";
        ExcelUtils excelUtils = ExcelUtils.getExcelUtils();
        excelUtils.generateClassEnhanceZ("DrgYcFzPojo", ApplicationUtils.pathPojo, path);
    }

}
