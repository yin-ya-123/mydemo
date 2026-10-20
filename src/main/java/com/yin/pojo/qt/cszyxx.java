package com.yin.pojo.qt;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class cszyxx {
	@ExcelProperty("入院日期")
	private String ryrq;
	@ExcelProperty("床位号")
	private String cwh;
	@ExcelProperty("就诊卡号")
	private String jzkh;
	@ExcelProperty("病人姓名")
	private String brxm;
	@ExcelProperty("住院号")
	private String zyh;
	@ExcelProperty("入院诊断")
	private String ryzd;
	@ExcelProperty("病区")
	private String bq;
	@ExcelProperty("性别")
	private String xb;
	@ExcelProperty("费用类型")
	private String fylx;
	@ExcelProperty("次数")
	private String cs;
	@ExcelProperty("在院天数")
	private String zyts;
	@ExcelProperty("状态")
	private String zt;
	@ExcelProperty("当前费用")
	private String dqfy;
	@ExcelProperty("身份证号")
	private String sfzh;
	@ExcelProperty("预交金")
	private String yjj;
	@ExcelProperty("报销金额")
	private String bxje;
	@ExcelProperty("当前余额")
	private String dqye;
	@ExcelProperty("序号")
	private String xh;
	@ExcelProperty("药费合计")
	private String yfhj;
	@ExcelProperty("其中西药")
	private String qzxy;
	@ExcelProperty("其中中成药")
	private String qzzcy;
	@ExcelProperty("其中草药")
	private String qzcy;
	@ExcelProperty("其中进口药")
	private String qzjky;
	@ExcelProperty("检验费")
	private String jyf;
	@ExcelProperty("手术费")
	private String ssf;
	@ExcelProperty("输氧费")
	private String syf;
	@ExcelProperty("材料费")
	private String clf;
	@ExcelProperty("输血费")
	private String sxf;
	@ExcelProperty("检查费")
	private String jcf;
	@ExcelProperty("床位费")
	private String cwf;
	@ExcelProperty("治疗费")
	private String zlf;
	@ExcelProperty("其中高压氧")
	private String qzgyy;
	@ExcelProperty("其中康复治疗")
	private String qzkfzl;
	@ExcelProperty("其中中医治疗")
	private String qzzyzl;
	@ExcelProperty("麻醉费")
	private String mzf;
	@ExcelProperty("监护费")
	private String jhf;
	@ExcelProperty("护理费")
	private String hlf;
	@ExcelProperty("诊疗费")
	private String zlf2;
	@ExcelProperty("其他费")
	private String qtf;
	@ExcelProperty("其中营养科")
	private String qzyyk;
	@ExcelProperty("诊查费")
	private String zcf;
	@ExcelProperty("伙食补足费")
	private String hsbzf;
	@ExcelProperty("本年度住院天数")
	private String bndzyts;
	@ExcelProperty("呼吸机辅助")
	private String hxjfz;
	@ExcelProperty("床日费用")
	private String crfy;
	@ExcelProperty("医保内金额")
	private String ybnje;
}