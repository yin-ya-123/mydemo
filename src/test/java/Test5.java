import cn.hutool.extra.pinyin.PinyinUtil;

/**
 * @author yin
 * @date 2025-01-10
 * @Description
 */
public class Test5 {
    public static void main(String[] args) {
        String firstLetter = PinyinUtil.getFirstLetter(sql, "");
        System.out.println(firstLetter);
    }

   static String sql = "SELECT\n" +
           "mi.js_lsh 结算单据号,\n" +
           "'H33011305117' 医疗机构编码,\n" +
           "'杭州富春康复医院' 医疗机构名称,\n" +
           "'临平区' 医疗机构所属行政区划,\n" +
           "'1' 医保结算等级,\n" +
           "'330113' 参保地统筹区域编码,\n" +
           "'临平区' 参保地统筹区域名称,\n" +
           "mi.js_rq 结算日期,\n" +
           "to_char(sysdate,'yyyy') 医保年度,\n" +
           "mi.JZ_LSH 就诊号,\n" +
           "to_char(mi.grbh) 个人编码,\n" +
           "br.zyh 病案号,\n" +
           "CASE WHEN MI.XZ ='310' THEN '职工'\n" +
           "  WHEN MI.XZ ='390' THEN '城乡'\n" +
           "    WHEN MI.XZ ='340' THEN '离休'\n" +
           "      WHEN MI.XZ ='391' THEN '省级子女统筹'\n" +
           "        ELSE (SELECT ZD.CODENAME FROM  dictmanage.dict_mi_jhcode ZD where ZD.codeid=MI.XZ AND ROWNUM=1) END 险种类型,\n" +
           "decode(mi.yllb,'11','普通门诊','14','门诊慢病','140104','门诊慢病','140201','门诊特病','51','生育门诊','普通门诊') 医疗类别,\n" +
           "xz.yb_ryxz_mc 人员类型,\n" +
           "f_getbmmc(br.ry_ks_id) 入院科室,\n" +
           "''  转科科室,\n" +
           "f_getbmmc(br.cy_ks_id) 出院科室,\n" +
           "ZG.Keyno 主诊医师编码,\n" +
           "ZG.Xm 主诊医师姓名,\n" +
           "br2.pat_name 患者姓名,\n" +
           "decode(BR2.xb_id, '1', '男', '2', '女', '不详') 患者性别,\n" +
           "br2.cs_rq 患者出生日期,\n" +
           "trunc(months_between(sysdate, BR2.cs_rq) / 12) 患者年龄,\n" +
           "case when substr(nvl(d.cbdtcqdm,'00'),1,2)<>substr(MDTRTAREA_ADMVS,1,2) then '是' else '否' end 异地标志,\n" +
           "br.ry_rq 入院日期,\n" +
           "br.cyqr_rq 出院日期,\n" +
           "(br.cy_rq-br.ry_rq) 住院天数,\n" +
           "(SELECT cylx.cylx_mc FROM DICTMANAGE.DICT_DISADMISS_TYPE cylx where cylx.cylx_id=br.cylx_id) 离院方式,\n" +
           "mi.FY_ZE 医疗总费用,\n" +
           "mi.tcjj_zf 基本统筹支付,\n" +
           "mi.dbjz_zf 大病保险,\n" +
           "nvl(round(fun_jsnode_data(mi.ret_jsxx, 'setlinfo.maf_pay'),2),0) 医疗救助,\n" +
           "MI.GWYBZ_ZF 公务员医疗补助,\n" +
           "nvl(round(fun_jsnode_data(mi.ret_jsxx, 'setlinfo.hifob_pay'),2),0) 大额补充,\n" +
           "nvl(round(fun_jsnode_data(mi.ret_jsxx, 'setlinfo.hifes_pay'),2),0) 企业补充,\n" +
           "mi.grxj_zf 个人现金支付,\n" +
           "nvl(round(fun_jsnode_data(mi.ret_jsxx, 'setlinfo.acct_pay'),2),0) 个人账户支付,\n" +
           "nvl(round(fun_jsnode_data(mi.ret_jsxx, 'setlinfo.oth_pay'),2),0) 其他支付,\n" +
           "decode(mi.zxjj_zf, '', '0.00', mi.zxjj_zf) 个人自付,\n" +
           "decode(mi.zf_je, '', '0.00', mi.zf_je) 个人自费,\n" +
           "nvl(round(fun_jsnode_data(mi.ret_jsxx, 'setlinfo.inscp_scp_amt'),2),0) 符合基本医疗保险的费用,\n" +
           "br.ry_zd_id 入院诊断编码,\n" +
           "br.ryzdms 入院诊断名称,\n" +
           "(\n" +
           "SELECT NVL(czd.icd_code,'无') FROM\n" +
           "his.pat_inpat_diagnosis zd2\n" +
           "INNER JOIN dictmanage.dict_icd czd ON ZD2.ZD_ICD=CZD.ICD_CODE\n" +
           "WHERE zd2.zdlx_id='1' and zd2.zdsh='1' AND ZD2.ZYLSH=br.ZYLSH AND ROWNUM=1\n" +
           ") 出院诊断编码,\n" +
           "(\n" +
           "SELECT NVL(czd.Jb_Mc,'无') FROM\n" +
           "his.pat_inpat_diagnosis zd2\n" +
           "INNER JOIN dictmanage.dict_icd czd ON ZD2.ZD_ICD=CZD.ICD_CODE\n" +
           "WHERE zd2.zdlx_id='1' and zd2.zdsh='1' AND ZD2.ZYLSH=br.ZYLSH AND ROWNUM=1\n" +
           ") 出院诊断名称,\n" +
           "'' 医保支付方式,\n" +
           "'' 病组病种编码,\n" +
           "'' 病组病种名称\n" +
           "FROM\n" +
           "his.mi_trans_info mi\n" +
           "join his.pat_inpat_info br on mi.jz_lsh=br.zylsh\n" +
           "join his.pat_info br2 on br.pat_id=br2.pat_id\n" +
           "join his.MIPAT_INOUT_RECORD d on br.zylsh=d.zylsh\n" +
           "left join dictmanage.dict_mi_pat_nature xz  on mi.yb_ryxz_id=XZ.YB_RYXZ_ID\n" +
           "join dictmanage.dict_empe ZG on br.zzys_id=zg.keyno\n" +
           ",dictmanage.mi_option\n" +
           "where mi.mistatus in ('PAY_OK', 'RECEDE_OK', 'MI_OK')\n" +
           "--and mi.js_rq > to_date('2024-01-01 00:00:00','yyyy-mm-dd hh24:mi:ss')  AND mi.js_rq < to_date('2024-04-01 00:00:00','yyyy-mm-dd hh24:mi:ss')\n" +
           "AND BR.ZYLSH='6914'" ;
}
