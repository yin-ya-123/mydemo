package com.yin.pojo.qt.wjh;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class Wjh_1 {
	@ExcelProperty("药品代码")
	private String ypdm;
	@ExcelProperty("药品名称")
	private String ypmc;
	@ExcelProperty("通用名整理")
	private String tymzl;
	@ExcelProperty("商品名")
	private String spm;
	@ExcelProperty("首拼")
	private String sp;
	@ExcelProperty("药品规格")
	private String ypgg;
	@ExcelProperty("药品（剂量）单位")
	private String ypdw;
	@ExcelProperty("剂型")
	private String jx;
	@ExcelProperty("厂家")
	private String cj;
	@ExcelProperty("药品类型(1是中西药，2是草药）")
	private String yplx;
	@ExcelProperty("是否抗生素")
	private String sfkss;
	@ExcelProperty("抗生素管理级别")
	private String kssgljb;
	@ExcelProperty("是否国家基本药物")
	private String sfgjjbyw;
	@ExcelProperty("是否本省基本药物")
	private String sfbsjbyw;
	@ExcelProperty("是否中药")
	private String sfzy;
	@ExcelProperty("是否在用（1是在用，0是停用）")
	private String sfzy1;
	@ExcelProperty("批准文号")
	private String pzwh;
	@ExcelProperty("拼音码")
	private String pym;
}