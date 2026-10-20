package com.yin.pojo;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author YIN
 * @since 2024-02-21
 */
@TableName("GLOBAL_TEMP_TABLE_NAME_MB1")
public class GlobalTempTableNameMb1 implements Serializable {

    private static final long serialVersionUID = 1L;

    private String 序号;

    private String 疑点明细编号;

    private String 单据号;

    private String 住院号;

    private String 医疗类别;

    private String 结算编码;

    private String 费用明细编码;

    private String 费用日期;

    private String 参保人姓名;

    private String 个人编号;

    private String 身份证号;

    private String 医保卡号;

    private String 参保类型;

    private String 开单医师;

    private String 开单科室;

    private String 项目编码;

    private String 项目名称;

    private String 院内编码;

    private String 院内名称;

    private String 数量;

    private String 总金额;

    private String 列支金额;

    private String 违规金额;

    private String 规则名称;

    private String 违规定性;

    private String 审核意见;

    public String get序号() {
        return 序号;
    }

    public void set序号(String 序号) {
        this.序号 = 序号;
    }

    public String get疑点明细编号() {
        return 疑点明细编号;
    }

    public void set疑点明细编号(String 疑点明细编号) {
        this.疑点明细编号 = 疑点明细编号;
    }

    public String get单据号() {
        return 单据号;
    }

    public void set单据号(String 单据号) {
        this.单据号 = 单据号;
    }

    public String get住院号() {
        return 住院号;
    }

    public void set住院号(String 住院号) {
        this.住院号 = 住院号;
    }

    public String get医疗类别() {
        return 医疗类别;
    }

    public void set医疗类别(String 医疗类别) {
        this.医疗类别 = 医疗类别;
    }

    public String get结算编码() {
        return 结算编码;
    }

    public void set结算编码(String 结算编码) {
        this.结算编码 = 结算编码;
    }

    public String get费用明细编码() {
        return 费用明细编码;
    }

    public void set费用明细编码(String 费用明细编码) {
        this.费用明细编码 = 费用明细编码;
    }

    public String get费用日期() {
        return 费用日期;
    }

    public void set费用日期(String 费用日期) {
        this.费用日期 = 费用日期;
    }

    public String get参保人姓名() {
        return 参保人姓名;
    }

    public void set参保人姓名(String 参保人姓名) {
        this.参保人姓名 = 参保人姓名;
    }

    public String get个人编号() {
        return 个人编号;
    }

    public void set个人编号(String 个人编号) {
        this.个人编号 = 个人编号;
    }

    public String get身份证号() {
        return 身份证号;
    }

    public void set身份证号(String 身份证号) {
        this.身份证号 = 身份证号;
    }

    public String get医保卡号() {
        return 医保卡号;
    }

    public void set医保卡号(String 医保卡号) {
        this.医保卡号 = 医保卡号;
    }

    public String get参保类型() {
        return 参保类型;
    }

    public void set参保类型(String 参保类型) {
        this.参保类型 = 参保类型;
    }

    public String get开单医师() {
        return 开单医师;
    }

    public void set开单医师(String 开单医师) {
        this.开单医师 = 开单医师;
    }

    public String get开单科室() {
        return 开单科室;
    }

    public void set开单科室(String 开单科室) {
        this.开单科室 = 开单科室;
    }

    public String get项目编码() {
        return 项目编码;
    }

    public void set项目编码(String 项目编码) {
        this.项目编码 = 项目编码;
    }

    public String get项目名称() {
        return 项目名称;
    }

    public void set项目名称(String 项目名称) {
        this.项目名称 = 项目名称;
    }

    public String get院内编码() {
        return 院内编码;
    }

    public void set院内编码(String 院内编码) {
        this.院内编码 = 院内编码;
    }

    public String get院内名称() {
        return 院内名称;
    }

    public void set院内名称(String 院内名称) {
        this.院内名称 = 院内名称;
    }

    public String get数量() {
        return 数量;
    }

    public void set数量(String 数量) {
        this.数量 = 数量;
    }

    public String get总金额() {
        return 总金额;
    }

    public void set总金额(String 总金额) {
        this.总金额 = 总金额;
    }

    public String get列支金额() {
        return 列支金额;
    }

    public void set列支金额(String 列支金额) {
        this.列支金额 = 列支金额;
    }

    public String get违规金额() {
        return 违规金额;
    }

    public void set违规金额(String 违规金额) {
        this.违规金额 = 违规金额;
    }

    public String get规则名称() {
        return 规则名称;
    }

    public void set规则名称(String 规则名称) {
        this.规则名称 = 规则名称;
    }

    public String get违规定性() {
        return 违规定性;
    }

    public void set违规定性(String 违规定性) {
        this.违规定性 = 违规定性;
    }

    public String get审核意见() {
        return 审核意见;
    }

    public void set审核意见(String 审核意见) {
        this.审核意见 = 审核意见;
    }

    @Override
    public String toString() {
        return "GlobalTempTableNameMb1{" +
                "序号=" + 序号 +
                ", 疑点明细编号=" + 疑点明细编号 +
                ", 单据号=" + 单据号 +
                ", 住院号=" + 住院号 +
                ", 医疗类别=" + 医疗类别 +
                ", 结算编码=" + 结算编码 +
                ", 费用明细编码=" + 费用明细编码 +
                ", 费用日期=" + 费用日期 +
                ", 参保人姓名=" + 参保人姓名 +
                ", 个人编号=" + 个人编号 +
                ", 身份证号=" + 身份证号 +
                ", 医保卡号=" + 医保卡号 +
                ", 参保类型=" + 参保类型 +
                ", 开单医师=" + 开单医师 +
                ", 开单科室=" + 开单科室 +
                ", 项目编码=" + 项目编码 +
                ", 项目名称=" + 项目名称 +
                ", 院内编码=" + 院内编码 +
                ", 院内名称=" + 院内名称 +
                ", 数量=" + 数量 +
                ", 总金额=" + 总金额 +
                ", 列支金额=" + 列支金额 +
                ", 违规金额=" + 违规金额 +
                ", 规则名称=" + 规则名称 +
                ", 违规定性=" + 违规定性 +
                ", 审核意见=" + 审核意见 +
                "}";
    }
}
