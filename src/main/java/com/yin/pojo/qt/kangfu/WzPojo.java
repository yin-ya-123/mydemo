package com.yin.pojo.qt.kangfu;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author yin
 * @date 2025-03-04
 * @Description
 */
@Data
public class WzPojo {

    @ExcelProperty("姓名")
    private String name;
    @ExcelProperty("科室")
    private String ks;
    @ExcelProperty("住院号")
    private String zyh;
    @ExcelProperty("zylsh")
    private String zylsh;
    @ExcelProperty("入院日期")
    private String rysj;
    @ExcelProperty("出院日期")
    private String cysj;
    @ExcelProperty("结算日期")
    private String jssj;
    @ExcelProperty("物资名称")
    private String wzmc;
    @ExcelProperty("规格")
    private String gg;
    @ExcelProperty("编码")
    private String bm;
    @ExcelProperty("上传日期")
    private String scsj;
    @ExcelProperty("费用id")
    private String fyid;
    @ExcelProperty("上传码1")
    private String one1;
    @ExcelProperty("上传码2")
    private String one2;
    @ExcelProperty("记账日期")
    private String jzrq;
    @ExcelProperty("费用id")
    private String fyid1;


}
