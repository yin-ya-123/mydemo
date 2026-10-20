package com.yin.pojo.qt.yb;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class AllData {
	@ExcelProperty("清算经办机构")
	private String qsjbjg;
	@ExcelProperty("定点医药机构名称")
	private String ddyyjgmc;
	@ExcelProperty("结算时间")
	private String jssj;
	@ExcelProperty("结算ID")
	private String jsID;
	@ExcelProperty("人员编号")
	private String rybh;
	@ExcelProperty("支付地点类别")
	private String zfddlb;
	@ExcelProperty("病例类型")
	private String bllx;
	@ExcelProperty("年度分组")
	private String ndfz;
	@ExcelProperty("按项目金额")
	private String axmje;
	@ExcelProperty("除外费用")
	private String cwfy;
	@ExcelProperty("分解费用")
	private String fjfy;
	@ExcelProperty("年度DRG支付金额")
	private String ndDRGzfje;
	@ExcelProperty("差异金额")
	private String cyje;
	@ExcelProperty("清算点数")
	private String qsds;
	@ExcelProperty("清算点值")
	private String qsdz;
	@ExcelProperty("清算金额")
	private String qsje;
	@ExcelProperty("床日病例点数")
	private String crblds;
	@ExcelProperty("床日病例金额")
	private String crblje;
	@ExcelProperty("中医激励点数")
	private String zyjlds;
	@ExcelProperty("中医激励金额")
	private String zyjlje;
	@ExcelProperty("国谈药点数")
	private String gtyds;
	@ExcelProperty("国谈药金额")
	private String gtyje;
	@ExcelProperty("新技术激励点数")
	private String xjsjlds;
	@ExcelProperty("新技术激励金额")
	private String xjsjlje;
	@ExcelProperty("一老一少激励点数")
	private String ylysjlds;
	@ExcelProperty("一老一少激励金额")
	private String ylysjlje;
	@ExcelProperty("重点病例点数")
	private String zdblds;
	@ExcelProperty("重点病例金额")
	private String zdblje;
	@ExcelProperty("特病单议点数")
	private String tbdyds;
	@ExcelProperty("特病单议金额")
	private String tbdyje;
	@ExcelProperty("交叉检查点数")
	private String jcjcds;
	@ExcelProperty("交叉检查金额")
	private String jcjcje;
	@ExcelProperty("日再入院点数15")
	private String rzryds15;
	@ExcelProperty("日再入院金额15")
	private String rzryje15;
	@ExcelProperty("评价激励点数")
	private String pjjlds;
	@ExcelProperty("评价激励金额")
	private String pjjlje;
	@ExcelProperty("备注")
	private String bz;
	@ExcelProperty("扣款标志")
	private String kkbz;
	@ExcelProperty("扣款原因")
	private String kkyy;
	@ExcelProperty("扣款点数")
	private String kkds;
	@ExcelProperty("DRG基础点数")
	private String DRGjcds;
	@ExcelProperty("分组调整标志")
	private String fzdzbz;
	@ExcelProperty("分组调整原因")
	private String fzdzyy;
	@ExcelProperty("双向转诊金额")
	private String sxzzje;
	@ExcelProperty("出院科室")
	private String cyks;
	@ExcelProperty("姓名")
	private String xm;
	@ExcelProperty("DRG编码")
	private String DRGbm;
	@ExcelProperty("DRG名称")
	private String DRGmc;
	@ExcelProperty("预测医疗总费用")
	private String ycylzfy;
	@ExcelProperty("总金额差额")
	private String zjece;
	@ExcelProperty("除外差额")
	private String cwce;
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
	@ExcelProperty("23年差异系数")
	private String ncyxs23;
	@ExcelProperty("22年差异系数")
	private String ncyxs22;
	@ExcelProperty("说明")
	private String sm;
}