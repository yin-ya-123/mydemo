package com.yin.pojo.qt.yb;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class Fz2024 {
	@ExcelProperty("结算ID")
	private String jsID;
	@ExcelProperty("人员编号")
	private String rybh;
	@ExcelProperty("就诊ID")
	private String jzID;
	@ExcelProperty("病案号")
	private String bah;
	@ExcelProperty("证件号码")
	private String zjhm;
	@ExcelProperty("人员姓名")
	private String ryxm;
	@ExcelProperty("定点医药机构编号")
	private String ddyyjgbh;
	@ExcelProperty("定点医药机构名称")
	private String ddyyjgmc;
	@ExcelProperty("定点归属医保区划")
	private String ddgsybqh;
	@ExcelProperty("结算时间")
	private String jssj;
	@ExcelProperty("入院时间")
	private String rysj;
	@ExcelProperty("出院时间")
	private String cysj;
	@ExcelProperty("病例类型")
	private String bllx;
	@ExcelProperty("结算类别")
	private String jslb;
	@ExcelProperty("医疗类别")
	private String yllb;
	@ExcelProperty("险种类别")
	private String xzlb;
	@ExcelProperty("人员类别")
	private String rylb;
	@ExcelProperty("特殊人员类型")
	private String tsrylx;
	@ExcelProperty("经办机构编号")
	private String jbjgbh;
	@ExcelProperty("支付地点类别")
	private String zfddlb;
	@ExcelProperty("重点病例类型")
	private String zdbllx;
	@ExcelProperty("结算清单状态")
	private String jsqdzt;
	@ExcelProperty("清算经办机构")
	private String qsjbjg;
	@ExcelProperty("清算经办机构名称")
	private String qsjbjgmc;
	@ExcelProperty("出院科室名称")
	private String cyksmc;
	@ExcelProperty("清算方式")
	private String qsfs;
	@ExcelProperty("医疗费总额")
	private String ylfze;
	@ExcelProperty("现金支付金额")
	private String xjzfje;
	@ExcelProperty("除外费用")
	private String cwfy;
	@ExcelProperty("DRG编码")
	private String DRGbm;
	@ExcelProperty("DRG名称")
	private String DRGmc;
	@ExcelProperty("基准点数")
	private String jzds;
	@ExcelProperty("差异系数")
	private String cyxs;
	@ExcelProperty("高倍追加点数")
	private String gbzjds;
	@ExcelProperty("调整点数")
	private String dzds;
	@ExcelProperty("调整原因")
	private String dzyy;
	@ExcelProperty("拨付点数")
	private String bfds;
	@ExcelProperty("拨付金额")
	private String bfje;
	@ExcelProperty("是否床日")
	private String sfcr;
	@ExcelProperty("床日类型名称")
	private String crlxmc;
	@ExcelProperty("是否入组")
	private String sfrz;
	@ExcelProperty("主诊断代码")
	private String zzddm;
	@ExcelProperty("主诊断名称")
	private String zzdmc;
	@ExcelProperty("主手术代码")
	private String zssdm;
	@ExcelProperty("主手术名称")
	private String zssmc;
	@ExcelProperty("年度累计天数")
	private String ndljts;
	@ExcelProperty("本次呼吸机天数")
	private String bchxjts;
	@ExcelProperty("是否在院")
	private String sfzy;
}