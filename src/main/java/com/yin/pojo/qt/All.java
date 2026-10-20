package com.yin.pojo.qt;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author yin
 * @date 2024-12-19
 * @Description
 */
@Data
public class All {
    @ExcelProperty("one")
    private String one;
    @ExcelProperty("two")
    private String two;
}
