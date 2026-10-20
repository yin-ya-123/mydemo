package com.yin.pojo.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created with IntelliJ IDEA
 *
 * @Auther: YIN
 * @DescriPtion: 抗菌药物使用强度实体类 科室明细
 * @Date:2024-01-17/01/17/17:17
 */
@Data
@EqualsAndHashCode
public class KjywSyqdKsMx {
    @ExcelProperty("部门")
    private String 部门;
    @ExcelProperty("药品名称")
    private String 药品名称;
    @ExcelProperty("规格")
    private String 规格;
    @ExcelProperty("总量")
    private String 总量;
    @ExcelProperty("含量")
    private String 含量;
    @ExcelProperty("含量单位")
    private String 含量单位;
    @ExcelProperty("DDD值")
    private String DDD值;
    @ExcelProperty("DDDS")
    private String DDDS;

}
