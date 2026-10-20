package com.yin.pojo.qt;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class Zy1 {
	@ExcelProperty("姓名")
	private String 姓名;
	@ExcelProperty("性别")
	private String 性别;
	@ExcelProperty("身份证")
	private String 身份证;
	@ExcelProperty("现住址")
	private String 现住址;
	@ExcelProperty("医保号")
	private String 医保号;
	@ExcelProperty("医保类型")
	private String 医保类型;
	@ExcelProperty("入院诊断")
	private String 入院诊断;
	@ExcelProperty("入院日期")
	private String 入院日期;
	@ExcelProperty("出院日期")
	private String 出院日期;
	@ExcelProperty("住院号")
	private String 住院号;
	@ExcelProperty("病区")
	private String 病区;
	@ExcelProperty("费用总额")
	private String 费用总额;
	@ExcelProperty("自费金额")
	private String 自费金额;
	@ExcelProperty("自理金额")
	private String 自理金额;
	@ExcelProperty("RET_JSXX")
	private String RET_JSXX;
	@ExcelProperty("符合政策范围金额")
	private String 符合政策范围金额;
}