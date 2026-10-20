package com.yin.utils.excel.params;

/**
 * excel参数设置
 */
public class ExcelParams {
    /**
     * excel第一行的大标题，比如《全校人员基本情况表》
     */
    public static String excelTiele="全校人员基本情况表";

    /**
     * excel第一行，设置需要合并到最后那一列的数字，如何第一列到第五列，那么这个数就是5
     */
    public static int lastCol=5;
    /**
     * excel选择第几个表格，默认为第一个
     */
    public static Integer sheetNum=0;
    /**
     * excel文件的内容标题列，默认第一行
     */
    public static Integer headRowNumber=0;

}
