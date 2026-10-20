package com.yin.pojo.qt.yb;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class Zymxb {
	@ExcelProperty("姓名")
	private String xm;
	@ExcelProperty("性别")
	private String xb;
	@ExcelProperty("身份证号")
	private String sfzh;
	@ExcelProperty("结算时间")
	private String jssj;
	@ExcelProperty("发生时间")
	private String fssj;
	@ExcelProperty("费用类别")
	private String fylb;
	@ExcelProperty("项目编码")
	private String xmbm;
	@ExcelProperty("名称")
	private String mc;
	@ExcelProperty("规格")
	private String gg;
	@ExcelProperty("单位")
	private String dw;
	@ExcelProperty("医保等级")
	private String ybdj;
	@ExcelProperty("贴数")
	private String ts;
	@ExcelProperty("自付比例")
	private String zfbl;
	@ExcelProperty("列支金额")
	private String lzje;
	@ExcelProperty("自理自费金额")
	private String zlzfje;
	@ExcelProperty("医生姓名")
	private String ysxm;
	@ExcelProperty("科室")
	private String ks;
	@ExcelProperty("疾病信息")
	private String jbxx;
}