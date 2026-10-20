package com.yin.pojo.qt.yb;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class YbYs2024 {
    @ExcelProperty("医保医师代码")
    private String ybysdm;
    @ExcelProperty("姓名")
    private String xm;
    @ExcelProperty("医疗机构名称")
    private String yljgmc;
    @ExcelProperty("性别")
    private String xb;
    @ExcelProperty("证件类型")
    private String zjlx;
    @ExcelProperty("证件号码")
    private String zjhm;
    @ExcelProperty("人员状态")
    private String ryzt;
    @ExcelProperty("医师资格证书编码")
    private String yszgzsbm;
    @ExcelProperty("医师执业证书编码")
    private String yszyzsbm;
    @ExcelProperty("执业医疗机构名称")
    private String zyyljgmc;
    @ExcelProperty("执业范围")
    private String zyfw;
    @ExcelProperty("执业级别")
    private String zyjb;
    @ExcelProperty("专业技术职务")
    private String zyjszw;
    @ExcelProperty("信用等级")
    private String xydj;
    @ExcelProperty("违规行为")
    private String wgxw;
    @ExcelProperty("执业状态")
    private String zyzt;
    @ExcelProperty("合同起始时间")
    private String htqssj;
    @ExcelProperty("合同截止时间")
    private String htjzsj;
    @ExcelProperty("添加时间")
    private String tjsj;
    @ExcelProperty("状态")
    private String zt;
}