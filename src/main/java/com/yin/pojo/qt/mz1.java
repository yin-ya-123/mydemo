package com.yin.pojo.qt;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class mz1 {
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
	@ExcelProperty("疾病诊断")
	private String 疾病诊断;
	@ExcelProperty("就诊日期")
	private String 就诊日期;
	@ExcelProperty("就诊号")
	private String 就诊号;
	@ExcelProperty("就诊科室")
	private String 就诊科室;
	@ExcelProperty("费用总额")
	private String 费用总额;
	@ExcelProperty("自费金额")
	private String 自费金额;
	@ExcelProperty("自理金额")
	private String 自理金额;
	@ExcelProperty("符合政策范围金额")
	private String 符合政策范围金额;
	@ExcelProperty("RET_JSXX")
	private String RET_JSXX;
}