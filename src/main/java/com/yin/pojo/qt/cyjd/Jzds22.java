package com.yin.pojo.qt.cyjd;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class Jzds22 {
	@ExcelProperty("序号")
	private String xh;
	@ExcelProperty("分组编码")
	private String fzbm;
	@ExcelProperty("分组名称")
	private String fzmc;
	@ExcelProperty("基准点数")
	private String jzds;
	@ExcelProperty("例均费用(元)")
	private String ljfy;
	@ExcelProperty("并发症")
	private String bfz;
	@ExcelProperty("分组类型")
	private String fzlx;
	@ExcelProperty("平均住院天数")
	private String pjzyts;
	@ExcelProperty("权重")
	private String qz;
	@ExcelProperty("歧义病组")
	private String qybz;
	@ExcelProperty("稳定标识")
	private String wdbs;
	@ExcelProperty("日均费用")
	private String rjfy;
	@ExcelProperty("点值")
	private String dz;
}