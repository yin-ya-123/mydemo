package com.yin.utils.excel.main.liangshan;

import com.yin.pojo.qt.liangshan.LsOnePojo;
import com.yin.utils.FileUtils;
import com.yin.utils.excel.ExcelUtils;
import org.apache.commons.collections4.ListUtils;

import java.io.*;
import java.util.List;

/**
 * @author yin
 * @date 2025-02-07
 * @Description csv导出
 */
public class CsvExport {
    public static void main(String[] args) throws IOException, NoSuchFieldException, IllegalAccessException {
        String one = "E:\\文件信息保存\\2025-02-10\\医嘱-匹配.xlsx";
        ExcelUtils<LsOnePojo> lsOnePojoExcelUtils = ExcelUtils.of(LsOnePojo.class);
        lsOnePojoExcelUtils.writeCsv(one, new LsOnePojo(), 1, "医嘱-匹配", 10000);
//        for (String s : lsOnePojoExcelUtils.csvWritePath) {
//            FileUtils.createFileZip(new File(s));
//        }
//        FileUtils.toZip(new File("E:\\文件信息保存\\2025-02-07"));
//        File file = new File("E:\\文件信息保存\\2025-02-07\\月份导出\\");
//        for (File listFile : Objects.requireNonNull(file.listFiles())) {
//            ExcelUtils<LsOnePojo> lsOnePojoExcelUtils = ExcelUtils.of(LsOnePojo.class);
//            lsOnePojoExcelUtils.writeCsv(listFile.getAbsolutePath(), new LsOnePojo(), 1);
//        }
    }

    public static void show() throws IOException {
        String one = "E:\\文件信息保存\\2025-02-07\\医嘱-匹配.xlsx";
        ExcelUtils<LsOnePojo> lsOnePojoExcelUtils = ExcelUtils.of(LsOnePojo.class);
        List<String> strings = lsOnePojoExcelUtils.getExcelTitle(one);
        List<LsOnePojo> lsOnePojos = lsOnePojoExcelUtils.readExcel(one, new LsOnePojo(), 1);
        String csvTitle = getCsvTitle(strings);
        List<List<LsOnePojo>> partition = ListUtils.partition(lsOnePojos, 10000);
        for (List<LsOnePojo> onePojos : partition) {
            String fileName = "E:\\文件信息保存\\2025-02-07\\医嘱数据导出-分割" + partition.indexOf(onePojos) + ".csv";
            //写入csv文件
            try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), "GB2312"))
            ) {
//            写表头
                bw.write(csvTitle);
                bw.newLine();
//            写数据
                for (LsOnePojo lsOnePojo : onePojos) {
                    String csvInfo = lsOnePojoExcelUtils.getCsvInfo(lsOnePojo);
                    bw.write(csvInfo);
                    bw.newLine();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }

            System.out.println("csv导出成功" + fileName);
        }
    }

    public static String getCsvTitle(List<String> tit) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < tit.size(); i++) {
            str.append(tit.get(i));
            if (i < tit.size() - 1) {
                str.append(",");
            }
        }
        return str.toString();
    }
}
