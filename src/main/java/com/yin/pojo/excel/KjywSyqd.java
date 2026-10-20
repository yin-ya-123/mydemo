package com.yin.pojo.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created with IntelliJ IDEA
 *
 * @Auther: YIN
 * @DescriPtion: 抗菌药物使用强度实体类
 * @Date:2024-01-17/01/17/17:17
 */
@Data
@EqualsAndHashCode
public class KjywSyqd {
    @ExcelProperty("科室")
    private String ks;
    @ExcelProperty("部门")
    private String bm;
    @ExcelProperty("出院人数")
    private String cyrq;
    @ExcelProperty("住院抗菌药物使用总量DDDS")
    private String zydds;
    @ExcelProperty("住院总天数")
    private String zyzts;
    @ExcelProperty("住院抗菌药物使用强度")
    private String syqd;
}
