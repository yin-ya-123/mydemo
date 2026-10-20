package com.yin.pojo.qt.yb;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class BrgjYsj {
	@ExcelProperty("医疗机构名称")
	private String yljgmc;
	@ExcelProperty("人员姓名")
	private String ryxm;
	@ExcelProperty("科室")
	private String ks;
	@ExcelProperty("姓名")
	private String xm;
	@ExcelProperty("身份证号")
	private String sfzh;
	@ExcelProperty("结算ID")
	private String jsID;
	@ExcelProperty("结算时间")
	private String jssj;
	@ExcelProperty("入院时间")
	private String rysj;
	@ExcelProperty("出院时间")
	private String cysj;
	@ExcelProperty("医疗费总额")
	private String ylfze;
	@ExcelProperty("基金支出总额")
	private String jjzcze;
	@ExcelProperty("数据检出原因")
	private String sjjcyy;
	@ExcelProperty("住院天数")
	private String zyts;
	@ExcelProperty("支付方式（按床日付费/按DRG付费）")
	private String zffs;
	@ExcelProperty("医保具体支付金额（元）")
	private String ybjtzfje;
	@ExcelProperty("自查自纠情况")
	private String zczjqk;
}