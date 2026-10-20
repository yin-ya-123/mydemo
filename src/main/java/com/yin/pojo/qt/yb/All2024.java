package com.yin.pojo.qt.yb;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class All2024 {
	@ExcelProperty("结算清单流水号")
	private String jsqdlsh;
	@ExcelProperty("住院号")
	private String zyh;
	@ExcelProperty("病案号")
	private String bah;
	@ExcelProperty("结算日期")
	private String jsrq;
	@ExcelProperty("结算ID")
	private String jsID;
	@ExcelProperty("人员编码")
	private String rybm;
	@ExcelProperty("人员姓名")
	private String ryxm;
	@ExcelProperty("性别")
	private String xb;
	@ExcelProperty("身份证号")
	private String sfzh;
	@ExcelProperty("人员类别")
	private String rylb;
	@ExcelProperty("医疗机构编码")
	private String yljgbm;
	@ExcelProperty("医疗机构名称")
	private String yljgmc;
	@ExcelProperty("DRG编码")
	private String DRGbm;
	@ExcelProperty("DRG名称")
	private String DRGmc;
	@ExcelProperty("拨付点数")
	private String bfds;
	@ExcelProperty("点值")
	private String dz;
	@ExcelProperty("基准点数")
	private String jzds;
	@ExcelProperty("按DRG月结总金额")
	private String aDRGyjzje;
	@ExcelProperty("差异系数")
	private String cyxs;
	@ExcelProperty("除外费用")
	private String cwfy;
	@ExcelProperty("统筹预拨金额")
	private String tcybje;
	@ExcelProperty("统筹应拨金额")
	private String tcybje2;
	@ExcelProperty("病例类型")
	private String bllx;
	@ExcelProperty("入院诊断")
	private String ryzd;
	@ExcelProperty("出院诊断")
	private String cyzd;
	@ExcelProperty("主手术编码")
	private String zssbm;
	@ExcelProperty("主手术名称")
	private String zssmc;
	@ExcelProperty("入院时间")
	private String rysj;
	@ExcelProperty("出院时间")
	private String cysj;
	@ExcelProperty("医疗费总额")
	private String ylfze;
	@ExcelProperty("申拨金额")
	private String sbje;
	@ExcelProperty("DRG扣款金额")
	private String DRGkkje;
	@ExcelProperty("DRG扣除点数")
	private String DRGkcds;
	@ExcelProperty("险种类型")
	private String xzlx;
	@ExcelProperty("就医类型")
	private String jylx;
	@ExcelProperty("支付地点类别")
	private String zfddlb;
	@ExcelProperty("出院科室")
	private String cyks;
	@ExcelProperty("医保区划")
	private String ybqh;
	@ExcelProperty("参保地")
	private String cbd;
	@ExcelProperty("就医地")
	private String jyd;
	@ExcelProperty("分组调整原因")
	private String fzdzyy;
	@ExcelProperty("评审状态")
	private String pszt;
	@ExcelProperty("申请状态")
	private String sqzt;
	@ExcelProperty("评审分组编码")
	private String psfzbm;
	@ExcelProperty("申请理由")
	private String sqly;
	@ExcelProperty("申请说明")
	private String sqsm;
	@ExcelProperty("评审理由")
	private String psly;
	@ExcelProperty("评审内容")
	private String psnr;
	@ExcelProperty("拒绝理由")
	private String jjly;
	@ExcelProperty("拒绝内容")
	private String jjnr;
	@ExcelProperty("退费标志")
	private String tfbz;
	@ExcelProperty("主诊断编码")
	private String zzdbm;
	@ExcelProperty("主诊断名称")
	private String zzdmc;
}