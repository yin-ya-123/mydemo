package com.yin.pojo.qt.yb;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class Cyxs {
	@ExcelProperty("清算年度")
	private String qsnd;
	@ExcelProperty("DRG编码")
	private String DRGbm;
	@ExcelProperty("DRG名称")
	private String DRGmc;
	@ExcelProperty("医疗机构名称")
	private String yljgmc;
	@ExcelProperty("医疗机构编号")
	private String yljgbh;
	@ExcelProperty("23年差异系数")
	private String ncyxs;
	@ExcelProperty("本院平均数")
	private String bypjs;
	@ExcelProperty("差异系数(年度)")
	private String cyxs;
	@ExcelProperty("22年差异系数")
	private String ncyxs1;
	@ExcelProperty("差异")
	private String cy;
}