package com.yin.utils.excel.main.pinghueryuan;

public class Sql {

    public static String sql1 ="WITH\n" +
            "PAR AS (\n" +
            "    SELECT\n" +
            "        to_date ('2026-08-01 00:00:00', 'yyyy-mm-dd hh24:mi:ss') KSSJ,\n" +
            "        to_date ('2026-08-01 23:59:59', 'yyyy-mm-dd hh24:mi:ss') JSSJ,\n" +
            "        '63,64,16,93,11,99' KFID_STR\n" +
            "    FROM\n" +
            "        DUAL\n" +
            "),\n" +
            "KF_LIST AS (\n" +
            "    SELECT\n" +
            "        REGEXP_SUBSTR (KFID_STR, '[^,]+', 1, LEVEL) AS KFID\n" +
            "    FROM\n" +
            "        PAR CONNECT BY LEVEL <= REGEXP_COUNT (KFID_STR, ',') + 1\n" +
            "),\n" +
            "TJXX AS (\n" +
            "    SELECT\n" +
            "        DPAR.DEPOT_ID,\n" +
            "        DPAR.DRUG_ID,\n" +
            "        DPAR.DRUG_AREA_ID,\n" +
            "        nvl (\n" +
            "            sum(\n" +
            "                CASE\n" +
            "                    WHEN DPAR.STOCK_AMOUNT > 0 THEN ROUND(DPAR.STOCK_AMOUNT, 2)\n" +
            "                END\n" +
            "            ),\n" +
            "            0\n" +
            "        ) 调价增值进价,\n" +
            "        nvl (\n" +
            "            sum(\n" +
            "                CASE\n" +
            "                    WHEN DPAR.STOCK_AMOUNT < 0 THEN ROUND(-1 * DPAR.STOCK_AMOUNT, 2)\n" +
            "                END\n" +
            "            ),\n" +
            "            0\n" +
            "        ) 调价减值进价,\n" +
            "        nvl (\n" +
            "            sum(\n" +
            "                CASE\n" +
            "                    WHEN DPAR.RETAIL_AMOUNT > 0 THEN ROUND(DPAR.RETAIL_AMOUNT, 2)\n" +
            "                END\n" +
            "            ),\n" +
            "            0\n" +
            "        ) 调价增值售价,\n" +
            "        nvl (\n" +
            "            sum(\n" +
            "                CASE\n" +
            "                    WHEN DPAR.RETAIL_AMOUNT < 0 THEN ROUND(-1 * DPAR.RETAIL_AMOUNT, 2)\n" +
            "                END\n" +
            "            ),\n" +
            "            0\n" +
            "        ) 调价减值售价\n" +
            "    FROM\n" +
            "        HIS.DRUG_PRICE_ADJUST_RECORD DPAR\n" +
            "        INNER JOIN KF_LIST KF ON DPAR.DEPOT_ID = KF.KFID\n" +
            "        CROSS JOIN PAR\n" +
            "    WHERE\n" +
            "        DPAR.CREATE_DATE BETWEEN PAR.KSSJ AND PAR.JSSJ\n" +
            "    GROUP BY\n" +
            "        DPAR.DEPOT_ID,\n" +
            "        DPAR.DRUG_ID,\n" +
            "        DPAR.DRUG_AREA_ID\n" +
            "),\n" +
            "JCSJ AS (\n" +
            "    SELECT\n" +
            "        DEPOT_ID,\n" +
            "        DRUG_ID,\n" +
            "        DRUG_AREA_ID,\n" +
            "        SUM(期初结存进价) 期初结存进价,\n" +
            "        SUM(期初结存售价) 期初结存售价,\n" +
            "        SUM(期末结存进价) 期末结存进价,\n" +
            "        SUM(期末结存售价) 期末结存售价\n" +
            "    FROM\n" +
            "        (\n" +
            "            SELECT\n" +
            "                DSM.DEPOT_ID,\n" +
            "                DSM.DRUG_ID,\n" +
            "                DSM.DRUG_AREA_ID,\n" +
            "                (DSM.REAL_STORAGE * DSM.STOCK_PRICE) 期初结存进价,\n" +
            "                (DSM.REAL_STORAGE * DSM.RETAIL_PRICE) 期初结存售价,\n" +
            "                0 期末结存进价,\n" +
            "                0 期末结存售价\n" +
            "            FROM\n" +
            "                HIS.DRUG_STORAGE_DETAIL_MRJL DSM\n" +
            "                INNER JOIN KF_LIST KF ON DSM.DEPOT_ID = KF.KFID\n" +
            "                CROSS JOIN PAR\n" +
            "            WHERE\n" +
            "                DSM.Record_Date = TRUNC (PAR.KSSJ) - 1\n" +
            "            UNION ALL\n" +
            "            SELECT\n" +
            "                DSM.DEPOT_ID,\n" +
            "                DSM.DRUG_ID,\n" +
            "                DSM.DRUG_AREA_ID,\n" +
            "                0 期初结存进价,\n" +
            "                0 期初结存售价,\n" +
            "                (DSM.REAL_STORAGE * DSM.STOCK_PRICE) 期末结存进价,\n" +
            "                (DSM.REAL_STORAGE * DSM.RETAIL_PRICE) 期末结存售价\n" +
            "            FROM\n" +
            "                HIS.DRUG_STORAGE_DETAIL_MRJL DSM\n" +
            "                INNER JOIN KF_LIST KF ON DSM.DEPOT_ID = KF.KFID\n" +
            "                CROSS JOIN PAR\n" +
            "            WHERE\n" +
            "                DSM.Record_Date = TRUNC (PAR.JSSJ)\n" +
            "            UNION ALL\n" +
            "            SELECT\n" +
            "                DSM.DEPOT_ID,\n" +
            "                DSM.DRUG_ID,\n" +
            "                DSM.DRUG_AREA_ID,\n" +
            "                0 期初结存进价,\n" +
            "                0 期初结存售价,\n" +
            "                (DSM.REAL_STORAGE * DSM.STOCK_PRICE) 期末结存进价,\n" +
            "                (DSM.REAL_STORAGE * DSM.RETAIL_PRICE) 期末结存售价\n" +
            "            FROM\n" +
            "                HIS.DRUG_STORAGE_DETAIL DSM\n" +
            "                INNER JOIN KF_LIST KF ON DSM.DEPOT_ID = KF.KFID\n" +
            "                CROSS JOIN PAR\n" +
            "            WHERE\n" +
            "                TRUNC (PAR.JSSJ) >= TRUNC (SYSDATE)\n" +
            "        )\n" +
            "    GROUP BY\n" +
            "        DEPOT_ID,\n" +
            "        DRUG_ID,\n" +
            "        DRUG_AREA_ID\n" +
            "),\n" +
            "YFRCK AS (\n" +
            "    SELECT\n" +
            "        KF.KFID AS DEPOT_ID,\n" +
            "        DID.DRUG_ID,\n" +
            "        DID.DRUG_AREA_ID,\n" +
            "        SUM(\n" +
            "            CASE\n" +
            "                WHEN LX.LX_ID = '3'\n" +
            "                AND MBKF.TYPE = '5' THEN did.AMOUNT_ACT * did.STOCK_PRICE\n" +
            "                ELSE 0\n" +
            "            END\n" +
            "        ) 药房出库进价,\n" +
            "        SUM(\n" +
            "            CASE\n" +
            "                WHEN LX.LX_ID = '3'\n" +
            "                AND QSKF.TYPE = '5'\n" +
            "                AND MBKF.TYPE <> '5' THEN did.AMOUNT_ACT * did.STOCK_PRICE\n" +
            "                ELSE 0\n" +
            "            END\n" +
            "        ) 药房入库进价,\n" +
            "        SUM(\n" +
            "            CASE\n" +
            "                WHEN LX.LX_ID = '3'\n" +
            "                AND QSKF.DEPOT_CODE = KF.KFID\n" +
            "                AND MBKF.TYPE <> '5' THEN did.AMOUNT_ACT * did.STOCK_PRICE\n" +
            "                ELSE 0\n" +
            "            END\n" +
            "        ) 药房出库给药房进价,\n" +
            "        SUM(\n" +
            "            CASE\n" +
            "                WHEN LX.LX_ID = '3'\n" +
            "                AND QSKF.TYPE <> '5'\n" +
            "                AND MBKF.Depot_Code = KF.KFID THEN did.AMOUNT_ACT * did.STOCK_PRICE\n" +
            "                ELSE 0\n" +
            "            END\n" +
            "        ) 其他药房出库给药房,\n" +
            "        SUM(\n" +
            "            CASE\n" +
            "                WHEN LX.LX_ID IN ('6')\n" +
            "                AND DI.SY_FLAG = '1' THEN did.AMOUNT_ACT * did.STOCK_PRICE\n" +
            "                ELSE 0\n" +
            "            END\n" +
            "        ) 报益进价,\n" +
            "        - SUM(\n" +
            "            CASE\n" +
            "                WHEN LX.LX_ID IN ('6')\n" +
            "                AND DI.SY_FLAG = '0' THEN did.AMOUNT_ACT * did.STOCK_PRICE\n" +
            "                ELSE 0\n" +
            "            END\n" +
            "        ) 报损进价,\n" +
            "        SUM(\n" +
            "            CASE\n" +
            "                WHEN LX.LX_ID IN ('7')\n" +
            "                AND DID.AMOUNT_ACT > 0 THEN did.AMOUNT_ACT * did.STOCK_PRICE\n" +
            "                ELSE 0\n" +
            "            END\n" +
            "        ) 盘盈进价,\n" +
            "        - SUM(\n" +
            "            CASE\n" +
            "                WHEN LX.LX_ID IN ('7')\n" +
            "                AND DID.AMOUNT_ACT < 0 THEN did.AMOUNT_ACT * did.STOCK_PRICE\n" +
            "                ELSE 0\n" +
            "            END\n" +
            "        ) 盘亏进价,\n" +
            "        SUM(\n" +
            "            CASE\n" +
            "                WHEN LX.FL_ID = '3' THEN did.AMOUNT_ACT * did.STOCK_PRICE\n" +
            "                ELSE 0\n" +
            "            END\n" +
            "        ) 药库其他所有出库进价,\n" +
            "        SUM(\n" +
            "            CASE\n" +
            "                WHEN LX.FL_ID = '4' THEN did.AMOUNT_ACT * did.STOCK_PRICE\n" +
            "                ELSE 0\n" +
            "            END\n" +
            "        ) 药库其他所有入库进价,\n" +
            "        SUM(\n" +
            "            CASE\n" +
            "                WHEN LX.LX_ID = '3'\n" +
            "                AND MBKF.TYPE = '5' THEN did.AMOUNT_ACT * did.retail_price\n" +
            "                ELSE 0\n" +
            "            END\n" +
            "        ) 药房出库售价,\n" +
            "        SUM(\n" +
            "            CASE\n" +
            "                WHEN LX.LX_ID = '3'\n" +
            "                AND QSKF.TYPE = '5'\n" +
            "                AND MBKF.TYPE <> '5' THEN did.AMOUNT_ACT * did.retail_price\n" +
            "                ELSE 0\n" +
            "            END\n" +
            "        ) 药房入库售价,\n" +
            "        SUM(\n" +
            "            CASE\n" +
            "                WHEN LX.LX_ID = '3'\n" +
            "                AND QSKF.DEPOT_CODE = KF.KFID\n" +
            "                AND MBKF.TYPE <> '5' THEN did.AMOUNT_ACT * did.retail_price\n" +
            "                ELSE 0\n" +
            "            END\n" +
            "        ) 药房出库给药房售价,\n" +
            "        SUM(\n" +
            "            CASE\n" +
            "                WHEN LX.LX_ID = '3'\n" +
            "                AND QSKF.TYPE <> '5'\n" +
            "                AND MBKF.Depot_Code = KF.KFID THEN did.AMOUNT_ACT * did.retail_price\n" +
            "                ELSE 0\n" +
            "            END\n" +
            "        ) 其他药房出库给售价,\n" +
            "        SUM(\n" +
            "            CASE\n" +
            "                WHEN LX.LX_ID IN ('6')\n" +
            "                AND DI.SY_FLAG = '1' THEN did.AMOUNT_ACT * did.retail_price\n" +
            "                ELSE 0\n" +
            "            END\n" +
            "        ) 报益售价,\n" +
            "        - SUM(\n" +
            "            CASE\n" +
            "                WHEN LX.LX_ID IN ('6')\n" +
            "                AND DI.SY_FLAG = '0' THEN did.AMOUNT_ACT * did.retail_price\n" +
            "                ELSE 0\n" +
            "            END\n" +
            "        ) 报损售价,\n" +
            "        SUM(\n" +
            "            CASE\n" +
            "                WHEN LX.LX_ID IN ('7')\n" +
            "                AND DID.AMOUNT_ACT > 0 THEN did.AMOUNT_ACT * did.retail_price\n" +
            "                ELSE 0\n" +
            "            END\n" +
            "        ) 盘盈售价,\n" +
            "        - SUM(\n" +
            "            CASE\n" +
            "                WHEN LX.LX_ID IN ('7')\n" +
            "                AND DID.AMOUNT_ACT < 0 THEN did.AMOUNT_ACT * did.retail_price\n" +
            "                ELSE 0\n" +
            "            END\n" +
            "        ) 盘亏售价,\n" +
            "        SUM(\n" +
            "            CASE\n" +
            "                WHEN LX.FL_ID = '3' THEN did.AMOUNT_ACT * did.retail_price\n" +
            "                ELSE 0\n" +
            "            END\n" +
            "        ) 药库其他所有出库售价,\n" +
            "        SUM(\n" +
            "            CASE\n" +
            "                WHEN LX.FL_ID = '4' THEN did.AMOUNT_ACT * did.retail_price\n" +
            "                ELSE 0\n" +
            "            END\n" +
            "        ) 药库其他所有入库售价\n" +
            "    FROM\n" +
            "        KF_LIST KF\n" +
            "        CROSS JOIN PAR\n" +
            "        INNER JOIN his.drug_inoutdepot di ON (\n" +
            "            DI.INDEPOT_ID = KF.KFID\n" +
            "            OR DI.OUTDEPOT_ID = KF.KFID\n" +
            "        )\n" +
            "        INNER JOIN his.DRUG_INOUTDEPOT_DETAIL did ON did.INOUT_NO = di.INOUT_NO\n" +
            "        LEFT JOIN DICTMANAGE.Dict_Depot QSKF ON DI.OUTDEPOT_ID = QSKF.DEPOT_CODE\n" +
            "        LEFT JOIN DICTMANAGE.Dict_Depot MBKF ON DI.INDEPOT_ID = MBKF.DEPOT_CODE\n" +
            "        JOIN DICTMANAGE.Dict_Inoutdepot_Type lx ON di.inout_type = lx.lx_id\n" +
            "    WHERE\n" +
            "        DI.DEL_FLAG = '0'\n" +
            "        AND DID.DEL_FLAG = '0'\n" +
            "        AND (\n" +
            "            CASE\n" +
            "                WHEN LX.LX_ID = '3'\n" +
            "                AND MBKF.TYPE = '5' THEN DI.AUDIT_DATE\n" +
            "                WHEN LX.LX_ID = '3'\n" +
            "                AND MBKF.TYPE <> '5' THEN DI.RECEIVE_DATE\n" +
            "                WHEN LX.LX_ID = '6' THEN DI.RECEIVE_DATE\n" +
            "                WHEN LX.LX_ID = '7' THEN DI.RECEIVE_DATE\n" +
            "                WHEN LX.FL_ID = '3' THEN DI.AUDIT_DATE\n" +
            "                WHEN LX.FL_ID = '4' THEN DI.RECEIVE_DATE\n" +
            "            END\n" +
            "        ) BETWEEN PAR.KSSJ AND PAR.JSSJ\n" +
            "    GROUP BY\n" +
            "        KF.KFID,\n" +
            "        DID.DRUG_AREA_ID,\n" +
            "        DID.DRUG_ID\n" +
            "),\n" +
            "MZFYXX AS (\n" +
            "    SELECT\n" +
            "        ODD.FYYF_ID AS DEPOT_ID,\n" +
            "        ODD.SFXM_ID DRUG_ID,\n" +
            "        ODD.YPCD_ID DRUG_AREA_ID,\n" +
            "        SUM(ODD.SFXM_SL * ODD.Sfxm_Jhj) AS jhJE,\n" +
            "        SUM(ODD.SFXM_SL * ODD.SFXM_DJ) AS JE\n" +
            "    FROM\n" +
            "        HIS.OUTPAT_DISPENSE_DETAIL ODD\n" +
            "        CROSS JOIN PAR\n" +
            "        INNER JOIN KF_LIST KF ON ODD.FYYF_ID = KF.KFID\n" +
            "    WHERE\n" +
            "        ODD.QXBZ IN ('0', '3')\n" +
            "        AND odd.fyck_id != 'zy'\n" +
            "        AND ODD.FY_RQ BETWEEN par.kssj AND par.jssj\n" +
            "    GROUP BY\n" +
            "        ODD.FYYF_ID,\n" +
            "        ODD.SFXM_ID,\n" +
            "        ODD.YPCD_ID\n" +
            "),\n" +
            "ZYFYXX AS (\n" +
            "    SELECT\n" +
            "        ZYFY.FY_YFID AS DEPOT_ID,\n" +
            "        ZYFY.SFXM_ID DRUG_ID,\n" +
            "        ZYFY.CD_ID DRUG_AREA_ID,\n" +
            "        SUM(ZYFY.SFXM_SL * ZYFY.SFXM_DJ) JE\n" +
            "    FROM\n" +
            "        HIS.INPAT_DISPENS_DETAIL ZYFY\n" +
            "        CROSS JOIN PAR\n" +
            "        INNER JOIN KF_LIST KF ON ZYFY.FY_YFID = KF.KFID\n" +
            "    WHERE\n" +
            "        ZYFY.FY_STATUS IN ('2', '7')\n" +
            "        AND ZYFY.FY_RQ >= par.kssj\n" +
            "        AND ZYFY.FY_RQ <= par.jssj\n" +
            "    GROUP BY\n" +
            "        ZYFY.FY_YFID,\n" +
            "        ZYFY.SFXM_ID,\n" +
            "        ZYFY.CD_ID\n" +
            "),\n" +
            "KCXX AS (\n" +
            "    SELECT\n" +
            "        KC.DEPOT_ID,\n" +
            "        KC.DRUG_ID,\n" +
            "        KC.DRUG_AREA_ID\n" +
            "    FROM\n" +
            "        HIS.DRUG_STORAGE KC\n" +
            "        INNER JOIN KF_LIST KF ON KC.DEPOT_ID = KF.KFID\n" +
            "),\n" +
            "LAST_INFO AS (\n" +
            "    SELECT\n" +
            "        GROUPING(YP.DRUG_TYPE) XH,\n" +
            "        DECODE(YP.DRUG_TYPE,'10','西药','20','成药','30','草药') DRUG_TYPE,\n" +
            "        NVL (KF.DEPOT_NAME, '总计') AS 库房ID,\n" +
            "        SUM(NVL (B.期初结存进价, 0)) 上月结存,\n" +
            "        SUM(nvl (药库其他所有入库进价, 0) + nvl (其他药房出库给药房, 0)) 药房调拨拨入,\n" +
            "        SUM(nvl (药房出库给药房进价, 0) + nvl (药库其他所有出库进价, 0)) 药房调拨拨出,\n" +
            "        SUM(nvl (药房入库进价, 0) - nvl (药房出库进价, 0)) 本期新领,\n" +
            "        SUM(nvl (报益进价, 0) - nvl (报损进价, 0)) 损益,\n" +
            "        SUM(nvl (盘盈进价, 0) - nvl (盘亏进价, 0)) 盈亏,\n" +
            "        SUM(nvl (E.jhJE, 0) + nvl (F.JE, 0)) 本月销售,\n" +
            "        SUM(nvl (B.期末结存进价, 0)) 本月结存,\n" +
            "        SUM(NVL (B.期初结存进价, 0)) + SUM(nvl (药库其他所有入库进价, 0) + nvl (其他药房出库给药房, 0)) - SUM(nvl (药房出库给药房进价, 0) + nvl (药库其他所有出库进价, 0)) + SUM(nvl (药房入库进价, 0) - nvl (药房出库进价, 0)) + SUM(nvl (报益进价, 0) - nvl (报损进价, 0)) + SUM(nvl (盘盈进价, 0) - nvl (盘亏进价, 0)) - SUM(nvl (E.jhJE, 0) + nvl (F.JE, 0)) - SUM(nvl (B.期末结存进价, 0)) 尾差\n" +
            "    FROM\n" +
            "        KCXX A\n" +
            "        LEFT JOIN JCSJ B ON A.DEPOT_ID = B.DEPOT_ID\n" +
            "        AND A.DRUG_ID = B.DRUG_ID\n" +
            "        AND A.DRUG_AREA_ID = B.DRUG_AREA_ID\n" +
            "        LEFT JOIN YFRCK C ON A.DEPOT_ID = C.DEPOT_ID\n" +
            "        AND A.DRUG_ID = C.DRUG_ID\n" +
            "        AND A.DRUG_AREA_ID = C.DRUG_AREA_ID\n" +
            "        LEFT JOIN TJXX D ON A.DEPOT_ID = D.DEPOT_ID\n" +
            "        AND A.DRUG_ID = D.DRUG_ID\n" +
            "        AND A.DRUG_AREA_ID = D.DRUG_AREA_ID\n" +
            "        LEFT JOIN MZFYXX E ON A.DEPOT_ID = E.DEPOT_ID\n" +
            "        AND A.DRUG_ID = E.DRUG_ID\n" +
            "        AND A.DRUG_AREA_ID = E.DRUG_AREA_ID\n" +
            "        LEFT JOIN ZYFYXX F ON A.DEPOT_ID = F.DEPOT_ID\n" +
            "        AND A.DRUG_ID = F.DRUG_ID\n" +
            "        AND A.DRUG_AREA_ID = F.DRUG_AREA_ID\n" +
            "        LEFT JOIN DICTMANAGE.DRUG_INFO YP ON A.DRUG_ID = YP.DRUG_ID\n" +
            "        AND A.DRUG_AREA_ID = YP.AREA_ID\n" +
            "        LEFT JOIN DICTMANAGE.DICT_DEPOT KF ON A.DEPOT_ID = KF.DEPOT_CODE\n" +
            "    GROUP BY\n" +
            "        GROUPING SETS ((KF.DEPOT_NAME, YP.DRUG_TYPE), ())\n" +
            "    ORDER BY\n" +
            "        GROUPING(YP.DRUG_TYPE),\n" +
            "        KF.DEPOT_NAME\n" +
            ")\n" +
            "SELECT\n" +
            "    '上期结存'                                               AS 报表项,\n" +
            "    C.DRUG_TYPE                                              AS 药品类型,\n" +
            "    NVL(MAX(CASE WHEN C.库房ID = '门诊药房（总院）' THEN C.上月结存 END), 0) AS 门诊药房,\n" +
            "    NVL(MAX(CASE WHEN C.库房ID = '芜湖病区药房'   THEN C.上月结存 END), 0) AS 芜湖病区药房,\n" +
            "    NVL(MAX(CASE WHEN C.库房ID = '南陵药房'        THEN C.上月结存 END), 0) AS 南陵药房,\n" +
            "    NVL(MAX(CASE WHEN C.库房ID = '芜湖外配药房'   THEN C.上月结存 END), 0) AS 芜湖外配药房,\n" +
            "    NVL(MAX(CASE WHEN C.库房ID = '芜湖门诊药房'   THEN C.上月结存 END), 0) AS 芜湖门诊药房,\n" +
            "    NVL(MAX(CASE WHEN C.库房ID = '芜湖GCP药房'    THEN C.上月结存 END), 0) AS 芜湖GCP药房\n" +
            "FROM\n" +
            "    LAST_INFO C\n" +
            "WHERE\n" +
            "    C.DRUG_TYPE IS NOT NULL\n" +
            "GROUP BY\n" +
            "    C.DRUG_TYPE\n" +
            "UNION ALL\n" +
            "SELECT\n" +
            "    '药房调拨-拨入',\n" +
            "    C.DRUG_TYPE,\n" +
            "    NVL(MAX(CASE WHEN C.库房ID = '门诊药房（总院）' THEN C.药房调拨拨入 END), 0),\n" +
            "    NVL(MAX(CASE WHEN C.库房ID = '芜湖病区药房'   THEN C.药房调拨拨入 END), 0),\n" +
            "    NVL(MAX(CASE WHEN C.库房ID = '南陵药房'        THEN C.药房调拨拨入 END), 0),\n" +
            "    NVL(MAX(CASE WHEN C.库房ID = '芜湖外配药房'   THEN C.药房调拨拨入 END), 0),\n" +
            "    NVL(MAX(CASE WHEN C.库房ID = '芜湖门诊药房'   THEN C.药房调拨拨入 END), 0),\n" +
            "    NVL(MAX(CASE WHEN C.库房ID = '芜湖GCP药房'    THEN C.药房调拨拨入 END), 0)\n" +
            "FROM\n" +
            "    LAST_INFO C\n" +
            "WHERE\n" +
            "    C.DRUG_TYPE IS NOT NULL\n" +
            "GROUP BY\n" +
            "    C.DRUG_TYPE\n" +
            "UNION ALL\n" +
            "SELECT\n" +
            "    '药房调拨-拨出',\n" +
            "    C.DRUG_TYPE,\n" +
            "    NVL(MAX(CASE WHEN C.库房ID = '门诊药房（总院）' THEN C.药房调拨拨出 END), 0),\n" +
            "    NVL(MAX(CASE WHEN C.库房ID = '芜湖病区药房'   THEN C.药房调拨拨出 END), 0),\n" +
            "    NVL(MAX(CASE WHEN C.库房ID = '南陵药房'        THEN C.药房调拨拨出 END), 0),\n" +
            "    NVL(MAX(CASE WHEN C.库房ID = '芜湖外配药房'   THEN C.药房调拨拨出 END), 0),\n" +
            "    NVL(MAX(CASE WHEN C.库房ID = '芜湖门诊药房'   THEN C.药房调拨拨出 END), 0),\n" +
            "    NVL(MAX(CASE WHEN C.库房ID = '芜湖GCP药房'    THEN C.药房调拨拨出 END), 0)\n" +
            "FROM\n" +
            "    LAST_INFO C\n" +
            "WHERE\n" +
            "    C.DRUG_TYPE IS NOT NULL\n" +
            "GROUP BY\n" +
            "    C.DRUG_TYPE\n" +
            "UNION ALL\n" +
            "SELECT\n" +
            "    '本期新领',\n" +
            "    C.DRUG_TYPE,\n" +
            "    NVL(MAX(CASE WHEN C.库房ID = '门诊药房（总院）' THEN C.本期新领 END), 0),\n" +
            "    NVL(MAX(CASE WHEN C.库房ID = '芜湖病区药房'   THEN C.本期新领 END), 0),\n" +
            "    NVL(MAX(CASE WHEN C.库房ID = '南陵药房'        THEN C.本期新领 END), 0),\n" +
            "    NVL(MAX(CASE WHEN C.库房ID = '芜湖外配药房'   THEN C.本期新领 END), 0),\n" +
            "    NVL(MAX(CASE WHEN C.库房ID = '芜湖门诊药房'   THEN C.本期新领 END), 0),\n" +
            "    NVL(MAX(CASE WHEN C.库房ID = '芜湖GCP药房'    THEN C.本期新领 END), 0)\n" +
            "FROM\n" +
            "    LAST_INFO C\n" +
            "WHERE\n" +
            "    C.DRUG_TYPE IS NOT NULL\n" +
            "GROUP BY\n" +
            "    C.DRUG_TYPE\n" +
            "UNION ALL\n" +
            "SELECT\n" +
            "    '本期销售',\n" +
            "    C.DRUG_TYPE,\n" +
            "    NVL(MAX(CASE WHEN C.库房ID = '门诊药房（总院）' THEN C.本月销售 END), 0),\n" +
            "    NVL(MAX(CASE WHEN C.库房ID = '芜湖病区药房'   THEN C.本月销售 END), 0),\n" +
            "    NVL(MAX(CASE WHEN C.库房ID = '南陵药房'        THEN C.本月销售 END), 0),\n" +
            "    NVL(MAX(CASE WHEN C.库房ID = '芜湖外配药房'   THEN C.本月销售 END), 0),\n" +
            "    NVL(MAX(CASE WHEN C.库房ID = '芜湖门诊药房'   THEN C.本月销售 END), 0),\n" +
            "    NVL(MAX(CASE WHEN C.库房ID = '芜湖GCP药房'    THEN C.本月销售 END), 0)\n" +
            "FROM\n" +
            "    LAST_INFO C\n" +
            "WHERE\n" +
            "    C.DRUG_TYPE IS NOT NULL\n" +
            "GROUP BY\n" +
            "    C.DRUG_TYPE\n" +
            "UNION ALL\n" +
            "SELECT\n" +
            "    '本期结存',\n" +
            "    C.DRUG_TYPE,\n" +
            "    NVL(MAX(CASE WHEN C.库房ID = '门诊药房（总院）' THEN C.本月结存 END), 0),\n" +
            "    NVL(MAX(CASE WHEN C.库房ID = '芜湖病区药房'   THEN C.本月结存 END), 0),\n" +
            "    NVL(MAX(CASE WHEN C.库房ID = '南陵药房'        THEN C.本月结存 END), 0),\n" +
            "    NVL(MAX(CASE WHEN C.库房ID = '芜湖外配药房'   THEN C.本月结存 END), 0),\n" +
            "    NVL(MAX(CASE WHEN C.库房ID = '芜湖门诊药房'   THEN C.本月结存 END), 0),\n" +
            "    NVL(MAX(CASE WHEN C.库房ID = '芜湖GCP药房'    THEN C.本月结存 END), 0)\n" +
            "FROM\n" +
            "    LAST_INFO C\n" +
            "WHERE\n" +
            "    C.DRUG_TYPE IS NOT NULL\n" +
            "GROUP BY\n" +
            "    C.DRUG_TYPE\n" +
            "UNION ALL\n" +
            "SELECT\n" +
            "    '尾差',\n" +
            "    C.DRUG_TYPE,\n" +
            "    NVL(MAX(CASE WHEN C.库房ID = '门诊药房（总院）' THEN C.尾差 END), 0),\n" +
            "    NVL(MAX(CASE WHEN C.库房ID = '芜湖病区药房'   THEN C.尾差 END), 0),\n" +
            "    NVL(MAX(CASE WHEN C.库房ID = '南陵药房'        THEN C.尾差 END), 0),\n" +
            "    NVL(MAX(CASE WHEN C.库房ID = '芜湖外配药房'   THEN C.尾差 END), 0),\n" +
            "    NVL(MAX(CASE WHEN C.库房ID = '芜湖门诊药房'   THEN C.尾差 END), 0),\n" +
            "    NVL(MAX(CASE WHEN C.库房ID = '芜湖GCP药房'    THEN C.尾差 END), 0)\n" +
            "FROM\n" +
            "    LAST_INFO C\n" +
            "WHERE\n" +
            "    C.DRUG_TYPE IS NOT NULL\n" +
            "GROUP BY\n" +
            "    C.DRUG_TYPE\n" +
            "ORDER BY\n" +
            "    DECODE(报表项,'上期结存',1,'药房调拨-拨入',2,'药房调拨-拨出',3,\n" +
            "                  '本期新领',4,'本期销售',5,'本期结存',6,'尾差',7),\n" +
            "    DECODE(药品类型,'西药',1,'成药',2,'中药',3,4);";


}
