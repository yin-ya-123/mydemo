package com.yin.utils.excel.main.pinghu.jxc.converter;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import com.yin.utils.excel.main.pinghu.jxc.pojo.Depot;
import com.yin.utils.excel.main.pinghu.jxc.pojo.Kcxx;
import com.yin.utils.excel.main.pinghu.jxc.pojo.WeiChaDate;
import com.yin.utils.excel.main.pinghu.jxc.pojo.WeiChaDrug;
import com.yin.utils.excel.main.pinghu.jxc.pojo.WeiChaTotal;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 尾差查询实体类转换工具
 */
public class WeiChaConverter {

    /**
     * 转换尾差查询-总
     */
    public static List<WeiChaTotal> convertToWeiChaTotal(List<Map<String, Object>> list) {
        List<WeiChaTotal> result = new ArrayList<>();
        for (Map<String, Object> map : list) {
            WeiChaTotal entity = new WeiChaTotal();
            entity.setYymc(MapUtil.getStr(map, "YYMC"));
            entity.setHospSn(MapUtil.getStr(map, "HOSP_SN"));
            entity.setDepotName(MapUtil.getStr(map, "库房名称"));
            entity.setDepotCode(MapUtil.getStr(map, "DEPOT_CODE"));
            entity.setWeiChaShouJia(MapUtil.getStr(map, "尾差售价"));
            entity.setWeiChaJinJia(MapUtil.getStr(map, "尾差进价"));
            entity.setQiChuJieCunJinJia(getBigDecimal(map, "期初结存进价"));
            entity.setYaoFangRuKuJinJia(getBigDecimal(map, "药房入库进价"));
            entity.setYaoFangChuKuJinJia(getBigDecimal(map, "药房出库进价"));
            entity.setBaoYiJinJia(getBigDecimal(map, "报益进价"));
            entity.setYaoKuQiTaRuKuJinJia(getBigDecimal(map, "药库其他所有入库进价"));
            entity.setQiTaYaoFangDiaoRuJinJia(getBigDecimal(map, "其他药房调入进价"));
            entity.setPanYingJinJia(getBigDecimal(map, "盘盈进价"));
            entity.setShouRuHeJiJinJia(getBigDecimal(map, "收入合计进价"));
            entity.setBaoSunJinJia(getBigDecimal(map, "报损进价"));
            entity.setYaoKuQiTaChuKuJinJia(getBigDecimal(map, "药库其他所有出库进价"));
            entity.setMenZhenFaYaoJinJia(getBigDecimal(map, "门诊发药进价"));
            entity.setZhuYuanFaYaoJinJia(getBigDecimal(map, "住院发药进价"));
            entity.setDiaoChuQiTaYaoFangJinJia(getBigDecimal(map, "调出其他药房进价"));
            entity.setPanKuiJinJia(getBigDecimal(map, "盘亏进价"));
            entity.setZhiChuHeJiJinJia(getBigDecimal(map, "支出合计进价"));
            entity.setTiaoJiaZengZhiJinJia(getBigDecimal(map, "调价增值进价"));
            entity.setTiaoJiaJianZhiJinJia(getBigDecimal(map, "调价减值进价"));
            entity.setQiMoJieCunJinJia(getBigDecimal(map, "期末结存进价"));
            entity.setQiChuJieCunShouJia(getBigDecimal(map, "期初结存售价"));
            entity.setYaoFangRuKuShouJia(getBigDecimal(map, "药房入库售价"));
            entity.setYaoFangChuKuShouJia(getBigDecimal(map, "药房出库售价"));
            entity.setBaoYiShouJia(getBigDecimal(map, "报益售价"));
            entity.setYaoKuQiTaRuKuShouJia(getBigDecimal(map, "药库其他所有入库售价"));
            entity.setQiTaYaoFangDiaoRuShouJia(getBigDecimal(map, "其他药房调入售价"));
            entity.setPanYingShouJia(getBigDecimal(map, "盘盈售价"));
            entity.setShouRuHeJiShouJia(getBigDecimal(map, "收入合计售价"));
            entity.setBaoSunShouJia(getBigDecimal(map, "报损售价"));
            entity.setYaoKuQiTaChuKuShouJia(getBigDecimal(map, "药库其他所有出库售价"));
            entity.setMenZhenFaYaoShouJia(getBigDecimal(map, "门诊发药售价"));
            entity.setZhuYuanFaYaoShouJia(getBigDecimal(map, "住院发药售价"));
            entity.setDiaoChuQiTaYaoFangShouJia(getBigDecimal(map, "调出其他药房售价"));
            entity.setPanKuiShouJia(getBigDecimal(map, "盘亏售价"));
            entity.setZhiChuHeJiShouJia(getBigDecimal(map, "支出合计售价"));
            entity.setTiaoJiaZengZhiShouJia(getBigDecimal(map, "调价增值售价"));
            entity.setTiaoJiaJianZhiShouJia(getBigDecimal(map, "调价减值售价"));
            entity.setQiMoJieCunShouJia(getBigDecimal(map, "期末结存售价"));
            result.add(entity);
        }
        return result;
    }

    /**
     * 转换尾差查询-药品
     */
    public static List<WeiChaDrug> convertToWeiChaDrug(List<Map<String, Object>> list) {
        List<WeiChaDrug> result = new ArrayList<>();
        for (Map<String, Object> map : list) {
            WeiChaDrug entity = new WeiChaDrug();
            entity.setAbsWeiChaJinJia(getBigDecimal(map, "ABS(尾差进价)"));
            entity.setYymc(MapUtil.getStr(map, "YYMC"));
            entity.setHospSn(MapUtil.getStr(map, "HOSP_SN"));
            entity.setDepotName(MapUtil.getStr(map, "库房名称"));
            entity.setDepotCode(MapUtil.getStr(map, "DEPOT_CODE"));
            entity.setDrugId(MapUtil.getStr(map, "DRUG_ID"));
            entity.setDrugAreaId(MapUtil.getStr(map, "DRUG_AREA_ID"));
            entity.setDrugName(MapUtil.getStr(map, "DRUG_NAME"));
            entity.setShuLiangWeiCha(getBigDecimal(map, "数量尾差"));
            entity.setWeiChaShouJia(getBigDecimal(map, "尾差售价"));
            entity.setWeiChaJinJia(getBigDecimal(map, "尾差进价"));
            entity.setQiChuJieCunShuLiang(getBigDecimal(map, "期初结存数量"));
            entity.setYaoFangRuKuShuLiang(getBigDecimal(map, "药房入库数量"));
            entity.setYaoFangChuKuShuLiang(getBigDecimal(map, "药房出库数量"));
            entity.setBaoYiShuLiang(getBigDecimal(map, "报益数量"));
            entity.setYaoKuQiTaRuKuShuLiang(getBigDecimal(map, "药库其他所有入库数量"));
            entity.setQiTaYaoFangDiaoRuShuLiang(getBigDecimal(map, "其他药房调入数量"));
            entity.setPanYingShuLiang(getBigDecimal(map, "盘盈数量"));
            entity.setShouRuHeJiShuLiang(getBigDecimal(map, "收入合计数量"));
            entity.setBaoSunShuLiang(getBigDecimal(map, "报损数量"));
            entity.setYaoKuQiTaChuKuShuLiang(getBigDecimal(map, "药库其他所有出库数量"));
            entity.setMenZhenFaYaoShuLiang(getBigDecimal(map, "门诊发药数量"));
            entity.setZhuYuanFaYaoShuLiang(getBigDecimal(map, "住院发药数量"));
            entity.setDiaoChuQiTaYaoFangShuLiang(getBigDecimal(map, "调出其他药房数量"));
            entity.setPanKuiShuLiang(getBigDecimal(map, "盘亏数量"));
            entity.setZhiChuHeJiShuLiang(getBigDecimal(map, "支出合计数量"));
            entity.setQiMoJieCunShuLiang(getBigDecimal(map, "期末结存数量"));
            entity.setQiChuJieCunJinJia(getBigDecimal(map, "期初结存进价"));
            entity.setYaoFangRuKuJinJia(getBigDecimal(map, "药房入库进价"));
            entity.setYaoFangChuKuJinJia(getBigDecimal(map, "药房出库进价"));
            entity.setBaoYiJinJia(getBigDecimal(map, "报益进价"));
            entity.setYaoKuQiTaRuKuJinJia(getBigDecimal(map, "药库其他所有入库进价"));
            entity.setQiTaYaoFangDiaoRuJinJia(getBigDecimal(map, "其他药房调入进价"));
            entity.setPanYingJinJia(getBigDecimal(map, "盘盈进价"));
            entity.setShouRuHeJiJinJia(getBigDecimal(map, "收入合计进价"));
            entity.setBaoSunJinJia(getBigDecimal(map, "报损进价"));
            entity.setYaoKuQiTaChuKuJinJia(getBigDecimal(map, "药库其他所有出库进价"));
            entity.setMenZhenFaYaoJinJia(getBigDecimal(map, "门诊发药进价"));
            entity.setZhuYuanFaYaoJinJia(getBigDecimal(map, "住院发药进价"));
            entity.setDiaoChuQiTaYaoFangJinJia(getBigDecimal(map, "调出其他药房进价"));
            entity.setPanKuiJinJia(getBigDecimal(map, "盘亏进价"));
            entity.setZhiChuHeJiJinJia(getBigDecimal(map, "支出合计进价"));
            entity.setTiaoJiaZengZhiJinJia(getBigDecimal(map, "调价增值进价"));
            entity.setTiaoJiaJianZhiJinJia(getBigDecimal(map, "调价减值进价"));
            entity.setQiMoJieCunJinJia(getBigDecimal(map, "期末结存进价"));
            entity.setQiChuJieCunShouJia(getBigDecimal(map, "期初结存售价"));
            entity.setYaoFangRuKuShouJia(getBigDecimal(map, "药房入库售价"));
            entity.setYaoFangChuKuShouJia(getBigDecimal(map, "药房出库售价"));
            entity.setBaoYiShouJia(getBigDecimal(map, "报益售价"));
            entity.setYaoKuQiTaRuKuShouJia(getBigDecimal(map, "药库其他所有入库售价"));
            entity.setQiTaYaoFangDiaoRuShouJia(getBigDecimal(map, "其他药房调入售价"));
            entity.setPanYingShouJia(getBigDecimal(map, "盘盈售价"));
            entity.setShouRuHeJiShouJia(getBigDecimal(map, "收入合计售价"));
            entity.setBaoSunShouJia(getBigDecimal(map, "报损售价"));
            entity.setYaoKuQiTaChuKuShouJia(getBigDecimal(map, "药库其他所有出库售价"));
            entity.setMenZhenFaYaoShouJia(getBigDecimal(map, "门诊发药售价"));
            entity.setZhuYuanFaYaoShouJia(getBigDecimal(map, "住院发药售价"));
            entity.setDiaoChuQiTaYaoFangShouJia(getBigDecimal(map, "调出其他药房售价"));
            entity.setPanKuiShouJia(getBigDecimal(map, "盘亏售价"));
            entity.setZhiChuHeJiShouJia(getBigDecimal(map, "支出合计售价"));
            entity.setTiaoJiaZengZhiShouJia(getBigDecimal(map, "调价增值售价"));
            entity.setTiaoJiaJianZhiShouJia(getBigDecimal(map, "调价减值售价"));
            entity.setQiMoJieCunShouJia(getBigDecimal(map, "期末结存售价"));
            result.add(entity);
        }
        return result;
    }

    /**
     * 转换尾差查询-日期
     */
    public static List<WeiChaDate> convertToWeiChaDate(List<Map<String, Object>> list) {
        List<WeiChaDate> result = new ArrayList<>();
        for (Map<String, Object> map : list) {
            WeiChaDate entity = new WeiChaDate();
            entity.setRq(MapUtil.get(map, "RQ", java.util.Date.class));
            entity.setYymc(MapUtil.getStr(map, "YYMC"));
            entity.setHospSn(MapUtil.getStr(map, "HOSP_SN"));
            entity.setDepotName(MapUtil.getStr(map, "库房名称"));
            entity.setDepotCode(MapUtil.getStr(map, "DEPOT_CODE"));
            entity.setDrugId(MapUtil.getStr(map, "DRUG_ID"));
            entity.setDrugAreaId(MapUtil.getStr(map, "DRUG_AREA_ID"));
            entity.setDrugName(MapUtil.getStr(map, "DRUG_NAME"));
            entity.setQiChuRiQi(MapUtil.get(map, "期初日期", java.util.Date.class));
            entity.setQiMoRiQi(MapUtil.get(map, "期末日期", java.util.Date.class));
            entity.setShuLiangWeiCha(getBigDecimal(map, "数量尾差"));
            entity.setWeiChaShouJia(getBigDecimal(map, "尾差售价"));
            entity.setWeiChaJinJia(getBigDecimal(map, "尾差进价"));
            entity.setQiChuJieCunShuLiang(getBigDecimal(map, "期初结存数量"));
            entity.setYaoFangRuKuShuLiang(getBigDecimal(map, "药房入库数量"));
            entity.setYaoFangChuKuShuLiang(getBigDecimal(map, "药房出库数量"));
            entity.setBaoYiShuLiang(getBigDecimal(map, "报益数量"));
            entity.setYaoKuQiTaRuKuShuLiang(getBigDecimal(map, "药库其他所有入库数量"));
            entity.setQiTaYaoFangDiaoRuShuLiang(getBigDecimal(map, "其他药房调入数量"));
            entity.setPanYingShuLiang(getBigDecimal(map, "盘盈数量"));
            entity.setShouRuHeJiShuLiang(getBigDecimal(map, "收入合计数量"));
            entity.setBaoSunShuLiang(getBigDecimal(map, "报损数量"));
            entity.setYaoKuQiTaChuKuShuLiang(getBigDecimal(map, "药库其他所有出库数量"));
            entity.setMenZhenFaYaoShuLiang(getBigDecimal(map, "门诊发药数量"));
            entity.setZhuYuanFaYaoShuLiang(getBigDecimal(map, "住院发药数量"));
            entity.setDiaoChuQiTaYaoFangShuLiang(getBigDecimal(map, "调出其他药房数量"));
            entity.setPanKuiShuLiang(getBigDecimal(map, "盘亏数量"));
            entity.setZhiChuHeJiShuLiang(getBigDecimal(map, "支出合计数量"));
            entity.setQiMoJieCunShuLiang(getBigDecimal(map, "期末结存数量"));
            entity.setRuChuKuShuLiang(getBigDecimal(map, "入出库数量"));
            entity.setQiChuJieCunJinJia(getBigDecimal(map, "期初结存进价"));
            entity.setYaoFangRuKuJinJia(getBigDecimal(map, "药房入库进价"));
            entity.setYaoFangChuKuJinJia(getBigDecimal(map, "药房出库进价"));
            entity.setBaoYiJinJia(getBigDecimal(map, "报益进价"));
            entity.setYaoKuQiTaRuKuJinJia(getBigDecimal(map, "药库其他所有入库进价"));
            entity.setQiTaYaoFangDiaoRuJinJia(getBigDecimal(map, "其他药房调入进价"));
            entity.setPanYingJinJia(getBigDecimal(map, "盘盈进价"));
            entity.setShouRuHeJiJinJia(getBigDecimal(map, "收入合计进价"));
            entity.setBaoSunJinJia(getBigDecimal(map, "报损进价"));
            entity.setYaoKuQiTaChuKuJinJia(getBigDecimal(map, "药库其他所有出库进价"));
            entity.setMenZhenFaYaoJinJia(getBigDecimal(map, "门诊发药进价"));
            entity.setZhuYuanFaYaoJinJia(getBigDecimal(map, "住院发药进价"));
            entity.setDiaoChuQiTaYaoFangJinJia(getBigDecimal(map, "调出其他药房进价"));
            entity.setPanKuiJinJia(getBigDecimal(map, "盘亏进价"));
            entity.setZhiChuHeJiJinJia(getBigDecimal(map, "支出合计进价"));
            entity.setTiaoJiaZengZhiJinJia(getBigDecimal(map, "调价增值进价"));
            entity.setTiaoJiaJianZhiJinJia(getBigDecimal(map, "调价减值进价"));
            entity.setQiMoJieCunJinJia(getBigDecimal(map, "期末结存进价"));
            entity.setShouJiaBuFenBiaoJi(MapUtil.getStr(map, "========== 售价部分 =========="));
            entity.setQiChuJieCunShouJia(getBigDecimal(map, "期初结存售价"));
            entity.setYaoFangRuKuShouJia(getBigDecimal(map, "药房入库售价"));
            entity.setYaoFangChuKuShouJia(getBigDecimal(map, "药房出库售价"));
            entity.setBaoYiShouJia(getBigDecimal(map, "报益售价"));
            entity.setYaoKuQiTaRuKuShouJia(getBigDecimal(map, "药库其他所有入库售价"));
            entity.setQiTaYaoFangDiaoRuShouJia(getBigDecimal(map, "其他药房调入售价"));
            entity.setPanYingShouJia(getBigDecimal(map, "盘盈售价"));
            entity.setShouRuHeJiShouJia(getBigDecimal(map, "收入合计售价"));
            entity.setBaoSunShouJia(getBigDecimal(map, "报损售价"));
            entity.setYaoKuQiTaChuKuShouJia(getBigDecimal(map, "药库其他所有出库售价"));
            entity.setMenZhenFaYaoShouJia(getBigDecimal(map, "门诊发药售价"));
            entity.setZhuYuanFaYaoShouJia(getBigDecimal(map, "住院发药售价"));
            entity.setDiaoChuQiTaYaoFangShouJia(getBigDecimal(map, "调出其他药房售价"));
            entity.setPanKuiShouJia(getBigDecimal(map, "盘亏售价"));
            entity.setZhiChuHeJiShouJia(getBigDecimal(map, "支出合计售价"));
            entity.setTiaoJiaZengZhiShouJia(getBigDecimal(map, "调价增值售价"));
            entity.setTiaoJiaJianZhiShouJia(getBigDecimal(map, "调价减值售价"));
            entity.setQiMoJieCunShouJia(getBigDecimal(map, "期末结存售价"));
            result.add(entity);
        }
        return result;
    }

    /**
     * 转换库房
     */
    public static List<Depot> convertToDepot(List<Map<String, Object>> list) {
        List<Depot> result = new ArrayList<>();
        for (Map<String, Object> map : list) {
            Depot entity = new Depot();
            entity.setDepotCode(MapUtil.getStr(map, "DEPOT_CODE"));
            entity.setDepotName(MapUtil.getStr(map, "DEPOT_NAME"));
            entity.setDepotDepart(MapUtil.getStr(map, "DEPOT_DEPART"));
            entity.setType(MapUtil.getStr(map, "TYPE"));
            entity.setExDepotType(MapUtil.getStr(map, "EX_DEPOT_TYPE"));
            entity.setDelFlag(MapUtil.getStr(map, "DEL_FLAG"));
            entity.setPinyin(MapUtil.getStr(map, "PINYIN"));
            entity.setZdym(MapUtil.getStr(map, "ZDYM"));
            entity.setCreateDate(MapUtil.get(map, "CREATE_DATE", Date.class));
            entity.setCreateEmpe(MapUtil.getStr(map, "CREATE_EMPE"));
            entity.setUpdateDate(MapUtil.get(map, "UPDATE_DATE", Date.class));
            entity.setUpdateEmpe(MapUtil.getStr(map, "UPDATE_EMPE"));
            entity.setDispenseObject(MapUtil.getStr(map, "DISPENSE_OBJECT"));
            entity.setAmWorkStartTime(MapUtil.getStr(map, "AM_WORK_START_TIME"));
            entity.setAmWorkEndTime(MapUtil.getStr(map, "AM_WORK_END_TIME"));
            entity.setPmWorkStartTime(MapUtil.getStr(map, "PM_WORK_START_TIME"));
            entity.setPmWorkEndTime(MapUtil.getStr(map, "PM_WORK_END_TIME"));
            entity.setDrugManagement(MapUtil.getStr(map, "DRUG_MANAGEMENT"));
            entity.setInventoryFlag(MapUtil.getStr(map, "INVENTORY_FLAG"));
            entity.setParentDepotCode(MapUtil.getStr(map, "PARENT_DEPOT_CODE"));
            entity.setAllowUndepotApply(MapUtil.getStr(map, "ALLOW_UNDEPOT_APPLY"));
            entity.setAllowSubdepotDispenses(MapUtil.getStr(map, "ALLOW_SUBDEPOT_DISPENSES"));
            entity.setManualWin(MapUtil.getStr(map, "MANUAL_WIN"));
            entity.setManualRule(MapUtil.getStr(map, "MANUAL_RULE"));
            entity.setBranchCode(MapUtil.getStr(map, "BRANCH_CODE"));
            entity.setHospitalCode(MapUtil.getStr(map, "HOSPITAL_CODE"));
            entity.setUsersGroup(MapUtil.getStr(map, "USERS_GROUP"));
            entity.setDispenseFlag(MapUtil.getStr(map, "DISPENSE_FLAG"));
            entity.setDepotType(MapUtil.getStr(map, "DEPOT_TYPE"));
            entity.setDispenseType(MapUtil.getStr(map, "DISPENSE_TYPE"));
            entity.setPivasFlag(MapUtil.getStr(map, "PIVAS_FLAG"));
            entity.setOtherCode(MapUtil.getStr(map, "OTHER_CODE"));
            entity.setPosition(MapUtil.getStr(map, "POSITION"));
            entity.setInventoryManaType(MapUtil.getStr(map, "INVENTORY_MANA_TYPE"));
            entity.setAllowNightReturnFlag(MapUtil.getStr(map, "ALLOW_NIGHT_RETURN_FLAG"));
            entity.setAllowNightDispenseFlag(MapUtil.getStr(map, "ALLOW_NIGHT_DISPENSE_FLAG"));
            result.add(entity);
        }
        return result;
    }

    /**
     * 转换库存信息
     */
    public static List<Kcxx> convertToKcxx(List<Map<String, Object>> list) {
        List<Kcxx> result = new ArrayList<>();
        for (Map<String, Object> map : list) {
            Kcxx entity = new Kcxx();
            entity.setRecordDate(MapUtil.getStr(map, "RECORD_DATE"));
            entity.setRecordId(MapUtil.getStr(map, "RECORD_ID"));
            entity.setIniId(MapUtil.getStr(map, "INI_ID"));
            entity.setBatchNo(MapUtil.getStr(map, "BATCH_NO"));
            entity.setDepotId(MapUtil.getStr(map, "DEPOT_ID"));
            entity.setDrugId(MapUtil.getStr(map, "DRUG_ID"));
            entity.setRealStorage(getBigDecimal(map, "REAL_STORAGE"));
            result.add(entity);
        }
        return result;
    }

    /**
     * 获取BigDecimal值，处理null情况
     */
    private static BigDecimal getBigDecimal(Map<String, Object> map, String key) {
        Object value = map.get(key);
        if (value == null) {
            return BigDecimal.ZERO;
        }
        if (value instanceof BigDecimal) {
            return (BigDecimal) value;
        }
        if (value instanceof Number) {
            return new BigDecimal(value.toString());
        }
        if (value instanceof String) {
            String str = ((String) value).trim();
            if (str.isEmpty()) {
                return BigDecimal.ZERO;
            }
            return new BigDecimal(str);
        }
        return BigDecimal.ZERO;
    }
}
