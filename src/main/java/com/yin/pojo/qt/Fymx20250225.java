package com.yin.pojo.qt;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class Fymx20250225 {
	@ExcelProperty("结算单据号")
	private String 结算单据号;
	@ExcelProperty("项目序号")
	private String 项目序号;
	@ExcelProperty("个人编码")
	private String 个人编码;
	@ExcelProperty("就医号")
	private String 就医号;
	@ExcelProperty("医疗机构编码")
	private String 医疗机构编码;
	@ExcelProperty("医疗机构名称")
	private String 医疗机构名称;
	@ExcelProperty("医保结算等级")
	private String 医保结算等级;
	@ExcelProperty("开单科室编码")
	private String 开单科室编码;
	@ExcelProperty("开单科室名称")
	private String 开单科室名称;
	@ExcelProperty("开单医师编码")
	private String 开单医师编码;
	@ExcelProperty("开单医师姓名")
	private String 开单医师姓名;
	@ExcelProperty("费用类别")
	private String 费用类别;
	@ExcelProperty("项目使用日期")
	private String 项目使用日期;
	@ExcelProperty("出院带药标识")
	private String 出院带药标识;
	@ExcelProperty("医院项目编码")
	private String 医院项目编码;
	@ExcelProperty("医院项目名称")
	private String 医院项目名称;
	@ExcelProperty("医保项目编码")
	private String 医保项目编码;
	@ExcelProperty("医保项目名称")
	private String 医保项目名称;
	@ExcelProperty("规格")
	private String 规格;
	@ExcelProperty("剂型")
	private String 剂型;
	@ExcelProperty("计价单位")
	private String 计价单位;
	@ExcelProperty("单价")
	private String 单价;
	@ExcelProperty("数量")
	private String 数量;
	@ExcelProperty("金额")
	private String 金额;
	@ExcelProperty("拒付金额")
	private String 拒付金额;
	@ExcelProperty("医保范围内金额")
	private String 医保范围内金额;
	@ExcelProperty("支付类别")
	private String 支付类别;
	@ExcelProperty("报销比例")
	private String 报销比例;
}