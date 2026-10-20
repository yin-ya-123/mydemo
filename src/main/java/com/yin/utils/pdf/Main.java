package com.yin.utils.pdf;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;

import java.io.File;
import java.io.IOException;

/**
 *
 *@author yin
 *@date 2025-06-19
 *@Description pdf 读取
 */
public class Main {
    public static void main(String[] args) throws IOException {
        String path ="E:\\文件信息保存\\2025-06-06\\浙江分组方案2.0.pdf";
        File file = new File(path);
        PDDocument pdDocument = Loader.loadPDF(file);
        PDFTextStripper pdfTextStripper = new PDFTextStripper();
        pdfTextStripper.setStartPage(29);
        pdfTextStripper.setEndPage(29);
        String text = pdfTextStripper.getText(pdDocument);
        System.out.println(text);
    }
}
