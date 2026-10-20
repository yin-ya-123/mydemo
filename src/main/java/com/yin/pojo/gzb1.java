package com.yin.pojo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class gzb1 {
	@ExcelProperty("hospitalCode")
	private String hospitalCode;
	@ExcelProperty("DrugCode")
	private String DrugCode;
	@ExcelProperty("DrugName")
	private String DrugName;
	@ExcelProperty("DrugNameFirstSpell")
	private String DrugNameFirstSpell;
	@ExcelProperty("DrugSpec")
	private String DrugSpec;
	@ExcelProperty("DosageUnits")
	private String DosageUnits;
	@ExcelProperty("DosageUnits1")
	private String DosageUnits1;
	@ExcelProperty("DosageForm")
	private String DosageForm;
	@ExcelProperty("Manufactor")
	private String Manufactor;
	@ExcelProperty("DrugCategory")
	private String DrugCategory;
	@ExcelProperty("IsAntibiotic")
	private String IsAntibiotic;
	@ExcelProperty("AntibioticLevel")
	private String AntibioticLevel;
	@ExcelProperty("IsCountryEssentialDrug")
	private String IsCountryEssentialDrug;
	@ExcelProperty("IsThisProvinceEssentialDrug")
	private String IsThisProvinceEssentialDrug;
	@ExcelProperty("IsChineseDrug")
	private String IsChineseDrug;
	@ExcelProperty("IsInUse")
	private String IsInUse;
	@ExcelProperty("DDD")
	private String DDD;
	@ExcelProperty("DDD_UNIT")
	private String DDD_UNIT;
	@ExcelProperty("updateDateTime")
	private String updateDateTime;
	@ExcelProperty("class_code")
	private String class_code;
	@ExcelProperty("class_name")
	private String class_name;
	@ExcelProperty("stock_amount")
	private String stock_amount;
	@ExcelProperty("retprice")
	private String retprice;
}