package com.yin.utils.excel.main.kangfu.sql;

/**
 * @author yin
 * @date 2025-03-04
 * @Description 物资信息查询sql
 */
public class WlSql {
    /**
     * 查询物资信息
     * @param wz_bh
     * @return
     */
    public static String getWlSql(String wz_bh){
        return "SELECT br.当前病区,\n" +
                "       当前病区id,\n" +
                "       br.ZYLSH,\n" +
                "       br.住院号,\n" +
                "       br.病人姓名,\n" +
                "       br.STATUS,\n" +
                "       br.床位号,\n" +
                "       BR.入院日期,\n" +
                "       BR.出院日期,\n" +
                "       BR.结算日期,\n" +
                "       W.WZ_MC 物资名称,\n" +
                "       W.WZ_GG 规格,\n" +
                "       CD.WZ_BH,\n" +
                "       CD.WZ_ID,\n" +
                "       CD.WZ_CD_ID,\n" +
                "       mx.medcodg,trunc(mx.sq_rq) 记账日期,mx.zyfymx_id ZYFYMXID,\n" +
                "       SUM(MX.SFXM_SL) SL\n" +
                "  FROM his.pat_inpat_order_cost mx\n" +
                "  LEFT join his.v_inpat_info_my br\n" +
                "    on mx.zylsh_id = br.ZYLSH\n" +
                "  LEFT join DICTMANAGE.Materials_Info w\n" +
                "    on mx.sfxm_id = w.wz_id\n" +
                "  LEFT join DICTMANAGE.Materials_Area_List cd\n" +
                "    on w.wz_id = cd.wz_id\n" +
                "   and mx.cd_id = cd.wz_cd_id\n" +
                "   and mx.sfxm_id = cd.wz_id\n" +
                "  LEFT JOIN his.v_hosp_directory ML ON CD.WZ_ID=ML.yyxmdm AND CD.WZ_CD_ID=ML.yyxmcddm \n" +
                " where \n" +
                " EXISTS (SELECT 1\n" +
                "          FROM HIS.MIPAT_INOUT_RECORD YBDJ\n" +
                "         WHERE YBDJ.ZYLSH = MX.ZYLSH_ID\n" +
                "           AND YBDJ.ZF_FLAG = '0') --去除自费患者\n" +
                "   AND NVL(MX.YBQZZF, '0') != '1' --去除强制医保自费的\n" +
                "   AND CD.WZ_BH = '"+wz_bh+"'\n" +
                "   AND BR.出院日期 > '2025-01-01 00:00:00'\n" +
                "   AND MX.FY_STATUS IN ('2', '7') and mx.medcodg is not null\n" +
                " GROUP BY br.ZYLSH,mx.medcodg,br.当前病区,\n" +
                "          当前病区id,\n" +
                "          br.住院号,\n" +
                "          br.病人姓名,\n" +
                "          br.STATUS,\n" +
                "          br.床位号,\n" +
                "          W.WZ_MC,\n" +
                "          W.WZ_GG,\n" +
                "          CD.WZ_BH,\n" +
                "          BR.入院日期,\n" +
                "          BR.出院日期,\n" +
                "          BR.结算日期,\n" +
                "          CD.WZ_ID,\n" +
                "          CD.WZ_CD_ID,trunc(mx.sq_rq),mx.zyfymx_id\n";
    }

    /**
     * 查询医保上传的日志信息
     * @return
     */
    public static String getWlSqlForMiInfo(String pat_lsh,String sendinfo,String zyfymxid){
        return "SELECT * \n" +
                "FROM his.mi_tradelog c\n" +
                " where c.jyh='2301' \n" +
                " AND C.PAT_LSH='"+pat_lsh+"' \n" +
                " AND C.SENDINFO LIKE '%"+sendinfo+"%'\n" +
                " AND C.SENDINFO LIKE '%"+zyfymxid+"%'\n" +
                " ORDER BY C.IN_TIME ASC";
    }
}
