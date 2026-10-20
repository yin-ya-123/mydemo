package com.yin.pojo.qt.ld;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class LdPtDzbl {
	@ExcelProperty("fixmedinsCode")
	private String fixmedinsCode;
	@ExcelProperty("fixmedinsName")
	private String fixmedinsName;
	@ExcelProperty("seltTime")
	private String seltTime;
	@ExcelProperty("certno")
	private String certno;
	@ExcelProperty("psnName")
	private String psnName;
	@ExcelProperty("setlID")
	private String setlID;
	@ExcelProperty("medType")
	private String medType;
	@ExcelProperty("mdtrtID")
	private String mdtrtID;
	@ExcelProperty("iptOtpNo")
	private String iptOtpNo;
	@ExcelProperty("refdSetlFlag")
	private String refdSetlFlag;
	@ExcelProperty("psnNo")
	private String psnNo;
	@ExcelProperty("clrWay")
	private String clrWay;
	@ExcelProperty("setlType")
	private String setlType;
	@ExcelProperty("caseHUp")
	private String caseHUp;
	@ExcelProperty("doctorAUp")
	private String doctorAUp;
	@ExcelProperty("begnDate")
	private String begnDate;
	@ExcelProperty("endDate")
	private String endDate;
	@ExcelProperty("iptDays")
	private String iptDays;
	@ExcelProperty("valiFlag")
	private String valiFlag;
	@ExcelProperty("rid")
	private String rid;
	@ExcelProperty("updtTime")
	private String updtTime;
	@ExcelProperty("crterId")
	private String crterId;
	@ExcelProperty("crterName")
	private String crterName;
	@ExcelProperty("crteTime")
	private String crteTime;
	@ExcelProperty("crteOptinsNo")
	private String crteOptinsNo;
	@ExcelProperty("opterId")
	private String opterId;
	@ExcelProperty("opterName")
	private String opterName;
	@ExcelProperty("optTime")
	private String optTime;
	@ExcelProperty("optinsNo")
	private String optinsNo;
	@ExcelProperty("poolareaNo")
	private String poolareaNo;
}