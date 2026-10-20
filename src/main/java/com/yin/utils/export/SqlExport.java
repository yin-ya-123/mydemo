package com.yin.utils.export;

import com.yin.MyDemoApplication;
import com.yin.common.ApplicationUtils;
import com.yin.common.err.YException;
import com.yin.pojo.qt.Fymx20250225;
import com.yin.service.OracleService;
import com.yin.utils.ConvertAllUtils;
import com.yin.utils.excel.ExcelUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author yin
 * @date 2025-02-25
 * @Description sql数据导出
 */
@SpringBootTest(classes = MyDemoApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
public class SqlExport {
    @Autowired
    private OracleService hisOracleServiceImpl;

    int maxSize = 1048500;

    @Test
    public void export() throws YException {
        List<String> datesBetween = ApplicationUtils.getDatesBetween("2023-01-01", "2024-01-01", "month");
        ExcelUtils<Fymx20250225> excelUtils = ExcelUtils.of(Fymx20250225.class);
        ArrayList<Fymx20250225> fymx20250225s1 = new ArrayList<>();
        for (int i = 0; i < datesBetween.size()-1; i++) {
            String date = datesBetween.get(i)+" 00:00:00";
            String endDate = datesBetween.get(i + 1) +" 00:00:00";
            log.info("导出{}到{}的数据",date,endDate);
            String zyCost = Sql.getZyCost(date, endDate);
            List list = hisOracleServiceImpl.selectAll(zyCost);
            ArrayList<Fymx20250225> fymx20250225s = ConvertAllUtils.convertListAll(list, Fymx20250225.class);
            break;
        }
    }

    public void writeExcel(ExcelUtils<?> excelUtils,List<?> list) throws YException {

    }


    @Test
    public void generateClass() throws IOException {
        //生成实体类
        String path="D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\2025-02-25\\费用明细.xlsx";
        ExcelUtils excelUtils = ExcelUtils.getExcelUtils();
        LinkedList excelTitle = excelUtils.getExcelTitle(path);
        excelUtils.generateClass("Fymx20250225",excelTitle,ApplicationUtils.pathPojoQT);

    }

}
