package com.yin.utils.excel.main.pinghu.jxc.pojo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 尾差查询-总 实体类
 */
@Data
public class WeiChaTotal {

    /**
     * 医院名称
     */
    private String yymc;

    /**
     * 医院编号
     */
    private String hospSn;

    /**
     * 库房名称
     */
    private String depotName;

    /**
     * 库房代码
     */
    private String depotCode;

    /**
     * 尾差售价
     */
    private String weiChaShouJia;

    /**
     * 尾差进价
     */
    private String weiChaJinJia;

    /**
     * 期初结存进价
     */
    private BigDecimal qiChuJieCunJinJia;

    /**
     * 药房入库进价
     */
    private BigDecimal yaoFangRuKuJinJia;

    /**
     * 药房出库进价
     */
    private BigDecimal yaoFangChuKuJinJia;

    /**
     * 报益进价
     */
    private BigDecimal baoYiJinJia;

    /**
     * 药库其他所有入库进价
     */
    private BigDecimal yaoKuQiTaRuKuJinJia;

    /**
     * 其他药房调入进价
     */
    private BigDecimal qiTaYaoFangDiaoRuJinJia;

    /**
     * 盘盈进价
     */
    private BigDecimal panYingJinJia;

    /**
     * 收入合计进价
     */
    private BigDecimal shouRuHeJiJinJia;

    /**
     * 报损进价
     */
    private BigDecimal baoSunJinJia;

    /**
     * 药库其他所有出库进价
     */
    private BigDecimal yaoKuQiTaChuKuJinJia;

    /**
     * 门诊发药进价
     */
    private BigDecimal menZhenFaYaoJinJia;

    /**
     * 住院发药进价
     */
    private BigDecimal zhuYuanFaYaoJinJia;

    /**
     * 调出其他药房进价
     */
    private BigDecimal diaoChuQiTaYaoFangJinJia;

    /**
     * 盘亏进价
     */
    private BigDecimal panKuiJinJia;

    /**
     * 支出合计进价
     */
    private BigDecimal zhiChuHeJiJinJia;

    /**
     * 调价增值进价
     */
    private BigDecimal tiaoJiaZengZhiJinJia;

    /**
     * 调价减值进价
     */
    private BigDecimal tiaoJiaJianZhiJinJia;

    /**
     * 期末结存进价
     */
    private BigDecimal qiMoJieCunJinJia;

    /**
     * 期初结存售价
     */
    private BigDecimal qiChuJieCunShouJia;

    /**
     * 药房入库售价
     */
    private BigDecimal yaoFangRuKuShouJia;

    /**
     * 药房出库售价
     */
    private BigDecimal yaoFangChuKuShouJia;

    /**
     * 报益售价
     */
    private BigDecimal baoYiShouJia;

    /**
     * 药库其他所有入库售价
     */
    private BigDecimal yaoKuQiTaRuKuShouJia;

    /**
     * 其他药房调入售价
     */
    private BigDecimal qiTaYaoFangDiaoRuShouJia;

    /**
     * 盘盈售价
     */
    private BigDecimal panYingShouJia;

    /**
     * 收入合计售价
     */
    private BigDecimal shouRuHeJiShouJia;

    /**
     * 报损售价
     */
    private BigDecimal baoSunShouJia;

    /**
     * 药库其他所有出库售价
     */
    private BigDecimal yaoKuQiTaChuKuShouJia;

    /**
     * 门诊发药售价
     */
    private BigDecimal menZhenFaYaoShouJia;

    /**
     * 住院发药售价
     */
    private BigDecimal zhuYuanFaYaoShouJia;

    /**
     * 调出其他药房售价
     */
    private BigDecimal diaoChuQiTaYaoFangShouJia;

    /**
     * 盘亏售价
     */
    private BigDecimal panKuiShouJia;

    /**
     * 支出合计售价
     */
    private BigDecimal zhiChuHeJiShouJia;

    /**
     * 调价增值售价
     */
    private BigDecimal tiaoJiaZengZhiShouJia;

    /**
     * 调价减值售价
     */
    private BigDecimal tiaoJiaJianZhiShouJia;

    /**
     * 期末结存售价
     */
    private BigDecimal qiMoJieCunShouJia;
}
