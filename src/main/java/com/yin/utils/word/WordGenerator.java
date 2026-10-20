package com.yin.utils.word;

import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;

public class WordGenerator {

    public static void main(String[] args) throws IOException {
        // 创建文档对象
        XWPFDocument document = new XWPFDocument();
        try {
            // 添加标题
            addTitle(document, "Java生成Word文档示例");

            // 添加段落
            addParagraph(document, "这是一个使用Apache POI库生成的Word文档示例。"
                    + "Apache POI是一个开源的Java库，用于处理Microsoft Office格式的文件，"
                    + "包括Word、Excel和PowerPoint等。");

            // 添加小标题
            addSubTitle(document, "主要功能");

            // 添加列表 - 确保数组不为空
            String[] listItems = {
                    "创建和编辑Word文档(.docx)",
                    "添加文本、段落和标题",
                    "创建有序和无序列表",
                    "插入表格并设置样式",
                    "设置文本格式（字体、大小、颜色等）"
            };
            addBulletList(document, listItems);

            // 添加小标题
            addSubTitle(document, "支持的格式");

            // 添加表格 - 确保二维数组不为空且有数据
            String[][] tableData = {
                    {"格式", "支持情况", "备注"},
                    {"DOCX", "完全支持", "推荐使用的格式"},
                    {"DOC", "有限支持", "旧版Word格式"},
                    {"PDF", "不直接支持", "可通过其他库转换"}
            };
            addTable(document, tableData);

            // 保存文档
            String filePath = "E:\\文件信息\\2025-09-05\\generated_document.docx";
            try (FileOutputStream out = new FileOutputStream(filePath)) {
                document.write(out);
                System.out.println("Word文档已成功生成: " + filePath);
            }

        } catch (Exception e) {
            System.err.println("生成Word文档时发生错误: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                document.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 添加标题
     */
    public static void addTitle(XWPFDocument document, String text) {
        if (text == null || text.isEmpty()) {
            System.out.println("标题文本不能为空");
            return;
        }

        XWPFParagraph title = document.createParagraph();
        title.setAlignment(ParagraphAlignment.CENTER);
        title.getCTP().getPPr().addNewOutlineLvl().setVal(BigInteger.valueOf(1));
        XWPFRun run = title.createRun();
        run.setText(text);
        run.setFontSize(20);
        run.setBold(true);
        run.setFontFamily("Arial");
        run.addBreak();
    }

    /**
     * 添加小标题
     */
    public static void addSubTitle(XWPFDocument document, String text) {
        if (text == null || text.isEmpty()) {
            System.out.println("小标题文本不能为空");
            return;
        }
        XWPFParagraph subTitle = document.createParagraph();
        subTitle.setAlignment(ParagraphAlignment.LEFT);
        // 设置Word内置的Heading 2样式
        // 使用Word的标准标题样式
        subTitle.getCTP().getPPr().addNewOutlineLvl().setVal(BigInteger.valueOf(2));
        XWPFRun run = subTitle.createRun();
        run.setText(text);
        run.setFontSize(16);
        run.setBold(true);
        run.setFontFamily("Arial");
        run.addBreak();
    }

    /**
     * 添加段落
     */
    public static void addParagraph(XWPFDocument document, String text) {
        if (text == null || text.isEmpty()) {
            System.out.println("段落文本不能为空");
            return;
        }

        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        paragraph.setSpacingAfter(100);

        XWPFRun run = paragraph.createRun();
        run.setText(text);
        run.setFontSize(12);
        run.setFontFamily("Arial");
        run.addBreak();
    }

    /**
     * 添加项目符号列表
     */
    public static void addBulletList(XWPFDocument document, String[] items) {
        // 检查列表项是否为空
        if (items == null || items.length == 0) {
            System.out.println("列表项不能为空");
            return;
        }

        for (String item : items) {
            // 跳过空的列表项
            if (item == null || item.isEmpty()) {
                continue;
            }

            XWPFParagraph paragraph = document.createParagraph();
            paragraph.setStyle("ListParagraph");
            paragraph.setNumID(BigInteger.valueOf(1));

            XWPFRun run = paragraph.createRun();
            run.setText(item);
            run.setFontSize(12);
            run.setFontFamily("Arial");
        }

        XWPFParagraph paragraph = document.createParagraph();
        paragraph.createRun().addBreak();
    }

    /**
     * 添加表格
     */
    public static void addTable(XWPFDocument document, String[][] data) {
        // 检查表格数据是否有效
        if (data == null || data.length == 0 || data[0] == null || data[0].length == 0) {
            System.out.println("表格数据不能为空");
            return;
        }

        // 创建表格
        XWPFTable table = document.createTable(data.length, data[0].length);
        // 设置表格宽度
        CTTblWidth tblWidth = table.getCTTbl().addNewTblPr().addNewTblW();
        tblWidth.setW(BigInteger.valueOf(8000));
        tblWidth.setType(STTblWidth.DXA);

        // 填充表格数据
        for (int row = 0; row < data.length; row++) {
            XWPFTableRow tableRow = table.getRow(row);

            // 确保行数据有效
            if (data[row] == null) {
                continue;
            }

            // 设置表头样式
            if (row == 0) {
                for (int col = 0; col < data[row].length; col++) {
                    XWPFTableCell cell = tableRow.getCell(col);
                    cell.setColor("E6E6FA");
                    // 设置垂直居中
                    cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
                    // 确保单元格有段落
                    XWPFParagraph paragraph;
                    if (!cell.getParagraphs().isEmpty()) {
                        paragraph = cell.getParagraphs().get(0);
                    } else {
                        paragraph = cell.addParagraph();
                    }
                    XWPFRun run = paragraph.createRun();
                    run.setText(data[row][col] != null ? data[row][col] : "");
                    run.setBold(true);
                    run.setFontSize(12);
                }
            } else {
                // 设置表格内容
                for (int col = 0; col < data[row].length && col < tableRow.getTableCells().size(); col++) {
                    XWPFTableCell cell = tableRow.getCell(col);
                    // 设置垂直居中
                    cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
                    // 确保单元格有段落
                    XWPFParagraph paragraph;
                    if (!cell.getParagraphs().isEmpty()) {
                        paragraph = cell.getParagraphs().get(0);
                    } else {
                        paragraph = cell.addParagraph();
                    }
                    XWPFRun run = paragraph.createRun();
                    run.setText(data[row][col] != null ? data[row][col] : "");
                    run.setFontSize(12);
                }
            }
        }

        // 在表格后添加一个换行
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.createRun().addBreak();
    }
}