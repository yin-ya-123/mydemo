package com.yin.pojo.qt.kangfu;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * 医保目录变更表
 */
@Data
public class HzYbHzBg {
	@ExcelProperty("序号")
	private String xh;
	@ExcelProperty("医保编码")
	private String ybbm;
	@ExcelProperty("分类")
	private String fl;
	@ExcelProperty("医保中文名称")
	private String ybzwmc;
	@ExcelProperty("医保文件剂型")
	private String ybwjjx;
	@ExcelProperty("备注")
	private String bz;
	@ExcelProperty("招标药品ID")
	private String zbypID;
	@ExcelProperty("招标药品通用名")
	private String zbyptym;
	@ExcelProperty("商品名")
	private String spm;
	@ExcelProperty("剂型")
	private String jx;
	@ExcelProperty("规格")
	private String gg;
	@ExcelProperty("单位")
	private String dw;
	@ExcelProperty("转换比")
	private String zhb;
	@ExcelProperty("包装材质")
	private String bzcz;
	@ExcelProperty("药品企业")
	private String ypqy;
	@ExcelProperty("支付标准")
	private String zfbz;
	@ExcelProperty("变更")
	private String bg;
	@ExcelProperty("变更原因")
	private String bgyy;
	@ExcelProperty("药品类型（供参考）")
	private String yplx;
}