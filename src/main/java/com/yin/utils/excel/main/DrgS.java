package com.yin.utils.excel.main;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson2.JSONObject;
import com.yin.MyDemoApplication;
import com.yin.service.OracleService;
import com.yin.utils.ConvertAllUtils;
import com.yin.utils.FileUtils;
import com.yin.utils.excel.sql.SqlUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = MyDemoApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
public class DrgS {
    @Autowired
    private OracleService oracleService;
    @Test
    public void show() throws IOException {
        System.out.println("123");
//        List list = oracleService.selectAll(getsql("2024-03-01", "2024-03-31"));
//        ArrayList<String> strings = new ArrayList<>();
//        for (Object o : list) {
//            JSONObject jsonObject = ConvertAllUtils.convertJson(o);
//            String s = getsql2("", jsonObject.get("ZYLSH").toString(),"4101A");
//            List list1 = oracleService.selectAll(s);
//            for (Object object : list1) {
//                JSONObject jsonObject1 = ConvertAllUtils.convertJson(object);
//                Object o1 = jsonObject1.get("RZ");
//                cn.hutool.json.JSONObject entries = ConvertAllUtils.convertJson2(o1);
//                log.info("{}住院流水号,{}",jsonObject.get("ZYLSH").toString(),entries.get("err_msg"));
//                //return;
//                if(entries.get("err_msg").equals("fsi调用mbs异常：以上传记录且状态为:1,不允许上传或修改")){
//                    strings.add(jsonObject.get("ZYLSH").toString());
//                    System.out.println(up1(jsonObject.get("ZYLSH").toString()));
//                }
//            }
//        }
//        String s = JSONUtil.toJsonStr(strings).replace("[", "").replaceAll("\"", "\'");
//        FileUtils.writeFile("D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\2024-09-26\\结算清单\\3.txt",s,false);
    }


    public  String getsql(String kssj, String jssj){
        SqlUtils sqlUtils = SqlUtils.iniSql(" select DISTINCT\n" +
                "             zy.pat_id,\n" +
                "             br.pat_name,\n" +
                "             sex.xb,\n" +
                "             to_char(br.cs_rq, 'yyyy-MM-dd') cs_rq,\n" +
                "             decode(substr(nvl(d.cbdtcqdm, '00'), 1, 2),\n" +
                "                    substr(MDTRTAREA_ADMVS, 1, 2),\n" +
                "                    decode(substr(d.cbdtcqdm, 1, 4),\n" +
                "                           substr(MDTRTAREA_ADMVS, 1, 4),\n" +
                "                           '市内',\n" +
                "                           '3399',\n" +
                "                           '省直',\n" +
                "                           '省内'),\n" +
                "                    '00',\n" +
                "                    '普通',\n" +
                "                    '省外') brxz_mc,\n" +
                "             bqdm.bm_mc cybqmc,\n" +
                "             ksdm.bm_mc cyksmc,\n" +
                "             zy.cwh,\n" +
                "             zy.zyh,\n" +
                "             zy.zylsh,\n" +
                "             TO_CHAR(zy.RY_RQ, 'yyyy-MM-dd') RY_RQ,\n" +
                "             to_char(zy.CY_JSRQ, 'yyyy-MM-dd') js_rq,\n" +
                "             to_char(zy.CY_RQ, 'yyyy-MM-dd') cy_rq,\n" +
                "             dise.diag_name CYZDMC,\n" +
                "             empe.xm zzysxm,\n" +
                "             fp.js_zje FY_ZE,\n" +
                "             fp.JSLSH_ID,\n" +
                "             zy.OLDUP_FLAG,\n" +
                "             tr.ybjy_lsh yb_jslsh,\n" +
                "             tr.grbh,\n" +
                "             d.yb_rydjh,\n" +
                "             decode(zy.D4101, '0', '成功', '', '', '失败') D4101,\n" +
                "             decode(zy.D4401, '0', '成功', '', '', '失败') D4401,\n" +
                "             decode(zy.D4402, '0', '成功', '', '', '失败') D4402,\n" +
                "             decode(zy.D4701, '0', '成功', '', '', '失败') D4701,\n" +
                "             decode(zy.D4102, '0', '成功', '', '', '失败') D4102,\n" +
                "             d.cbdtcqdm cbdt,\n" +
                "             decode(substr(nvl(d.cbdtcqdm, '00'), 1, 2),\n" +
                "                    substr(MDTRTAREA_ADMVS, 1, 2),\n" +
                "                    decode(substr(d.cbdtcqdm, 1, 4),\n" +
                "                           substr(MDTRTAREA_ADMVS, 1, 4),\n" +
                "                           '1',\n" +
                "                           '3399',\n" +
                "                           '5',\n" +
                "                           '2'),\n" +
                "                    '00',\n" +
                "                    '4',\n" +
                "                    '3') cbd\n" +
                "         from pat_inpat_info zy\n" +
                "                  inner join his.pat_info br\n" +
                "                             on zy.pat_id = br.pat_id\n" +
                "                  left join his.MIPAT_INOUT_RECORD d\n" +
                "                            on zy.zylsh = d.zylsh\n" +
                "                                and d.zf_flag = '0'\n" +
                "                  inner join his.inpat_settle_invoice fp\n" +
                "                             on fp.zylsh = zy.zylsh\n" +
                "                                 and js_zje > 0\n" +
                "                                 and substr(paytype, 1, 1) = '2'\n" +
                "                  left join mi_trans_info tr\n" +
                "                            on zy.zylsh = tr.jz_lsh\n" +
                "                                and (tr.mistatus = 'PAY_OK' or\n" +
                "                                     (tr.mistatus = 'RECEDE_OK' and tr.fy_ze > 0))\n" +
                "                                and fp.jslsh_id = tr.js_lsh\n" +
                "                  left join dictmanage.dict_sex sex\n" +
                "                            on br.xb_id = sex.xb_id\n" +
                "                  left join dictmanage.dict_pat_character patxz\n" +
                "                            on zy.pat_xzid = patxz.brxz_id\n" +
                "                  left join dictmanage.dict_depart bqdm\n" +
                "                            on zy.cy_bq_id = bqdm.bm_id\n" +
                "                  left join dictmanage.dict_depart ksdm\n" +
                "                            on zy.cy_ks_id = ksdm.bm_id\n" +
                "                  left join dictmanage.dict_empe empe\n" +
                "                            on empe.keyno = zy.zzys_id\n" +
                "                  left join SYN_BASEINFO base\n" +
                "                            on base.ipt_no = zy.zylsh\n" +
                "                  left join SYN_DISEINFO dise\n" +
                "                            on dise.brxh = base.brxh, dictmanage.mi_option\n" +
                "         where (dise.maindiag_flag = '1' or dise.maindiag_flag is null)\n" +
                "           and fp.js_zje > 0\n" +
                "           \n" +
                "           and (zy.D4101 = '-1' or zy.D4101 is null or zy.D4401 = '-1' or\n" +
                "                zy.D4401 is null or zy.D4402 = '-1' or zy.D4402 is null or\n" +
                "                zy.D4701 = '-1' or zy.D4701 is null)");
        sqlUtils.AndSql(" tr.ybjsrq >= to_date('{0}', 'yyyy-mm-dd')",kssj)
                .AndSql(" tr.ybjsrq < to_date('{0}', 'yyyy-mm-dd') + 1",jssj);
        return sqlUtils.getSql();
    }

    public String getsql2(String patid,String zylsh,String type){
        String format = String.format("select TO_CHAR(RECEIVEINFO) rz\n" +
                "  from (select row_number() over(partition by pat_lsh, jyh order by out_time desc) xh,\n" +
                "               a.*\n" +
                "          from his.MI_TRADELOG a\n" +
                "         where pat_lsh = '%s' and jyh='%s')\n" +
                " where xh = 1\n" +
                "   and jyh in ('4101A', '4401', '4402', '4701', '4102')\n", zylsh,type);
        return format;
    }

    public String up1(String zylsh){
        String format = String.format("UPDATE his.pat_inpat_info c SET C.D4101='1' WHERE C.ZYLSH='%S'",zylsh);
        return format;
    }
}
