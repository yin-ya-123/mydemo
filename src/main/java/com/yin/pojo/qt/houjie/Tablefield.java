package com.yin.pojo.qt.houjie;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class Tablefield {
	@ExcelProperty("TABLENAME")
	private String TABLENAME;
	@ExcelProperty("COLUMNNAME")
	private String COLUMNNAME;
	@ExcelProperty("DATATYPE")
	private String DATATYPE;
	@ExcelProperty("CHARLENGTH")
	private String CHARLENGTH;
	@ExcelProperty("NULLABLE")
	private String NULLABLE;
	@ExcelProperty("COMMENTS")
	private String COMMENTS;
	@ExcelProperty("OWNER")
	private String OWNER;
}