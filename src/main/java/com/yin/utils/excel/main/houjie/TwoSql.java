package com.yin.utils.excel.main.houjie;

public class TwoSql {

    public static String get90211(){
        return "--INSERT INTO MI_FIXMEDINS_INFO \n" +
                "SELECT\n" +
                "--his.hospital_fixmedins_seq.nextval XH,\n" +
                " A.*\n" +
                "  FROM (SELECT DISTINCT   PAT.PAT_ID AS 病人ID,\n" +
                "                        PAT.PAT_ID AS 就诊卡号,\n" +
                "                        JSON_VALUE(MI.SENDINFO, '$.input.data.psn_no') AS 人员编号,\n" +
                "                        pat.pat_name AS 姓名,\n" +
                "                        pat.jt_dh AS 电话,\n" +
                "                        pat.CURRENT_ADDRESS AS 地址,\n" +
                "                        d.Nat_Dic_Val_Code AS 参保类型ID,\n" +
                "                        d.Nat_Dic_Val_Name AS 参保类型,\n" +
                "                        JSON_VALUE(MI.SENDINFO, '$.input.data.biz_appy_type') AS 业务申请类型ID,\n" +
                "                        DECODE(JSON_VALUE(MI.SENDINFO,\n" +
                "                                          '$.input.data.biz_appy_type'),\n" +
                "                               '03',\n" +
                "                               '就医定点医疗机构登记') AS 业务申请类型,\n" +
                "                        TRUNC(TO_DATE(JSON_VALUE(MI.SENDINFO,\n" +
                "                                                 '$.input.data.begndate'),\n" +
                "                                      'YYYY-MM-DD HH24:MI:SS')) AS 开始日期,\n" +
                "                        TO_DATE(JSON_VALUE(MI.SENDINFO,\n" +
                "                                           '$.input.data.enddate'),\n" +
                "                                'YYYY-MM-DD') AS 结束日期,\n" +
                "                        CZR.XM AS 代办人姓名,\n" +
                "                        '01' AS 代办人证件类型ID,\n" +
                "                        '身份证' AS 代办人证件类型,\n" +
                "                        CZR.SFZ AS 代办人证件号码,\n" +
                "                        CZR.MOBILEPH AS 代办人联系方式,\n" +
                "                        JSON_VALUE(MI.SENDINFO, '$.input.data.agnter_addr') AS 代办人联系地址,\n" +
                "                        '9' AS 代办人关系ID,\n" +
                "                        '非亲属' AS 代办人关系,\n" +
                "                        JSON_VALUE(MI.SENDINFO, '$.input.data.fix_srt_no') AS 定点排序号,\n" +
                "                        JSON_VALUE(MI.SENDINFO,\n" +
                "                                   '$.input.data.fixmedins_code') AS 定点医药机构编号,\n" +
                "                        JSON_VALUE(MI.SENDINFO,\n" +
                "                                   '$.input.data.fixmedins_name') AS 定点医药机构名称,\n" +
                "                        JSON_VALUE(MI.SENDINFO, '$.input.data.memo') AS 备注,\n" +
                "                        3 AS 状态,\n" +
                "                        -- 从出参JSON中提取待遇申报明细流水号\n" +
                "                        JSON_VALUE(JSON_VALUE(MI.RECEIVEINFO, '$.data'),\n" +
                "                                   '$.output.result.trt_dcla_detl_sn') AS 待遇申报明细流水号,\n" +
                "                        CZR.KEYNO AS 备案人ID,\n" +
                "                        CZR.XM AS 备案人,\n" +
                "                        TO_DATE(JSON_VALUE(MI.SENDINFO, '$.inf_time'),\n" +
                "                                'YYYY-MM-DD HH24:MI:SS') AS 备案时间,\n" +
                "                        SYSDATE AS 最后更新时间,\n" +
                "                        '8888' AS 最后更新人,\n" +
                "                        JSON_VALUE(MI.SENDINFO,\n" +
                "                                   '$.input.data.fix_local_type') AS 就医点类型,\n" +
                "                        JSON_VALUE(MI.SENDINFO,\n" +
                "                                   '$.input.data.fix_main_scd_flag') AS 主辅标志\n" +
                "          FROM his.mi_tradelog MI\n" +
                "          JOIN his.mi_patient_info mi\n" +
                "            ON JSON_VALUE(MI.SENDINFO, '$.input.data.psn_no') = MI.PSN_NO\n" +
                "          JOIN HIS.PAT_INFO PAT\n" +
                "            ON PAT.zj_hm = mi.certno\n" +
                "          JOIN (SELECT ROW_NUMBER() OVER(PARTITION BY A.PAT_ID ORDER BY A.SETL_TIME DESC) XH,\n" +
                "                      A.*\n" +
                "                 FROM HIS.MI_TRANS_INFO A) A\n" +
                "            ON PAT.pat_id = A.PAT_ID\n" +
                "           AND XH = 1\n" +
                "          left join dictmanage.dict_mi_code d\n" +
                "            on a.med_type = d.nat_dic_val_code\n" +
                "           and d.dic_type_code = 'MED_TYPE'\n" +
                "          LEFT JOIN DICTMANAGE.DICT_EMPE CZR\n" +
                "            ON JSON_VALUE(MI.SENDINFO, '$.opter') = CZR.KEYNO\n" +
                "         WHERE MI.INFNO = '90211'\n" +
                "           AND MI.INFCODE = '0'\n" +
                "           AND MI.REF_TIME > TRUNC(SYSDATE)) A\n";
    }


    public static String MI_FIXMEDINS_INFO(String psn_no){
        return "SELECT * FROM MI_FIXMEDINS_INFO gj where gj.psn_no ='"+psn_no+"'";
    }
}
