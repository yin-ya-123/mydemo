package com.yin.utils.excel.main.yb;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson2.JSONObject;
import com.yin.MyDemoApplication;
import com.yin.common.ApplicationUtils;
import com.yin.pojo.qt.DrgPatPojo;
import com.yin.service.OracleService;
import com.yin.utils.ConvertAllUtils;
import com.yin.utils.excel.ExcelUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author yin
 * @date 2024-12-12
 * @Description drgs 数据处理
 */
@SpringBootTest(classes = MyDemoApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
public class DrgsCl {
    static String path="D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\2024-12-12";
    @Autowired
    private OracleService hisOracleServiceImpl;

    @Test
    public void test() {
        String drgPatInfo = getDrgPatInfo("2024-11-01", "2024-11-30");
        List list = hisOracleServiceImpl.selectAll(drgPatInfo);
        for (Object o : list) {
            JSONObject jsonObject = ConvertAllUtils.convertJson(o);
            String patId = jsonObject.get("PAT_ID").toString();
            String zylsh = jsonObject.get("ZYLSH").toString();
            String d4101 =  null!=jsonObject.get("D4101")?jsonObject.get("D4101").toString():"";
            if(d4101.equals("失败")){
                String miLogInfo = getMiLogInfo(patId, zylsh);
                List list1 = hisOracleServiceImpl.selectAll(miLogInfo);
                for (Object o1 : list1) {
                    JSONObject jsonObject1 = ConvertAllUtils.convertJson(o1);
                    String string = jsonObject1.get("JYH").toString();
                    String receiveinfo = jsonObject1.get("RECEIVEINFO").toString();
                    if(string.equals("4101A")){
                        cn.hutool.json.JSONObject jsonObject2 = JSONUtil.parseObj(receiveinfo);
                        String errMsg = jsonObject2.get("err_msg").toString();
                        if(errMsg.trim().equals("fsi调用mbs异常：以上传记录且状态为:1,不允许上传或修改")){
                            String patInfoD4101Update = getPatInfoD4101Update(zylsh);
//                            System.out.println(patInfoD4101Update);
                            hisOracleServiceImpl.insertAll(patInfoD4101Update);
                        }
                    }
                }
            }
        }
    }



    public String getPatInfoD4101Update(String zylsh){
        return "UPDATE his.pat_inpat_info br SET BR.D4101='0' WHERE BR.ZYLSH='"+zylsh+"'";
    }

    public String getMiLogInfo(String patId,String patLsh){
        return "select *\n" +
                "  from (select row_number() over(partition by pat_lsh, jyh order by out_time desc) xh,\n" +
                "               a.*\n" +
                "          from his.MI_TRADELOG a\n" +
                "         where pat_id = '"+patId+"'\n" +
                "           and pat_lsh = '"+patLsh+"')\n" +
                " where xh = 1\n" +
                "   and jyh in ('4101A', '4401', '4402', '4701', '4102')\n";
    }


    public String getDrgPatInfo(String kssj,String jssj){
        return "SELECT TMP_PAGE.*, ROWNUM ROW_ID\n" +
                "FROM (select DISTINCT zy.pat_id,\n" +
                "                      br.pat_name,\n" +
                "                      sex.xb,\n" +
                "                      to_char(br.cs_rq, 'yyyy-MM-dd')              cs_rq,\n" +
                "                      decode(substr(nvl(d.cbdtcqdm, '00'), 1, 2), substr(MDTRTAREA_ADMVS, 1, 2),\n" +
                "                             decode(substr(d.cbdtcqdm, 1, 4), substr(MDTRTAREA_ADMVS, 1, 4), '市内', '3399', '省直',\n" +
                "                                    '省内'), '00', '普通', '省外') brxz_mc,\n" +
                "                      bqdm.bm_mc                                   cybqmc,\n" +
                "                      ksdm.bm_mc                                   cyksmc,\n" +
                "                      zy.cwh,\n" +
                "                      zy.zyh,\n" +
                "                      zy.zylsh,\n" +
                "                      TO_CHAR(zy.RY_RQ, 'yyyy-MM-dd')              RY_RQ,\n" +
                "                      to_char(zy.CY_JSRQ, 'yyyy-MM-dd')            js_rq,\n" +
                "                      to_char(zy.CY_RQ, 'yyyy-MM-dd')              cy_rq,\n" +
                "                      dise.diag_name                               CYZDMC,\n" +
                "                      empe.xm                                      zzysxm,\n" +
                "                      fp.js_zje                                    FY_ZE,\n" +
                "                      fp.JSLSH_ID,\n" +
                "                      zy.OLDUP_FLAG,\n" +
                "                      tr.ybjy_lsh                                  yb_jslsh,\n" +
                "                      tr.grbh,\n" +
                "                      d.yb_rydjh,\n" +
                "                      decode(zy.D4101, '0', '成功', '', '', '失败'） D4101,\n" +
                "                             decode(zy.D4401, '0', '成功', '', '', '失败'） D4401,\n" +
                "                                    decode(zy.D4402, '0', '成功', '', '', '失败'） D4402,\n" +
                "                                           decode(zy.D4701, '0', '成功', '', '', '失败'） D4701,\n" +
                "                                                  decode(zy.D4102, '0', '成功', '', '', '失败'） D4102, d.cbdtcqdm cbdt,\n" +
                "                                                         decode(substr(nvl(d.cbdtcqdm, '00'), 1, 2),\n" +
                "                                                                substr(MDTRTAREA_ADMVS, 1, 2),\n" +
                "                                                                decode(substr(d.cbdtcqdm, 1, 4),\n" +
                "                                                                       substr(MDTRTAREA_ADMVS, 1, 4), '1', '3399', '5',\n" +
                "                                                                       '2'), '00', '4', '3') cbd from pat_inpat_info zy\n" +
                "                                                         inner join his.pat_info br on zy.pat_id = br.pat_id left join\n" +
                "                                                         his.MIPAT_INOUT_RECORD d on\n" +
                "                                                         zy.zylsh = d.zylsh and d.zf_flag = '0'\n" +
                "--and nvl(d.yb_jslsh,'0')!='0' \n" +
                "                                                         inner join his.inpat_settle_invoice fp on\n" +
                "                                                         fp.zylsh = zy.zylsh and js_zje > 0 and\n" +
                "                                                         substr(paytype, 1, 1) = '2'\n" +
                "--and fp.zf_flag='0' \n" +
                "                                                         left join mi_trans_info tr on zy.zylsh = tr.jz_lsh\n" +
                "                                                             and\n" +
                "                                                                                       (tr.mistatus = 'PAY_OK' or (tr.mistatus = 'RECEDE_OK' and tr.fy_ze > 0))\n" +
                "                                                             and fp.jslsh_id = tr.js_lsh left join dictmanage.dict_sex\n" +
                "                                                         sex on br.xb_id = sex.xb_id\n" +
                "                                                         left join dictmanage.dict_pat_character patxz on\n" +
                "                                                         zy.pat_xzid = patxz.brxz_id\n" +
                "                                                         left join dictmanage.dict_depart bqdm on\n" +
                "                                                         zy.cy_bq_id = bqdm.bm_id\n" +
                "                                                         left join dictmanage.dict_depart ksdm on\n" +
                "                                                         zy.cy_ks_id = ksdm.bm_id\n" +
                "                                                         left join dictmanage.dict_empe empe on empe.keyno = zy.zzys_id\n" +
                "                                                         left join SYN_BASEINFO base on base.ipt_no = zy.zylsh\n" +
                "                                                         left join SYN_DISEINFO dise on dise.brxh = base.brxh,\n" +
                "                                                         dictmanage.mi_option where\n" +
                "                                                         (dise.maindiag_flag = '1' or dise.maindiag_flag is null)\n" +
                "                                                             and fp.js_zje > 0 and\n" +
                "                                                         tr.ybjsrq >= to_date('"+kssj+"', 'yyyy-mm-dd')\n" +
                "                                                             and tr.ybjsrq < to_date('"+jssj+"', 'yyyy-mm-dd') + 1\n" +
                "                                                         order by to_char(zy.CY_JSRQ, 'yyyy-MM-dd')) TMP_PAGE \n";
    }

    @Test
    public void generateClass() throws IOException {
        ExcelUtils excelUtils = ExcelUtils.getExcelUtils();
        LinkedList<String> excelTitle = excelUtils.getExcelTitle("D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\2024-12-12\\drg.xlsx");
        excelUtils.generateClassZ("DrgPatPojo", excelTitle, ApplicationUtils.pathPojoP);
    }
}
