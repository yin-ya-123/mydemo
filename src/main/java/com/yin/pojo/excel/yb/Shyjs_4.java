package com.yin.pojo.excel.yb;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 第四个模板 预剔除费用表
 */
@Data
@EqualsAndHashCode
public class Shyjs_4 {
    //序号  疑点明细编号  单据号 住院号(门诊号)  医疗类别  结算编码  费用明细编码  费用日期  参保人姓名 个人编号  身份证号  参保类型  开单医师  开单科室  项目编码  项目名称  院内编码  院内名称  数量  总金额 列支金额  违规金额  规则名称  违规定性  审核意见
    @ExcelProperty("序号")
    private String xh;
    @ExcelProperty("疑点明细编号")
    private String ydmdbh;
    @ExcelProperty("单据号")
    private String djh;
    @ExcelProperty("住院号(门诊号)")
    private String zyh;
    @ExcelProperty("医疗类别")
    private String yllb;
    @ExcelProperty("结算编码")
    private String jsbm;
    @ExcelProperty("费用明细编码")
    private String fymxbm;
    @ExcelProperty("费用日期")
    private String fyrq;
    @ExcelProperty("参保人姓名")
    private String cbrxm;
    @ExcelProperty("个人编号")
    private String grbh;
    @ExcelProperty("身份证号")
    private String sfzh;
    @ExcelProperty("参保类型")
    private String cblx;
    @ExcelProperty("开单医生")
    private String kdys;
    @ExcelProperty("开单科室")
    private String kdks;
    @ExcelProperty("项目编码")
    private String xmbm;
    @ExcelProperty("项目名称")
    private String xmmc;
    @ExcelProperty("院内编码")
    private String ynbmb;
    @ExcelProperty("院内名称")
    private String ynbmc;
    @ExcelProperty("数量")
    private String sl;
    @ExcelProperty("单价")
    private String dj;
    @ExcelProperty("总金额")
    private String zje;
    @ExcelProperty("列支金额")
    private String lzje;
    @ExcelProperty("预剔除金额")
    private String ytcje;
    @ExcelProperty("基金拨付比")
    private String jjbfb;
    @ExcelProperty("实际预剔除金额")
    private String sjytcje;
    @ExcelProperty("违规内容")
    private String wgnr;
    @ExcelProperty("审核意见")
    private String shyj;
}
