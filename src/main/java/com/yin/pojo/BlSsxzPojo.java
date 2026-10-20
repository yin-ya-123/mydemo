package com.yin.pojo;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class BlSsxzPojo {

	@ExcelProperty("CODE")
	private String CODE;
	@ExcelProperty("名称")
	private String mc;
	@ExcelProperty("pym")
	private String pym;
}