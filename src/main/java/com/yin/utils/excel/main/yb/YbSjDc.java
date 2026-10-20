package com.yin.utils.excel.main.yb;

import com.alibaba.fastjson2.JSONObject;
import com.yin.MyDemoApplication;
import com.yin.service.OracleService;
import com.yin.utils.ConvertAllUtils;
import com.yin.utils.excel.ExcelUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author yin
 * @date 2024-11-12
 * @Description
 */
@SpringBootTest(classes = MyDemoApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
@Component
public class YbSjDc {
    @Autowired
    private OracleService oracleService2;
    @Test
    public void test() throws IOException {
//        String pathPojo = "D:\\cs\\ssxjg\\myDemo\\src\\main\\java\\com\\yin\\pojo\\qt\\yb";
//        ExcelUtils excelUtils = ExcelUtils.getExcelUtils();
//        List list = oracleService2.selectAll(getSQL());
//        JSONObject jsonObject = ConvertAllUtils.convertJson(list.get(0));
//        LinkedList<String> strings = new LinkedList<>();
//        for (Map.Entry<String, Object> stringObjectEntry : jsonObject.entrySet()) {
//            strings.add(stringObjectEntry.getKey());
//        }
//        excelUtils.generateClassZ("Zymxb",strings,pathPojo);

    }

    public String getSQL(){
        return "SELECT \n" +
                "ZYBR.pat_name 姓名,\n" +
                "ZYBR.xb 性别,\n" +
                "ZYBR.sfz_hm 身份证号,\n" +
                "ZYPJ.JS_RQ 结算时间,\n" +
                "ZYPJ.JS_RQ 发生时间,\n" +
                "ZYBR.ybxz 费用类别,\n" +
                "ML.XMDM 项目编码,\n" +
                "ML.YP_MC 名称,\n" +
                "ML.GG 规格,\n" +
                "NVL(ML.dw,F_GETYPDW(MX.SFXM_ID,MX.CD_ID,'3')) 单位, \n" +
                "CASE WHEN MI.JS_LSH IS NULL THEN '丙' ELSE \n" +
                "  CASE WHEN MX.RET_YBFL='01' THEN '甲'\n" +
                "  WHEN MX.RET_YBFL ='02' THEN '乙' \n" +
                "    WHEN MX.RET_YBFL ='03' THEN '丙' ELSE '丙' END END 医保等级,\n" +
                "MX.TS 贴数,\n" +
                "MX.RET6 自付比例,\n" +
                "'' 列支金额,\n" +
                "MX.RET7 自理自费金额,\n" +
                "YS.XM 医生姓名,\n" +
                "BM.BM_MC 科室,\n" +
                "(\n" +
                "SELECT TO_CHAR(WM_CONCAT(DISTINCT czd.Jb_Mc)) ZDXX FROM\n" +
                "his.pat_inpat_diagnosis zd2\n" +
                "INNER JOIN dictmanage.dict_icd czd ON ZD2.ZD_ICD=CZD.ICD_CODE\n" +
                "WHERE ZD2.ZYLSH=ZYBR.ZYLSH AND ZYPJ.ZYLSH=ZD2.ZYLSH\n" +
                ") 疾病信息\n" +
                "  FROM his.inpat_settle_invoice ZYPJ\n" +
                "  JOIN HIS.PAT_INPAT_ORDER_COST MX ON ZYPJ.JSLSH_ID=MX.JSLSH_ID AND ZYPJ.ZYLSH=MX.ZYLSH_ID\n" +
                "  left join mi_trans_info mi\n" +
                "    on ZYPJ.JSLSH_ID = mi.js_lsh\n" +
                "   and mi.mistatus in ('PAY_OK', 'RECEDE_OK', 'MI_OK')\n" +
                "    JOIN GZKBDML_MY ML ON MX.SFXM_ID=ML.YP_ID AND MX.Cd_Id=ML.Yp_Cd_Id\n" +
                "  JOIN HIS.V_INPAT_INFO ZYBR ON ZYPJ.ZYLSH=ZYBR.zylsh\n" +
                "  JOIN DICTMANAGE.DICT_EMPE YS ON MX.SQ_YSID=YS.KEYNO\n" +
                "  JOIN DICTMANAGE.DICT_DEPART BM ON MX.SQ_KSID=BM.BM_ID\n" +
                " WHERE \n" +
                " ((ZYPJ.zf_flag = '0' AND ZYPJ.js_zje > 0 AND ZYPJ.cjr <> '99999999') OR\n" +
                "       (ZYPJ.zf_flag = '1' and ZYPJ.js_zje > 0 and ZYPJ.cjr <> '99999999'))\n" +
                "       AND MX.FY_STATUS IN ('2','7')\n" +
                "       AND ZYPJ.JS_RQ BETWEEN to_date('2019-01-01 00:00:00', 'yyyy-mm-dd hh24:mi:ss') AND to_date('2023-12-21 23:59:59', 'yyyy-mm-dd hh24:mi:ss')  \n" +
                " AND ZYPJ.JSLSH_ID='1567601'";
    }
}
