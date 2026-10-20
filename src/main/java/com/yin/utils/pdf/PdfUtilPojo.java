package com.yin.utils.pdf;

import lombok.Data;

import java.io.File;

/**
 *
 *@author yin
 *@date 2025-04-27
 *@Description pdf工具类
 */
@Data
public class PdfUtilPojo {
    /**
     * pdf文件路径
     */
    private String pdfPath;
    /**
     * pdf 合并输出路径
     */
    private String pdfToPath;
    /**
     * pdf 设置书签后输出路径
     */
    private String pdfItemPath;
    /**
     * pdf 书签名称
     */
    private String pdfItemName;
    /**
     * pdf 书签页码
     */
    private int pageNum;
    /**
     * pdf 文件排序
     */
    private int sort;

    public File getPdfPathFile() {
        return new File(pdfPath);
    }

    public File getPdfToPathFile() {
        return new File(pdfToPath);
    }
    public File getPdfItemPathFile() {
        return new File(pdfItemPath);
    }
}
