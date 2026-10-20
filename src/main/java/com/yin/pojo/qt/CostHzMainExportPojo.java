package com.yin.pojo.qt;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class CostHzMainExportPojo {
	@ExcelProperty("序号")
	private String 序号;
	@ExcelProperty("出院科室")
	private String 出院科室;
	@ExcelProperty("病案号")
	private String 病案号;
	@ExcelProperty("医保类型")
	private String 医保类型;
	@ExcelProperty("姓名")
	private String 姓名;
	@ExcelProperty("入院时间")
	private String 入院时间;
	@ExcelProperty("年龄")
	private String 年龄;
	@ExcelProperty("出院时间")
	private String 出院时间;
	@ExcelProperty("住院天数")
	private String 住院天数;
	@ExcelProperty("住院次数")
	private String 住院次数;
	@ExcelProperty("出院诊断")
	private String 出院诊断;
	@ExcelProperty("主要手术")
	private String 主要手术;
	@ExcelProperty("DRG编码")
	private String DRG编码;
	@ExcelProperty("DRG名称")
	private String DRG名称;
	@ExcelProperty("住院费用总计")
	private String 住院费用总计;
	@ExcelProperty("药品费")
	private String 药品费;
	@ExcelProperty("耗材费")
	private String 耗材费;
	@ExcelProperty("检验")
	private String 检验;
	@ExcelProperty("其中康复治疗")
	private String 其中康复治疗;
	@ExcelProperty("其中高压氧")
	private String 其中高压氧;
	@ExcelProperty("预测医疗总费用")
	private String 预测医疗总费用;
	@ExcelProperty("预测病例总点数")
	private String 预测病例总点数;
	@ExcelProperty("预测医保结算费用")
	private String 预测医保结算费用;
	@ExcelProperty("预测盈亏")
	private String 预测盈亏;
	@ExcelProperty("预测病例类型")
	private String 预测病例类型;
	@ExcelProperty("同级别医院标杆费用")
	private String 同级别医院标杆费用;
	@ExcelProperty("床日类型")
	private String 床日类型;
	@ExcelProperty("床日费用")
	private String 床日费用;
	@ExcelProperty("备注")
	private String 备注;
	@ExcelProperty("本年度住院累计")
	private String 本年度住院累计;
	@ExcelProperty("本次呼吸机辅助天数")
	private String 本次呼吸机辅助天数;
	@ExcelProperty("患者是否在院")
	private String 患者是否在院;
	@ExcelProperty("审批重传")
	private String 审批重传;
	@ExcelProperty("是否调整")
	private String 是否调整;
	@ExcelProperty("博石")
	private String 博石;
	@ExcelProperty("智慧")
	private String 智慧;
	@ExcelProperty("上传")
	private String 上传;
	@ExcelProperty("住院流水号")
	private String 住院流水号;
}