package com.yin.pojo.qt.cyjd;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class Cyxs23 {
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
	@ExcelProperty("差异系数")
	private String cyxs;
	@ExcelProperty("本院平均数")
	private String bypjs;
	@ExcelProperty("差异系数q(年度)")
	private String cyxsq;
	@ExcelProperty("二二年差异系数")
	private String eencyxs;
}