package com.yin.utils.excel.main.kangfu.jxc;

/**
 *
 *@author yin
 *@date 2025-04-08
 *@Description
 */
public class JxcSql {

    public static String getJxcCSql(String jssj){
        String sql = "with ypxx as\n" +
                " (select '1' as lx,\n" +
                "          yp.yp_mc as \"药品名称\",\n" +
                "          cd.sccj_mc as \"生产厂家\",\n" +
                "          yp.gg as \"规格\",\n" +
                "          dw.dw_mc as \"单位\",\n" +
                "          n.yp_spmc as \"商品名\",\n" +
                "          ROUND(sum(nvl((a.SJ_KC) / A.PL_XS * KC.PL_XS, 0)), 2) as \"期初库存\",\n" +
                "          sum(-1 * b.fysl * kc.pl_xs) as \"销\",\n" +
                "          sum(rksl * kc.pl_xs) as \"进\",\n" +
                "          sum(sy * kc.pl_xs) as \"损益\",\n" +
                "          sum(rkks * kc.pl_xs) as \"领用\",\n" +
                "          ROUND(sum(nvl(nvl(z.SJ_KC / Z.PL_XS * KC.PL_XS, kc.sj_kc), 0)), 2) as \"期末库存\",\n" +
                "          ROUND(ROUND(sum(nvl((a.SJ_KC) / A.PL_XS * KC.PL_XS, 0)), 2) -\n" +
                "                sum(b.fysl * kc.pl_xs) + sum(rksl * kc.pl_xs) +\n" +
                "                sum(sy * kc.pl_xs) -\n" +
                "                ROUND(sum(nvl(nvl(z.SJ_KC / Z.PL_XS * KC.PL_XS, kc.sj_kc), 0)),\n" +
                "                      2) + sum(rkks * kc.pl_xs),\n" +
                "                2) as sl,\n" +
                "          b.yp_id,\n" +
                "          b.cd_id,\n" +
                "          kc.ls_dj \"零售价\",\n" +
                "          cw.cwh\n" +
                "    from his.drug_storage kc\n" +
                "    left join drug_storage_mrjl a\n" +
                "      on kc.yp_id = a.yp_id\n" +
                "     and kc.cd_id = a.cd_id\n" +
                "     and kc.kf_id = a.kf_id\n" +
                "     and kc.kf_id = '3'\n" +
                "     and to_char(a.cjsj, 'yyyy-mm-dd') = '2025-01-01'\n" +
                "    left join drug_storage_mrjl z\n" +
                "      on kc.yp_id = z.yp_id\n" +
                "     and kc.cd_id = z.cd_id\n" +
                "     and kc.kf_id = z.kf_id\n" +
                "     and z.kf_id = '3'\n" +
                "     and to_char(z.cjsj - 1, 'yyyy-mm-dd') = ':jssj'\n" +
                "    left join DICTMANAGE.drug_area_list n\n" +
                "      on a.yp_id = n.yp_id\n" +
                "     and a.cd_id = n.yp_cd_id\n" +
                "    left join DICTMANAGE.dict_drug_area cd\n" +
                "      on kc.CD_ID = cd.sccj_id\n" +
                "    left join DICTMANAGE.dict_pack_unit dw\n" +
                "      on kc.xsdw_id = dw.dw_id\n" +
                "    left join his.drug_cw cw\n" +
                "      on cw.kf_id = kc.kf_id\n" +
                "     and cw.yp_id = kc.yp_id\n" +
                "     and cw.cd_id = kc.cd_id\n" +
                "    left join DICTMANAGE.drug_info yp\n" +
                "      on kc.yp_id = yp.yp_id\n" +
                "     and yp_lx in ('1', '2'),\n" +
                "   (\n" +
                "          --发药\n" +
                "          select yp_id,\n" +
                "                  cd_id,\n" +
                "                  sum(sl) as fysl,\n" +
                "                  sum(rksl) as rksl,\n" +
                "                  sum(rkks) as rkks,\n" +
                "                  sum(sy) as sy\n" +
                "            from (select SFXM_ID as yp_id,\n" +
                "                          a.YPCD_ID as cd_id,\n" +
                "                          SUM(A.SFXM_SL / a.yp_plxs) as sl,\n" +
                "                          0 as rksl,\n" +
                "                          0 as rkks,\n" +
                "                          0 sy\n" +
                "                     from outpat_dispense_detail a, drug_storage b\n" +
                "                    where a.FY_STATUS in ('2', '7')\n" +
                "                      and a.sfxm_id = b.yp_id\n" +
                "                      and a.fyyf_id = b.kf_id\n" +
                "                      and -- a.sfxm_id='7039' and \n" +
                "                          a.fy_rq < to_date(':jssj', 'yyyy-mm-dd') + 1\n" +
                "                      and a.fy_rq >= to_date('2025-01-01', 'yyyy-mm-dd')\n" +
                "                      and a.fyyf_id = '3'\n" +
                "                      AND A.YPCD_ID = B.CD_ID\n" +
                "                    group by sfxm_id, a.YPCD_ID --,--A.YP_PLXS--,B.pl_xs\n" +
                "                   UNION ALL\n" +
                "                   select SFXM_ID as yp_id,\n" +
                "                          a.CD_ID as cd_id,\n" +
                "                          SUM(A.SFXM_SL / a.yp_plxs) as sl,\n" +
                "                          0 as rksl,\n" +
                "                          0 as rkks,\n" +
                "                          0 sy\n" +
                "                     from Inpat_Dispens_Detail a, drug_storage b\n" +
                "                    where a.FY_STATUS in ('1', '2', '7')\n" +
                "                      and a.sfxm_id = b.yp_id\n" +
                "                      and a.fy_yfid = b.kf_id\n" +
                "                      and -- a.sfxm_id='7039' and \n" +
                "                          a.cjsj < to_date(':jssj', 'yyyy-mm-dd') + 1\n" +
                "                      and a.cjsj >= to_date('2025-01-01', 'yyyy-mm-dd')\n" +
                "                      and a.fy_yfid = '3'\n" +
                "                      AND A.CD_ID = B.CD_ID\n" +
                "                    group by sfxm_id, a.CD_ID --,--A.YP_PLXS--,B.pl_xs\n" +
                "                   UNION ALL\n" +
                "                   --结束时间点的未退药 ，放在销里边正数 ，为了把当天已经写入的发药表数据抵消\n" +
                "                   select a.yp_id,\n" +
                "                          a.cd_id,\n" +
                "                          SUM(a.kc_sl) as sl,\n" +
                "                          0 as rksl,\n" +
                "                          0 as rkks,\n" +
                "                          0 sy\n" +
                "                     from bq_tykf_mrjl a, drug_storage b\n" +
                "                    where a.yp_id = b.yp_id\n" +
                "                      and a.cd_id = b.cd_id\n" +
                "                      and a.kc_sl <> 0\n" +
                "                      and b.kf_id = '3'\n" +
                "                      and '3' = '3'\n" +
                "                      and trunc(a.cjsj - 1) = to_date(':jssj', 'yyyy-mm-dd')\n" +
                "                    group by a.yp_id, a.CD_ID\n" +
                "                   union ALL\n" +
                "                   --开始时间点的未退药 , 放在销里边负数，为了之前的未退药导致期初数不对，减去销\n" +
                "                   select a.yp_id,\n" +
                "                          a.cd_id,\n" +
                "                          -SUM(a.kc_sl) as sl,\n" +
                "                          0 as rksl,\n" +
                "                          0 as rkks,\n" +
                "                          0 sy\n" +
                "                     from bq_tykf_mrjl a, drug_storage b\n" +
                "                    where a.yp_id = b.yp_id\n" +
                "                      and a.cd_id = b.cd_id\n" +
                "                      and a.kc_sl <> 0\n" +
                "                      and b.kf_id = '3'\n" +
                "                      and '3' = '3'\n" +
                "                      and trunc(a.cjsj) = to_date('2025-01-01', 'yyyy-mm-dd')\n" +
                "                    group by a.yp_id, a.CD_ID\n" +
                "                   UNION ALL\n" +
                "                   select a.yp_id,\n" +
                "                          a.cd_id,\n" +
                "                          SUM(a.kc_sl) as sl,\n" +
                "                          0 as rksl,\n" +
                "                          0 as rkks,\n" +
                "                          0 sy\n" +
                "                     from bq_tykf a, drug_storage b\n" +
                "                    where a.yp_id = b.yp_id\n" +
                "                      and a.cd_id = b.cd_id\n" +
                "                      and a.kc_sl <> 0\n" +
                "                      and b.kf_id = '3'\n" +
                "                      and '3' = '3'\n" +
                "                      and trunc(sysdate) = to_date(':jssj', 'yyyy-mm-dd')\n" +
                "                    group by a.yp_id, a.CD_ID\n" +
                "                   union ALL\n" +
                "                   --入库\n" +
                "                   select a.yp_id,\n" +
                "                          a.cd_id,\n" +
                "                          0 as fysl,\n" +
                "                          sum(sl / a.pl_xs) as rksl,\n" +
                "                          0 as rkks,\n" +
                "                          0 sy\n" +
                "                     from drug_inoutdepot       a,\n" +
                "                          drug_storage          b,\n" +
                "                          DICTMANAGE.dict_depot kf\n" +
                "                    where a.yp_id = b.yp_id\n" +
                "                      and a.ed_kf = b.kf_id\n" +
                "                      and ((ED_kf = '3' AND A.LX NOT IN ('1', '2')) OR\n" +
                "                          (ED_kf = '6' AND '6' = '3'))\n" +
                "                      and a.lx <> '8'\n" +
                "                      and a.cd_id = b.cd_id\n" +
                "                      and st_kf = kf.kf_id\n" +
                "                      and a.js_rq < to_date(':jssj', 'yyyy-mm-dd') + 1\n" +
                "                      and a.js_rq >= to_date('2025-01-01', 'yyyy-mm-dd')\n" +
                "                      and a.del_flag = '0'\n" +
                "                      and a.lx not in ('6', '7')\n" +
                "                    group by a.yp_id, a.cd_id\n" +
                "                   union ALL\n" +
                "                   --出库\n" +
                "                   select a.yp_id,\n" +
                "                          a.cd_id,\n" +
                "                          0 as fysl,\n" +
                "                          sum(-1 * sl / a.pl_xs) as rksl,\n" +
                "                          0 as rkks,\n" +
                "                          0 sy\n" +
                "                     from drug_inoutdepot       a,\n" +
                "                          drug_storage          b,\n" +
                "                          DICTMANAGE.dict_depot kf\n" +
                "                    where a.yp_id = b.yp_id\n" +
                "                      and a.st_kf = b.kf_id\n" +
                "                      and ((st_kf = '3' AND A.LX NOT IN ('1', '2')) OR\n" +
                "                          (ST_KF = '6' AND '6' = '3'))\n" +
                "                      and a.lx <> '8'\n" +
                "                      and a.cd_id = b.cd_id\n" +
                "                      and ed_kf = kf.kf_id\n" +
                "                      and a.sh_rq < to_date(':jssj', 'yyyy-mm-dd') + 1\n" +
                "                      and a.sh_rq >= to_date('2025-01-01', 'yyyy-mm-dd')\n" +
                "                      and a.del_flag = '0'\n" +
                "                      and a.lx not in ('6', '7')\n" +
                "                    group by a.yp_id, a.cd_id\n" +
                "                   --入库科室\n" +
                "                   union ALL\n" +
                "                   select a.yp_id,\n" +
                "                          a.cd_id,\n" +
                "                          0 as fysl,\n" +
                "                          0 as rksl,\n" +
                "                          sum(sl / a.pl_xs) as rkks,\n" +
                "                          0 sy\n" +
                "                     from drug_inoutdepot        a,\n" +
                "                          drug_storage           b,\n" +
                "                          DICTMANAGE.dict_depart bm\n" +
                "                    where a.yp_id = b.yp_id\n" +
                "                      and a.ed_kf = b.kf_id\n" +
                "                      and ed_kf = '3'\n" +
                "                      and lx <> '8'\n" +
                "                      and a.cd_id = b.cd_id\n" +
                "                      and st_kf = bm.bm_id\n" +
                "                      and a.js_rq < to_date(':jssj', 'yyyy-mm-dd') + 1\n" +
                "                      and a.js_rq >= to_date('2025-01-01', 'yyyy-mm-dd')\n" +
                "                      and a.del_flag = '0'\n" +
                "                      and lx in ('4', '3')\n" +
                "                    group by a.yp_id, a.cd_id\n" +
                "                   union all\n" +
                "                   --出库科室\n" +
                "                   select a.yp_id,\n" +
                "                          a.cd_id,\n" +
                "                          0 as fysl,\n" +
                "                          0 as rksl,\n" +
                "                          sum(-1 * sl / a.pl_xs) as rkks,\n" +
                "                          0 sy\n" +
                "                     from drug_inoutdepot        a,\n" +
                "                          drug_storage           b,\n" +
                "                          DICTMANAGE.dict_depart bm\n" +
                "                    where a.yp_id = b.yp_id\n" +
                "                      and a.st_kf = b.kf_id\n" +
                "                      and st_kF = '3'\n" +
                "                      and lx <> '8'\n" +
                "                      and a.cd_id = b.cd_id\n" +
                "                      and ed_kf = bm.bm_id\n" +
                "                      and a.sh_rq < to_date(':jssj', 'yyyy-mm-dd') + 1\n" +
                "                      and a.sh_rq >= to_date('2025-01-01', 'yyyy-mm-dd')\n" +
                "                      and a.del_flag = '0'\n" +
                "                      and lx in ('4', '3')\n" +
                "                    group by a.yp_id, a.cd_id\n" +
                "                   --采购入库\n" +
                "                   union ALL\n" +
                "                   select a.yp_id,\n" +
                "                          a.cd_id,\n" +
                "                          0 as fysl,\n" +
                "                          0 as rksl,\n" +
                "                          sum(sl / a.pl_xs) as rkks,\n" +
                "                          0 sy\n" +
                "                     from drug_inoutdepot               a,\n" +
                "                          drug_storage                  b,\n" +
                "                          dictmanage.dict_drug_supplier bm\n" +
                "                    where a.yp_id = b.yp_id\n" +
                "                      and a.ed_kf = b.kf_id\n" +
                "                      and ed_kf = '3'\n" +
                "                      and lx <> '8'\n" +
                "                      and a.cd_id = b.cd_id\n" +
                "                      and st_kf = bm.ghs_id\n" +
                "                      and a.sh_rq < to_date(':jssj', 'yyyy-mm-dd') + 1\n" +
                "                      and a.sh_rq >= to_date('2025-01-01', 'yyyy-mm-dd')\n" +
                "                      and a.del_flag = '0'\n" +
                "                      and lx in ('1')\n" +
                "                    group by a.yp_id, a.cd_id\n" +
                "                   union all\n" +
                "                   --采购退库\n" +
                "                   select a.yp_id,\n" +
                "                          a.cd_id,\n" +
                "                          0 as fysl,\n" +
                "                          0 as rksl,\n" +
                "                          sum(-1 * sl / a.pl_xs) as rkks,\n" +
                "                          0 sy\n" +
                "                     from drug_inoutdepot               a,\n" +
                "                          drug_storage                  b,\n" +
                "                          dictmanage.dict_drug_supplier bm\n" +
                "                    where a.yp_id = b.yp_id\n" +
                "                      and a.st_kf = b.kf_id\n" +
                "                      and st_kF = '3'\n" +
                "                      and lx <> '8'\n" +
                "                      and a.cd_id = b.cd_id\n" +
                "                      and ed_kf = bm.ghs_id\n" +
                "                      and a.sh_rq < to_date(':jssj', 'yyyy-mm-dd') + 1\n" +
                "                      and a.sh_rq >= to_date('2025-01-01', 'yyyy-mm-dd')\n" +
                "                      and a.del_flag = '0'\n" +
                "                      and lx in ('2')\n" +
                "                    group by a.yp_id, a.cd_id\n" +
                "                   union ALL\n" +
                "                   --损益\n" +
                "                   select a.yp_id,\n" +
                "                          a.cd_id,\n" +
                "                          0 as fysl,\n" +
                "                          0 as rksl,\n" +
                "                          0 as rkks,\n" +
                "                          sum(1 * sl / a.pl_xs) sy\n" +
                "                     from drug_inoutdepot a, drug_storage b\n" +
                "                    where a.yp_id = b.yp_id\n" +
                "                      and a.st_kf = b.kf_id\n" +
                "                      and st_kf = '3'\n" +
                "                      and lx <> '8'\n" +
                "                      and a.cd_id = b.cd_id\n" +
                "                      and a.sh_rq < to_date(':jssj', 'yyyy-mm-dd') + 1\n" +
                "                      and a.sh_rq >= to_date('2025-01-01', 'yyyy-mm-dd')\n" +
                "                      and a.del_flag = '0'\n" +
                "                      and lx in ('6', '7', '9')\n" +
                "                    group by a.cd_id, a.yp_id\n" +
                "                   \n" +
                "                   )\n" +
                "           group by yp_id, cd_id) b\n" +
                "   where b.CD_ID = kc.cd_id\n" +
                "     and b.yp_id = kc.yp_id\n" +
                "     and kc.kf_id = '3' --and yp.pym like '%'||upper(:in_pym)||'%'\n" +
                "   group by yp.yp_mc,\n" +
                "             cd.sccj_mc,\n" +
                "             dw.dw_mc,\n" +
                "             z.PL_XS,\n" +
                "             yp.gg,\n" +
                "             yp.bz_bm,\n" +
                "             b.yp_id,\n" +
                "             b.cd_id,\n" +
                "             kc.ls_dj,\n" +
                "             YP_SPMC,\n" +
                "             cw.cwh)\n" +
                "select yp_id as \"标准编码\",\n" +
                "       cwh 仓位号,\n" +
                "       药品名称,\n" +
                "       商品名,\n" +
                "       生产厂家,\n" +
                "       规格,\n" +
                "       单位,\n" +
                "       sum(期初库存) as 期初库存,\n" +
                "       sum(进) as 进,\n" +
                "       ROUND(sum(销), 2) as \"销\",\n" +
                "       sum(损益) as \"损溢\",\n" +
                "       sum(领用) as \"领用\",\n" +
                "       sum(期末库存) as \"期末库存\",\n" +
                "       sum(sl) as\"差\",\n" +
                "       零售价,\n" +
                "       yp_id,\n" +
                "       cd_id\n" +
                "  from ypxx\n" +
                " where lx = '1'\n" +
                " group by lx,\n" +
                "          药品名称,\n" +
                "          生产厂家,\n" +
                "          单位,\n" +
                "          规格,\n" +
                "          yp_id,\n" +
                "          cd_id,\n" +
                "          零售价,\n" +
                "          商品名,\n" +
                "          cwh\n" +
                "having sum (sl) <> 0\n" +
                " order by cwh\n";
        return sql.replace(":jssj", jssj);
    }
}
