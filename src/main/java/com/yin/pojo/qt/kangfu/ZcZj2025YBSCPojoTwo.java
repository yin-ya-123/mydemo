package com.yin.pojo.qt.kangfu;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class ZcZj2025YBSCPojoTwo {
	@ExcelProperty("问题类别")
	private String 问题类别;
	@ExcelProperty("事项")
	private String 事项;
	@ExcelProperty("机构名称")
	private String 机构名称;
	@ExcelProperty("住院号/门诊号")
	private String 住院号;
	@ExcelProperty("参保人身份证号")
	private String 参保人身份证号;
	@ExcelProperty("参保人姓名")
	private String 参保人姓名;
	@ExcelProperty("结算时间（说明：yyyy-MM-ddHH:mm:ss）")
	private String 结算时间;
	@ExcelProperty("就医方式")
	private String 就医方式;
	@ExcelProperty("科室（说明：最大长度为30）")
	private String 科室;
	@ExcelProperty("医生（说明：最大长度为30）")
	private String 医生;
	@ExcelProperty("诊断（说明：最大长度为500）")
	private String 诊断;
	@ExcelProperty("医疗目录编码（MED_LIST_CODG）")
	private String 医疗目录编码;
	@ExcelProperty("医疗目录名称")
	private String 医疗目录名称;
	@ExcelProperty("收费项目等级（说明：甲类、乙类、丙类）")
	private String 收费项目等级;
	@ExcelProperty("费用发生时间（说明：yyyy-MM-ddHH:mm:ss）")
	private String 费用发生时间;
	@ExcelProperty("单价")
	private String 单价;
	@ExcelProperty("数量")
	private String 数量;
	@ExcelProperty("金额")
	private String 金额;
	@ExcelProperty("结算ID（SETL_ID）")
	private String 结算ID;
	@ExcelProperty("就诊ID（MDTRT_ID）")
	private String 就诊ID;
	@ExcelProperty("人员编号（PSN_NO）")
	private String 人员编号;
	@ExcelProperty("费用明细流水号（FEEDETL_SN）")
	private String 费用明细流水号;
}