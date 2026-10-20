package com.yin.pojo.qt.liangshan;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class LsCyCfMxPojo {
	@ExcelProperty("YZJL_ID")
	private Integer YZJL_ID;
	@ExcelProperty("CFXQ")
	private String CFXX;
}