package com.yin.pojo.ndfz;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class Ycfz {
	@ExcelProperty("序号")
	private String xh;
	@ExcelProperty("出院科室")
	private String cyks;
	@ExcelProperty("病案号")
	private String bah;
	@ExcelProperty("医保类型")
	private String yblx;
	@ExcelProperty("姓名")
	private String xm;
	@ExcelProperty("入院时间")
	private String rysj;
	@ExcelProperty("年龄")
	private String nl;
	@ExcelProperty("出院时间")
	private String cysj;
	@ExcelProperty("住院天数")
	private String zyts;
	@ExcelProperty("住院次数")
	private String zycs;
	@ExcelProperty("出院诊断")
	private String cyzd;
	@ExcelProperty("主要手术（操作）")
	private String zyss;
	@ExcelProperty("DRG编码(预测)")
	private String DRGbm;
	@ExcelProperty("DRG名称(预测)")
	private String DRGmc;
	@ExcelProperty("住院费用总计")
	private String zyfyzj;
	@ExcelProperty("预测医疗总费用")
	private String ycylzfy;
	@ExcelProperty("预测病例总点数")
	private String ycblzds;
	@ExcelProperty("预测医保结算费用")
	private String ycybjsfy;
	@ExcelProperty("预测盈亏")
	private String ycyk;
	@ExcelProperty("预测病例类型")
	private String ycbllx;
	@ExcelProperty("同级别医院标杆费用")
	private String tjbyybgfy;
	@ExcelProperty("床日类型")
	private String crlx;
	@ExcelProperty("床日费用")
	private String crfy;
}