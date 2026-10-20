package com.yin.pojo.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class Excel {
    @ExcelProperty("患者姓名")
    private String one;
    @ExcelProperty("病区")
    private String two;
    @ExcelProperty("床号")
    private String three;
    @ExcelProperty("医嘱")
    private String foure;
}
