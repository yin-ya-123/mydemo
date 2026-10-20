package com.yin.utils.excel.main.kangfu.sql;

/**
 * @author yin
 * @date 2025-02-19
 * @Description 自查自纠需要sql
 */
public class ZcZj2025Sql {

    public static String getPatZdxx(String zylsh) {
        return "SELECT ZD.ZYLSH,\n" +
                "       listagg(ZD.DZBL_CODE || '-' || ZD.DZBL_MC, ',') within group(order by ZD.CJSJ) zd\n" +
                "  FROM HIS.PAT_INPAT_DIAGNOSIS ZD\n" +
                " WHERE ZD.ZYLSH in (SELECT * FROM  table(fun_row_split('" + zylsh + "')))\n" +
                " GROUP BY ZD.ZYLSH";
    }

    public static String getPatZdxxAll(){
        return "SELECT ZD.ZYLSH,\n" +
                "       listagg(ZD.DZBL_CODE || '-' || ZD.DZBL_MC, ',') within group(order by ZD.CJSJ) zd\n" +
                "  FROM \n" +
                "  HIS.PAT_INPAT_DIAGNOSIS ZD\n" +
                "  JOIN HIS.PAT_INPAT_INFO BR ON BR.ZYLSH=ZD.ZYLSH\n" +
                " WHERE BR.CY_JSRQ BETWEEN to_date('2023-01-01 00:00:00', 'yyyy-mm-dd hh24:mi:ss') AND to_date('2024-12-31 23:59:59', 'yyyy-mm-dd hh24:mi:ss') \n" +
                " GROUP BY ZD.ZYLSH";
    }

    public static String getPatYbxx(String zylsh) {
        return "SELECT mi.jz_lsh ZYLSH,mi.mistatus,MI.FY_ZE 总金额,\n" +
                "       MI.YB_FY 医保统筹金额,\n" +
                "       MI.ZF_JE 自付金额,\n" +
                "       mi.ybjy_lsh 结算ID,\n" +
                "       mi.jzxh 就诊ID,\n" +
                "       mi.grbh 人员编号\n" +
                "  from his.mi_trans_info mi \n" +
                " where mi.mistatus='PAY_OK'\n" +
                " and jz_lsh in (SELECT * FROM  table(fun_row_split('" + zylsh + "')))";
    }
    public static String getPatYbxxAll(){
        return "\n" +
                "SELECT mi.jz_lsh ZYLSH,mi.mistatus,MI.FY_ZE 总金额,\n" +
                "       MI.YB_FY 医保统筹金额,\n" +
                "       MI.ZF_JE 自付金额,\n" +
                "       mi.ybjy_lsh 结算ID,\n" +
                "       mi.jzxh 就诊ID,\n" +
                "       mi.grbh 人员编号" +
                "  from his.mi_trans_info mi \n" +
                " where  mi.mistatus='PAY_OK'\n" +
                " AND MI.Js_Rq BETWEEN to_date('2023-01-01 00:00:00', 'yyyy-mm-dd hh24:mi:ss') AND to_date('2024-12-31 23:59:59', 'yyyy-mm-dd hh24:mi:ss') \n" +
                " AND MI.MZZY_FLAG='B'\n";
    }

    public static String getZcZjPat(String fz){
        return "SELECT \n" +
                "FZ,\n" +
                "listagg(''''||zylsh||'''',',') within group (order by zylsh) ZYLSH\n" +
                "FROM (\n" +
                "select row_number() over(order by zylsh desc) XH,ceil(row_number() over(order by zylsh desc)/"+fz+") FZ,zylsh,count(t.zylsh) from global_temp_table_name t where t.zylsh is not null group by zylsh\n" +
                ")GROUP BY FZ";
    }

    public static String getZcZjPatZyfymx(String zylsh){
        return "SELECT\n" +
                "distinct a.问题类别 wt,mx.ZYFYMX_ID zyfymxid\n" +
                " FROM \n" +
                "global_temp_table_name a \n" +
                "join his.pat_inpat_order_cost mx on a.zylsh=mx.zylsh_id and mx.sfxm_id=a.sfxm_id and a.费用发生时间 = trunc(mx.sq_rq)\n" +
                "where a.zylsh in ("+zylsh+")";
    }


    public static String getZDxx(){
        return "SELECT * FROM global_temp_table_nameZDXX";
    }

    public static String getGwyc(){
        return "SELECT * FROM\n" +
                "global_temp_table_name cc\n" +
                "where cc.问题类别='将不符合医保范围内的高危压疮防范护理纳入医保支付。'";
    }

    public static String getWtlx(){
        return "select \n" +
                "t.问题序号,\n" +
                "count(distinct t.zylsh_id) 人数,\n" +
                "sum(金额) je,\n" +
                "SUM(T.医保统筹金额)/SUM(T.总金额) \"医保统筹金额/总金额比例\"\n" +
                " from 自查自纠最终汇总数据明细2025 t\n" +
                " group by t.问题序号";
    }

    public static String getWtlxAll(String l){
        return "SELECT \n" +
                "a.问题类别,\n" +
                "a.事项,\n" +
                "a.机构名称,\n" +
                "a.住院号门诊号 \"住院号\", \n" +
                "a.参保人身份证号,\n" +
                "a.参保人姓名,\n" +
                "a.结算时间,\n" +
                "a.就医方式,\n" +
                "a.科室 科室,\n" +
                "a.医生,\n" +
                "substr(a.诊断,0,500) 诊断,\n" +
                "a.医疗目录编码,\n" +
                "a.医疗目录名称,\n" +
                "a.收费项目等级,\n" +
                "a.费用发生时间,\n" +
                "a.单价,\n" +
                "a.数量,\n" +
                "a.金额,\n" +
                "a.结算id,\n" +
                "a.就诊id,\n" +
                "a.人员编号,\n" +
                "a.费用明细流水号\n" +
                " FROM 自查自纠最终汇总数据明细2025 a \n" +
                " where a.问题序号 ='"+l+"'";
    }
    public static String getWtlx2(){
        return "SELECT a.编码 问题序号,sum(a.金额) je FROM global_temp_table_name a group by a.编码";
    }

    public static String getWtlxAll2(String l){
        return "SELECT \n" +
                "a.问题类别,\n" +
                "a.事项,\n" +
                "a.机构名称,\n" +
                "a.住院号,\n" +
                "a.身份证号码 参保人身份证号,\n" +
                "a.姓名 参保人姓名,\n" +
                "a.结算时间,\n" +
                "'' 就医方式,\n" +
                "a.病区 科室,\n" +
                "a.医生,\n" +
                "substr(a.诊断,0,500) 诊断,\n" +
                "a.医疗目录编码,\n" +
                "a.医疗目录名称,\n" +
                "a.收费项目等级,\n" +
                "a.费用发生时间,\n" +
                "a.单价,\n" +
                "a.数量,\n" +
                "a.金额,\n" +
                "a.结算id,\n" +
                "a.就诊id,\n" +
                "a.人员编号,\n" +
                "a.费用明细流水号\n" +
                "FROM \n" +
                "global_temp_table_name a\n" +
                "where a.编码 ='"+l+"'";
    }
}
