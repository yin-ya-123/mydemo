package com.yin.utils.excel.NotPojoExcel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author yin
 * @date 2024-11-26
 * @Description
 */
public class ExcelTool {

    public static SheetData read(InputStream is, int sheetIndex){
        ArrayDataListener arrayDataListener = new ArrayDataListener();
        EasyExcel.read(is, arrayDataListener).sheet(sheetIndex).doRead();
        return arrayDataListener.getData();
    }
    public static SheetData read(String path) throws IOException {
        return read(path, 0);
    }
    public static SheetData read(String path, int sheetIndex) throws IOException {
        FileInputStream fis = null;
        SheetData data = null;
        try{
            fis = new FileInputStream(path);
            data = read(fis, sheetIndex);
        } finally {
            if(null!=fis){
                fis.close();
            }
        }
        return data;
    }

    public static void write(String dist, SheetData data){
        write(dist, data, 0);
    }

    public static void write(String dist, SheetData data, int sheetIndex){
        ExcelWriterBuilder excelWriter = EasyExcel.write(dist).useDefaultStyle(false);
        excelWriter.head(data.toExcelHeader());
        excelWriter.sheet(sheetIndex).doWrite(data.getDatas());
    }

}
