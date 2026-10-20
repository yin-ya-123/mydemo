package com.yin.pojo.qt;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class Ml {
	@ExcelProperty("序号")
	private String xh;
	@ExcelProperty("药品通用名")
	private String yptym;
}