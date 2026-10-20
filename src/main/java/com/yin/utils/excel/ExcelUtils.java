package com.yin.utils.excel;

import cn.hutool.extra.pinyin.PinyinUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.analysis.ExcelReadExecutor;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.listener.PageReadListener;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.fastjson2.JSONObject;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.yin.common.err.YException;
import com.yin.utils.ConvertAllUtils;
import com.yin.utils.FileUtils;
import com.yin.utils.excel.ExcelListener.DataListener;
import com.yin.utils.excel.ExcelListener.MonthSheetWriteHandler;
import com.yin.utils.excel.params.ExcelParams;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jetbrains.annotations.NotNull;
import org.springframework.util.StringUtils;

import java.io.*;
import java.lang.reflect.Field;
import java.util.*;

/**
 * 操作excel
 */
@Slf4j
public class ExcelUtils<T> {
    @Setter
    private Class<T> dataClass;
    private static ExcelUtils excelUtils;

    public ExcelUtils() {
    }

    public ExcelUtils(Class<T> dataClass) {
        this.dataClass = dataClass;
    }

    public static <T> ExcelUtils getExcelUtils() {
        if (null == excelUtils) {
            excelUtils = new ExcelUtils<T>();
            return excelUtils;
        }
        return excelUtils;
    }

    // 添加静态工厂方法
    public static <T> ExcelUtils<T> of(Class<T> clazz) {
        return new ExcelUtils<>(clazz);
    }

    private List<T> excelList = ListUtils.newArrayList();

    /**
     * 存放csv写入的时候的路径
     */
    public final ArrayList<String> csvWritePath = new ArrayList<>();

    /**
     * 读取excel文件内容，默认读取是从excel的第2行开始读取
     *
     * @param path excel文件地址
     * @param data 实体类，需要new一下
     * @return 返回读取了全部数据的List集合
     */
    public List<T> readExcel(String path, T data) {
        EasyExcel.read(path, data.getClass(), new PageReadListener<T>(dataList -> {
            //循环获取读的数据
            for (T allData : dataList) {
                excelData(allData);
            }
        })).sheet().headRowNumber(2).doRead();
        return excelList;
    }

    /**
     * 读取excel文件内容，默认读取是从excel的第2行开始读取
     *
     * @param path          excel文件地址
     * @param headRowNumber 表头行数,从第几行开始读取
     * @return 返回读取了全部数据的List集合
     */
    public List<T> readExcel(String path, Integer headRowNumber) {
        //循环使用的时候，每一次都创建一个对象，避免使用一个对象导致数据重复
        List<T> excelList1 = ListUtils.newArrayList();
        //循环获取读的数据
        EasyExcel.read(path, dataClass, new PageReadListener<T>(excelList1::addAll))
                .sheet().headRowNumber(headRowNumber).doRead();
        return excelList1;
    }

    /**
     * 读取excel，如果出现空行不读取，如果空行下边还有数据同样不读取
     * @param path excel文件地址
     * @param headRowNumber 表头行数,从第几行开始读取
     * @return
     */
    public List<T> readExcelIsNotNull(String path,Integer headRowNumber){
       return EasyExcel.read(path,dataClass,new DataListener<T>()).sheet().headRowNumber(headRowNumber).doReadSync();
    }
    /**
     * 读取excel文件内容，默认读取是从excel的第2行开始读取
     *
     * @param path          excel文件地址
     * @param headRowNumber 表头行数,从第几行开始读取
     * @return 返回读取了全部数据的List集合
     */
    public List<T> readExcel(String path, Integer headRowNumber, ReadSheet readSheet) {
        //循环使用的时候，每一次都创建一个对象，避免使用一个对象导致数据重复
        List<T> excelList1 = ListUtils.newArrayList();
        //循环获取读的数据
        EasyExcel.read(path, dataClass, new PageReadListener<T>(excelList1::addAll))
                .sheet(readSheet.getSheetNo(), readSheet.getSheetName()).headRowNumber(headRowNumber).doRead();
        return excelList1;
    }
    /**
     * 读取excel，如果出现空行不读取，如果空行下边还有数据同样不读取
     *
     * @param path          excel文件地址
     * @param headRowNumber 表头行数,从第几行开始读取
     * @return 返回读取了全部数据的List集合
     */
    public List<T> readExcelIsNotNull(String path, Integer headRowNumber, ReadSheet readSheet) {
        //循环使用的时候，每一次都创建一个对象，避免使用一个对象导致数据重复
        List<T> excelList1 = ListUtils.newArrayList();
        //循环获取读的数据
        EasyExcel.read(path,dataClass,new DataListener<T>(excelList1::addAll)).sheet(readSheet.getSheetNo(),readSheet.getSheetName()).headRowNumber(headRowNumber).doRead();
        return excelList1;
    }

    /**
     * @param path          excel文件地址
     * @param headRowNumber 表头行数,从第几行开始读取
     * @param notSheetName  不需要查询的sheet表单名称
     * @return map集合，key为表单名称，value为表单数据
     */
    public HashMap<String, List<T>> readeExcelSheetAll(String path, Integer headRowNumber, String... notSheetName) {
        HashMap<String, List<T>> excelMap = new HashMap<>();
        ExcelReader build = EasyExcel.read(path).build();
        ExcelReadExecutor excelReadExecutor = build.excelExecutor();
        List<ReadSheet> readSheets = excelReadExecutor.sheetList();
        log.info("表格数量:{}",readSheets.size());
        for (ReadSheet readSheet : readSheets) {
            if (notSheetName.length >0 && Arrays.asList(notSheetName).contains(readSheet.getSheetName())) {
                continue;
            }
            List<T> ts = readExcel(path, headRowNumber, readSheet);
            excelMap.put(readSheet.getSheetName(), ts);
        }
        build.finish();
        return excelMap;
    }
    /**
     * @param path          excel文件地址
     * @param headRowNumber 表头行数,从第几行开始读取
     * @param notSheetName  不需要查询的sheet表单名称
     * @return map集合，key为表单名称，value为表单数据
     */
    public HashMap<String, List<T>> readeExcelSheetAllIsNotNull(String path, Integer headRowNumber, String... notSheetName) {
        HashMap<String, List<T>> excelMap = new HashMap<>();
        ExcelReader build = EasyExcel.read(path).build();
        ExcelReadExecutor excelReadExecutor = build.excelExecutor();
        List<ReadSheet> readSheets = excelReadExecutor.sheetList();
        log.info("表格数量:{}",readSheets.size());
        for (ReadSheet readSheet : readSheets) {
            if (notSheetName.length >0 && Arrays.asList(notSheetName).contains(readSheet.getSheetName())) {
                continue;
            }
            List<T> ts = readExcelIsNotNull(path, headRowNumber, readSheet);
            excelMap.put(readSheet.getSheetName(), ts);
        }
        build.finish();
        return excelMap;
    }

    /**
     * 读取excel文件内容，然后根据指定字段，当作key,根据key自动去重数据
     * 适合用于一条一条数据使用
     * @param path          excel文件地址
     * @param headRowNumber 表头行数,从第几行开始读取
     * @param fieldName     字段名称
     * @return map集合，key为字段名称拼接，value为excel数据
     */
    public HashMap<String, List<T>> readeExcelForFieldMap(String path, Integer headRowNumber, String... fieldName) {
        HashMap<String, List<T>> listHashMap = new HashMap<>();
        List<T> ts = readExcel(path, headRowNumber);
        if(ts.isEmpty()){
            log.info("数据读取为空!");
            return listHashMap;
        }
        StringBuilder key = new StringBuilder();
        for (T t : ts) {
            JSONObject listJsonObject = ConvertAllUtils.convertJson(t);
            if (listJsonObject == null) {
                log.info("读取到一行空数据!");
                continue;
            }
            //进行key值计算
            key.setLength(0);
            for (int i = 0; i < fieldName.length; i++) {
                String fileNameLoc = getFileNameLoc(fieldName[i]);
                if (fileNameLoc.isEmpty()) {
                    log.error("字段注解ExcelProperty为空");
                    continue;
                }
                String string = listJsonObject.get(fileNameLoc).toString();
                key.append(string);
                if (i < fieldName.length - 1) {
                    key.append("-");
                }
            }
            listHashMap.put(key.toString(), ts);
        }
        return listHashMap;
    }

    /**
     * 读取excel文件内容，默认读取是从excel的第2行开始读取
     *
     * @param path          excel文件地址
     * @param data          实体类，需要new一下
     * @param headRowNumber 表头行数,从第几行开始读取
     * @return 返回读取了全部数据的List集合
     */
    public List<T> readExcel(String path, T data, Integer headRowNumber) {
        //循环使用的时候，每一次都创建一个对象，避免使用一个对象导致数据重复
        List<T> excelList1 = ListUtils.newArrayList();
        //循环获取读的数据
        EasyExcel.read(path, data.getClass(), new PageReadListener<T>(excelList1::addAll))
                .sheet().headRowNumber(headRowNumber).doRead();
        return excelList1;
    }

    /**
     * 读取excel文件内容，默认读取是从excel的第2行开始读取
     *
     * @param path          excel文件地址
     * @param data          实体类，需要new一下
     * @param headRowNumber 表头行数,从第几行开始读取
     * @param sheetNumber   第几个表单
     * @return 返回读取了全部数据的List集合
     */
    public List<T> readExcel(String path, T data, Integer headRowNumber, Integer sheetNumber) {
        //循环使用的时候，每一次都创建一个对象，避免使用一个对象导致数据重复
        List<T> excelList1 = ListUtils.newArrayList();
        //循环获取读的数据
        EasyExcel.read(path, data.getClass(), new PageReadListener<T>(excelList1::addAll)).sheet(sheetNumber).headRowNumber(headRowNumber).doRead();
        return excelList1;
    }

    /**
     * 读取excel文件内容，然后获取文件的每列的标题信息
     *
     * @param path excel文件地址
     * @param data 实体类，需要new一下
     * @return 返回读取了全部数据的List集合
     */
    public List<String> readExcelGetTitle(String path, T data) {
        LinkedList<String> titleList = new LinkedList<>();
        EasyExcel.read(path, data.getClass(), new AnalysisEventListener() {
            @Override
            public void invoke(Object o, AnalysisContext analysisContext) {
//                log.info("解析到一个数据：{}",JSON.toJSONString(o));
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
//                log.info("解析的所有数据");
            }

            @Override
            public void invokeHeadMap(Map headMap, AnalysisContext context) {
                for (Object o : headMap.entrySet()) {
                    titleList.add(JSONObject.toJSONString(o));
                }
            }
        }).sheet().headRowNumber(2).doRead();
        //headRowNumber控制从第几行开始读取,然后invokeHeadMap获取标题信息
        return titleList;
    }

    /**
     * 读取excel文件内容，然后获取文件的每列的标题信息
     *
     * @param path          excel文件地址
     * @param data          实体类，需要new一下
     * @param headRowNumber 标题行 1 开始
     * @return 返回读取了全部数据的List集合
     */
    public List<String> readExcelGetTitle(String path, T data, Integer headRowNumber) {
        LinkedList<String> titleList = new LinkedList<>();
        EasyExcel.read(path, data.getClass(), new AnalysisEventListener() {
            @Override
            public void invoke(Object o, AnalysisContext analysisContext) {
//                log.info("解析到一个数据：{}",JSON.toJSONString(o));
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
//                log.info("解析的所有数据");
            }

            @Override
            public void invokeHeadMap(Map headMap, AnalysisContext context) {
                for (Object o : headMap.entrySet()) {
                    titleList.add(JSONObject.toJSONString(o));
                }
            }
        }).sheet().headRowNumber(headRowNumber).doRead();
        //headRowNumber控制从第几行开始读取,然后invokeHeadMap获取标题信息
        return titleList;
    }

    /**
     * 写入excel数据，使用了readExcel方法后，可以直接使用该方法直接写入
     *
     * @param path       写入的excel地址，可以直接写入，会自动创建
     * @param data       对应的实体类
     * @param excelTiele excel的文件大标题
     * @param lastCol    excel大标题合并的列数，从readExcelGetTitle获取
     */
    public void writeExcel(String path, T data, String sheet, String excelTiele, int lastCol) {
        ExcelParams.excelTiele = excelTiele;
        ExcelParams.lastCol = lastCol;
        if (!StringUtils.hasText(sheet)) {
            sheet = "sheet";
        }
        EasyExcel.write(path, data.getClass())
                .useDefaultStyle(false) //取消写入的时候标题的样式
                .sheet(sheet)
                .registerWriteHandler(new MonthSheetWriteHandler())
                .relativeHeadRowIndex(1) //如果设置大标题必须设置为1,设置第几行设置字段标题
                .doWrite(() -> {
                    // 分页查询数据
                    return excelList;
                });
        System.out.println("写入完毕");
    }

    /**
     * 写入excel数据
     *
     * @param path       写入的excel地址，可以直接收入，会自动创建
     * @param data       对应的实体类
     * @param excelTiele excel的文件大标题
     * @param lastCol    excel大标题合并的列数，从readExcelGetTitle获取
     * @param list       excel写入文件的数据，从readExcel获取，可以对获取的数据进行修改之后再写入
     */
    public void writeExcel(String path, T data, String sheet, String excelTiele, int lastCol, List<T> list) {
        ExcelParams.excelTiele = excelTiele;
        ExcelParams.lastCol = lastCol;
        if (!StringUtils.hasText(sheet)) {
            sheet = "sheet";
        }
        EasyExcel.write(path, data.getClass())
                .useDefaultStyle(false) //取消写入的时候标题的样式
                .sheet(sheet)
                .registerWriteHandler(new MonthSheetWriteHandler())
                .relativeHeadRowIndex(1) //如果设置大标题必须设置为1,设置第几行设置字段标题
                .doWrite(() -> {
                    // 分页查询数据
                    return list;
                });
//        System.out.println(JSON.toJSONString(list));
        System.out.println("写入完毕");
    }

    /**
     * 写入excel数据
     *
     * @param path 写入的excel地址，可以直接收入，会自动创建
     * @param data 对应的实体类
     * @param list excel写入文件的数据，从readExcel获取，可以对获取的数据进行修改之后再写入
     */
    public void writeExcel(String path, T data, List<T> list) {
        EasyExcel.write(path, data.getClass())
                .useDefaultStyle(false) //取消写入的时候标题的样式
                .sheet("sheet")
//                .registerWriteHandler(new MonthSheetWriteHandler())
//                .relativeHeadRowIndex(1) //如果设置大标题必须设置为1,设置第几行设置字段标题
                .doWrite(() -> {
                    // 分页查询数据
                    return list;
                });
//        System.out.println(JSON.toJSONString(list));
//        System.out.println("写入完毕");
    }

    /**
     * 写入excel数据 ,不手动传入实体类
     *
     * @param path 写入的excel地址，可以直接收入，会自动创建
     * @param list excel写入文件的数据，从readExcel获取，可以对获取的数据进行修改之后再写入
     */
    public void writeExcel(String path, List<T> list) {
        EasyExcel.write(path, dataClass)
                .useDefaultStyle(false) //取消写入的时候标题的样式
                .sheet("sheet")
//                .registerWriteHandler(new MonthSheetWriteHandler())
//                .relativeHeadRowIndex(1) //如果设置大标题必须设置为1,设置第几行设置字段标题
                .doWrite(() -> {
                    // 分页查询数据
                    return list;
                });
//        System.out.println(JSON.toJSONString(list));
//        System.out.println("写入完毕");
    }

    /**
     * 写入excel数据 ,不手动传入实体类 ,跟buildExcelWriter联合使用
     *
     * @param path      写入的excel地址，可以直接收入，会自动创建
     * @param list      excel写入文件的数据，从readExcel获取，可以对获取的数据进行修改之后再写入
     * @param sheetName sheetName 表格名称
     */
    public void writeExcelSheet(String path, List<T> list, ExcelWriter writer, int index, String sheetName) {
        WriteSheet sheet = EasyExcel.writerSheet(index, sheetName).build();
        writer.write(list, sheet);
    }

    /**
     * 写入excel数据 ,不手动传入实体类
     *
     * @param path        写入的excel地址，可以直接收入，会自动创建
     * @param list        excel写入文件的数据，从readExcel获取，可以对获取的数据进行修改之后再写入
     * @param sheetPrefix sheetName 表格名称前缀，会自动跟表格下标
     */
    public void writeExcelSheetForPrefix(String path, List<List<T>> list, String sheetPrefix) {
        ExcelWriter writer = EasyExcel.write(path, dataClass).useDefaultStyle(false).build();
        for (int i = 0; i < list.size(); i++) {
            List<T> ts = list.get(i);
            String sheetName = sheetPrefix + i;
            WriteSheet sheet = EasyExcel.writerSheet(i, sheetName).build();
            writer.write(ts, sheet);
        }
        writer.finish();
    }

    /**
     * 写入excel数据 ,不手动传入实体类
     *
     * @param path      写入的excel地址，可以直接收入，会自动创建
     * @param list      excel写入文件的数据，从readExcel获取，可以对获取的数据进行修改之后再写入
     * @param fieldName sheetName表格名称前缀，会自动跟表格下标,输入字段名称，根据输入字段的第一行数据获取
     */
    public void writeExcelSheetForFieldName(String path, List<List<T>> list, String fieldName) {
        ExcelWriter writer = EasyExcel.write(path, dataClass).useDefaultStyle(false).build();
        String fileNameLoc = getFileNameLoc(fieldName);
        for (int i = 0; i < list.size(); i++) {
            List<T> ts = list.get(i);
            JSONObject jsonObject = ConvertAllUtils.convertJson(ts.get(0));
            String string = jsonObject.get(fileNameLoc).toString();
            if (!StringUtils.hasText(string)) {
                return;
            }
            String sheetName = string + "-" + i;
            WriteSheet sheet = EasyExcel.writerSheet(i, sheetName).build();
            writer.write(ts, sheet);
        }
        writer.finish();
    }

    /**
     * 根据传入的字段名称，字段上边的注解名称，获取字段自己的名称，从而动态获取数据
     *
     * @param fieldName 字段名称
     * @return 字段自己的名称
     */
    private @NotNull String getFileNameLoc(String fieldName) {
        Field[] fields = dataClass.getDeclaredFields();
        for (Field field : fields) {
            ExcelProperty annotation = field.getAnnotation(ExcelProperty.class);
            if (annotation != null) {
                String[] s = annotation.value();
                if (Arrays.asList(s).contains(fieldName)) {
                    return field.getName().replaceAll("\\[", "").replaceAll("]", "");
                }
            }
        }
        return "";
    }


    /**
     * 构建excel文件对象
     *
     * @param path
     * @return
     */
    public ExcelWriter buildExcelWriter(String path) {
        return EasyExcel.write(path, dataClass).useDefaultStyle(false).build();
    }

    /**
     * 写入excel数据 ,不手动传入实体类
     *
     * @param path  写入的excel地址，可以直接收入，会自动创建
     * @param limit 分割
     */
    public void writeExcel(String path, int limit) {
        List<T> ts = readExcel(path, 1);
        String filePath = FileUtils.getFilePath(path);
        String fileName = FileUtils.getFileName(path);
        String fileSuffix = FileUtils.getFileSuffix(path);
//        分割集合
        List<List<T>> partition = org.apache.commons.collections4.ListUtils.partition(ts, limit);
        for (int i = 0; i < partition.size(); i++) {
            String saveFile = filePath + File.separator + fileName + "-分割" + i + "." + fileSuffix;
            int finalI = i;
            EasyExcel.write(saveFile, dataClass)
                    .useDefaultStyle(false) //取消写入的时候标题的样式
                    .sheet("sheet")
//                .registerWriteHandler(new MonthSheetWriteHandler())
//                .relativeHeadRowIndex(1) //如果设置大标题必须设置为1,设置第几行设置字段标题
                    .doWrite(() -> {
                        // 分页查询数据
                        return partition.get(finalI);
                    });
        }

//        System.out.println(JSON.toJSONString(list));
//        System.out.println("写入完毕");
    }

    /**
     * 写入csv
     *
     * @param path          exce文件路径
     * @param data          实体类
     * @param headRowNumber 读取数据行
     * @param savePath      保存路径
     * @throws IOException
     */
    public void writeCsv(String path, T data, int headRowNumber, String savePath) throws IOException {
        List<String> strings = getExcelTitle(path);
        List<T> ts = readExcel(path, data, headRowNumber);
        String csvTitle = getCsvTitle(strings);
        writeCsvSave(savePath, csvTitle, ts);
    }

    /**
     * 写入csv 默认跟写入文件同名
     *
     * @param path          exce文件路径
     * @param data          实体类
     * @param headRowNumber 读取数据行
     * @throws IOException
     */
    public void writeCsv(String path, T data, int headRowNumber) throws IOException {
        List<String> strings = getExcelTitle(path);
        List<T> ts = readExcel(path, data, headRowNumber);
        String csvTitle = getCsvTitle(strings);
        String savePath = FileUtils.getFilePath(path) + File.separator + FileUtils.getFileName(path) + ".csv";
        writeCsvSave(savePath, csvTitle, ts);
    }

    /**
     * 写入csv 默认跟写入文件同名
     *
     * @param path          exce文件路径
     * @param data          实体类
     * @param headRowNumber 读取数据行
     * @param savePath      保存路径,不需要写后缀，值需要写文件名称 ,默认为excel相同路径下
     * @param limit         数量多少分割为一个分解
     * @throws IOException
     */
    public void writeCsv(String path, T data, int headRowNumber, String savePath, int limit) throws IOException {
        String filePath = FileUtils.getFilePath(path);
        List<String> strings = getExcelTitle(path);
        List<T> ts = readExcel(path, data, headRowNumber);
        List<List<T>> partition = org.apache.commons.collections4.ListUtils.partition(ts, limit);
        String csvTitle = getCsvTitle(strings);
        for (List<T> tList : partition) {
            String newSavePath = filePath + File.separator + savePath + "-分割" + partition.indexOf(tList) + ".csv";
            writeCsvSave(newSavePath, csvTitle, tList);
        }
    }

    /**
     * 写入csv 默认跟写入文件同名
     *
     * @param path          exce文件路径
     * @param headRowNumber 读取数据行
     * @param savePath      保存路径,不需要写后缀，值需要写文件名称 ,默认为excel相同路径下
     * @param limit         数量多少分割为一个分解
     * @throws IOException
     */
    public void writeCsv(String path, int headRowNumber, String savePath, int limit) throws IOException {
        String filePath = FileUtils.getFilePath(path);
        List<String> strings = getExcelTitle(path);
        List<T> ts = readExcel(path, headRowNumber);
        List<List<T>> partition = org.apache.commons.collections4.ListUtils.partition(ts, limit);
        String csvTitle = getCsvTitle(strings);
        for (List<T> tList : partition) {
            String newSavePath = filePath + File.separator + savePath + "-分割" + partition.indexOf(tList) + ".csv";
            writeCsvSave(newSavePath, csvTitle, tList);
        }
    }

    /**
     * 写入csv
     *
     * @param path          exce文件路径
     * @param data          实体类
     * @param headRowNumber 读取数据行
     * @param limit         数量多少分割为一个分解
     * @throws IOException
     */
    public void writeCsv(String path, T data, int headRowNumber, int limit) throws IOException {
        String filePath = FileUtils.getFilePath(path);
        List<String> strings = getExcelTitle(path);
        List<T> ts = readExcel(path, data, headRowNumber);
        List<List<T>> partition = org.apache.commons.collections4.ListUtils.partition(ts, limit);
        String csvTitle = getCsvTitle(strings);
        String savePath = FileUtils.getFilePath(path) + File.separator + FileUtils.getFileName(path) + ".csv";
        for (List<T> tList : partition) {
            String newSavePath = filePath + File.separator + savePath + "-分割" + partition.indexOf(tList) + ".csv";
            writeCsvSave(newSavePath, csvTitle, tList);
        }
    }

    /**
     * 写入csv
     *
     * @param path          exce文件路径
     * @param headRowNumber 读取数据行
     * @param limit         数量多少分割为一个分解
     * @throws IOException
     */
    public void writeCsv(String path, int headRowNumber, int limit) throws IOException {
        List<String> strings = getExcelTitle(path);
        List<T> ts = readExcel(path, headRowNumber);
        List<List<T>> partition = org.apache.commons.collections4.ListUtils.partition(ts, limit);
        String csvTitle = getCsvTitle(strings);
        String savePath = FileUtils.getFilePath(path) + File.separator + FileUtils.getFileName(path) + ".csv";
        for (List<T> tList : partition) {
            String newSavePath = savePath + "-分割" + partition.indexOf(tList) + ".csv";
            writeCsvSave(newSavePath, csvTitle, tList);
        }
    }


    /**
     * 写入csv
     *
     * @param savePath 保存路径
     * @param tit      csv标题 getCsvTitle
     * @param onePojos csv数据
     */
    public void writeCsvSave(String savePath, String tit, List<T> onePojos) {
        //写入csv文件
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(savePath), "GB2312"))
        ) {
//            写表头
            bw.write(tit);
            bw.newLine();
//            写数据
            for (T onePojo : onePojos) {
                String csvInfo = getCsvInfo(onePojo);
                bw.write(csvInfo);
                bw.newLine();
            }
        } catch (IOException | NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        csvWritePath.add(savePath);
        log.info("csv导出成功:{}", savePath);
    }

    /**
     * csv转换excel
     *
     * @param csvPath  csv的路径
     * @param savePath 保存的excel的路径
     */
    public void csvToExcel(String csvPath, String savePath) {
        try (CSVReader csvReader = new CSVReader(new InputStreamReader(new FileInputStream(csvPath), "GB2312")); Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("data");
            String[] nextLine;
            int rowNum = 0;
            while ((nextLine = csvReader.readNext()) != null) {
                Row row = sheet.createRow(rowNum++);
                for (int i = 0; i < nextLine.length; i++) {
                    Cell cell = row.createCell(i);
                    String value = nextLine[i];
                    cell.setCellValue(value);
                }
            }
            try (FileOutputStream fos = new FileOutputStream(savePath)) {
                workbook.write(fos);
            }
//            System.out.println("转换成功:"+savePath);
        } catch (IOException | CsvValidationException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 把整个文件夹中的excel汇总成一个，需要是同一个数据字段的 只处理xlsx
     *
     * @param path
     * @param savePath
     */
    public void excelToAll(String path, String savePath, T data) throws YException {
        if (FileUtils.isFile(path)) {
            throw new YException(path + "该路径不是目录,无法合并");
        }
        if (savePath == null) {
            throw new NullPointerException("savePath汇总文件为空");
        }
        File sourcePath = new File(path);
        File[] sourcePaths = sourcePath.listFiles();
        assert sourcePaths != null;
        ArrayList<T> allData = new ArrayList<>();
        for (File excelPath : sourcePaths) {
//            equalsIgnoreCase 忽略大小写比较
            String fileSuffix = FileUtils.getFileSuffix(excelPath.getAbsolutePath());
            if (excelPath.isFile() && fileSuffix.equalsIgnoreCase("xlsx")) {
                System.out.println(excelPath.getAbsolutePath());
                List<T> ts = readExcel(excelPath.getAbsolutePath(), data, 1);
                allData.addAll(ts);
            }
        }
        writeExcel(savePath, data, allData);
    }

    /**
     * 封装要写入的数据
     *
     * @return
     */
    private List<T> excelData(T classData) {
        excelList.add(classData);
//        log.info("数据已读取,已写入集合:{}", JSON.toJSONString(classData));
        return excelList;
    }

    /**
     * 封装要写入的数据
     *
     * @return
     */
    private List<T> excelData2(T classData, List<T> excelList) {
        excelList.add(classData);
//        log.info("数据已读取,已写入集合:{}", JSON.toJSONString(classData));
        return excelList;
    }

    /**
     * 使用代码打开excel文件
     *
     * @param path excel完整路径
     */
    public void OpenExcel(String path) throws IOException {
        Runtime.getRuntime().exec(String.format("cmd /c start %s", path));
    }

    /**
     * 根据地址获取Excel的标题
     *
     * @param path excel路径
     * @return
     * @throws IOException
     */
    public LinkedList<String> getExcelTitle(String path) throws IOException {
        LinkedList<String> titleList = new LinkedList<>();
        //获取文件后缀
        String suffix = FileUtils.getFileSuffix(path);
        if (suffix.equals(".xls") || (suffix.equals("xls"))) {
            FileInputStream fileInputStream = new FileInputStream(new File(path));
            HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);
            HSSFSheet sheetAt = workbook.getSheetAt(ExcelParams.sheetNum);
            HSSFRow row = sheetAt.getRow(ExcelParams.headRowNumber);
            for (Cell cell : row) {//列
                titleList.add(cell.getStringCellValue());
            }
            workbook.close();
            fileInputStream.close();
        } else if (suffix.equals(".xlsx") || (suffix.equals("xlsx"))) {
            XSSFWorkbook workbook = new XSSFWorkbook(path);
            XSSFSheet sheetAt = workbook.getSheetAt(ExcelParams.sheetNum);
            XSSFRow row = sheetAt.getRow(ExcelParams.headRowNumber);//行
            for (Cell cell : row) {//列
                titleList.add(cell.getStringCellValue());
            }
            workbook.close();
        }
        return titleList;
    }

    /**
     * 根据地址获取Excel的标题
     *
     * @param path excel路径
     * @return
     * @throws IOException
     */
    public LinkedList<String> getExcelTitle(String path, Integer sheet, Integer headRowNumber) throws IOException {
        LinkedList<String> titleList = new LinkedList<>();
        XSSFWorkbook workbook = new XSSFWorkbook(path);
        XSSFSheet sheetAt = workbook.getSheetAt(sheet);
        XSSFRow row = sheetAt.getRow(headRowNumber);//行
        for (Cell cell : row) {//列
            titleList.add(cell.getStringCellValue());
        }
        workbook.close();
        return titleList;
    }

    /**
     * 根据实体类，获取实体类对应的数据，然后组成每一列的csv格式数据
     *
     * @param o 传入的数据类
     * @return
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public String getCsvInfo(Object o) throws NoSuchFieldException, IllegalAccessException {
        StringBuilder str = new StringBuilder();
        T o1 = (T) o;
        Class<?> aClass = o1.getClass();
        for (Field field : aClass.getDeclaredFields()) {
            Field declaredField = aClass.getDeclaredField(field.getName());
            // 允许访问私有字段
            declaredField.setAccessible(true);
            Object o2 = declaredField.get(o);
            if (o2 != null) {
                str.append(o2).append(",");
            } else {
                str.append(" ,");
            }
        }
        return str.toString();
    }

    /**
     * 生成csv的标题
     *
     * @param tit 标题
     * @return
     */
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

    /**
     * 根据Json来获取字段，然后生产实体嘞
     *
     * @param jsonObject
     * @return
     */
    public LinkedList<String> getJsonTitle(JSONObject jsonObject) {
        LinkedList<String> titleList = new LinkedList<>();
        jsonObject.forEach((k, v) -> {
            titleList.add(k);
        });
        return titleList;
    }

    /**
     * 根据Json来获取字段，然后生产实体嘞
     *
     * @param jsonObject
     * @return
     */
    public LinkedList<String> getJsonTitle(cn.hutool.json.JSONObject jsonObject) {
        LinkedList<String> titleList = new LinkedList<>();
        jsonObject.forEach((k, v) -> {
            titleList.add(k);
        });
        return titleList;
    }

    /*
    *  ExcelUtils excelUtils = ExcelUtils.getExcelUtils();
        String path = "D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\2024-09-19\\202401-08DRGs预测分组0912-分析.xlsx";
        String pojoPath = "D:\\cs\\ssxjg\\myDemo\\src\\main\\java\\com\\yin\\pojo\\qt";
        LinkedList excelTitle = excelUtils.getExcelTitle(path);
        excelUtils.generateClass("CostHzMainExportPojo2", excelTitle, pojoPath);
    * */

    /**
     * 根据传入的excel生成实体类
     *
     * @param className 实体类名
     * @param classPath 实体类地址
     * @param path      excel 地址
     */
    public void generateClassEnhanceZ(String className, String classPath, String path) throws IOException {
        LinkedList<String> excelTitle = getExcelTitle(path);
//        System.out.println(excelTitle);
        generateClassZ(className, excelTitle, classPath);
    }

    /**
     * 根据传入的excel生成实体类
     *
     * @param className 实体类名
     * @param classPath 实体类地址
     * @param path      excel 地址
     */
    public void generateClassEnhance(String className, String classPath, String path) throws IOException {
        LinkedList<String> excelTitle = getExcelTitle(path);
        generateClass(className, excelTitle, classPath);
    }


    /**
     * 传入标题集合生成实体类
     *
     * @param className 实体类名
     * @param titleList 属性集合
     * @param classPath 实体类地址
     */
    public void generateClassZ(String className, LinkedList<String> titleList, String classPath) throws IOException {
        StringBuilder entityClass = new StringBuilder();
        /**
         * import com.alibaba.excel.annotation.ExcelProperty;
         * import lombok.Data;
         */
        FileUtils.createFile(classPath);
        String packAge = getPackAge(classPath);
        entityClass.append("package ").append(packAge).append(";").append("\n");//包路径添加
        entityClass.append("import com.alibaba.excel.annotation.ExcelProperty;\n");//包路径添加
        entityClass.append("import lombok.Data;\n");//包路径添加
        entityClass.append("\n");
        entityClass.append("@Data\n");//lombok 注解添加
        entityClass.append(String.format("public class %s {\n", className));
        titleList.stream().forEach(tiele -> {
            /**
             * \\s 匹配任何空白字符，包括空格、制表符、换页符等。
             * + 表示匹配一次或多次。
             */
            String fieldName = tiele.replaceAll("\\s+", ""); //去除所有空格
            //暂时只处理英文下的()
            String newFileName = fieldName.indexOf("(") > 0 ? fieldName.substring(0, fieldName.indexOf("(")) : fieldName;
            String fieldNamePinyin = PinyinUtil.getFirstLetter(newFileName, "");//使用hutool获取首字符拼音
            String fieldType = "String"; //默认为String类型
            //追加属性
            entityClass.append(String.format("\t@ExcelProperty(\"%s\")\n", fieldName));
            entityClass.append("\tprivate ").append(fieldType).append(" ").append(fieldNamePinyin).append(";\n");
        });
        entityClass.append("}");
        FileWriter fileWriter = new FileWriter(classPath + "\\" + className + ".java");
        fileWriter.write(entityClass.toString());
        fileWriter.close();
        System.out.println("实体类生成成功");
    }

    /**
     * 传入标题集合生成实体类
     *
     * @param className 实体类名
     * @param titleList 属性集合
     * @param classPath 实体类地址
     */
    public void generateClass(String className, LinkedList<String> titleList, String classPath) throws IOException {
        StringBuilder entityClass = new StringBuilder();
        /**
         * import com.alibaba.excel.annotation.ExcelProperty;
         * import lombok.Data;
         */
        String packAge = getPackAge(classPath);
        entityClass.append("package ").append(packAge).append(";").append("\n");//包路径添加
        entityClass.append("import com.alibaba.excel.annotation.ExcelProperty;\n");//包路径添加
        entityClass.append("import lombok.Data;\n");//包路径添加
        entityClass.append("\n");
        entityClass.append("@Data\n");//lombok 注解添加
        entityClass.append(String.format("public class %s {\n", className));
        titleList.stream().forEach(tiele -> {
            /**
             * \\s 匹配任何空白字符，包括空格、制表符、换页符等。
             * + 表示匹配一次或多次。
             */
            String fieldName = tiele.replaceAll("\\s+", ""); //去除所有空格
            //暂时只处理英文下的()
            String newFileName = fieldName.indexOf("(") > 0 ? fieldName.substring(0, fieldName.indexOf("(")) : fieldName;
            String fieldNamePinyin = newFileName;//使用hutool获取首字符拼音
            String fieldType = "String"; //默认为String类型
            //追加属性
            entityClass.append(String.format("\t@ExcelProperty(\"%s\")\n", fieldName));
            entityClass.append("\tprivate ").append(fieldType).append(" ").append(fieldNamePinyin).append(";\n");
        });
        entityClass.append("}");
        FileWriter fileWriter = new FileWriter(classPath + "\\" + className + ".java");
        fileWriter.write(entityClass.toString());
        fileWriter.close();
        System.out.println("实体类生成成功");
    }


    /**
     * 生成包路径
     *
     * @param classPath
     * @return
     */
    public String getPackAge(String classPath) {
        return classPath.substring(classPath.indexOf("java") + 5).replaceAll("\\\\", ".");
    }


}
