package com.yin.pojo.qt.cyjd;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class Cyxs22 {
	@ExcelProperty("序号")
	private String xh;
	@ExcelProperty("决算年份")
	private String jsnf;
	@ExcelProperty("基金类型")
	private String jjlx;
	@ExcelProperty("分组编码")
	private String fzbm;
	@ExcelProperty("分组名称")
	private String fzmc;
	@ExcelProperty("机构名称")
	private String jgmc;
	@ExcelProperty("差异系数")
	private String cyxs;
	@ExcelProperty("并发症")
	private String bfz;
	@ExcelProperty("分组类型")
	private String fzlx;
	@ExcelProperty("社会平均数")
	private String shpjs;
	@ExcelProperty("本院平均数")
	private String bypjs;
	@ExcelProperty("机构级别")
	private String jgjb;
}