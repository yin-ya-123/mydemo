package com.yin.pojo.qt;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class Dyy {
	@ExcelProperty("pageNum")
	private String pageNum;
	@ExcelProperty("pageSize")
	private String pageSize;
	@ExcelProperty("isPage")
	private String isPage;
	@ExcelProperty("beddayCasExitChkId")
	private String beddayCasExitChkId;
	@ExcelProperty("fixmedinsCode")
	private String fixmedinsCode;
	@ExcelProperty("fixmedinsName")
	private String fixmedinsName;
	@ExcelProperty("psnNo")
	private String psnNo;
	@ExcelProperty("certno")
	private String certno;
	@ExcelProperty("psnName")
	private String psnName;
	@ExcelProperty("gend")
	private String gend;
	@ExcelProperty("gendName")
	private String gendName;
	@ExcelProperty("setlListSn")
	private String setlListSn;
	@ExcelProperty("dscgCaty")
	private String dscgCaty;
	@ExcelProperty("dscgCatyName")
	private String dscgCatyName;
	@ExcelProperty("admTime")
	private String admTime;
	@ExcelProperty("dscgTime")
	private String dscgTime;
	@ExcelProperty("actIptDays")
	private String actIptDays;
	@ExcelProperty("medfeeSumamt")
	private String medfeeSumamt;
	@ExcelProperty("setlId")
	private String setlId;
	@ExcelProperty("setlTime")
	private String setlTime;
	@ExcelProperty("admdvs")
	private String admdvs;
	@ExcelProperty("admdvsName")
	private String admdvsName;
}