package com.yin.pojo.qt.yb;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class Bzph {
    @ExcelProperty("分组编码")
    private String fzbm;
    @ExcelProperty("分组名称")
    private String fzmc;
    @ExcelProperty("例均费用")
    private String ljfy;
    @ExcelProperty("基准点数")
    private String jzds;
    @ExcelProperty("差异系数")
    private String cyxs;
    @ExcelProperty("杭脑点数")
    private String hnds;
    @ExcelProperty("月度点值")
    private String yddz;
    @ExcelProperty("预结算费用")
    private String yjsfy;
    @ExcelProperty("拨付金额")
    private String bfje;
    @ExcelProperty("低/正常/高病例")
    private String dzcgbl;
    @ExcelProperty("医保盈亏")
    private String ybyk;
    @ExcelProperty("财务盈亏(正数盈利)")
    private String cwyk;
    @ExcelProperty("低倍率费用")
    private String dblfy;
    @ExcelProperty("高倍率费用")
    private String gblfy;
}