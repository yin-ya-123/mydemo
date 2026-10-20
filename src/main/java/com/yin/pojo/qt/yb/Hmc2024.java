package com.yin.pojo.qt.yb;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class Hmc2024 {
	@ExcelProperty("序号")
	private String xh;
	@ExcelProperty("姓名")
	private String xm;
	@ExcelProperty("身份证号码")
	private String sfzhm;
	@ExcelProperty("执业资格")
	private String zyzg;
	@ExcelProperty("专业技术职称")
	private String zyjszc;
	@ExcelProperty("执业证书号")
	private String zyzsh;
	@ExcelProperty("人员类型")
	private String rylx;
	@ExcelProperty("是否在本单位参保")
	private String sfzbdwcb;
	@ExcelProperty("本单位参保时间")
	private String bdwcbsj;
	@ExcelProperty("备注")
	private String bz;
}