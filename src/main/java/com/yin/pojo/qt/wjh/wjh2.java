package com.yin.pojo.qt.wjh;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class wjh2 {
	@ExcelProperty("名称")
	private String mc;
	@ExcelProperty("检索码")
	private String jsm;
	@ExcelProperty("拼音码")
	private String pym;
	@ExcelProperty("规格")
	private String gg;
	@ExcelProperty("项目分类编码")
	private String xmflbm;
	@ExcelProperty("项目分类名称")
	private String xmflmc;
	@ExcelProperty("费用类别")
	private String fylb;
	@ExcelProperty("价格")
	private String jg;
	@ExcelProperty("单位")
	private String dw;
	@ExcelProperty("项目代码")
	private String xmdm;
	@ExcelProperty("项目代码1")
	private String xmdm1;
	@ExcelProperty("病案编号")
	private String babh;
	@ExcelProperty("病案名称")
	private String bamc;
	@ExcelProperty("是否启用")
	private String sfqy;
}