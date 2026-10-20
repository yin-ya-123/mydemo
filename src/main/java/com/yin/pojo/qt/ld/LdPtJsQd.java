package com.yin.pojo.qt.ld;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class LdPtJsQd {
	@ExcelProperty("setlId")
	private String setlId;
	@ExcelProperty("psnNo")
	private String psnNo;
	@ExcelProperty("psnName")
	private String psnName;
	@ExcelProperty("fixmedinsCode")
	private String fixmedinsCode;
	@ExcelProperty("fixmedinsName")
	private String fixmedinsName;
	@ExcelProperty("setlTime")
	private String setlTime;
	@ExcelProperty("setlType")
	private String setlType;
	@ExcelProperty("setlTypeName")
	private String setlTypeName;
	@ExcelProperty("medType")
	private String medType;
	@ExcelProperty("medTypeName")
	private String medTypeName;
	@ExcelProperty("insutype")
	private String insutype;
	@ExcelProperty("insutypeName")
	private String insutypeName;
	@ExcelProperty("psnType")
	private String psnType;
	@ExcelProperty("psnTypeName")
	private String psnTypeName;
	@ExcelProperty("insuDrgAdmdvs")
	private String insuDrgAdmdvs;
	@ExcelProperty("insuDrgAdmdvsName")
	private String insuDrgAdmdvsName;
	@ExcelProperty("spPsnType")
	private String spPsnType;
	@ExcelProperty("optinsNo")
	private String optinsNo;
	@ExcelProperty("payLoc")
	private String payLoc;
	@ExcelProperty("payLocName")
	private String payLocName;
	@ExcelProperty("stasType")
	private String stasType;
	@ExcelProperty("stasTypeName")
	private String stasTypeName;
	@ExcelProperty("certno")
	private String certno;
	@ExcelProperty("clrOptins")
	private String clrOptins;
	@ExcelProperty("clrOptinsName")
	private String clrOptinsName;
	@ExcelProperty("mdtrtId")
	private String mdtrtId;
	@ExcelProperty("medfeeSumamt")
	private String medfeeSumamt;
	@ExcelProperty("isBed")
	private String isBed;
	@ExcelProperty("admTime")
	private String admTime;
	@ExcelProperty("dscgTime")
	private String dscgTime;
	@ExcelProperty("fixBlngAdmdvs")
	private String fixBlngAdmdvs;
	@ExcelProperty("medcasno")
	private String medcasno;
}