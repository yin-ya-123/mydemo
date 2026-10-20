package com.yin.utils.export;

/**
 * @author yin
 * @date 2025-02-25
 * @Description
 */
public class Sql {

    public static String getZyCost(String beginDate, String endDate){
        return "SELECT * FROM \n" +
                "(SELECT mx.jslsh_id 结算单据号,\n" +
                "       rownum 项目序号,\n" +
                "       mi.grbh as 个人编码,\n" +
                "       mx.zylsh_id 就医号,\n" +
                "       'H33011305117' 医疗机构编码,\n" +
                "       '杭州富春康复医院' 医疗机构名称,\n" +
                "       '1' 医保结算等级,\n" +
                "       mx.sq_ksid 开单科室编码,\n" +
                "       f_getbmmc(mx.sq_ksid) 开单科室名称,\n" +
                "       mx.sq_ysid 开单医师编码,\n" +
                "       f_getygxm(mx.sq_ysid) 开单医师姓名,\n" +
                "       lx.sflx_mc 费用类别,\n" +
                "       to_char(mx.sq_rq,'yyyy-mm-dd') 项目使用日期,\n" +
                "       decode(CYDY_FLAG, '1', '是', '否') 出院带药标识,\n" +
                "       mx.sfxm_id 医院项目编码,\n" +
                "       ml.yp_mc 医院项目名称,\n" +
                "       ml.XMDM 医保项目编码,\n" +
                "       ml.YB_MC 医保项目名称,\n" +
                "       nvl(ml.gg, '无') 规格,\n" +
                "       nvl(ml.jx_mc, '无') 剂型,\n" +
                "       nvl(nvl(mx.FYDWMC, ml.dw), '无') 计价单位,\n" +
                "       mx.sfxm_dj 单价,\n" +
                "       mx.sfxm_sl 数量,\n" +
                "       mx.sfxm_je 金额,\n" +
                "       0 拒付金额,\n" +
                "       nvl(mx.RET10,'0') 医保范围内金额,\n" +
                "       decode(mx.ret6, '1', '丙', '0', '甲', '乙') 支付类别,\n" +
                "       (to_number(mx.ret6) * 100 || '%') 报销比例\n" +
                "  FROM his.pat_inpat_order_cost mx\n" +
                "  join his.mi_trans_info mi\n" +
                "    on mx.zylsh_id = mi.jz_lsh and mx.jslsh_id=mi.js_lsh\n" +
                "  join dictmanage.dict_charge_type lx\n" +
                "    on mx.zysf_lx = lx.sflx_id\n" +
                "  join GZKBDML_MY ml\n" +
                "    on mx.sfxm_id = ml.YP_ID and mx.cd_id=ml.Yp_Cd_Id\n" +
                " where mi.js_rq > to_date('"+beginDate+"','yyyy-mm-dd hh24:mi:ss') " +
                " AND mi.js_rq < to_date('"+endDate+"','yyyy-mm-dd hh24:mi:ss')\n" +
                "   and mx.fy_status in ('2', '7') and mi.mistatus in ('PAY_OK', 'RECEDE_OK', 'MI_OK') --and mx.zylsh_id='4976' and ml.YP_MC like '%高危%'\n" +
                ")";
    }

}
