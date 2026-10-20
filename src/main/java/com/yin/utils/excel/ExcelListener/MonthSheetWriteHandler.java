package com.yin.utils.excel.ExcelListener;

import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import com.yin.utils.excel.params.ExcelParams;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * 写入的时候设置大标题
 */
public class MonthSheetWriteHandler implements SheetWriteHandler {

    @Override
    public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
        //这一行从 writeWorkbookHolder 对象中获取一个 Workbook 对象，Workbook 是 Apache POI 中表示 Excel 文档的类。
        Workbook workbook = writeWorkbookHolder.getWorkbook();
        //这一行从 workbook 中获取第一个工作表（Sheet），即 Excel 文档中的第一个表格。
        Sheet sheet = workbook.getSheetAt(0);
        //这一行在第一个工作表中创建一行，行索引为 0，即第一行。
        Row row1 = sheet.createRow(0);
        //这一行设置第一行的行高为 800 像素。
        row1.setHeight((short) 800);
        //这一行在第一行中创建一个单元格，单元格索引为 0，即第一列。
        Cell cell = row1.createCell(0);
        //这一行设置单元格的值为一个字符串，内容为"TestFileUtil.excelTiele"加上当前日期和时间的字符串。
        cell.setCellValue(ExcelParams.excelTiele);
        //这一行创建一个 CellStyle 对象，用于设置单元格的样式。
        CellStyle cellStyle = workbook.createCellStyle();
        //这一行设置单元格的垂直对齐方式为居中对齐。
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        //这一行设置单元格的水平对齐方式为居中对齐。
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        //这一行创建一个 Font 对象，用于设置字体的属性。
        Font font = workbook.createFont();
        //设置字体大小
        font.setFontHeightInPoints((short)5);
        //这一行将字体设置为粗体。
        font.setBold(true);
        //这一行设置字体的高度为 400 像素。
        font.setFontHeight((short) 400);
        //这一行设置字体的名称为"宋体"。
        font.setFontName("宋体");
        //这一行将创建的字体对象应用于单元格样式中。
        cellStyle.setFont(font);
        //这一行将创建的单元格样式应用于指定的单元格。
        cell.setCellStyle(cellStyle);
        //这一行将第一行的前九列合并成一个单元格区域，表示这些列的内容是连续的。
        sheet.addMergedRegionUnsafe(new CellRangeAddress(0, 0, 0, ExcelParams.lastCol-1));
    }


}
