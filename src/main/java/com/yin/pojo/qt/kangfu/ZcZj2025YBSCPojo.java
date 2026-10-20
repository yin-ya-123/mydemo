package com.yin.pojo.qt.kangfu;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class ZcZj2025YBSCPojo {
	@ExcelProperty("科室")
	private String ks;
	@ExcelProperty("ZYLSH_ID")
	private String ZYLSH_ID;
	@ExcelProperty("诊断")
	private String zd;
	@ExcelProperty("问题类别")
	private String wtlb;
	@ExcelProperty("事项")
	private String sx;
	@ExcelProperty("机构名称")
	private String jgmc;
	@ExcelProperty("住院号门诊号")
	private String zyh;
	@ExcelProperty("参保人身份证号")
	private String cbrsfzh;
	@ExcelProperty("医保类型")
	private String yblx;
	@ExcelProperty("参保人姓名")
	private String cbrxm;
	@ExcelProperty("结算时间")
	private String jssj;
	@ExcelProperty("就医方式")
	private String jyfs;
	@ExcelProperty("科室1")
	private String ks1;
	@ExcelProperty("医生")
	private String ys;
	@ExcelProperty("诊断1")
	private String zd1;
	@ExcelProperty("医疗目录编码")
	private String ylmlbm;
	@ExcelProperty("医疗目录名称")
	private String ylmlmc;
	@ExcelProperty("收费项目等级")
	private String sfxmdj;
	@ExcelProperty("费用发生时间")
	private String fyfssj;
	@ExcelProperty("总金额")
	private String zje;
	@ExcelProperty("医保统筹金额")
	private String ybtcje;
	@ExcelProperty("自付金额")
	private String zfje;
	@ExcelProperty("单价")
	private String dj;
	@ExcelProperty("数量")
	private String sl;
	@ExcelProperty("金额")
	private Double je;
	@ExcelProperty("结算ID")
	private String jsID;
	@ExcelProperty("就诊ID")
	private String jzID;
	@ExcelProperty("人员编号")
	private String rybh;
	@ExcelProperty("费用明细流水号")
	private String fymxlsh;
	@ExcelProperty("问题序号")
	private String wtxh;
}