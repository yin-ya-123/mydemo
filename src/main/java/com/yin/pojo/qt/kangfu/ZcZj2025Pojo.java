package com.yin.pojo.qt.kangfu;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class ZcZj2025Pojo {
	@ExcelProperty("科室2")
	private String ks2;
	@ExcelProperty("诊断2")
	private String zd2;
	@ExcelProperty("问题类别")
	private String wtlb;
	@ExcelProperty("事项")
	private String sx;
	@ExcelProperty("机构名称")
	private String jgmc;
	@ExcelProperty("ZYLSH")
	private Integer ZYLSH;
	@ExcelProperty("住院号门诊号")
	private String zyhmzh;
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
	@ExcelProperty("科室")
	private String ks;
	@ExcelProperty("医生")
	private String ys;
	@ExcelProperty("诊断")
	private String zd;
	@ExcelProperty("SFXM_ID")
	private String SFXM_ID;
	@ExcelProperty("医疗目录编码")
	private String ylmlbm;
	@ExcelProperty("医疗目录名称")
	private String ylmlmc;
	@ExcelProperty("收费项目等级")
	private String sfxmdj;
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
	private String je;
	@ExcelProperty("结算ID")
	private String jsID;
	@ExcelProperty("就诊ID")
	private String jzID;
	@ExcelProperty("人员编号")
	private String rybh;
	@ExcelProperty("SFXM_ID1")
	private String SFXM_ID1;
	@ExcelProperty("医疗目录编码1")
	private String ylmlbm1;
	@ExcelProperty("医疗目录名称1")
	private String ylmlmc1;
	@ExcelProperty("收费项目等级1")
	private String sfxmdj1;
	@ExcelProperty("总金额1")
	private String zje1;
	@ExcelProperty("医保统筹金额1")
	private String ybtcje1;
	@ExcelProperty("自付金额1")
	private String zfje1;
	@ExcelProperty("单价1")
	private String dj1;
	@ExcelProperty("数量1")
	private String sl1;
	@ExcelProperty("金额1")
	private String je1;
	@ExcelProperty("问题")
	private String WT;
	@ExcelProperty("ZYFYMX_ID")
	private String ZYFYMXID;
}