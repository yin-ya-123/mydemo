package com.yin.pojo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author yin
 * @date 2024-11-15
 * @Description
 */
@Data
public class Text1 {

    @ExcelProperty("图片")
    private byte[] byteArray;
}
