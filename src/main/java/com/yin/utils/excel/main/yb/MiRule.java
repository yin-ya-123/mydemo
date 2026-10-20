package com.yin.utils.excel.main.yb;

/**
 * @author yin
 * @date 2024-12-03
 * @Description 医保规则处理查询
 */
public class MiRule {

    public String getPatInptInfo(){
        return "SELECT * FROM his.pat_inpat_info br where br.status='5' AND BR.CY_JSRQ > TRUNC(SYSDATE)";
    }
    //获取患者费用信息
    public String getPatInfoCost(String zylsh,String ybdm){
        return "SELECT BR.ZYH 住院号,\n" +
                "       BR.zylsh 住院流水号,\n" +
                "       BR.ZYBQMC 病区,\n" +
                "       BR.PAT_NAME 姓名,\n" +
                "       BR.ry_rq 入院日期,\n" +
                "       BR.cy_rq 出院日期,\n" +
                "       '住院' 类型,\n" +
                "       mx.js_rq 结算日期,\n" +
                "       BR.XB 性别,\n" +
                "       BR.YBXZ 费用类型,\n" +
                "       ML.yp_mc 项目名称,\n" +
                "       --TRUNC(MX.SQ_RQ) 记账日期,\n" +
                "       MX.SFXM_DJ 单价,\n" +
                "       SUM(MX.SFXM_SL) 数量,\n" +
                "       SUM(MX.SSJE) 金额,\n" +
                "       --LISTAGG(D.PAT_NAME,'/') WITHIN GROUP (ORDER BY D.PAT_ID) --OVER (PARTITION BY D.PAT_NAME)\n" +
                "       ZD.ZD 疾病诊断\n" +
                "  FROM HIS.PAT_INPAT_ORDER_COST MX\n" +
                "  JOIN GZKBDML_MY ML\n" +
                "    ON MX.SFXM_ID = ML.YP_ID\n" +
                "   AND MX.CD_ID = ML.Yp_Cd_Id\n" +
                "  JOIN HIS.V_INPAT_INFO BR\n" +
                "    ON MX.ZYLSH_ID = BR.ZYLSH\n" +
                "  JOIN (SELECT ZD.ZYLSH,\n" +
                "               listagg(ZD.DZBL_CODE || '-' || ZD.DZBL_MC, ',') within group(order by ZD.CJSJ) zd\n" +
                "          FROM HIS.PAT_INPAT_DIAGNOSIS ZD\n" +
                "         GROUP BY ZD.ZYLSH) ZD\n" +
                "    ON BR.zylsh = ZD.ZYLSH\n" +
                " WHERE EXISTS (SELECT 1\n" +
                "          FROM HIS.MIPAT_INOUT_RECORD YBDJ\n" +
                "         WHERE YBDJ.ZYLSH = MX.ZYLSH_ID\n" +
                "           AND YBDJ.ZF_FLAG = '0') --去除自费患者\n" +
                "   AND NVL(MX.YBQZZF, '0') != '1' --去除强制医保自费的\n" +
                "   AND MX.FY_STATUS IN ('2', '7')\n" +
                "   AND MX.ZYLSH_ID ='8835'\n" +
                " GROUP BY BR.ZYH,\n" +
                "          BR.zylsh,\n" +
                "          BR.ZYBQMC,\n" +
                "          BR.PAT_NAME,\n" +
                "          BR.ry_rq,\n" +
                "          BR.cy_rq,\n" +
                "          mx.js_rq,\n" +
                "          BR.XB,\n" +
                "          BR.YBXZ,\n" +
                "          ML.yp_mc,\n" +
                "          --TRUNC(MX.SQ_RQ),\n" +
                "          MX.SFXM_DJ,\n" +
                "          ZD.ZD\n" +
                "HAVING SUM(SSJE) <> 0\n";
    }
}
