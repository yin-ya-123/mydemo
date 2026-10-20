package com.yin.utils.pdf;

import com.yin.utils.FileUtils;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.io.RandomAccessStreamCache;
import org.apache.pdfbox.io.RandomAccessStreamCacheImpl;
import org.apache.pdfbox.io.ScratchFile;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDPageDestination;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDPageFitWidthDestination;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDDocumentOutline;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineItem;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.junit.Test;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yin
 * @date 2025-02-20
 * @Description
 */
public class PdfApp {
    public static void main(String[] args) throws IOException {
        String pdfPath = "D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\2025-02-19\\（2023版）浙江省基本医疗医疗服务项目目录(1).pdf";
        PDDocument pdDocument = Loader.loadPDF(new File(pdfPath));
        int numberOfPages = pdDocument.getNumberOfPages();
//        System.out.println(numberOfPages);
        PDPageTree pages = pdDocument.getPages();
        PDPage pdPage = pages.get(10);
        PDRectangle bBox = pdPage.getBBox();
        System.out.println(bBox.getWidth());
        System.out.println(bBox.getHeight());
        PDFTextStripperByArea pdfTextStripperByArea = new PDFTextStripperByArea();
        pdfTextStripperByArea.addRegion("one", new Rectangle(0, 0, (int) bBox.getWidth(), (int) bBox.getHeight()));
        pdfTextStripperByArea.extractRegions(pdPage);
        List<String> regions = pdfTextStripperByArea.getRegions();
        for (String region : regions) {
            String textForRegion = pdfTextStripperByArea.getTextForRegion(region);
            System.out.println(textForRegion);
        }
        pdDocument.close();
    }

    @Test
    public void test() {
        String path = "D:\\公司\\笔记\\文档\\医保\\[接口文档 字典] 浙江省医疗保障信息平台定点医药机构接口规范V1.6-20220124.pdf";
        String path2 = "E:\\文件信息保存\\2025-03-13";
        int splitPages = 20;
        try (PDDocument document = Loader.loadPDF(new File(path))) {
            if (document == null || document.getNumberOfPages() == 0) {
                System.out.println("PDF文件为空");
                return;
            }
            Splitter splitter = new Splitter();
            splitter.setSplitAtPage(splitPages);
            List<PDDocument> split = splitter.split(document);
            for (int i = 0; i < split.size(); i++) {
                try (PDDocument pd = split.get(i);) {
                    String outputPath = path2 + File.separator + String.format("output_%d.pdf", i + 1);
                    pd.save(outputPath);
                    System.out.println("Created: " + outputPath);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 合并pdf
     * @throws IOException
     */
    @Test
    public void test2() throws IOException {
        // 定义文件路径
        String path = "E:\\文件信息保存\\2025-04-27\\浙江省-杭州富春康复医院-0000003285-20241020\\病重";
        // 定义合并后的PDF文件路径
        String path2 = "E:\\文件信息保存\\2025-04-27\\浙江省-杭州富春康复医院-0000003285-20241020\\病重\\病重患者护理记录单.pdf";
        // 创建File对象，指向指定的目录
        File file = new File(path);
        // 获取该目录下的所有文件和目录
        File[] files = file.listFiles();
        // 创建PDFMergerUtility对象，用于合并PDF文件
        PDFMergerUtility pdfMergerUtility = new PDFMergerUtility();
        // 设置合并后的PDF文件名
        pdfMergerUtility.setDestinationFileName(path2);
        // 遍历目录下的所有文件
        assert files != null;
        HashMap<Integer, String> map = new HashMap<>();
        for (File file1 : files) {
            String absolutePath = file1.getAbsolutePath();
            String key = absolutePath.substring(absolutePath.lastIndexOf("（") + 1, absolutePath.lastIndexOf("）"));
            map.put(Integer.valueOf(key), absolutePath);
        }
        //进行排序
        TreeMap<Integer, String> collect = map.entrySet().stream().sorted(Map.Entry.comparingByKey()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                (oldValue, newValue) -> oldValue, () -> new TreeMap<>()));
        for (Map.Entry<Integer, String> stringStringEntry : collect.entrySet()) {
            pdfMergerUtility.addSource(stringStringEntry.getValue());

        }
        // 设置文档合并模式为优化资源模式
//        pdfMergerUtility.setDocumentMergeMode(PDFMergerUtility.DocumentMergeMode.OPTIMIZE_RESOURCES_MODE);
        pdfMergerUtility.mergeDocuments(null);
    }

    @Test
    public void test3() throws IOException {
        File file = new File("E:\\文件信息保存\\2025-04-27\\浙江省-杭州富春康复医院-0000003285-20241020\\1、住院病案首页.pdf");
        PDDocument pdDocument = Loader.loadPDF(file);
        int numberOfPages = pdDocument.getNumberOfPages();
        System.out.println(numberOfPages);
        PDDocumentOutline documentOutline = new PDDocumentOutline();
        pdDocument.getDocumentCatalog().setDocumentOutline(documentOutline);
        PDPageDestination pageDestination = new PDPageFitWidthDestination();
        pageDestination.setPage(pdDocument.getPage(0));
        PDOutlineItem pdOutlineItem = new PDOutlineItem();
        pdOutlineItem.setDestination(pageDestination); //设置跳转页数
        pdOutlineItem.setTitle("第" + 1 + "页");
        documentOutline.addLast(pdOutlineItem);
        pdDocument.save("E:\\文件信息保存\\2025-04-27\\浙江省-杭州富春康复医院-0000003285-20241020\\1、住院病案首页2.pdf");
    }

    @Test
    public void test4() throws IOException {
        File file = new File("E:\\文件信息保存\\2025-04-27\\浙江省-杭州富春康复医院-0000003285-20241020");
        String path2 = "E:\\文件信息保存\\2025-04-27\\浙江省-杭州富春康复医院-0000003285-20241020\\汇总.pdf";
        String path3 = "E:\\文件信息保存\\2025-04-27\\浙江省-杭州富春康复医院-0000003285-20241020\\汇总3.pdf";
        ArrayList<PdfUtilPojo> pdfUtilPojos = new ArrayList<>();
        for (File listFile : file.listFiles()) {
            PdfUtilPojo pdfUtilPojo = new PdfUtilPojo();
            pdfUtilPojo.setPdfPath(listFile.getAbsolutePath());
            pdfUtilPojo.setPdfToPath(path2);
            pdfUtilPojo.setPdfItemPath(path3);
            String absolutePath = listFile.getAbsolutePath();
            String fileName = FileUtils.getFileName(absolutePath);
            String substring = fileName.substring(0, fileName.indexOf("、"));
            pdfUtilPojo.setSort(Integer.parseInt(substring));
            pdfUtilPojos.add(pdfUtilPojo);
        }
        PdfUtils.mergePdfItem(pdfUtilPojos);
    }
}
