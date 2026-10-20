package com.yin.pojo.qt.jxc;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class JxcPojo {
	@ExcelProperty("标准编码")
	private String bzbm;
	@ExcelProperty("仓位号")
	private String cwh;
	@ExcelProperty("药品名称")
	private String ypmc;
	@ExcelProperty("商品名")
	private String spm;
	@ExcelProperty("生产厂家")
	private String sccj;
	@ExcelProperty("规格")
	private String gg;
	@ExcelProperty("单位")
	private String dw;
	@ExcelProperty("期初库存")
	private String qckc;
	@ExcelProperty("进")
	private String j;
	@ExcelProperty("销")
	private String x;
	@ExcelProperty("损溢")
	private Double sy;
	@ExcelProperty("领用")
	private String ly;
	@ExcelProperty("期末库存")
	private String qmkc;
	@ExcelProperty("差")
	private String c;
	@ExcelProperty("零售价")
	private String lsj;
	@ExcelProperty("YP_ID")
	private String YP_ID;
	@ExcelProperty("CD_ID")
	private String CD_ID;
}