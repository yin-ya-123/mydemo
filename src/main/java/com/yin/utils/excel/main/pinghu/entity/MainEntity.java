package com.yin.utils.excel.main.pinghu.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class MainEntity {
    /**
     * YYMC
     * CF_LSH_ID
     * DRUG_NAME
     * DRUG_ID
     * ZSM
     */
    @ExcelProperty("YYMC")
    private String YYMC;
    @ExcelProperty("CF_LSH_ID")
    private String CF_LSH_ID;
    @ExcelProperty("ZSM")
    private String ZSM;
}
