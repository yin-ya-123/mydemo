package com.yin.utils.excel.main.kangfu;

import com.yin.common.ApplicationUtils;
import com.yin.pojo.qt.kangfu.ItemCataloguePojo;
import com.yin.utils.excel.ExcelUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * @author yin
 * @date 2025-03-12
 * @Description
 */
public class ItemCatalogue {
    @Test
    public void main(){
        String path ="D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\2025-03-12\\工作簿3.xlsx";
        String savePath ="D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\2025-03-12\\item工作簿3.xlsx";
        ExcelUtils<ItemCataloguePojo> itemCataloguePojoExcelUtils = ExcelUtils.of(ItemCataloguePojo.class);
        List<ItemCataloguePojo> itemCataloguePojos = itemCataloguePojoExcelUtils.readExcel(path, 1);
        itemCataloguePojoExcelUtils.writeExcel(savePath,itemCataloguePojos);
    }

    @Test
    public void generate() throws IOException {
        String path ="D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\2025-03-12\\工作簿3.xlsx";
        ExcelUtils excelUtils = ExcelUtils.getExcelUtils();
        excelUtils.generateClassEnhanceZ("ItemCataloguePojo", ApplicationUtils.pathPojoKangFu, path);
    }
}
