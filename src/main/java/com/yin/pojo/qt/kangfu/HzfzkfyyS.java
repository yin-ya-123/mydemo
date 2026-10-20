package com.yin.pojo.qt.kangfu;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class HzfzkfyyS {
	@ExcelProperty("结算ID")
	private String jsID;
	@ExcelProperty("身份证号")
	private String sfzh;
	@ExcelProperty("人员姓名")
	private String ryxm;
	@ExcelProperty("机构编码")
	private String jgbm;
	@ExcelProperty("机构名称")
	private String jgmc;
	@ExcelProperty("入院时间")
	private String rysj;
	@ExcelProperty("出院时间")
	private String cysj;
}