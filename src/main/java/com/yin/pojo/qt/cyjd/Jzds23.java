package com.yin.pojo.qt.cyjd;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class Jzds23 {
	@ExcelProperty("清算年度")
	private String qsnd;
	@ExcelProperty("定点归属医保区划")
	private String ddgsybqh;
	@ExcelProperty("DRG编码")
	private String DRGbm;
	@ExcelProperty("DRG名称")
	private String DRGmc;
	@ExcelProperty("基准点数")
	private String jzds;
	@ExcelProperty("例均费用")
	private String ljfy;
	@ExcelProperty("平均住院天数")
	private String pjzyts;
	@ExcelProperty("基准点数2(年度)")
	private String jzds2;
	@ExcelProperty("22年基准点数")
	private String njzds;
	@ExcelProperty("22年例均费用")
	private String nljfy;
	@ExcelProperty("例均费用(年度)")
	private String ljfy2;
	@ExcelProperty("平均住院天数(年度)")
	private String pjzyts2;
	@ExcelProperty("歧义病组")
	private String qybz;
}