package com.yin.pojo.qt.kangfu;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class ItemCataloguePojo {
	@ExcelProperty("编码")
	private String bm;
	@ExcelProperty("one")
	private String one;
	@ExcelProperty("two")
	private String two;
	@ExcelProperty("项目名称")
	private String xmmc;
	@ExcelProperty("three")
	private String three;
	@ExcelProperty("four")
	private String four;
	@ExcelProperty("项目内涵")
	private String xmnh;
	@ExcelProperty("five")
	private String five;
	@ExcelProperty("six")
	private String six;
	@ExcelProperty("除外内容")
	private String cwnr;
	@ExcelProperty("计价单位")
	private String jjdw;
	@ExcelProperty("省级价格")
	private String sjjg;
	@ExcelProperty("备注")
	private String bz;
	@ExcelProperty("seven")
	private String seven;
	@ExcelProperty("分类")
	private String fl;
	@ExcelProperty("eight")
	private String eight;
	@ExcelProperty("先行支付比例")
	private String xxzfbl;
	@ExcelProperty("限定支付范围")
	private String xdzffw;
}