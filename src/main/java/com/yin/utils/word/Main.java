package com.yin.utils.word;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.yin.MyDemoApplication;
import com.yin.service.impl.EyOracleServiceImpl;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.FileOutputStream;
import java.util.List;

@SpringBootTest(classes = MyDemoApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
public class Main {
    @Resource
    private EyOracleServiceImpl eyOracleService;

    @SneakyThrows
    @Test
    public void show() {
        String sql = "SELECT C.TABLE_NAME  tableName,\n" +
                "\t\t\t C.COLUMN_NAME columnName,\n" +
                "\t\t\t C.DATA_TYPE   dataType,\n" +
                "\t\t\t C.CHAR_LENGTH CHARLENGTH,\n" +
                "\t\t\t C.NULLABLE,\n" +
                "\t\t\t D.COMMENTS,\n" +
                "\t\t\t C.OWNER\n" +
                "\tFROM ALL_TAB_COLUMNS C\n" +
                "\tJOIN all_col_comments d\n" +
                "\t\tON C.COLUMN_NAME = D.COLUMN_NAME\n" +
                "\t AND C.TABLE_NAME = D.TABLE_NAME\n" +
                " WHERE 1 = 1\n" +
                "\t AND C.TABLE_NAME = 'PAT_INPAT_ORDER_COST'\n" +
                " order by c.COLUMN_ID";
        List list = eyOracleService.selectAll(sql);
        JSONObject entries = JSONUtil.parseObj(list.get(0));
        // 创建文档对象
        XWPFDocument document = new XWPFDocument();
        String tablename = entries.get("TABLENAME").toString();
        WordGenerator.addSubTitle(document, tablename);
        // 添加表格 - 确保二维数组不为空且有数据
        String[][] tableData = new String[list.size()+1][2];
        tableData[0][0] = "字段名";
//        tableData[0][1] = "数据类型";
//        tableData[0][2] = "字段长度";
//        tableData[0][1] = "是否为空";
        tableData[0][1] = "字段描述";
        for (int i = 0; i < list.size(); i++) {
            JSONObject entries1 = JSONUtil.parseObj(list.get(i));
            String columnname = entries1.get("COLUMNNAME").toString();
//            String datatype = entries1.get("DATATYPE").toString();
//            String charlength = entries1.get("CHARLENGTH").toString();
            String nullable = entries1.get("NULLABLE").toString();
            String comments = entries1.get("COMMENTS").toString();
            tableData[i+1][0] = columnname;
//            tableData[i+1][1] = datatype;
//            tableData[i+1][2] = charlength;
//            tableData[i+1][1] = nullable;
            tableData[i+1][1] = comments;
        }
//        log.info("{}", list);
        WordGenerator.addTable(document, tableData);
        // 保存文档
        String filePath = "E:\\文件信息\\2025-09-05\\generated_document.docx";
        try (FileOutputStream out = new FileOutputStream(filePath)) {
            document.write(out);
            System.out.println("Word文档已成功生成: " + filePath);
        }
    }
}
