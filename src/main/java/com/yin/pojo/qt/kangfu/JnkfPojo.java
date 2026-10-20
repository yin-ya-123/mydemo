package com.yin.pojo.qt.kangfu;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class JnkfPojo {
	@ExcelProperty("问题类别")
	private String wtlb;
	@ExcelProperty("事项")
	private String sx;
	@ExcelProperty("机构名称")
	private String jgmc;
	@ExcelProperty("住院号")
	private String zyh;
	@ExcelProperty("身份证号码")
	private String sfzhm;
	@ExcelProperty("姓名")
	private String xm;
	@ExcelProperty("医保类型")
	private String yblx;
	@ExcelProperty("结算时间")
	private String jssj;
	@ExcelProperty("病区")
	private String bq;
	@ExcelProperty("医生")
	private String ys;
	@ExcelProperty("诊断")
	private String zd;
	@ExcelProperty("医疗目录编码")
	private String ylmlbm;
	@ExcelProperty("医疗目录名称")
	private String ylmlmc;
	@ExcelProperty("收费项目等级")
	private String sfxmdj;
	@ExcelProperty("费用发生时间")
	private String fyfssj;
	@ExcelProperty("单价")
	private String dj;
	@ExcelProperty("数量")
	private String sl;
	@ExcelProperty("金额")
	private String je;
	@ExcelProperty("结算ID")
	private String jsID;
	@ExcelProperty("就诊ID")
	private String jzID;
	@ExcelProperty("人员编号")
	private String rybh;
	@ExcelProperty("费用明细流水号")
	private String fymxlsh;
	@ExcelProperty("编码")
	private String bm;

}