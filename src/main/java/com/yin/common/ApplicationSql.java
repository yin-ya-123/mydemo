package com.yin.common;

/**
 * Created with IntelliJ IDEA
 *
 * @Auther: YIN
 * @DescriPtion:
 * @Date:2023-11-30/11/30/10:59
 */
public class ApplicationSql {

    public static String getWjsSql(String rq){
        return  String.format("\n" +
                "   select  bq_id,sum( hzf) hzf,sum(czf) czf,sum(zlfzy) zlfzy, sum(hyf) hyf,sum(zlf) zlf, sum(flf) flf,sum(qjf) qjf,sum(xdjh) xdjh, sum(zj) zj, sum(llf) llf,\n" +
                "           sum(zlf1) zlf1, sum(ss) ss, sum(gyyf) gyyf,sum(syf) syf, sum(dnqy) dnqy, sum(jg) jg,sum(mzf) mzf, sum(xtf) xtf, sum(hlf) hlf,sum(tnf) tnf, sum(zlf2) zlf2, sum(hlfy) hlfy,\n" +
                "           sum(jr) jr,sum(jsf) jsf, sum(ssf) ssf,sum(ssclf) ssclf,sum(jcclf) jcclf,sum(qtwsclsr) qtwsclsr, sum(xyf) xyf,\n" +
                "           sum(cyf) cyf,sum(zcyf) zcyf,sum(ktf) ktf,sum(hjje) hjje from (\n" +
                "   select  a.bq_id,sysdate,to_date('2022-06-01','yyyy-mm-dd hh24:mi:ss'),to_date('2023-09-01','yyyy-mm-dd hh24:mi:ss'),sum(case when zysf_lx in ('01') then ssje else 0 end) hzf,\n" +
                "          sum(case when zysf_lx in ('02') then ssje else 0 end) czf,sum(case when zysf_lx in ('03') then ssje else 0 end) zlfzy, sum(case when zysf_lx in ('04') then ssje else 0 end) hyf,\n" +
                "          sum(case when zysf_lx in ('05') then ssje else 0 end) zlf,sum(case when zysf_lx in ('06') then ssje else 0 end) flf,sum(case when zysf_lx in ('08') then ssje else 0 end) qjf,\n" +
                "          sum(case when zysf_lx in ('17') then ssje else 0 end) xdjh,sum(case when zysf_lx in ('20') then ssje else 0 end) zj,sum(case when zysf_lx in ('30') then ssje else 0 end) llf,\n" +
                "          sum(case when zysf_lx in ('31') then ssje else 0 end) zlf1,sum(case when zysf_lx in ('32') then ssje else 0 end) ss,sum(case when zysf_lx in ('33') then ssje else 0 end) gyyf,\n" +
                "          sum(case when zysf_lx in ('34') then ssje else 0 end) syf,sum(case when zysf_lx in ('36') then ssje else 0 end) dnqy,sum(case when zysf_lx in ('39') then ssje else 0 end) jg,\n" +
                "          sum(case when zysf_lx in ('40') then ssje else 0 end) mzf,sum(case when zysf_lx in ('44') then ssje else 0 end) xtf,sum(case when zysf_lx in ('45') then ssje else 0 end) hlf,\n" +
                "          sum(case when zysf_lx in ('46') then ssje else 0 end) tnf,sum(case when zysf_lx in ('47') then ssje else 0 end) zlf2,sum(case when zysf_lx in ('49') then ssje else 0 end) hlfy,\n" +
                "          sum(case when zysf_lx in ('50') then ssje else 0 end) jr,sum(case when zysf_lx in ('54') then ssje else 0 end) jsf,sum(case when zysf_lx in ('90') then ssje else 0 end) ssf,\n" +
                "          sum(case when zysf_lx in ('109') then ssje else 0 end) ssclf,sum(case when zysf_lx in ('110') then ssje else 0 end) jcclf,sum(case when zysf_lx in ('43') then ssje else 0 end) qtwsclsr,\n" +
                "          sum(case when zysf_lx in ('111') then ssje else 0 end) xyf,sum(case when zysf_lx in ('112') then ssje else 0 end) cyf,sum(case when zysf_lx in ('113') then ssje else 0 end) zcyf,\n" +
                "          sum(case when zysf_lx in ('114') then ssje else 0 end) ktf,\n" +
                "          sum(ssje) hjje,\n" +
                "          sysdate CJSJ,'99999999' CJR,sysdate ZHGXSJ,'99999999' ZHGXR,'1' ybbz\n" +
                "          from pat_inpat_order_cost a,dictmanage.dict_depart b,pat_inpat_info c where  a.fy_status in ('2','7') \n" +
                "          and ( a.js_rq is null or(a.jz_rq<to_date('%s','yyyy-mm-dd hh24:mi:ss') \n" +
                "          and a.js_rq>=to_date('%s','yyyy-mm-dd hh24:mi:ss') )) and a.bq_id=b.bm_id\n" +
                "          and jz_rq>=to_date('2022-06-06','yyyy-mm-dd') and bq_id<>'cs0001' and bq_id<>'BQ0003' and a.zylsh_id=c.zylsh  and a.zyfymx_id not in('3','4','7','8','9','11','12','13','14','15','16','17','18')\n" +
                "          and jz_rq<to_date('%s','yyyy-mm-dd hh24:mi:ss')\n" +
                "          group by a.bq_id,b.bm_mc\n" +
                "        union all\n" +
                "select bq_id,SC_RQ,KS_RQ,JS_RQ,- hzf,-czf, -zlfzy, -hyf,-zlf, -flf,-qjf,-xdjh, -zj, -llf,\n" +
                "           -zlf1, -ss, -gyyf,-syf, -dnqy, -jg,-mzf, -xtf, -hlf,-tnf, -zlf2, -hlfy,-jr,-jsf, -ssf,-ssclf,-jcclf,-qtwsclsr,-xyf,\n" +
                "           -cyf,-zcyf,-ktf,-hjje,CJSJ,CJR,ZHGXSJ,ZHGXR,ybbz from zywsfrpt_mx where  to_char(trunc(sc_rq),'yyyy-mm-dd')='2024-03-01' ) group by bq_id order by bq_id",rq,rq,rq);
    }


    public static String getLsb(){
        return String.format("SELECT * FROM global_temp_table_name");
    }
    public static String getXmForBrxx(String tj){
        return "SELECT \n" +
                "distinct\n" +
                "br.pat_name,br.zyksmc,br.zycwh,c.yz_mc\n" +
                "FROM\n" +
                "his.pat_inpat_order_info c\n" +
                "join his.v_inpat_info br on c.zylsh=br.zylsh\n" +
                "where\n" +
                "c.yz_mc like '%"+tj+"%' \n" +
                "and c.cjsj between to_date('2024-01-01 00:00:00','yyyy-mm-dd hh24:mi:ss') and  to_date('2024-03-04 23:59:59','yyyy-mm-dd hh24:mi:ss')";
    }
}
