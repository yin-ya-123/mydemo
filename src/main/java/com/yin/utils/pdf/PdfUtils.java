package com.yin.utils.pdf;
import com.yin.utils.FileUtils;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDPageDestination;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDPageFitWidthDestination;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDDocumentOutline;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineItem;
import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 *@author yin
 *@date 2025-04-27
 *@Description
 */
public class PdfUtils {
    /**
     * 合并PDF文件
     * @param pdfUtilPojos 指定pdf存在目录
     */
    public static List<PdfUtilPojo> mergePdf(List<PdfUtilPojo> pdfUtilPojos) throws IOException {
//      进行排序
        List<PdfUtilPojo> collect = pdfUtilPojos.stream()
                .sorted(Comparator.comparing(PdfUtilPojo::getSort))
                .collect(Collectors.toList());
        // 创建PDFMergerUtility对象，用于合并PDF文件
        PDFMergerUtility pdfMergerUtility = new PDFMergerUtility();
        // 设置合并后的PDF文件名
        pdfMergerUtility.setDestinationFileName(pdfUtilPojos.get(0).getPdfToPath());
        for (PdfUtilPojo pdfUtilPojo : collect) {
            File pdfPathFile = pdfUtilPojo.getPdfPathFile();
            pdfMergerUtility.addSource(pdfPathFile);
        }
        // 合并PDF文件
        pdfMergerUtility.mergeDocuments(null);
        return collect;
    }

    /**
     * 设置多个pdf合并，并且根据文件名称设置书签
     * 默认根据文件名称设置书签名
     * 书签跳转为文件第一页
     * @param pdfUtilPojos
     */
    public static void mergePdfItem(List<PdfUtilPojo> pdfUtilPojos) throws IOException {
        //先合并pdf
        pdfUtilPojos = mergePdf(pdfUtilPojos);
        //获取汇总后的路径
        try (PDDocument pdDocument = Loader.loadPDF(pdfUtilPojos.get(0).getPdfToPathFile())) {
            int startPage = 0;
            PDDocumentOutline documentOutline = new PDDocumentOutline();
            pdDocument.getDocumentCatalog().setDocumentOutline(documentOutline);
            PDPageDestination pageDestination = new PDPageFitWidthDestination();
            for (PdfUtilPojo pdfUtilPojo : pdfUtilPojos) {
                if (pdfUtilPojo.getPageNum() > 0) {
                    startPage = pdfUtilPojo.getPageNum();
                }
                pageDestination.setPage(pdDocument.getPage(startPage));
                PDOutlineItem pdOutlineItem = new PDOutlineItem();
                pdOutlineItem.setDestination(pageDestination); //设置跳转页数
                if (pdfUtilPojo.getPdfItemName() != null && !pdfUtilPojo.getPdfItemName().isEmpty()) {
                    pdOutlineItem.setTitle(pdfUtilPojo.getPdfItemName()); //设置书签名称
                } else {
                    //获取文件名称
                    String fileName = FileUtils.getFileName(pdfUtilPojo.getPdfPath());
                    pdOutlineItem.setTitle(fileName); //设置书签名称
                }
                documentOutline.addLast(pdOutlineItem);
                //设置书签层级
                try (PDDocument pdfDoc = Loader.loadPDF(pdfUtilPojo.getPdfPathFile())) {
                    startPage += pdfDoc.getNumberOfPages();
                }
                ;
            }
            //保存
            pdDocument.save(pdfUtilPojos.get(0).getPdfItemPathFile());
        }
    }

}
